import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ErasingMaximum {
    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n;
        n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i)
            a[i] = in.nextInt();

        int[] result = new int[n - 1];

        // your code
        int max = a[0];
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
                indexes.clear();
                indexes.add(i);
            } else if (a[i] == max) {
                indexes.add(i);
            }
        }

        int shiftIndex = indexes.get(0);
        if (indexes.size() >= 3) {
            shiftIndex = indexes.get(2);
        }
        for (int i = 0; i < shiftIndex; i++) {
            result[i] = a[i];
        }
        for (int i = shiftIndex+1; i < a.length; i++) {
            result[i-1] = a[i];
        }
        for (int i = 0; i < result.length; ++i) {
            if (i != 0) out.print(' ');
            out.print(result[i]);
        }
        out.println();

        in.close();
        out.close();
    }
}