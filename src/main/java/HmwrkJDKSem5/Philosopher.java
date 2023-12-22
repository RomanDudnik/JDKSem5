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
            boolean isTableOccupied = false;
            for (int i = 0; i < 3; i++) {
                if (isTableOccupied) {
                    think();
                    isTableOccupied = table.tryAcquire();
                } else {
                    table.acquire();
                    isTableOccupied = true;
                }
                eat();
                table.release();
                think();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
