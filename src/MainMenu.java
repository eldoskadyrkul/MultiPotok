import javafx.application.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import port.*;
import small_library.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {
        System.out.println("Главное меню:");
        System.out.println("1. Порт");
        System.out.println("2. Маленькая библиотека");
        System.out.println("3. Выход из программы");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пункт меню:");
        int x = scanner.nextInt();
        switch (x) {
            case 1:
                portMain();
                break;
            case 2:
                small_libs();
                break;
            case 3:
                System.exit(0);
                break;
                default:
                    System.out.println("Введите пункт меню еще рвз");
        }
    }

    static void portMain() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество кораблей:");
        int x = scanner.nextInt();
        System.out.println("Введите количество контейнеров:");
        int a = scanner.nextInt();
        System.out.println("Введите грузоподъемность порта:");
        int b = scanner.nextInt();

        Port port = new Port(x, a, b);

        List<Ship> shipList = new ArrayList<>();

        for (int c = 0; c < x; c++) {
            shipList.add(new Ship("Корабль " + c, 260, 260, 0, port));
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 4; i < x; i++) {
            shipList.add(new Ship("Корабль" + i, 350, 350, 0, port));
        }

        for (Ship ship: shipList) {
            try {
                ship.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Все корабли закончили свои задания");
    }

    static void small_libs() {
        Logger myLogger = LogManager.getLogger(MainMenu.class.getName());
        myLogger.info("Приложение начал работу");

        // заполним параметры для запуска приложения
        Scanner scannerBooks = new Scanner(System.in);
        System.out.println("Введите количество книг выдаваемые на руки:");
        int x = scannerBooks.nextInt();
        System.out.println("Введите количество книг, читаемые в библиотеке:");
        int y = scannerBooks.nextInt();
        System.out.println("Введите количество читающих:");
        int z = scannerBooks.nextInt();
        int booksFreeCount =  Integer.valueOf(x);
        int booksByReadyRoomCount = Integer.valueOf(y);
        int peoplesCount = Integer.valueOf(z);

        Library bookLibrary = new Library(booksFreeCount, booksByReadyRoomCount, peoplesCount);

        try {
            bookLibrary.startWorkLibrary();

        }   catch (InterruptedException e) {
            e.printStackTrace();
            myLogger.error(e.getMessage());
        }

        myLogger.info("Приложение завершил работу");
    }
}
