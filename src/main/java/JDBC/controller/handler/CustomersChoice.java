package JDBC.controller.handler;

import JDBC.controller.BaseControllerImpl;
import JDBC.model.Companies;
import JDBC.model.Customers;

import java.util.Scanner;

public class CustomersChoice extends UserChoiceHandler {

    public CustomersChoice(UserChoiceHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Scanner scanner) {

        String simpleName = Customers.class.getSimpleName();

        if(!controllers.containsKey(simpleName)) {
            controllers.put(simpleName,new BaseControllerImpl<>(Customers.class, scanner));
        }
        controllers.get(simpleName).general();
    }

    @Override
    protected boolean isApplicable(String choice) {
        return "2".equals(choice);
    }
}