package port;

public class Ship extends Thread {

    Port port;

    int containersLeave; // осталось контейнеров
    int containersTake; // сколько взял контейнеров

    int currentContainer; // текущее количество контейнеров

    // Конструктор
    public Ship(String name, int containersLeave, int containersTake, int currentContainer, Port port) {
        super(name);
         this.containersLeave = containersLeave;
         this.containersTake = containersTake;
         this.port = port;
         this.currentContainer = currentContainer;
         start();
    }

    @Override
    public void run() {
        boolean isStart = false;

        try {
            while (true) {
                if (!isStart) {
                    port.downloadCont();
                }

                isStart = false;
                if(containersLeave != 0 && containersTake != 0) {
                    containersTake--;
                    System.out.println("На порту осталось " + containersTake);
                    containersLeave--;
                    System.out.println("корабль взял " + containersLeave);
                    isStart = true;
                } else {
                    if (containersLeave != 0) {
                        synchronized (port) {
                            if (port.getQuantityCont() > port.getSpacionesPort()) {
                                port.addContainer();
                                containersLeave--;
                                System.out.println("На порту осталось " + containersTake);
                                isStart = true;
                            }
                        }
                    } else {
                        if (containersTake != 0) {
                            synchronized (port) {
                                if (port.getSpacionesPort() > 0) {
                                    port.takeContainer();
                                    containersTake--;
                                    System.out.println("корабль взял " + containersLeave);
                                    isStart = true;
                                }
                            }
                        } else {
                            System.out.println(Thread.currentThread().getName() +  " закончил свою работу");
                            port.returnShip();
                            break;
                        }
                    }
                }

                if (isStart) {
                    Thread.sleep(10);
                } else {
                    port.returnShip();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
