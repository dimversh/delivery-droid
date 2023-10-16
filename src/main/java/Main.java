import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            synchronized (sizeToFreq) {
                Runnable runnable = () -> {
                    String generatedRoute = generateRoute("RLRFR", 100);

                    int countOfR = 0;
                    for (int j = 0; j < generatedRoute.length(); j++) {
                        if (generatedRoute.charAt(j) == 'R') {
                            countOfR++;
                        } else {
                            if (countOfR == 1)
                                sizeToFreq.put(countOfR, 1);
                            if (countOfR > 0) {
                                sizeToFreq.put(countOfR, sizeToFreq.getOrDefault(countOfR, 0) + 1);
                            }
                            countOfR = 0;
                        }
                    }
                    if (countOfR > 0) {
                        sizeToFreq.put(countOfR, sizeToFreq.getOrDefault(countOfR, 0) + 1);
                    }
                };
                executor.execute(runnable);
            }



        }

        executor.shutdown();

        while (!executor.isTerminated()) {

        }
        System.out.println("Executor закончил работу!");
        int more = 0;
        int morecount = 0;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            int frequency = entry.getKey();
            int count = entry.getValue();
            if (morecount < count) {
                morecount = count;
                more = frequency;
            }

            System.out.println("Другие размеры: " + frequency + ": " + count);

        }
        System.out.println("Самое частое число повторений = " + more);


    }


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
