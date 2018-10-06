package small_library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PeopleRead implements Runnable {

    static Logger peoplelogger = LogManager.getLogger(PeopleRead.class.getName());

    private String peopleName;
    private static final Random random = new Random();

    private static final int NOVISITLIBRARY = 0;
    private static final int VISITLIBRARY = 1;

    private static final int NOACTION = 0;
    private static final int GETBOOK = 1;
    private static final int OUTLIBRARY = 2;

    private List<BooksLibs> booksLibsList;

    private List<BooksLibs> BooksbyReady = new ArrayList<>();
    private List<Thread> threadsBooksReady = new ArrayList<>();

    public PeopleRead(String peopleName, List<BooksLibs> libsList) {
        this.peopleName = peopleName;
        this.BooksbyReady = libsList;

        peoplelogger.info("Создан новый посетитель: имя = " + peopleName);
    }

    public String getPeopleName() {
        return peopleName;
    }

    private int getActionbyRandom(int actionValue) {
        return random.nextInt(actionValue);
    }

    private BooksLibs getBookForReady() {
        BooksLibs booksLib = null;

        if (booksLibsList.size() > 0) {
            int idBook = random.nextInt(booksLibsList.size() + 1);

            int x = 0;
            for (BooksLibs booksLibs1 : booksLibsList) {
                if (x == idBook) {
                    booksLib = booksLibs1;
                    break;
                }
                x++;
            }
        }

        if (booksLib != null && !booksLibsList.contains(booksLib)) {
            booksLibsList.add(booksLib);
        } else {
            booksLib = null;
        }

        if (booksLib != null)
            peoplelogger.info(this.peopleName + " выбрал книгу " + booksLib.getBookName());

        return booksLib;
    }

    @Override
    public void run() {
        try {
            int actionPeople = getActionbyRandom(2);

            if (actionPeople == NOVISITLIBRARY) {
                peoplelogger.info(this.peopleName + " не пришел в библиотеку");
                return;
            }

            if (actionPeople == VISITLIBRARY) {
                peoplelogger.info(this.peopleName + " пришел в библиотеку");
                return;
            }

            actionPeople = -1;

            while (actionPeople != OUTLIBRARY) {
                actionPeople = getActionbyRandom(3);

                if (actionPeople == NOACTION) {
                    continue;
                }

                if (actionPeople == GETBOOK) {
                    BooksLibs booksLibs = getBookForReady();

                    if (booksLibs != null) {
                        if (booksLibs.getPeopleReadArrayList().isEmpty()) {
                            booksLibs.addListForReady(this);

                            Thread thread = new Thread(booksLibs);
                            threadsBooksReady.add(thread);
                            thread.start();
                        } else {
                            booksLibs.addListForReady(this);
                        }
                    }

                    Thread.sleep(random.nextInt(100));

                    continue;
                }

                if (actionPeople == OUTLIBRARY) {
                    int cointThead = threadsBooksReady.size();

                    while (cointThead > 0) {
                        for (Thread thd :
                                threadsBooksReady) {
                            if (thd.getState() == Thread.State.TERMINATED) {
                                cointThead--;
                            }

                            Thread.sleep(200);
                        }

                        peoplelogger.info(this.peopleName + " вышел из библиотеки");
                        return;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

            peoplelogger.info(e.getMessage());
        }
    }
}