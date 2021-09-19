package JDBC.controller.handler;

import JDBC.controller.BaseRepositoryController;
import JDBC.model.Customers;

import java.util.Scanner;

public class CustomersChoice extends UserChoiceHandler {

    public CustomersChoice(UserChoiceHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Scanner scanner) {
        new BaseRepositoryController<>(Customers.class,scanner).general();
    }

    @Override
    protected boolean isApplicable(String choice) {
        return "2".equals(choice);
    }
}