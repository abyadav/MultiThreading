import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LoggerMultiThreaded {

    private static BlockingQueue<String> bq = new LinkedBlockingDeque();

    private static void addLog() {
        for (int i = 0; i < 10; i++) {
            String str = getSaltString();
            try {
                bq.put(str);
            } catch (Exception e) {
            }

        }
    }

    private static void consumeLog() {
        while (true) {
            try {
                String str = bq.take();
                System.out.println("writing log to file: " + str);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    protected static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                addLog();
            }).start();
            ;
        }

        Thread t1 = new Thread(() -> {
            consumeLog();
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}