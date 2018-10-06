package small_library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class BooksLibs implements Runnable {

    Logger bookLogger = LogManager.getLogger(BooksLibs.class.getName());

    private String bookName;
    private int TimeRead = 2000;
    private boolean ReadyRoom;

    LinkedList<PeopleRead> peopleReadArrayList = new LinkedList<>();

    public BooksLibs(String nameBook, boolean ReadRoom, int ReadTime) {
        this.bookName = nameBook;
        this.ReadyRoom = ReadRoom;
        this.TimeRead = ReadTime;

        bookLogger.info("Книга поступила: Название книги " + bookName +
                ", приступил к чтению " + ReadyRoom + " на время " + TimeRead);

    }

    public String getBookName() {
        return bookName;
    }

    public boolean isReadyRoom() {
        return ReadyRoom;
    }

    public List<PeopleRead> getPeopleReadArrayList() {
        return peopleReadArrayList;
    }

    public void addListForReady(PeopleRead peopleRead) {
        peopleReadArrayList.offer(peopleRead);
    }


    @Override
    public void run() {
        try {
            while (!peopleReadArrayList.isEmpty()) {
                PeopleRead peopleRead = peopleReadArrayList.poll();

                readyBook(new Random().nextInt(this.TimeRead), peopleRead.getPeopleName());


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readyBook(int TimeReady, String peoplename) throws InterruptedException {
        bookLogger.info(peoplename + ": " + this.bookName + " начал читать книгу");
        Thread.sleep(TimeRead);
        bookLogger.info(peoplename + ": " + bookName + " закончил читать книгу");
    }
}
