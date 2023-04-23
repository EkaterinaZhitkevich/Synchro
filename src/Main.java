import java.util.*;

public class Main {
    public static String letters = "RLRFR";
    public static int length = 100;
    public static int threadCount = 1000;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException{

            Runnable myRunnable = () -> {
                String direction = generateRoute(letters, length);
                int count = countRightDirection(direction);
                synchronized ((sizeToFreq)) {
                    if (!sizeToFreq.containsKey(count)) {
                        sizeToFreq.put(count, 1);
                    } else {
                        int temp = sizeToFreq.get(count);
                        sizeToFreq.replace(count, temp, ++temp);
                    }
                }
            };
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            threads.add(new Thread(myRunnable));
        }
        for (Thread thread:threads) {
           thread.start();
        }
        for (Thread thread:threads){
            thread.join();
        }

        printMap(sizeToFreq);
    }


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countRightDirection(String direction) {
        int count = 0;
        for (int j = 0; j < direction.length(); j++) {
            if (direction.charAt(j) == 'R') {
                count++;
            }
        }
        return count;
    }

    public static void printMap(Map<Integer, Integer> map) {
        int max = map.values().stream().max(Integer::compareTo).get();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry:map.entrySet()) {
  if (entry.getValue() == max){
      if (entry.getValue() == 2 || entry.getValue() == 3 || entry.getValue() == 4){
          stringBuilder.append("Самое частое количество повторений - ")
                  .append(entry.getKey())
                  .append(" (встретилось ").append(entry.getValue()).append(" раза)")
                  .append("\n");
      } else {
          stringBuilder.append("Самое частое количество повторений - ")
                  .append(entry.getKey())
                  .append(" (встретилось ").append(entry.getValue()).append(" раз)")
                  .append("\n");
      }
  }

        }
        stringBuilder.append("Другие размеры:").append("\n");
        for (Map.Entry<Integer, Integer> entry:map.entrySet()){
            if (entry.getValue() == max) {
             continue;
            }
            if (entry.getValue() == 2 || entry.getValue() == 3 || entry.getValue() == 4){
                stringBuilder.append(" - ").append(entry.getKey())
                        .append(" (").append(entry.getValue())
                        .append(" раза)").append("\n");
            }else {
                stringBuilder.append(" - ").append(entry.getKey())
                        .append(" (").append(entry.getValue())
                        .append(" раз)").append("\n");
            }
            }
        System.out.println(stringBuilder.toString());
        }
        
    }

