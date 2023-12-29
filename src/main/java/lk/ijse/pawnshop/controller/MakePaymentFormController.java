package lk.ijse.pawnshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import lk.ijse.pawnshop.bo.BOFactory;
import lk.ijse.pawnshop.bo.custom.PaymentDetailsBO;
import lk.ijse.pawnshop.dao.custom.PaymentDetailsDAO;
import lk.ijse.pawnshop.dao.custom.QueryDAO;
import lk.ijse.pawnshop.dao.custom.impl.QueryDAOImpl;
import lk.ijse.pawnshop.db.DbConnection;
import lk.ijse.pawnshop.dao.custom.impl.PaymentDetailsDAOImpl;
import lk.ijse.pawnshop.util.GmailSender;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MakePaymentFormController {

    public TextField txtPaymentId;
    public ComboBox cmbInstallmentId;
    public TextField txtAmount;
    public DatePicker txtPaymentDate;
    public Button btnMakePayment;
    public TextField txtTotalLoan;
    public TextField txtDueLoan;
    private InstallmentFormController installmentFormController;
    QueryDAO queryDAO = new QueryDAOImpl();
    //PaymentDetailsDAO paymentDetailsDAO = new PaymentDetailsDAOImpl();
    PaymentDetailsBO paymentDetailsBO = (PaymentDetailsBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.PAYMENTDETAILS);



    public void initialize(){
        txtPaymentDate.setEditable(false);
        txtPaymentDate.setValue(java.time.LocalDate.now());
    }

    public void setParentController(InstallmentFormController installmentFormController){
        this.installmentFormController = installmentFormController;
    }


    public void txtPaymentIdOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentId.getText();

        if (!paymentId.isEmpty()) {
            try {
                double grantedLoanAmount = queryDAO.getGrantedLoanAmount(paymentId);

                double dueLoanAmount = queryDAO.getDueLoanAmount(paymentId);

                if (grantedLoanAmount >= 0 && dueLoanAmount >= 0) {
                    txtTotalLoan.setText(String.valueOf(grantedLoanAmount));
                    txtDueLoan.setText(String.valueOf(dueLoanAmount));
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid loan amounts").show();
                }

                List<String> nonPaidInstallmentIds = paymentDetailsBO.getNonPaidInstallmentIds(paymentId);

                if (!nonPaidInstallmentIds.isEmpty()) {
                    ObservableList<String> observableList = FXCollections.observableArrayList(nonPaidInstallmentIds);
                    cmbInstallmentId.setItems(observableList);
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "No non-paid installments found").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "An error occurred while fetching loan amounts").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Please enter a payment ID").show();
        }
    }

    public void cmbInstallmentIdOnAction(ActionEvent actionEvent) {
        String selectedInstallmentId = (String) cmbInstallmentId.getValue();

        if (selectedInstallmentId != null) {
            try {
                double monthlyInstallmentAmount = paymentDetailsBO.getMonthlyInstallmentAmount(selectedInstallmentId);
                txtAmount.setText(String.valueOf(monthlyInstallmentAmount));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Please select an installment ID").show();
        }
    }

    public void btnMakePaymentOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentId.getText();
        String selectedInstallmentId = (String) cmbInstallmentId.getValue();

        if (paymentId.isEmpty() || selectedInstallmentId == null) {
            new Alert(Alert.AlertType.INFORMATION, "Please enter a payment ID and select an installment ID").show();
            return;
        }

        try {
            boolean isPaymentUpdated = paymentDetailsBO.updatePaymentStatus(paymentId, selectedInstallmentId);

            if (isPaymentUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Payment made successfully").show();
                printReport();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update payment").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating payment").show();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void printReport() throws JRException {
        String PaymentId = txtPaymentId.getText();
        String customerEmail = queryDAO.getCustomerEmail(PaymentId);//PaymentDetailsDAOImpl.getCustomerEmail(PaymentId);

        InputStream resourceAsStream = getClass().getResourceAsStream("/report/Bill.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JRDesignQuery jrDesignQuery = new JRDesignQuery();
        jrDesignQuery.setText("SELECT\n" +
                "    c.customer_id,\n" +
                "    c.name AS customer_name,\n" +
                "    (SELECT COALESCE(SUM(i.amount), 0) FROM installments i WHERE i.payment_id = pd.payment_id) AS total_loan_amount,\n" +
                "    (SELECT COALESCE(SUM(i.amount), 0) FROM installments i WHERE i.payment_id = pd.payment_id AND i.paymentStatus = 'NON-PAID') AS due_loan_amount,\n" +
                "    (SELECT i.installment_id FROM installments i WHERE i.payment_id = pd.payment_id AND i.paymentStatus = 'PAID' ORDER BY i.installment_id DESC LIMIT 1) AS newly_paid_installment_id,\n" +
                "    (SELECT COALESCE(SUM(i.amount), 0) FROM installments i WHERE i.payment_id = pd.payment_id AND i.installment_id = newly_paid_installment_id) AS amount_from_new_installment,\n" +
                "    (SELECT COALESCE(SUM(i.amount), 0) FROM installments i WHERE i.payment_id = pd.payment_id AND i.paymentStatus = 'NON-PAID' AND i.installment_id > newly_paid_installment_id) AS remaining_balance,\n" +
                "    (SELECT COUNT(*) FROM installments i WHERE i.payment_id = pd.payment_id AND i.paymentStatus = 'NON-PAID') AS remaining_installments\n" +
                "FROM\n" +
                "    paymentDetails pd\n" +
                "JOIN\n" +
                "    inventory inv ON pd.inventory_id = inv.inventory_id\n" +
                "JOIN\n" +
                "    customer c ON inv.customer_id = c.customer_id\n" +
                "WHERE\n" +
                "    pd.payment_id = '" + PaymentId + "'");
        load.setQuery(jrDesignQuery);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        try{
            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            compileReport,
                            null,
                            DbConnection.getInstance().getConnection()
                    );

            LocalDateTime currentDateTime = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

            String formattedDateTime = currentDateTime.format(formatter);

            String fileNamePrefix = "myFile_";

            String fileName = fileNamePrefix + formattedDateTime + ".pdf";
            String outputFile = "/home/hp/Documents/MyReports/" + fileName; //want to add my path
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
            JasperViewer.viewReport(jasperPrint, false);
            new Thread(){
                public void run(){
                    new GmailSender().sendEmailWithAttachment(customerEmail,"Your Payment Successful","Your Monthly Installment Payment is Successful ! \n Thank You.",outputFile);
                }
            }.start();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

}
