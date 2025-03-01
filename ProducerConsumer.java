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
                System.out.println("Queue size maximum");
                wait();
            } catch (Exception e) {
            }
        }

        dq.addLast(curr + 1);
        curr++;
        notify();
        System.out.println("Produced " + curr);
    }

    public synchronized void consume() {
        while (dq.size() == 0) {
            try {
                System.out.println("Queue empty, waiting...");
                wait();
            } catch (Exception e) {
            }
        }
        int curr = dq.removeFirst();
        notify();
        System.out.println("Consumed " + curr);

    }
}
