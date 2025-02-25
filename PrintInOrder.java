import java.util.concurrent.CountDownLatch;

public class PrintInOrder {

    static CountDownLatch firstDone = new CountDownLatch(1);
    static CountDownLatch secondDone = new CountDownLatch(1);

    private static void printFirst() {
        System.out.println("First print..");
        firstDone.countDown();
    }

    private static void secondPrint() {
        try {
            firstDone.await();
            System.out.println("Second done");
            secondDone.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void thirdPrint() {
        try {
            secondDone.await();
            System.out.println("Third done..");
        } catch (InterruptedException e) {

        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            printFirst();
        });

        Thread t2 = new Thread(() -> {
            secondPrint();
        });

        Thread t3 = new Thread(() -> {
            thirdPrint();
        });

        t3.start();
        t1.start();
        t2.start();
    }
}
