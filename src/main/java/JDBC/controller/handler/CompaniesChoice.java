package JDBC.controller.handler;

import JDBC.controller.BaseRepositoryController;
import JDBC.model.Companies;

import java.util.Scanner;

public class CompaniesChoice extends UserChoiceHandler {

    public CompaniesChoice(UserChoiceHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Scanner scanner) {
        new BaseRepositoryController<>(Companies.class, scanner).general();
    }

    @Override
    protected boolean isApplicable(String choice) {
        return "1".equals(choice);
    }
}