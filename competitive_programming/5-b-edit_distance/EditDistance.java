import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class EditDistance {
    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n, m;
        n = in.nextInt();
        m = in.nextInt();
        String u, w;
        u = in.next();
        w = in.next();
        int I, D, S;
        I = in.nextInt();
        D = in.nextInt();
        S = in.nextInt();

        int result = 0;

        // your code
        int[][] memo = new int[u.length()][w.length()];
        for (int i = 0; i < u.length(); i++)
            Arrays.fill(memo[i], -1);
        result = editDistance(u, w, u.length() - 1, w.length() - 1, I, D, S, memo);
        out.println(result);

        out.close();
    }

    private static int editDistance(String u, String w, int ui, int wi, int I, int D, int S, int[][] memo) {
        if (ui < 0) {
            return I * (wi + 1); // insert u
        }
        if (wi < 0) {
            return D * (ui + 1); // delete u
        }
        if (memo[ui][wi] != -1) {
            return memo[ui][wi];
        }

        // insertion
        int minCost = editDistance(u, w, ui, wi - 1, I, D, S, memo) + I; // insert u
        minCost = Math.min(minCost, editDistance(u, w, ui - 1, wi, I, D, S, memo) + D); // delete u
        if (u.charAt(ui) == w.charAt(wi)) {
            minCost = Math.min(minCost, editDistance(u, w, ui - 1, wi - 1, I, D, S, memo)); // keep
        } else {
            minCost = Math.min(minCost, editDistance(u, w, ui - 1, wi - 1, I, D, S, memo) + S); // sub
        }

        memo[ui][wi] = minCost;
        return minCost;
    }
}