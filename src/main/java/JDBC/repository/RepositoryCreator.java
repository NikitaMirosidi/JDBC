package JDBC.repository;

import JDBC.model.BaseModel;
import lombok.SneakyThrows;


import java.util.HashMap;
import java.util.Map;

public class RepositoryCreator {

    private final static Map<String, BaseRepository> STANDARD_REPOSITORIES = new HashMap<>();
    private static CustomStatements customRepository;

    @SneakyThrows
    public static <T extends BaseModel> BaseRepository<T> of(Class<T> modelClass) {

        String modelName = modelClass.getName();

        if (!STANDARD_REPOSITORIES.containsKey(modelName)) {
            STANDARD_REPOSITORIES.put(modelName, new StandardStatements<>(modelClass));
        }

        return STANDARD_REPOSITORIES.get(modelName);
    }

    public static CustomStatements of() {

        if (customRepository == null) {
            customRepository = new CustomStatements();
        }

        return customRepository;
    }
}