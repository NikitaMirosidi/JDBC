package JDBC.controller.handler;

import JDBC.controller.BaseControllerImpl;
import JDBC.model.Companies;
import JDBC.model.Customers;
import JDBC.model.Developers;

import java.util.Scanner;

public class DevelopersChoice extends UserChoiceHandler {

    public DevelopersChoice(UserChoiceHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Scanner scanner) {

        String simpleName = Developers.class.getSimpleName();

        if(!controllers.containsKey(simpleName)) {
            controllers.put(simpleName,new BaseControllerImpl<>(Developers.class, scanner));
            controllers.get(simpleName).general();
        }
        else {
            controllers.get(simpleName).general();
        }
    }

    @Override
    protected boolean isApplicable(String choice) {
        return "3".equals(choice);
    }
}