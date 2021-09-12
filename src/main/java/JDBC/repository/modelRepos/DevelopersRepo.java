package JDBC.repository.modelRepos;

import JDBC.model.Developers;

public class DevelopersRepo extends BaseStatements<Developers> {
    public DevelopersRepo() {
        super(Developers.class);
    }
}