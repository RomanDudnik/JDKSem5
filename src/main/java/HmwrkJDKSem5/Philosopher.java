package HmwrkJDKSem5;

import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    private final Semaphore table;
    private final int num;

    public Philosopher(Semaphore table, int num) {
        this.table = table;
        this.num = num;
    }

    private void eat() throws InterruptedException {
        sleep(500);
        System.out.println("Philosopher " + num + " is eating.");
    }

    private void think() throws InterruptedException {
        sleep(500);
        System.out.println("Philosopher " + num + " is thinking.");
    }

    @Override
    public void run() {
        try {
            for(int i = 0; i < 3; i++) {
                table.acquire(); // философ занимает стол
                eat();  // философ ест
                if (table.availablePermits() == 0) {     // проверяем, занят ли стол
                    think();  // философ размышляет
                }
                table.acquire();  // повторная попытка занять стол
                eat();  // философ ест
                table.release();  // философ освобождает стол
                think();  // философ размышляет
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
