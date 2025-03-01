import java.util.ArrayDeque;
import java.util.Deque;

public class ProducerConsumer {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource(10);

        Thread produceThread = new Thread(() -> {
            for (int i = 0; i < 100; i++)
                sharedResource.produce();
        });

        Thread consumerThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                // try {
                // Thread.currentThread().sleep(20);

                // } catch (InterruptedException e) {
                // }
                sharedResource.consume();
            }
        });

        consumerThread.start();
        produceThread.start();
    }
}

class SharedResource {
    Deque<Integer> dq = new ArrayDeque<>();
    int capacity;
    int curr = 0;

    SharedResource(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce() {

        while (dq.size() == capacity || curr == 101) {
            try {
                notifyAll();
                System.out.println("Waiting...");
                wait();
            } catch (Exception e) {
            }
        }
        if (curr > 100) {
            notifyAll();

            return;
        }

        dq.addLast(curr++);
        notifyAll();
        System.out.println("Produced " + curr);
        System.out.println(dq.size());
    }

    public synchronized void consume() {
        while (dq.size() == 0) {
            notify();
            try {
                wait();
            } catch (Exception e) {
            }
        }
        if (dq.size() != 0) {
            int curr = dq.removeFirst();
            notify();
            System.out.println("Consumed " + curr);
        }
    }
}
