package JDBC.controller.handler;

import JDBC.controller.BaseControllerImpl;
import JDBC.model.Companies;

import java.util.Scanner;

public class CompaniesChoice extends UserChoiceHandler {

    public CompaniesChoice(UserChoiceHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Scanner scanner) {

        String simpleName = Companies.class.getSimpleName();

        if(!controllers.containsKey(simpleName)) {
            controllers.put(simpleName,new BaseControllerImpl<>(Companies.class, scanner));
        }
        controllers.get(simpleName).general();
    }

    @Override
    protected boolean isApplicable(String choice) {
        return "1".equals(choice);
    }
}