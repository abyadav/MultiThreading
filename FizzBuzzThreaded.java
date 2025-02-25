import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FizzBuzzThreaded {
    private final static Lock lock = new ReentrantLock();

    static Condition cond = lock.newCondition();
    private static final Object mtx = new Object();
    static int curr, n;

    private static void fizz() {
        while (true) {

            synchronized (mtx) {
                // lock.lock();

                while (curr <= n && (curr % 3) != 0) {
                    try {
                        // cond.await();
                        mtx.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (curr > n) {
                    break;
                }

                try {
                    // if ((curr % 3) == 0) {
                    System.out.println("fizz " + curr);
                    curr++;
                    // cond.signalAll();
                    mtx.notifyAll();
                    // }

                } catch (Exception e) {
                } finally {
                    // lock.unlock();
                }
            }
        }

    }

    private static void buzz() {
        while (true) {

            synchronized (mtx) {
                // lock.lock();

                while (curr <= n && (curr % 5) != 0) {
                    try {
                        // cond.await();
                        mtx.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (curr > n) {
                    break;
                }
                try {
                    // if ((curr % 5) == 0) {
                    System.out.println("buzz " + curr);
                    curr++;
                    // cond.signalAll();
                    mtx.notifyAll();
                    // }

                } catch (Exception e) {
                } finally {
                    // lock.unlock();
                }
            }
        }

    }

    private static void number() {
        while (true) {
            synchronized (mtx) {

                while (curr <= n && (curr % 3 == 0 || curr % 5 == 0)) {
                    try {
                        // cond.await();
                        mtx.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (curr > n) {
                    break;
                }

                try {
                    // if ((curr % 3) == 0) {
                    System.out.println("number " + curr);
                    curr++;
                    // cond.signalAll();
                    mtx.notifyAll();
                    // }

                } catch (Exception e) {
                } finally {
                    // lock.unlock();
                }
            }
        }

    }

    public static void main(String[] args) {
        curr = 1;
        n = 100;
        Thread t1 = new Thread(() -> {
            fizz();
        });

        Thread t2 = new Thread(() -> {
            buzz();
        });

        Thread t3 = new Thread(() -> {
            number();
        });

        t1.start();
        t2.start();
        t3.start();
    }

}
