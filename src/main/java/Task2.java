
/*
    Создайте два потока A и B.
    Поток A меняет значение Boolean переменной switcher с задержкой 1000 миллисекунд
    (true в состояние false и наоборот).
    Поток B ожидает состояния true переменной switcher и выводит на консоль обратный отсчет
    от 100 с задержкой 100 миллисекунд и приостанавливает свое действие,
    как только поток A переключит switcher в состояние false.
    Условием завершения работы потоков является достижение отсчета нулевой отметки.
 */
public class Task2 {
    static boolean switcher = false;
    static int count = 100;

    public static void main(String[] args) throws InterruptedException {
        Thread switcherThread = new Thread(() -> {

            try {
                switcher = !switcher;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Switcher thread was interrupted");
            }
        });

        Thread counterThread = new Thread(() -> {
            while (count > 0) {
                while (!switcher) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(count--);
            }
        });
        switcherThread.start();
        counterThread.start();
        switcherThread.join();
        counterThread.join();

    }
}
