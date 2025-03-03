import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosopher {
    public static void main(String[] args) {
        Table table = new Table(5);
        Thread[] philosopherThread = new Thread[5];
        for (int i = 0; i < 5; i++) {
            final int currPhilosopher = i;
            philosopherThread[i] = new Thread(() -> {
                while (true) {
                    table.letPhilosopherEat(currPhilosopher);
                    try {
                        // System.out.println();
                        // Thread.sleep((int) (100 * Math.random()));
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            });
        }
        for (int i = 0; i < 5; i++) {
            philosopherThread[i].start();
        }
    }
}

class Table {
    Lock[] forks;
    int philosophersCount;

    Table(int philosophersCount) {
        this.philosophersCount = philosophersCount;
        forks = new Lock[philosophersCount];
        for (int i = 0; i < philosophersCount; i++) {
            forks[i] = new ReentrantLock();
        }
    }

    public void letPhilosopherEat(int philosopher) {
        forks[philosopher].lock();
        forks[(philosopher + 1) % philosophersCount].lock();
        try {
            System.out.println(philosopher + " has started eating");
            // Thread.sleep((int) (100 * Math.random()));
        } catch (Exception e) {
        } finally {
            forks[philosopher].unlock();
            forks[(philosopher + 1) % philosophersCount].unlock();
        }
    }
}
