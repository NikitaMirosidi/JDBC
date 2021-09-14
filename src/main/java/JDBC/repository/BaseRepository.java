package JDBC.repository;

import JDBC.model.BaseModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<V extends BaseModel> {

    void save(V model) throws NoSuchFieldException, IllegalAccessException, SQLException;

    void saveAll (Iterable<V> models);

    Optional<V> getById(int id) throws SQLException;

    List<V> getAll() throws SQLException;

    void update(V model) throws SQLException, NoSuchFieldException, IllegalAccessException;

    void deleteById(int id) throws SQLException;
}