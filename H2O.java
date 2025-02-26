import java.util.concurrent.Semaphore;

class H2O {
    private static final Semaphore hydrogenSemaphore = new Semaphore(2);
    private static final Semaphore oxygenSemaphore = new Semaphore(0);

    private static Object mtx = new Object();

    private static int hydrogenCount = 0;

    private static void produceHydrogen(Runnable hydrogenGenerator) throws InterruptedException {
        hydrogenSemaphore.acquire();

        synchronized (mtx) {
            hydrogenGenerator.run();
            hydrogenCount++;
            if (hydrogenCount == 2) {
                oxygenSemaphore.release();
            }
        }
    }

    private static void produceOxygen(Runnable oxygenGenerator) throws InterruptedException {
        oxygenSemaphore.acquire();

        oxygenGenerator.run();
        System.out.println("H2O is generated");

        hydrogenCount = 0;
        hydrogenSemaphore.release(2);
    }

    public static void main(String[] args) {

        Runnable hydrogenGenerator = () -> {
            System.out.print("H ");
        };

        Runnable oxygenGenerator = () -> {
            System.out.print("O ");
        };
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    produceHydrogen(hydrogenGenerator);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                try {
                    produceHydrogen(hydrogenGenerator);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                try {
                    produceOxygen(oxygenGenerator);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}