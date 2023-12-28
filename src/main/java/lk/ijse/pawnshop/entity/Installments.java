package lk.ijse.pawnshop.entity;

import java.time.LocalDate;

public class Installments {
    private String installmentId;
    private String paymentId;
    private double amount;
    private String paymentStatus;

    private LocalDate dateGranted;
    private LocalDate dueDate;

    public Installments() {
    }

    public Installments(String installmentId, String paymentId, double amount, String paymentStatus, LocalDate dateGranted, LocalDate dueDate) {
        this.installmentId = installmentId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.dateGranted = dateGranted;
        this.dueDate = dueDate;
    }

    public String getInstallmentId() {
        return installmentId;
    }

    public void setInstallmentId(String installmentId) {
        this.installmentId = installmentId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getDateGranted() {
        return dateGranted;
    }

    public void setDateGranted(LocalDate dateGranted) {
        this.dateGranted = dateGranted;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
