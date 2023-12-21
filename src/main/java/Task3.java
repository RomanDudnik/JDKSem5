import java.util.Random;
import java.util.concurrent.CountDownLatch;

/*
    3 бегуна должны прийти к старту гонки
    Программа должна гарантировать, что гонка начнется только когда все три участника
    будут на старте
    Программа должна отсчитать “На старт”, “Внимание”, “Марш”
    Программа должна завершиться когда все участники закончат гонку.
 */
public class Task3 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch allStart = new CountDownLatch(3);
        CountDownLatch start = new CountDownLatch(1);
        Thread runner1 = new Thread(new Runner(allStart, start));
        Thread runner2 = new Thread(new Runner(allStart, start));
        Thread runner3 = new Thread(new Runner(allStart, start));
        runner1.start();
        runner2.start();
        runner3.start();
        System.out.println("Waiting for start...");
        allStart.await();
        start.countDown();
        runner1.join();
        runner2.join();
        runner3.join();
        System.out.println("Race is finished!");

    }

    static class Runner implements Runnable {
        private CountDownLatch readyToStart;
        private CountDownLatch raceStart;

        public Runner(CountDownLatch readyToStart, CountDownLatch raceStart) {
            this.readyToStart = readyToStart;
            this.raceStart = raceStart;
        }

        @Override
        public void run() {
            System.out.println("Go to start " + Thread.currentThread().getName());
            try {
                Thread.sleep(new Random().nextInt(500, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            readyToStart.countDown();
            try {
                raceStart.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Running... " + Thread.currentThread().getName());
            try {
                Thread.sleep(new Random().nextInt(3000, 5000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Finish" + Thread.currentThread().getName());
        }
    }
}
