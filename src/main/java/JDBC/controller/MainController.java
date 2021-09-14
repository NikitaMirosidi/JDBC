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

            String i = scanner.next();

            switch (i) {
                case "1":
                    new StandardStatementsController<>(Companies.class, RepositoryCreator.of(Companies.class),scanner).general();
                    break;
                case "2":
                    new StandardStatementsController<>(Customers.class, RepositoryCreator.of(Customers.class),scanner).general();
                    break;
                case "3":
                    new StandardStatementsController<>(Developers.class, RepositoryCreator.of(Developers.class),scanner).general();
                    break;
                case "4":
                    new StandardStatementsController<>(Projects.class, RepositoryCreator.of(Projects.class),scanner).general();
                    break;
                case "5":
                    new StandardStatementsController<>(Skills.class, RepositoryCreator.of(Skills.class),scanner).general();
                    break;
                case "6":
                    new CustomStatementsController().general(RepositoryCreator.of(), scanner);
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
