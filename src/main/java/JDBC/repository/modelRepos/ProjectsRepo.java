package JDBC.repository.modelRepos;

import JDBC.model.Projects;

public class ProjectsRepo extends BaseStatements<Projects> {
    public ProjectsRepo() {
        super(Projects.class);
    }
}