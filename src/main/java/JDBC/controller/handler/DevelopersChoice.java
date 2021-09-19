package JDBC.controller.handler;

import JDBC.controller.BaseRepositoryController;
import JDBC.model.Developers;

import java.util.Scanner;

public class DevelopersChoice extends UserChoiceHandler {

    public DevelopersChoice(UserChoiceHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Scanner scanner) {
        new BaseRepositoryController<>(Developers.class,scanner).general();
    }

    @Override
    protected boolean isApplicable(String choice) {
        return "3".equals(choice);
    }
}