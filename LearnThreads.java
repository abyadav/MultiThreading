import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LearnThreads {
    static LinkedBlockingQueue<Integer> bq = new LinkedBlockingQueue<>(1);

    public static void main(String[] args) {

        BlockingQueue<Integer> bq = new LinkedBlockingQueue<>(5);
        ProducerThread pt = new ProducerThread(bq);
        ConsumerThread ct = new ConsumerThread(bq);

        pt.start();
        ct.start();
    }

}

class ConsumerThread extends Thread {
    BlockingQueue<Integer> bq;

    public ConsumerThread(BlockingQueue<Integer> bq) {
        this.bq = bq;
    }

    public void run() {
        while (true) {
            try {
                if (bq.isEmpty()) {
                    synchronized (bq) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
            }

            Integer val = bq.poll();
            System.out.println("Consumed value: " + val);
        }
    }
}

class ProducerThread extends Thread {
    BlockingQueue<Integer> bq;

    public ProducerThread(BlockingQueue<Integer> bq) {
        this.bq = bq;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            if (bq.isEmpty()) {
            }
            bq.add(i);
        }
    }
}

class MyThread extends Thread {

    @Override
    public void run() {
        // try {
        Thread.currentThread().interrupt();
        for (int i = 0; i < 5; i++) {
            System.out.println("OKK" + i);
            // Thread.currentThread().sleep(1000);;
        }
        // Thread.sleep(4000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }
}
