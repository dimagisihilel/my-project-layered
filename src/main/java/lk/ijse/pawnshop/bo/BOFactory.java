package lk.ijse.pawnshop.bo;

import lk.ijse.pawnshop.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public enum BOType{
        CUSTOMER, EMPLOYEE, INSTALLMENT, INVENTORY, LOGIN, PAYMENTDETAILS, USER
    }
    public SuperBO getBO(BOType boType){
        switch (boType) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case INSTALLMENT:
                return new InstallmentBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case PAYMENTDETAILS:
                return new PaymentDetailsBOImpl();
            case USER:
                return new UserBoImpl();
            default:
                return null;
        }
    }
}
