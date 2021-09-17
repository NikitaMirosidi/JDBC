package JDBC.repository;

import JDBC.config.DatabaseConnection;
import JDBC.model.Developers;
import JDBC.util.PropertiesLoader;
import lombok.SneakyThrows;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomRepository implements Closeable {

    private final Connection CONNECTION;
    private final String SCHEME_NAME;

    public CustomRepository() {
        this.CONNECTION = DatabaseConnection.getInstance().getConnection();
        this.SCHEME_NAME = PropertiesLoader.getProperty("schemeName");
    }

    @SneakyThrows
    public int getProjectsDevsSalary(String projectName) { //зарплата (сумма) всех разработчиков отдельного проекта

        String sql = "SELECT SUM(" + SCHEME_NAME + ".developers.salary) " +
                "FROM " + SCHEME_NAME + ".developers " +
                "WHERE " + SCHEME_NAME + ".developers.id IN (" +
                    "SELECT " + SCHEME_NAME + ".dev_and_projects.developer_id " +
                    "FROM " + SCHEME_NAME + ".dev_and_projects " +
                    "WHERE " + SCHEME_NAME + ".dev_and_projects.project_id = (" +
                        "SELECT " + SCHEME_NAME + ".projects.id " +
                        "FROM " + SCHEME_NAME + ".projects " +
                        "WHERE " + SCHEME_NAME + ".projects.name = '" + projectName + "'))";

        Statement statement = CONNECTION.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();

        return resultSet.getInt(1);
    }

    @SneakyThrows
    public List<Developers> getDevsByProject(String projectName) {//список разрабов определенного проекта

        String sql = "SELECT * " +
                "FROM " + SCHEME_NAME + ".developers " +
                "WHERE " + SCHEME_NAME + ".developers.id IN (" +
                    "SELECT " + SCHEME_NAME + ".dev_and_projects.developer_id " +
                    "FROM " + SCHEME_NAME + ".dev_and_projects " +
                    "WHERE " + SCHEME_NAME + ".dev_and_projects.project_id = (" +
                        "SELECT " + SCHEME_NAME + ".projects.id " +
                        "FROM " + SCHEME_NAME + ".projects " +
                        "WHERE " + SCHEME_NAME + ".projects.name = '" + projectName + "'))";

        Statement statement = CONNECTION.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<Developers> developers = new ArrayList<>();

        while (resultSet.next()) {
            developers.add(devCreator(resultSet));
        }

        return developers;
    }

    @SneakyThrows
    public List<Developers> getDevsByLanguage(String languageName) {//список всех разрабов определенного языка

        String sql = "SELECT * " +
                "FROM " + SCHEME_NAME + ".developers " +
                "WHERE " + SCHEME_NAME + ".developers.id IN (" +
                    "SELECT " + SCHEME_NAME + ".dev_and_skills.developer_id " +
                    "FROM " + SCHEME_NAME + ".dev_and_skills " +
                    "WHERE " + SCHEME_NAME + ".dev_and_skills.skill_id IN (" +
                        "SELECT " + SCHEME_NAME + ".skills.id " +
                        "FROM " + SCHEME_NAME + ".skills " +
                        "WHERE " + SCHEME_NAME + ".skills.name = '" + languageName + "'))";

        Statement statement = CONNECTION.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<Developers> developers = new ArrayList<>();

        while (resultSet.next()) {
            developers.add(devCreator(resultSet));
        }

        return developers;
    }

    @SneakyThrows
    public List<Developers> getDevsByLevel(String levelName) {//список всех разрабов определенного уровня

        String sql = "SELECT * " +
                "FROM " + SCHEME_NAME + ".developers " +
                "WHERE " + SCHEME_NAME + ".developers.id IN (" +
                    "SELECT " + SCHEME_NAME + ".dev_and_skills.developer_id " +
                    "FROM " + SCHEME_NAME + ".dev_and_skills " +
                    "WHERE " + SCHEME_NAME + ".dev_and_skills.skill_id IN (" +
                        "SELECT " + SCHEME_NAME + ".skills.id " +
                        "FROM " + SCHEME_NAME + ".skills " +
                        "WHERE " + SCHEME_NAME + ".skills.level = '" + levelName + "'))";

        Statement statement = CONNECTION.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<Developers> developers = new ArrayList<>();

        while (resultSet.next()) {
            developers.add(devCreator(resultSet));
        }

        return developers;
    }

    @SneakyThrows
    public List<String> getProjects() {//список проектов в формате: "дата создания - название проекта - количество разработчиков на этом проекте"

        String sql = "SELECT " +
                SCHEME_NAME + ".projects.creation_date, " +
                SCHEME_NAME + ".projects.name, " +
                "COUNT(*) AS number_of_developers " +
                "FROM " +
                SCHEME_NAME + ".projects, " +
                SCHEME_NAME + ".dev_and_projects " +
                "WHERE " + SCHEME_NAME + ".dev_and_projects.project_id = " + SCHEME_NAME + ".projects.id " +
                "GROUP BY " + SCHEME_NAME + ".dev_and_projects.project_id";

        Statement statement = CONNECTION.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<String> projects = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        while (resultSet.next()) {
            builder
                    .append("(")
                    .append(resultSet.getString("creation_date"))
                    .append(", ")
                    .append(resultSet.getString("name"))
                    .append(", ")
                    .append(resultSet.getInt("number_of_developers"))
                    .append(")");

            projects.add(builder.toString());
            builder.setLength(0);
        }

        return projects;
    }

    @SneakyThrows
    private Developers devCreator(ResultSet resultSet) {

        return Developers.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .age(resultSet.getInt("age"))
                .gender(resultSet.getString("sex"))
                .email(resultSet.getString("email"))
                .salary(resultSet.getInt("salary"))
                .companyId(resultSet.getInt("company_id"))
                .build();
    }

    @SneakyThrows
    @Override
    public void close() {

        CONNECTION.close();
    }
}