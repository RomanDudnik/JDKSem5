package HmwrkJDKSem5;

import java.util.concurrent.Semaphore;

/*
    Есть пять философов (потоки), которые могут либо обедать, либо размышлять.
    Каждый философ должен пообедать три раза. Каждый прием пищи длиться 500 миллисекунд
    После каждого приема пищи философ должен размышлять
    Не должно возникнуть общей блокировки
    Философы не должны есть больше одного раза подряд
 */

public class Main {
    public static void main(String[] args) {

        Philosopher[] philosophers = new Philosopher[5];  // 5 философов
        Semaphore table = new Semaphore(1); // стол
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(table, i);
            philosophers[i].start();
        }

        for (Philosopher philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
