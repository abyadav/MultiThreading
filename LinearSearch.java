import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LinearSearch {

    static Object mtx = new Object();
    static int found = -1;
    static int globalmin = Integer.MAX_VALUE;
    static int globalMax = Integer.MIN_VALUE;
    static long gsum = 0;

    public static void main(String[] args) {
        int[] arr = new int[1000000];
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            arr[i] = (int) (random.nextInt(10000));

        }

        int target = random.nextInt(10000);
        List<Integer> res = new ArrayList<>();

        Thread[] threads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            final int idx = i;
            threads[i] = new Thread(() -> linearSearch(arr, idx, target, res));
            threads[i].start();
        }
        for (int i = 0; i < 20; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(res.size());
        for (int i = 0; i < res.size(); i++) {
            // System.out.println(arr[res.get(i)]);
        }
        System.out.println(globalmin + " " + globalMax + " " + gsum);

    }

    private static void linearSearch(int[] arr, int idx, int target, List<Integer> res) {
        int l = (1000000) / 20 * idx, r = (1000000) / 20 * (idx + 1) - 1;
        int lmin = Integer.MAX_VALUE, lmax = Integer.MIN_VALUE;
        long sum = 0;
        for (int i = l; i <= r; i++) {
            lmin = Math.min(lmin, arr[i]);
            lmax = Math.max(lmax, arr[i]);
            sum = sum + arr[i];
            if (arr[i] == target) {
            }
        }
        synchronized (mtx) {
            globalmin = Math.min(globalmin, lmin);
            globalMax = Math.max(globalMax, lmax);
            gsum += sum;
            // break;
        }
    }
}
