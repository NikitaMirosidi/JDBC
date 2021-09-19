package JDBC.controller.handler;

import java.util.Scanner;

public class UnsupportedChioce extends UserChoiceHandler {

    public UnsupportedChioce() {
        super (null);
    }

    @Override
    protected void apply(Scanner scanner) {
        System.out.println("Поддерживаемая функция не выбрана!\n" +
                "Попробуйте еще раз.\n");
    }

    @Override
    protected boolean isApplicable(String choice) {
        return true;
    }
}