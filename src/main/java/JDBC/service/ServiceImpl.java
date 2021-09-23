package JDBC.service;

import JDBC.config.DatabaseConnection;
import JDBC.model.BaseModel;
import JDBC.model.Companies;
import JDBC.model.Customers;
import JDBC.repository.BaseRepository;
import JDBC.repository.RepositoryCreator;
import JDBC.util.PropertiesLoader;
import lombok.SneakyThrows;

import javax.persistence.Entity;
import java.lang.reflect.Field;

import java.lang.reflect.Modifier;
import java.sql.DataTruncation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceImpl<T extends BaseModel> extends BaseService<T>{

    private final BaseRepository<T> REPOSITORY;
    private final Class<T> MODEL_CLASS;
    private final Scanner SCANNER;
    private final List<Field> RELATION_FIELDS;
    private final String SCHEME_NAME;

    public ServiceImpl(Class<T> modelClass, Scanner scanner) {
        this.MODEL_CLASS = modelClass;
        this.SCANNER = scanner;
        this.REPOSITORY = RepositoryCreator.of(modelClass);
        this.RELATION_FIELDS = Arrays.stream(MODEL_CLASS.getDeclaredFields())
                .filter(a -> !Modifier.isStatic(a.getModifiers()))
                .filter(e -> e.getName().toLowerCase().contains("id"))
                .filter(e -> e.getName().length() > 2)
                .peek(e -> e.setAccessible(true))
                .collect(Collectors.toList());
        this.SCHEME_NAME = PropertiesLoader.getProperty("schemeName");
    }

    @SneakyThrows
    @Override
    public String save(T model) {

        String answer = "";

        if(isUniqueFieldDuplicate(model)) {
            answer += "Запись с указанным значением 'name' уже есть в базе.\n";
        }

        answer += isCorrectRelations(model);

        if(answer.length() != 0) {
            answer += "Сохранение невозможно!";
            return answer;
        }
        else {
            REPOSITORY.save(model);
            return "Сохранено.\n";
        }
    }

    @SneakyThrows
    @Override
    public Optional<T> getById(int id) {

        return REPOSITORY.getById(id);
    }

    @SneakyThrows
    @Override
    public List<T> getAll() {
        return REPOSITORY.getAll();
    }

    @Override
    public String update(T model) {

        String answer = "";

        if (!isIdInBase(model)) {
            answer += "В базе отсутствует запись с таким ID.\n";
        }
        answer += isCorrectRelations(model);

        if(answer.length() != 0) {
            return answer;
        }
        else {
            return "Обновлено.\n";
        }
    }

    @SneakyThrows
    @Override
    public String delete(int id) {

        if (getById(id).isEmpty()) {
            return "Запись с указанным ID отсутствует в базе.";
        }
        else {
            REPOSITORY.deleteById(id);
            return "Удалено.";
        }
    }

    private boolean isIdInBase(T model) {

        return getById(model.getId()).isPresent();
    }

    @SneakyThrows
    private boolean isUniqueFieldDuplicate(T model) {

        String name = "companies, customers, projects";
        String email = "developers";

        if(name.contains(MODEL_CLASS.getSimpleName().toLowerCase())) {

            return isPresent(model, "name");
        }
        if(email.contains(MODEL_CLASS.getSimpleName().toLowerCase())) {

            return isPresent(model, "email");
        }
        else {
            return false;
        }
    }

    @SneakyThrows
    private String isCorrectRelations(T model) {

        StringBuilder answer = new StringBuilder();

        if(RELATION_FIELDS.isEmpty()) {
            return answer.toString();
        }
        else {

            Map<String, Field> collect = RELATION_FIELDS.stream()
                    .filter(e -> e.getName().contains("company") || e.getName().contains("customer"))
                    .collect(Collectors.toMap(Field::getName, e -> e));

            collect.keySet().forEach(e -> answer.append(isAbsent(model, e, collect)));
        }

        return answer.toString();
    }

    @SneakyThrows
    private boolean isPresent(T model, String fieldName) {

        String tableName = MODEL_CLASS.getAnnotation(Entity.class).name();
        Field field = MODEL_CLASS.getDeclaredField(fieldName);
        field.setAccessible(true);
        String fieldValue = (String) field.get(model);
        String sql = "SELECT * FROM " + SCHEME_NAME + "." + tableName
                + " WHERE name = '" + fieldValue + "'";

        return DatabaseConnection
                .getInstance()
                .getConnection()
                .createStatement()
                .executeQuery(sql)
                .next();
    }

    @SneakyThrows
    private String isAbsent(T model, String fieldName, Map<String, Field> collection) {

        String answer = "";

        if(fieldName.contains("company")) {
            if (RepositoryCreator.of(Companies.class).getById(collection.get(fieldName).getInt(model)).isEmpty()) {
                answer += "Указанное значение companyId отсутствует в базе.\n";
            }
        }
        if(fieldName.contains("customer")) {
            if (RepositoryCreator.of(Customers.class).getById(collection.get(fieldName).getInt(model)).isEmpty()) {
                answer += "Указанное значение customerId отсутствует в базе.\n";
            }
        }

        return answer;
    }
}