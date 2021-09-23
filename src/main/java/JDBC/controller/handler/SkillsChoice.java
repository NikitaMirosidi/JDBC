package JDBC.controller.handler;

import JDBC.controller.BaseControllerImpl;
import JDBC.model.Projects;
import JDBC.model.Skills;

import java.util.Scanner;

public class SkillsChoice extends UserChoiceHandler {

    public SkillsChoice(UserChoiceHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Scanner scanner) {

        String simpleName = Skills.class.getSimpleName();

        if(!controllers.containsKey(simpleName)) {
            controllers.put(simpleName,new BaseControllerImpl<>(Skills.class, scanner));
            controllers.get(simpleName).general();
        }
        else {
            controllers.get(simpleName).general();
        }
    }

    @Override
    protected boolean isApplicable(String choice) {
        return "5".equals(choice);
    }
}