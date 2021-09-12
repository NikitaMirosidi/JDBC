package JDBC.repository.modelRepos;

import JDBC.model.Customers;

public class CustomersRepo extends BaseStatements<Customers> {
    public CustomersRepo() {
        super(Customers.class);
    }
}