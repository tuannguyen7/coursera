import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Manhattan {
    public static void main(String[] arg) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n;
        n = in.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];

        for (int i = 0; i < n; ++i) {
            x[i] = in.nextInt();
            y[i] = in.nextInt();
        }

        int[] f = new int[n];
        int[] s = new int[n];
        int maxSumIndex = 0;
        int minSumIndex = 0;
        int maxSubIndex = 0;
        int minSubIndex = 0;
        int maxDis = 0;

        // your code
        f[0] = 1;
        s[0] = 1;
        for (int i = 1; i < n; i++) {
            int d1 = distance(x, y, i, maxSumIndex);
            int d2 = distance(x, y, i, minSumIndex);
            int d3 = distance(x, y, i, maxSubIndex);
            int d4 = distance(x, y, i, minSubIndex);
            int curMaxDis = d1;
            int leftIndex = maxSumIndex;
            if (d2 > curMaxDis) {
                leftIndex = minSumIndex;
                curMaxDis = d2;
            }
            if (d3 > curMaxDis) {
                leftIndex = maxSubIndex;
                curMaxDis = d3;
            }
            if (d4 > curMaxDis) {
                leftIndex = minSubIndex;
                curMaxDis = d4;
            }

            if (curMaxDis > maxDis) {
                maxDis = curMaxDis;
                f[i] = leftIndex + 1;
                s[i] = i + 1;
            } else {
                f[i] = f[i-1];
                s[i] = s[i-1];
            }

            if (x[i] + y[i] > x[maxSumIndex] + y[maxSumIndex]) {
                maxSumIndex = i;
            }
            if (x[i] + y[i] < x[minSumIndex] + y[minSumIndex]) {
                minSumIndex = i;
            }
            if (x[i] - y[i] > x[maxSubIndex] - y[maxSubIndex]) {
                maxSubIndex = i;
            }
            if (x[i] - y[i] < x[minSubIndex] - y[minSubIndex]) {
                minSubIndex = i;
            }
        }

        for (int i = 0; i < n; ++i) {
            out.println(f[i] + " " + s[i]);
        }

        out.close();
    }

    private static int distance(int[] x, int[] y, int index1, int index2) {
        return Math.abs(x[index1] -x[index2]) + Math.abs(y[index1] - y[index2]);
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}