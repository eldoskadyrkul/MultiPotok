package port;

import java.util.ArrayList;
import java.util.List;

public class Port {

    private int CargoCapacity; // грузоподъемность
    private int QuantityCont; // количество контейнеров
    private int SpacionesPort; // вместимость порта

    // Переменную ships преобразую в массив
    List<Thread> ships = new ArrayList<>();

    // Конструктор
    public Port(int CargoCap, int QuanCont, int SpacePort) {
        this.CargoCapacity = CargoCap;
        this.QuantityCont = QuanCont;
        this.SpacionesPort = SpacePort;
    }

    // Метод get
    public int getQuantityCont() {
        return QuantityCont;
    }

    public int getSpacionesPort() {
        return SpacionesPort;
    }

    // Добавляет контейнер
    public void addContainer() {
        QuantityCont++;
    }

    // Загружает контейнер
    public void takeContainer() {
        SpacionesPort--;
    }

    // загрузка контейнеров
    synchronized void downloadCont() {
        while (CargoCapacity == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ships.add(Thread.currentThread());
        System.out.println("Корабль загрузился " + Thread.currentThread().getName());
        CargoCapacity--;
    }

    // Возвращение в порт
    synchronized  void returnShip() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " остался на причале");
        System.out.println("Общее количество контейнеров в порту " + SpacionesPort);

        if (ships.contains(Thread.currentThread())) {
            QuantityCont++;
        }
        ships.remove(Thread.currentThread());

        notifyAll();
    }
}
