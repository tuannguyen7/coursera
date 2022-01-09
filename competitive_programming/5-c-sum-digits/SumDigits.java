import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class SumDigits {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int S = in.nextInt();
        int L = in.nextInt();

        // your code
        long dp[][] = new long[L][S + 1];
        for (int i = 0; i < L; i++)
            Arrays.fill(dp[i], 0);
        for (int i = 0; i <= Math.min(9, S); i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < L; i++)
            for (int j = 0; j <= S; j++) {
                for (int k = 0; k <= 9; k++) {
                    if (i == L - 1 && k == 0) continue; // no leading zero
                    if (j - k < 0) break;
                    dp[i][j] += dp[i - 1][j - k];
                }
            }

        long result = dp[L-1][S];
        out.println(result);

        out.close();
    }
}
