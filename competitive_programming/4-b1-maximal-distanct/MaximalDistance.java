import java.io.*;
import java.util.StringTokenizer;

public class MaximalDistance {
    public static void main(String[] arg) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n;
        n = in.nextInt();
        int[] x = new int[n];

        for (int i = 0; i < n; ++i) {
            x[i] = in.nextInt();
        }

        int[] f = new int[n];
        int[] s = new int[n];


        // your code
        int curMinIndex = 0;
        int curMaxIndex = 0;
        for (int i = 0; i < n; i++) {
            if (x[i] > x[curMaxIndex]) {
                curMaxIndex = i;
            }
            if (x[i] < x[curMinIndex]) {
                curMinIndex = i;
            }
            f[i] = Math.min(curMinIndex, curMaxIndex) + 1;
            s[i] = Math.max(curMinIndex, curMaxIndex) + 1;
        }

        for (int i = 0; i < n; ++i) {
            out.println(f[i] + " " + s[i]);
        }

        out.close();
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
