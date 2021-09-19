package JDBC.controller.handler;

import JDBC.controller.BaseRepositoryController;
import JDBC.model.Skills;

import java.util.Scanner;

public class SkillsChoice extends UserChoiceHandler {

    public SkillsChoice(UserChoiceHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Scanner scanner) {
        new BaseRepositoryController<>(Skills.class,scanner).general();
    }

    @Override
    protected boolean isApplicable(String choice) {
        return "5".equals(choice);
    }
}