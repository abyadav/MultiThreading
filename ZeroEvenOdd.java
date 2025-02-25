import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZeroEvenOdd {
    private static Lock lock = new ReentrantLock();

    private static final Condition zeroCond = lock.newCondition();
    private static final Condition evenCond = lock.newCondition();
    private static final Condition oddCond = lock.newCondition();

    private static int curr = 1, n = 6;
    private static boolean wasPrevZero = false;

    private static void printZero() throws InterruptedException {
        while (true) {
            lock.lock();
            System.out.println("Came for zero " + wasPrevZero + " " + curr);

            if (curr > n) {
                break;
            }
            while (wasPrevZero == true) {
                zeroCond.await();
            }
            try {
                wasPrevZero = true;
                System.out.println(0);
                if ((curr & 1) == 0) {
                    evenCond.signal();
                } else {
                    oddCond.signal();
                }
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        }
    }

    private static void printEven() throws InterruptedException {
        while (true) {
            lock.lock();
            System.out.println("Came for even " + curr);

            if (curr > n) {
                break;
            }

            while (wasPrevZero == false || curr % 2 > 0) {
                evenCond.await();
                // continue;
            }

            try {
                System.out.println(curr);
                curr++;
                wasPrevZero = false;
                zeroCond.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    private static void printOdd() throws InterruptedException {
        while (true) {
            lock.lock();
            System.out.println("Came for odd " + curr);

            if (curr > n) {
                break;
            }

            // System.out.println((curr % 2 == 0));
            while (wasPrevZero == false || curr % 2 == 0) {
                oddCond.await();
                // continue;
            }

            try {
                // System.out.println("Printed odd with " + (curr % 2 == 0));
                System.out.println(curr);
                curr++;
                wasPrevZero = false;
                zeroCond.signal();
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                printEven();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                printOdd();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                printZero();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

}
