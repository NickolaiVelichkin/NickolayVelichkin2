package hw7;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CollectionPerformanceExperiment {
    private static final int N = 10_000;
    private static long checksum = 0;

    public static void main(String[] args) {
        System.out.printf("%-30s | %-8s | %-15s | %-15s%n", "Operation", "Count", "ArrayList (ns)", "LinkedList (ns)");
        System.out.println("-----------------------------------------------------------------------------");
        for (int op = 1; op <= 6; op++) {
            long arrayTime = measure(op, true);
            long linkedTime = measure(op, false);
            System.out.printf("%-30s | %-8d | %-15d | %-15d%n", getName(op), N, arrayTime, linkedTime);
        }
        System.out.println("Checksum: " + checksum);
        System.out.println("\nВыводы:");
        System.out.println("1. Добавление в конец у обеих коллекций занимает амортизированное O(1), но ArrayList быстрее за счет локальности памяти и отсутствия создания узлов Node.");
        System.out.println("2. Добавление и удаление из начала у LinkedList работает за O(1) через смену ссылок, а у ArrayList требует O(n) на каждый сдвиг элементов массива.");
        System.out.println("3. Вставка в середину у обоих списков работает за O(n): ArrayList сдвигает половину массива, а LinkedList ищет середину перебором по ссылкам.");
        System.out.println("4. При вставке в середину ArrayList быстрее, так как копирование памяти через System.arraycopy эффективнее для кэша процессора, чем обход узлов LinkedList.");
        System.out.println("5. Доступ по индексу get(i) в ArrayList выполняется за O(1) (весь цикл O(n)), а в LinkedList каждый вызов идет по ссылкам за O(n), давая в сумме O(n^2).");
        System.out.println("6. Удаление с конца выполняется за O(1) в обеих коллекциях, так как сдвиг элементов не нужен, а у LinkedList есть ссылка на последний узел.");
    }

    private static String getName(int op) {
        if (op == 1) return "Add to end";
        if (op == 2) return "Add to beginning";
        if (op == 3) return "Insert in middle";
        if (op == 4) return "Get by index";
        if (op == 5) return "Remove from beginning";
        return "Remove from end";
    }

    private static long measure(int op, boolean useArrayList) {
        long totalTime = 0;
        for (int run = 0; run < 7; run++) {
            List<Integer> list = useArrayList ? new ArrayList<>() : new LinkedList<>();
            if (op >= 4) {
                for (int i = 0; i < N; i++) {
                    list.add(i);
                }
            }

            long start = System.nanoTime();
            if (op == 1) {
                for (int i = 0; i < N; i++) list.add(i);
            } else if (op == 2) {
                for (int i = 0; i < N; i++) list.add(0, i);
            } else if (op == 3) {
                for (int i = 0; i < N; i++) list.add(list.size() / 2, i);
            } else if (op == 4) {
                for (int i = 0; i < N; i++) checksum += list.get(i);
            } else if (op == 5) {
                for (int i = 0; i < N; i++) checksum += list.remove(0);
            } else if (op == 6) {
                for (int i = 0; i < N; i++) checksum += list.remove(list.size() - 1);
            }
            long time = System.nanoTime() - start;

            checksum += list.size();
            if (run >= 2) {
                totalTime += time;
            }
        }
        return totalTime / 5;
    }
}