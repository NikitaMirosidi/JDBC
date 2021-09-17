package JDBC.repository;

import JDBC.model.BaseModel;
import JDBC.config.DatabaseConnection;
import JDBC.util.PropertiesLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Closeable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseRepositoryImpl<V extends BaseModel> implements BaseRepository<V>, Closeable {

    private final Connection CONNECTION;
    private final Class<V> MODEL_CLASS;
    private final String SCHEME_NAME;
    private final String TABLE_NAME;
    private final Map<String, String> COLUMN_TO_FIELD;
    private final Map<String, String> COLUMN_TO_FIELD_WITHOUT_ID;
    private final ObjectMapper MAPPER;

    private PreparedStatement getAllStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement postStatement;
    private PreparedStatement putStatement;
    private PreparedStatement deleteStatement;

    @SneakyThrows
    public BaseRepositoryImpl(Class<V> modelClass) {

        this.CONNECTION = DatabaseConnection.getInstance().getConnection();
        this.MODEL_CLASS = modelClass;
        this.SCHEME_NAME = PropertiesLoader.getProperty("schemeName");
        this.TABLE_NAME = modelClass.getAnnotation(Entity.class).name();
        this.COLUMN_TO_FIELD = Arrays.stream(modelClass.getDeclaredFields())
                .filter(a -> !Modifier.isStatic(a.getModifiers()))
                .collect(Collectors.toMap(a -> a.getAnnotation(Column.class).name(), Field::getName));
        this.COLUMN_TO_FIELD_WITHOUT_ID = Arrays.stream(modelClass.getDeclaredFields())
                .filter(a -> !Modifier.isStatic(a.getModifiers()))
                .filter(a -> !a.getName().equals("id"))
                .collect(Collectors.toMap(a -> a.getAnnotation(Column.class).name(), Field::getName));
        this.MAPPER = new ObjectMapper();

        String countValues = IntStream.range(0, COLUMN_TO_FIELD_WITHOUT_ID.size()).mapToObj(i -> "?").collect(Collectors.joining(","));
        String fieldsForCreate = String.join(",", COLUMN_TO_FIELD_WITHOUT_ID.keySet());
        String fieldsForUpdate = COLUMN_TO_FIELD_WITHOUT_ID.keySet().stream().map(i -> i + " = ?").collect(Collectors.joining(","));

        this.getAllStatement = CONNECTION.prepareStatement("SELECT * FROM " + SCHEME_NAME + "." + TABLE_NAME);
        this.getByIdStatement = CONNECTION.prepareStatement("SELECT * FROM " + SCHEME_NAME + "." + TABLE_NAME + " WHERE id = ?");
        this.postStatement = CONNECTION.prepareStatement("INSERT INTO " + SCHEME_NAME + "." + TABLE_NAME + " (" + fieldsForCreate + ") VALUES (" + countValues + ")");
        this.putStatement = CONNECTION.prepareStatement("UPDATE " + SCHEME_NAME + "." + TABLE_NAME + " SET " + fieldsForUpdate + " WHERE id = ?");
        this.deleteStatement = CONNECTION.prepareStatement("DELETE FROM " + SCHEME_NAME + "." + TABLE_NAME + " WHERE id = ?");
    }

    @Override
    public void save(V model) throws SQLException{

        try {
            executeStatement(postStatement, model);
        }
        catch (NoSuchFieldException | IllegalAccessException c) {
            c.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public void saveAll(Iterable<V> models) {

        for (V model : models) {
            save(model);
        }
    }

    @SneakyThrows
    @Override
    public Optional<V> getById(int id) {

        getByIdStatement.setObject(1, id);
        List<V> result = parse(getByIdStatement.executeQuery());

        if (result.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(result.get(0));
    }

    @SneakyThrows
    @Override
    public List<V> getAll() {

        return parse(getAllStatement.executeQuery());
    }


    @Override
    public void update(V model) throws SQLException {
        try {
            putStatement.setObject(COLUMN_TO_FIELD_WITHOUT_ID.size() + 1, model.getId());
            executeStatement(putStatement, model);
        }
        catch (NoSuchFieldException | IllegalAccessException c) {
            c.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) throws SQLException{

        deleteStatement.setObject(1, id);
        deleteStatement.executeUpdate();
    }

    @SneakyThrows
    private List<V> parse (ResultSet resultSet) {

        List<V> result = new ArrayList<>();

        while(resultSet.next()) {
            Map<String, Object> objectMap = new HashMap<>();
            for (String field : COLUMN_TO_FIELD.keySet()) {
                objectMap.put(COLUMN_TO_FIELD.get(field), resultSet.getObject(field));
            }

            result.add(MAPPER.convertValue(objectMap, MODEL_CLASS));
        }

        return result;
    }


    private void executeStatement(PreparedStatement statement, V model) throws NoSuchFieldException, SQLException, IllegalAccessException {

        int count = 1;

        for (String field : COLUMN_TO_FIELD_WITHOUT_ID.values()) {
            Field declaredField = MODEL_CLASS.getDeclaredField(field);
            declaredField.setAccessible(true);
            statement.setObject(count++, declaredField.get(model));
        }

        statement.executeUpdate();
    }


    @SneakyThrows
    @Override
    public void close() {

        CONNECTION.close();
    }
}