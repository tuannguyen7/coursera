import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class CheapestPermutation {

    private static int[] minPath;
    private static int minCost = Integer.MAX_VALUE;

    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n;
        n = in.nextInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                a[i][j] = in.nextInt();

        // your code
        int[] paths = new int[n];
        boolean[] visited = new boolean[n];
        cost(n, a, 0, 0, paths, visited);

        for (int i = 0; i < minPath.length; ++i) {
            if (i != 0) out.print(' ');
            out.print(minPath[i]);
        }
        out.println();

        in.close();
        out.close();
    }

    private static void cost(int n, int[][] a, int curLen, int curCost, int[] paths, boolean[] visited) {
        if (curLen == n) {
            if (curCost < minCost) {
                minPath = Arrays.copyOf(paths, n);
                minCost = curCost;
            }
        }
        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            paths[curLen] = i + 1;
            if (curLen > 0) {
                int prevIndex = paths[curLen - 1] - 1;
                cost(n, a, curLen + 1, curCost + a[i][prevIndex], paths, visited);
            } else {
                cost(n, a, curLen + 1, curCost, paths, visited);
            }
            visited[i] = false;
        }
    }
}