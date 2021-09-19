package JDBC.controller.handler;

import JDBC.controller.BaseRepositoryController;
import JDBC.model.Projects;

import java.util.Scanner;

public class ProjectsChoice extends UserChoiceHandler {

    public ProjectsChoice(UserChoiceHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Scanner scanner) {
        new BaseRepositoryController<>(Projects.class,scanner).general();
    }

    @Override
    protected boolean isApplicable(String choice) {
        return "4".equals(choice);
    }
}
