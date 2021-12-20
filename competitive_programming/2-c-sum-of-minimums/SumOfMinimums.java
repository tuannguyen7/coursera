import java.io.PrintWriter;
import java.util.Scanner;

public class SumOfMinimums {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }

        // your code
        long result = solve(arr);
        out.println(result);

        in.close();
        out.close();
    }

    private static long solve(int[] arr) {
        long sum = 0;
        for (int len = 0; len < arr.length; len++) {
            for (int i = 0; i < arr.length - len; i++) {
                sum += min(arr, i, i + len);
            }
        }
        return sum;
    }

    private static int min(int[] arr, int start, int end) {
        int min = Integer.MAX_VALUE;
        for (; start <= end; start++) {
            min = Math.min(arr[start], min);
        }
        return min;
    }
}
