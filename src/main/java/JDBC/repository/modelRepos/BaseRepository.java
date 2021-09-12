package JDBC.repository.modelRepos;

import JDBC.model.BaseModel;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<V extends BaseModel> {

    public abstract void save(V model);

    public abstract void saveAll (Iterable<V> models);

    public abstract Optional<V> getById(int id);

    public abstract List<V> getAll();

    public abstract void update(V model);

    public abstract void deleteById(int id);
}