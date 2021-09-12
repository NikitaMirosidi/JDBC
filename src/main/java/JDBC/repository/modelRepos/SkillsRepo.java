package JDBC.repository.modelRepos;

import JDBC.model.Skills;

public class SkillsRepo extends BaseStatements<Skills> {
    public SkillsRepo() {
        super(Skills.class);
    }
}