package JDBC.controller.handler;

import JDBC.controller.CustomRepositoryController;
import JDBC.repository.RepositoryCreator;

import java.util.Scanner;

public class CustomRepoChoice extends UserChoiceHandler {

    public CustomRepoChoice(UserChoiceHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Scanner scanner) {
        new CustomRepositoryController().general(RepositoryCreator.of(), scanner);
    }

    @Override
    protected boolean isApplicable(String choice) {
        return "6".equals(choice);
    }
}