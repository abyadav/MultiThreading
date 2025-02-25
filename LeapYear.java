import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LeapYear {
    private static int[] years = new int[] { 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002,
            2003, 2004 };
    private static final Lock lock = new ReentrantLock();
    private static Condition leapYearCond = lock.newCondition();
    private static Condition nonLeapYearCond = lock.newCondition();

    private static int currIdx = 0;

    private static void printLeapYear() {
        while (currIdx < years.length) {
            lock.lock();
            try {
                if (checkLeapYear(currIdx)) {
                    System.out.println("Leap year " + years[currIdx]);
                    currIdx++;
                    nonLeapYearCond.signal();
                } else {
                    leapYearCond.await();
                }
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                lock.unlock();
            }
        }
    }

    private static void printNonLeapYear() {
        while (currIdx < years.length) {
            lock.lock();
            try {
                if (!checkLeapYear(currIdx)) {
                    System.out.println("Non leap year " + years[currIdx]);
                    currIdx++;
                    leapYearCond.signal();
                } else {
                    nonLeapYearCond.await();
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }

        }
    }

    private static boolean checkLeapYear(int idx) {
        int year = years[idx];
        if (year % 400 == 0) {
            return true;
        }
        if (year % 100 == 0) {
            return false;
        }

        if (year % 4 == 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            printLeapYear();
        });

        Thread t2 = new Thread(() -> {
            printNonLeapYear();
        });

        t1.start();
        t2.start();
    }
}
