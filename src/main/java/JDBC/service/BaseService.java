package JDBC.service;

import JDBC.model.BaseModel;

import java.util.List;
import java.util.Optional;

public abstract class BaseService <T extends BaseModel> {

    public abstract String save(T model);
    public abstract Optional<T> getById(int id);
    public abstract List<T> getAll();
    public abstract String update(T model);
    public abstract String delete(int id);
}