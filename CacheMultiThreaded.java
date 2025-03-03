import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CacheMultiThreaded {
    public static void main(String[] args) {
        MultiThreadedCache cache = new MultiThreadedCache();
        Thread[] addThreads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            addThreads[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    final int key = (int) (10 * Math.random());
                    final int val = (int) (10 * Math.random());
                    cache.addValue(key, val);
                    System.out.println("Added key value " + key + " " + val);
                }
            });
        }

        Thread[] gettThreads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            gettThreads[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    final int key = (int) (10 * Math.random());

                    Integer val = cache.getValue(key);
                    System.out.println("Got key value pair from cache: " + key + " " + val);
                }
            });
        }

        for (int i = 0; i < 10; i++) {
            // gettThreads[i].setPriority(10);
            gettThreads[i].start();
        }
        for (int i = 0; i < 10; i++) {
            addThreads[i].start();
        }

    }
}

class MultiThreadedCache {
    private ConcurrentHashMap<Integer, Node> cacheMap;

    MultiThreadedCache() {
        cacheMap = new ConcurrentHashMap<>();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this::removeExpired, 2, 10,
                TimeUnit.MILLISECONDS);
    }

    private void removeExpired() {
        for (Map.Entry<Integer, Node> entry : cacheMap.entrySet()) {
            if (entry.getValue().expiryLong < System.currentTimeMillis()) {
                System.out.println("Removing from cache " + entry.getKey() + " " + entry.getValue().expiryLong + " "
                        + System.currentTimeMillis());
                cacheMap.remove(entry.getKey());
            }
        }
    }

    public Integer getValue(int key) {
        if (!cacheMap.containsKey(key)) {
            System.out.println("Key not found " + key);
            return null;
        }
        Node val = cacheMap.get(key);
        // System.out.println(val.value + " " + val.expiryLong + " " +
        // System.currentTimeMillis());
        if (val.expiryLong < System.currentTimeMillis()) {
            System.out.println("Removing while getting: " + key);
            cacheMap.remove(key);
            return null;
        }
        return val.value;
    }

    public void addValue(int key, int val) {
        Node node = null;
        if (cacheMap.containsKey(key)) {
            node = cacheMap.get(key);
        } else {
            node = new Node();

        }
        node.value = val;
        node.expiryLong = System.currentTimeMillis() + 10;
        cacheMap.put(key, node);
    }
}

class Node {
    Integer value;
    Long expiryLong;
}
