import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiBuffer {
    public static void main(String[] args) {
        BufferQueue bq = new BufferQueue();
        Thread[] producThreads = new Thread[10];

        Thread[] consumerThreads = new Thread[5];

        for (int i = 0; i < 10; i++) {
            final int buffer = i;
            producThreads[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    bq.produce(buffer);
                    try {
                        // Thread.sleep(10);
                    } catch (Exception e) {

                    }
                }
            });
        }

        for (int i = 0; i < 5; i++) {
            consumerThreads[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    bq.consume();
                }
            });
        }

        for (int i = 0; i < 10; i++) {
            producThreads[i].start();
        }
        for (int i = 0; i < 5; i++) {
            consumerThreads[i].start();
        }
    }
}

class BufferQueue {
    private static int bufferCount = 10;
    private static int bufferSize = 5;

    private static int producerThreadCount = bufferCount;
    private static int consumerThreadCount = 5;

    private static List<Integer>[] buffers;
    Lock[] locks = new Lock[bufferCount];
    Semaphore[] bufferNotFull = new Semaphore[bufferCount];
    Condition[] conditions = new Condition[bufferCount];

    @SuppressWarnings("unchecked")
    BufferQueue() {
        buffers = new ArrayList[bufferCount];
        for (int i = 0; i < bufferCount; i++) {
            buffers[i] = new ArrayList<>();
            locks[i] = new ReentrantLock();
            conditions[i] = locks[i].newCondition();
            bufferNotFull[i] = new Semaphore(bufferSize);
        }
    }

    public void produce(int buffer) {
        try {
            bufferNotFull[buffer].acquire();
            locks[buffer].lock();

            int produced = (int) (Math.random() * 1000);
            System.out.println("Thread " + Thread.currentThread().getName() + " produced " + produced);
            buffers[buffer].add(produced);
        } catch (Exception e) {
        } finally {
            locks[buffer].unlock();
        }

    }

    public void consume() {
        while (true) {

            for (int i = 0; i < bufferCount; i++) {
                locks[i].lock();
                try {

                    if (buffers[i].size() == 0) {
                        continue;
                    }
                    int consumed = buffers[i].remove(buffers[i].size() - 1);
                    System.out.println(
                            "Thread " + Thread.currentThread().getName() + " consumed " + consumed + " from " + i);
                    bufferNotFull[i].release();
                } catch (Exception e) {
                } finally {
                    locks[i].unlock();
                }
            }

        }
    }

}
