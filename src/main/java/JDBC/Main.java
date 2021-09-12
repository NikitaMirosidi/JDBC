package JDBC;

import JDBC.model.*;
import JDBC.repository.CustomStatements;
import JDBC.repository.modelRepos.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {

        CompaniesRepo companiesRepo = new CompaniesRepo();
        BaseRepository<Customers> customersRepo = new CustomersRepo();
        BaseStatements<Developers> developersRepo = new DevelopersRepo();
        ProjectsRepo projectsRepo = new ProjectsRepo();
        SkillsRepo skillsRepo = new SkillsRepo();

        List<Companies> allCompanies = companiesRepo.getAll();
        System.out.println(allCompanies + "\n");

        List<Customers> allCustomers = customersRepo.getAll();
        System.out.println(allCustomers + "\n");

        List<Developers> allDevelopers = developersRepo.getAll();
        System.out.println(allDevelopers + "\n");

        List<Projects> allProjects = projectsRepo.getAll();
        System.out.println(allProjects + "\n");

        List<Skills> allSkills = skillsRepo.getAll();
        System.out.println(allSkills + "\n");

        /*System.out.println(customersRepo.getById(2).get());

        Customers marioMario = Customers.builder()
                .name("Mario Corp.")
                .ownerName("Mario Mario")
                .build();

        customersRepo.save(marioMario);

        Customers luigiMario = Customers.builder()
                .name("Luigi Corp.")
                .ownerName("Luigi Mario")
                .build();

        List<Customers> customers = new ArrayList<>();
        customers.add(marioMario);
        customers.add(luigiMario);

        customersRepo.saveAll(customers);

        Customers marioMarioUpd = Customers.builder()
                .id(19)
                .name("New Mario Corp.")
                .ownerName("Mario Mario Mario")
                .build();

        customersRepo.update(marioMarioUpd);

        customersRepo.deleteById(17);
        customersRepo.deleteById(18);

        CustomStatements customStatements = new CustomStatements();
        System.out.println(customStatements.getProjectsDevsSalary("J.A.R.V.I.S."));
        System.out.println(customStatements.getProjectsDevs("Pillow fart"));
        System.out.println(customStatements.getDevsByLanguage("Java"));
        System.out.println(customStatements.getDevsByLevel("Middle"));
        System.out.println(customStatements.getProjects());*/
    }
}