package JDBC.repository;

import JDBC.config.DatabaseConnection;
import JDBC.model.Developers;
import lombok.SneakyThrows;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomStatements implements Closeable {

    private final Connection CONNECTION;

    public CustomStatements() {
        this.CONNECTION = DatabaseConnection.getInstance().getConnection();
    }

    @SneakyThrows
    public int getProjectsDevsSalary(String projectName) { //зарплата (сумма) всех разработчиков отдельного проекта

        String sql = "SELECT SUM(homework_3.developers.salary) " +
                "FROM homework_3.developers " +
                "WHERE homework_3.developers.id IN (" +
                    "SELECT homework_3.dev_and_projects.developer_id " +
                    "FROM homework_3.dev_and_projects " +
                    "WHERE homework_3.dev_and_projects.project_id = (" +
                        "SELECT homework_3.projects.id " +
                        "FROM homework_3.projects " +
                        "WHERE homework_3.projects.name = '" + projectName + "'))";

        Statement statement = CONNECTION.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();

        return resultSet.getInt(1);
    }

    @SneakyThrows
    public List<Developers> getDevsByProject(String projectName) {//список разрабов определенного проекта

        String sql = "SELECT * " +
                "FROM homework_3.developers " +
                "WHERE homework_3.developers.id IN (" +
                    "SELECT homework_3.dev_and_projects.developer_id " +
                    "FROM homework_3.dev_and_projects " +
                    "WHERE homework_3.dev_and_projects.project_id = (" +
                        "SELECT homework_3.projects.id " +
                        "FROM homework_3.projects " +
                        "WHERE homework_3.projects.name = '" + projectName + "'))";

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
                "FROM homework_3.developers " +
                "WHERE homework_3.developers.id IN (" +
                    "SELECT homework_3.dev_and_skills.developer_id " +
                    "FROM homework_3.dev_and_skills " +
                    "WHERE homework_3.dev_and_skills.skill_id IN (" +
                        "SELECT homework_3.skills.id " +
                        "FROM homework_3.skills " +
                        "WHERE homework_3.skills.name = '" + languageName + "'))";

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
                "FROM homework_3.developers " +
                "WHERE homework_3.developers.id IN (" +
                    "SELECT homework_3.dev_and_skills.developer_id " +
                    "FROM homework_3.dev_and_skills " +
                    "WHERE homework_3.dev_and_skills.skill_id IN (" +
                        "SELECT homework_3.skills.id " +
                        "FROM homework_3.skills " +
                        "WHERE homework_3.skills.level = '" + levelName + "'))";

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
                "homework_3.projects.creation_date, " +
                "homework_3.projects.name, " +
                "COUNT(*) AS number_of_developers " +
                "FROM " +
                "homework_3.projects, " +
                "homework_3.dev_and_projects " +
                "WHERE homework_3.dev_and_projects.project_id = homework_3.projects.id " +
                "GROUP BY homework_3.dev_and_projects.project_id";

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
                .sex(resultSet.getString("sex"))
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