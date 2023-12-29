package lk.ijse.pawnshop.dao;

import lk.ijse.pawnshop.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }
    public enum DAOTypes{
        CUSTOMER, EMPLOYEE, INSTALLMENT, INVENTORY, LOGIN, PAYMENTDETAILS, QUERY, USER
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case INSTALLMENT:
                return new InstallmentDAOImpl();
            case INVENTORY:
                return new InventoryDAOImpl();
            case LOGIN:
                return new LoginDAOImpl();
            case PAYMENTDETAILS:
                return new PaymentDetailsDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }











}
