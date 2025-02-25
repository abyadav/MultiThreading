import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PalindromDetector {
    private static final Lock lock = new ReentrantLock();

    private static Condition cond = lock.newCondition();

    private static int n = 100;
    private static int curr = 1;

    private static void printPalindrome() {
        while (curr <= n) {
            lock.lock();
            try {

                if (checkPalindrome(curr)) {
                    System.out.println("Palindrome: " + curr);
                    curr++;

                    System.out.println("signalled other thread");
                    cond.signal();
                } else {
                    System.out.println("going to call await");
                    cond.await();
                    System.out.println("Wait over");
                }
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                System.out.println("Unlocking");
                lock.unlock();
            }
        }
    }

    private static void printNonPalindrome() {
        while (curr <= n) {
            lock.lock();
            try {

                if (!checkPalindrome(curr)) {
                    System.out.println("Non Palindrome: " + curr);
                    curr++;
                    cond.signal();
                } else {
                    cond.await();
                }
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                lock.unlock();
            }
        }
    }

    private static boolean checkPalindrome(int curr) {
        String num = String.valueOf(curr);
        String r = new StringBuilder(num).reverse().toString();
        return num.equals(r);
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            printNonPalindrome();
        });

        Thread t2 = new Thread(() -> {
            printPalindrome();
        });

        t1.start();
        t2.start();
    }
}
