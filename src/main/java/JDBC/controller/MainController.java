package JDBC.controller;

import JDBC.model.*;
import JDBC.repository.RepositoryCreator;

import java.util.Scanner;

public class MainController {

    public void userInterface() {

        boolean a = true;
        Scanner scanner = new Scanner(System.in);

        while (a) {

            System.out.println("\nГлавное меню:\n" +
                    "1 - стандартная работа с Companies\n" +
                    "2 - стандартная работа с Customers\n" +
                    "3 - стандартная работа с Developers\n" +
                    "4 - стандартная работа с Projects\n" +
                    "5 - стандартная работа с Skills\n" +
                    "6 - нестандартные запросы\n" +
                    "0 - завершение приложения\n");

            String i = scanner.nextLine();

            switch (i) {
                case "1":
                    new BaseRepositoryController<>(Companies.class, scanner).general();
                    break;
                case "2":
                    new BaseRepositoryController<>(Customers.class,scanner).general();
                    break;
                case "3":
                    new BaseRepositoryController<>(Developers.class,scanner).general();
                    break;
                case "4":
                    new BaseRepositoryController<>(Projects.class,scanner).general();
                    break;
                case "5":
                    new BaseRepositoryController<>(Skills.class,scanner).general();
                    break;
                case "6":
                    new CustomRepositoryController().general(RepositoryCreator.of(), scanner);
                    break;
                case "0":
                    a = false;
                    System.out.println("До скорых встреч\n");
                    break;
                default:
                    System.out.println("Поддерживаемая функция не выбрана\n" +
                            "Попробуйте еще раз\n");
                    break;
            }
        }

        scanner.close();
    }
}