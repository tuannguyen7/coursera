import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class MakeItSorted {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++)
            numbers[i] = in.nextInt();

        // your code
        long dp[][] = new long[n + 1][1001];
        Arrays.fill(dp[0], 0);
        for (int i = 1; i < n+1; i++)
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        for (int i = 1; i < n + 1; i++) {
            int nIndex = i -1;
            dp[i][1] = dp[i-1][1] + Math.abs(numbers[nIndex] - 1);
            for (int j = 2; j <= 1000; j++) {
                dp[i][j] = Math.min(dp[i][j-1], dp[i-1][j] + Math.abs(numbers[nIndex] - j));
            }
        }

        long result = Integer.MAX_VALUE;
        for (int i = 1; i < 1001; i++) {
            result = Math.min(result, dp[n][i]);
        }

        out.println(result);
        out.close();
    }
}
