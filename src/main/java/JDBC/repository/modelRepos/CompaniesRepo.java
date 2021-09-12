package JDBC.repository.modelRepos;

import JDBC.model.Companies;

public class CompaniesRepo extends BaseStatements<Companies> {
    public CompaniesRepo() {
        super(Companies.class);
    }
}