package JDBC.service;

import JDBC.model.BaseModel;

import java.util.List;

public abstract class BaseService <T extends BaseModel> {

    public abstract void create(T model);
    public abstract T getById(T model);
    public abstract List<T> getAll(T model);
    public abstract void update(T model);
    public abstract void delete(T model);
}