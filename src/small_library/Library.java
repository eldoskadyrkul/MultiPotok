package small_library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Library {
    static Logger librarylogger = LogManager.getLogger(Library.class.getName());

    private static final int TIME_READY = 2000;

    private int booksFree;
    private int booksLibrary;
    private List<BooksLibs> booksLibs = new ArrayList<>();

    private int peoplesCont;

    private List<PeopleRead> peopleReadList = new ArrayList<>();
    private List<Thread> threadsPeoples = new ArrayList<>();

    public Library(int booksFree, int booksLibrary, int peoplesCont) {
        this.booksFree = booksFree;
        librarylogger.info("Add books by free ready to library...");
        fillBooksLibrary(false, booksFree);

        // заполним книги, которые можно читать в читальном зале библиотеки
        this.booksLibrary = booksLibrary;
        librarylogger.info("Add books by ready room to library...");
        fillBooksLibrary(true, booksLibrary);

        // заполним список читателей библиотеки
        librarylogger.info("Add peoples readers to library...");
        for (int i = 0; i < peoplesCont; i++) {
            PeopleRead people = new PeopleRead("People" + i, booksLibs);
            peopleReadList.add(people);
        }
    }

    // геттеры
    public List<BooksLibs> getLstBooksLibs() {
        return booksLibs;
    }

    public List<PeopleRead> getLstPeoplesRead() {
        return peopleReadList;
    }

    // метод заполняет список книг в библиотеке
    private void fillBooksLibrary(boolean byReadyRoom, int count) {
        int firstIndex = booksLibs.size();

        for (int i = firstIndex; i < (firstIndex) + count; i++) {
            BooksLibs book = new BooksLibs("Book" + i, byReadyRoom, new Random().nextInt(TIME_READY));
            booksLibs.add(book);
        }
    }

    // метод запускает работу библиотеки и инициирует потоки действий читателей
    public void startWorkLibrary() throws InterruptedException {
        for (PeopleRead people : peopleReadList) {
            // запустим поток действий читателя
            Thread thread = new Thread(people);
            threadsPeoples.add(thread);
            thread.start();
        }

        int countThreads = threadsPeoples.size();

        // ждем завершения работы всех потоков действий читателей
        while (countThreads > 0) {
            for (Thread thd : threadsPeoples) {
                if (thd.getState() == Thread.State.TERMINATED) {
                    countThreads --;
                }

                Thread.sleep(200);
            }
        }
    }
}
