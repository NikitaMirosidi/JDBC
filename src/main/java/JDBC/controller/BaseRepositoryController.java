package JDBC.controller;

import JDBC.model.*;
import JDBC.repository.BaseRepository;
import JDBC.repository.RepositoryCreator;
import JDBC.util.ModelCreator;
import JDBC.util.ModelCreatorUtil;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import lombok.SneakyThrows;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BaseRepositoryController<T extends BaseModel> implements BaseController<T> {

    private final BaseRepository<T> REPOSITORY;
    private final Class<T> MODEL_CLASS;
    private final Scanner SCANNER;

    public BaseRepositoryController(Class<T> modelClass, Scanner scanner) {
        this.MODEL_CLASS = modelClass;
        this.SCANNER = scanner;
        this.REPOSITORY = RepositoryCreator.of(modelClass);
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

            String i = SCANNER.nextLine();
            T model;
            int id;

            switch (i) {
                case "1":

                    model = ModelCreator.create(MODEL_CLASS, SCANNER);
                    save(model);
                    break;
                case "2":

                    id = ModelCreatorUtil.getInt("ID",SCANNER);
                    Optional<T> opt = get(id);

                    if(opt.isEmpty()) {
                        System.out.println("В базе отсутствует запись с таким ID.");
                    }
                    else {
                        System.out.println(opt.get());
                    }
                    break;
                case "3":

                    System.out.println(getAll());
                    break;
                case "4":

                    model = ModelCreator.create(MODEL_CLASS, SCANNER);
                    update(model);
                    break;
                case "5":

                    id = ModelCreatorUtil.getInt("ID",SCANNER);
                    delete(id);
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
    public void save(T model) {

            try {
                REPOSITORY.save(model);
                System.out.println("Сохранено.");
            }
            catch (MysqlDataTruncation ex) {
                System.out.println("Дата указана неверно.");
            }
            catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("В базе уже есть запись с таким именем.");

                if(MODEL_CLASS.getSimpleName().equals("Projects")) {
                    System.out.println("Или указанный ID владельца/ID исполнителя отсутствует в базе.");
                }
            }
    }

    @SneakyThrows
    @Override
    public Optional<T> get(int id) {

        return REPOSITORY.getById(id);
    }

    @SneakyThrows
    @Override
    public List<T> getAll() {
        return REPOSITORY.getAll();
    }

    @SneakyThrows
    @Override
    public void update(T model) {

        if(get(model.getId()).isPresent()) {

            REPOSITORY.update(model);
            System.out.println("Обновлено.");
        }
        else {
            System.out.println("В базе отсутствует запись с таким ID.");
        }
    }

    @SneakyThrows
    @Override
    public void delete(int id) {

        if(get(id).isPresent()) {

            REPOSITORY.deleteById(id);
            System.out.println("Удалено.");
        }
        else {
            System.out.println("В базе отсутствует запись с таким ID.");
        }
    }
}