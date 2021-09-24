package JDBC.controller.handler;

import JDBC.controller.BaseControllerImpl;
import JDBC.model.Projects;

import java.util.Scanner;

public class ProjectsChoice extends UserChoiceHandler {

    public ProjectsChoice(UserChoiceHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Scanner scanner) {

        String simpleName = Projects.class.getSimpleName();

        if(!controllers.containsKey(simpleName)) {
            controllers.put(simpleName,new BaseControllerImpl<>(Projects.class, scanner));
        }
        controllers.get(simpleName).general();
    }

    @Override
    protected boolean isApplicable(String choice) {
        return "4".equals(choice);
    }
}
