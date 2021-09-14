package JDBC.util;

import JDBC.model.*;
import lombok.SneakyThrows;

import java.util.*;

public class ModelCreator {

    @SneakyThrows
    public static BaseModel create(String modelName, Scanner scanner) {

        BaseModel model;

        switch (modelName) {
            case "Companies":

                model = createCompany(scanner);

                break;
            case "Customers":

                model = createCustomer(scanner);

                break;
            case "Developers":

                model = createDeveloper(scanner);

                break;
            case "Projects":

                model = createProject(scanner);

                break;
            case "Skills":

                model = createSkill(scanner);

                break;
            default:
                System.out.println("\n");
                throw new RuntimeException();
        }

        return model;
    }

    private static Companies createCompany(Scanner scanner) {

        System.out.println("Заполните данные о компании.\n");

        int id = getInt(scanner, "ID", "ID указан");
        String name;
        String registrationCountry;

        System.out.print("Укажите название компании: ");
        name = scanner.next();

        System.out.print("Укажите страну регистрации компании: ");
        registrationCountry = scanner.next();

        return Companies.builder()
                .id(id)
                .name(name)
                .registrationCountry(registrationCountry)
                .build();
    }

    private static Customers createCustomer(Scanner scanner) {

        System.out.println("Заполните данные о компании-заказчике.\n");

        int id = getInt(scanner, "ID", "ID указан");

        System.out.print("Укажите название компании-заказчика: ");
        String name = scanner.next();

        System.out.print("Укажите имя владельца компании-заказчика: ");
        String ownerName = scanner.next();

        return Customers.builder()
                .id(id)
                .name(name)
                .ownerName(ownerName)
                .build();
    }

    private static Developers createDeveloper(Scanner scanner) {

        System.out.println("Заполните данные о разработчике.\n");

        int id = getInt(scanner, "ID", "ID указан");

        System.out.print("Укажите имя разработчика: ");
        String name = scanner.next();

        int age = getInt(scanner, "возраст", "Возраст указан");

        System.out.print("Укажите пол разработчика: ");
        String sex = scanner.next();

        System.out.print("Укажите e-mail разработчика: ");
        String email = scanner.next();

        int salary = getInt(scanner, "зарплату", "Зарплата указана");
        int companyId = getInt(scanner, "ID компании работодателя разработчика", "ID указан");

        return Developers.builder()
                .id(id)
                .name(name)
                .age(age)
                .sex(sex)
                .email(email)
                .salary(salary)
                .companyId(companyId)
                .build();
    }

    private static Projects createProject(Scanner scanner) {

        System.out.println("Заполните данные о проекте.\n");

        int id = getInt(scanner, "ID", "ID указан");

        System.out.print("Укажите название проекта: ");
        String name = scanner.next();

        int cost = getInt(scanner, "стоимость", "стоимость указана");

        System.out.print("Укажите дату старта проекта в формате год-месяц-день: ");
        String creationDate = scanner.next();

        int customerId = getInt(scanner, "ID владельца", "ID указан");
        int companyId =  getInt(scanner, "ID исполнителя", "ID указан");

        return Projects.builder()
                .id(id)
                .name(name)
                .cost(cost)
                .creationDate(creationDate)
                .customerId(customerId)
                .companyId(companyId)
                .build();
    }

    private static Skills createSkill(Scanner scanner) {

        System.out.println("Заполните данные о навыке.\n");

        int id = getInt(scanner, "ID", "ID указан");

        System.out.print("Укажите название умения: ");
        String name = scanner.next();

        System.out.print("Укажите уровень умения: ");
        String level = scanner.next();

        return Skills.builder()
                .id(id)
                .name(name)
                .level(level)
                .build();
    }

    public static int getInt(Scanner scanner, String str1, String str2) {

        int id;

        while (true) {

            System.out.print("Укажите " + str1 + ": ");

            try {
                id = scanner.nextInt();
                break;
            }
            catch (InputMismatchException e) {
                System.out.println(str2 + " неверно. Поробуйте еще раз\n");
                scanner.next();
            }
        }

        return id;
    }
}