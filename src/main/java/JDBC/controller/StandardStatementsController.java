package JDBC.controller;

import JDBC.model.*;
import JDBC.repository.BaseRepository;
import JDBC.util.ModelCreator;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class StandardStatementsController<V extends BaseModel> implements BaseController {

    private final BaseRepository REPOSITORY;
    private final Class<V> MODEL_CLASS;
    private final Scanner SCANNER;

    public StandardStatementsController(Class<V> modelClass, BaseRepository repository, Scanner scanner) {
        this.MODEL_CLASS = modelClass;
        this.SCANNER = scanner;
        this.REPOSITORY = repository;
    }

    @SneakyThrows
    @Override
    public void general() {

        boolean a = true;

        while (a) {

            System.out.println("\nМеню " + MODEL_CLASS.getSimpleName() + ":\n" +
                    "1 - сохранить\n" +
                    "2 - получить по ID\n" +
                    "3 - получить всё\n" +
                    "4 - обновить\n" +
                    "5 - удалить\n" +
                    "0 - возврат в предыдущее меню\n");

            String i = SCANNER.next();

            switch (i) {
                case "1":
                    save();
                    break;
                case "2":
                    get();
                    break;
                case "3":
                    getAll();
                    break;
                case "4":
                    update();
                    break;
                case "5":
                    delete();
                    break;
                case "0":
                    a = false;
                    break;
                default:
                    System.out.println("Поддерживаемая функция не выбрана\n" +
                            "Попробуйте еще раз\n");
                    break;
            }
        }
    }

    @SneakyThrows
    @Override
    public void save() {

        try {
            REPOSITORY.save(ModelCreator.create(MODEL_CLASS.getSimpleName(), SCANNER));
            System.out.println("Сохранено.");
        }
        catch (SQLException e) {
            System.out.println("В базе уже имеется подобная запись.");

            if (MODEL_CLASS.getSimpleName().equals("Projects")) {
                System.out.println("Или указанный ID владельца/ID исполнителя отсутствует в базе.");
            }
        }
    }

    @SneakyThrows
    @Override
    public void get() {

        Optional response = REPOSITORY.getById(ModelCreator.getInt(SCANNER, "ID", "ID указан"));

        if(response.isPresent()) {
            System.out.println(response.get());
        }
        else {
            System.out.println("В базе отсутствует запись с таким ID.");
        }
    }

    @SneakyThrows
    @Override
    public void getAll() {
        System.out.println(REPOSITORY.getAll());
    }

    @SneakyThrows
    @Override
    public void update() {

        try {
            REPOSITORY.update(ModelCreator.create(MODEL_CLASS.getSimpleName(), SCANNER));
            System.out.println("Обновлено.");
        }
        catch (SQLException e) {
            System.out.println("В базе отсутствует запись с таким ID.");
        }
    }

    @SneakyThrows
    @Override
    public void delete() {

        try {
            REPOSITORY.deleteById(ModelCreator.getInt(SCANNER, "ID", "ID указан"));
            System.out.println("Удалено.");
        }
        catch (SQLException e) {
            System.out.println("В базе отсутствует запись с таким ID.");
        }
    }
}