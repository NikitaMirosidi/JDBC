package JDBC.controller;

import JDBC.repository.CustomRepository;

import java.util.Scanner;

public class CustomController {

    public void general(CustomRepository repository, Scanner scanner) {

        boolean a = true;
        StringBuilder builder = new StringBuilder();

        while (a) {

            System.out.println("\nМеню нестандартных запросов:\n" +
                    "1 - получить зарплату (сумма) всех разработчиков отдельного проекта\n" +
                    "2 - получить список разработчиков определенного проекта\n" +
                    "3 - получить список всех разрабов владеющих определенным языком разработки\n" +
                    "4 - получить список всех разрабов определенного уровня\n" +
                    "5 - получить список проектов в формате: дата создания - название проекта - количество разработчиков на этом проекте\n" +
                    "0 - возврат в предыдущее меню\n");

            String i = scanner.nextLine();

            switch (i) {
                case "1":
                    System.out.print("Укажите название проекта: ");
                    builder.append(scanner.nextLine());
                    System.out.println(repository.getProjectsDevsSalary(builder.toString()) + "$");
                    builder.setLength(0);
                    break;
                case "2":
                    System.out.print("Укажите название проекта: ");
                    builder.append(scanner.nextLine());
                    System.out.println(repository.getDevsByProject(builder.toString()));
                    builder.setLength(0);
                    break;
                case "3":
                    System.out.print("Укажите название языка: ");
                    builder.append(scanner.nextLine());
                    System.out.println(repository.getDevsByLanguage(builder.toString()));
                    builder.setLength(0);
                    break;
                case "4":
                    System.out.print("Укажите название уровня: ");
                    builder.append(scanner.nextLine());
                    System.out.println(repository.getDevsByLevel(builder.toString()));
                    builder.setLength(0);
                    break;
                case "5":
                    System.out.println(repository.getProjects());
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
}