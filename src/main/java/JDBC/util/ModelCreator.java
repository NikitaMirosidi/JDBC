package JDBC.util;

import JDBC.model.BaseModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ModelCreator {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    public static <T extends BaseModel> T create(Class<T> modelClass, Scanner scanner) {

        Map<String, String> nameToType = Arrays.stream(modelClass.getDeclaredFields())
                .filter(a -> !Modifier.isStatic(a.getModifiers()))
                .collect(Collectors.toMap(Field::getName, a -> a.getType().getSimpleName()));

        Map<String, Object> objectMap = new HashMap<>();

        for (String fieldName : nameToType.keySet()) {

            if(nameToType.get(fieldName).equals("int")) {
                objectMap.put(fieldName, ModelCreatorUtil.getInt(fieldName, scanner));
            }

            if(nameToType.get(fieldName).equals("String")) {
                objectMap.put(fieldName, ModelCreatorUtil.getString(fieldName, scanner));
            }
        }

        return MAPPER.convertValue(objectMap, modelClass);
    }
}