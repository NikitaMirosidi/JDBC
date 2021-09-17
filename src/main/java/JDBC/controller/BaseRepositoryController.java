package JDBC.controller;

import JDBC.model.*;
import JDBC.repository.BaseRepository;
import JDBC.util.ModelCreator;
import JDBC.util.ModelCreatorUtil;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BaseRepositoryController<V extends BaseModel> implements BaseController<BaseModel> {

    private final BaseRepository<BaseModel> REPOSITORY;
    private final Class<V> MODEL_CLASS;
    private final Scanner SCANNER;

    public BaseRepositoryController(Class<V> modelClass, BaseRepository repository, Scanner scanner) {
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

            String i = SCANNER.nextLine();
            BaseModel model;
            int id;

            switch (i) {
                case "1":

                    model = ModelCreator.create(MODEL_CLASS, SCANNER);
                    save(model);
                    break;
                case "2":

                    id = ModelCreatorUtil.getInt("ID",SCANNER);
                    Optional<BaseModel> opt = get(id);

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
    public void save(BaseModel model) {

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
    public Optional<BaseModel> get(int id) {

        return REPOSITORY.getById(id);
    }

    @SneakyThrows
    @Override
    public List<BaseModel> getAll() {
        return REPOSITORY.getAll();
    }

    @SneakyThrows
    @Override
    public void update(BaseModel model) {

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