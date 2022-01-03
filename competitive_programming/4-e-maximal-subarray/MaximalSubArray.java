import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MaximalSubArray {

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n;
        n = in.nextInt();
        int[] a = new int[n];
        long[] result = new long[n];
        for (int i = 0; i < n; ++i) {
            a[i] = in.nextInt();
        }

        // your code
        long[] auxArr1 = new long[n];
        long[] auxArr2 = new long[n];
        auxArr1[0] = a[0];
        auxArr2[n-1] = a[n-1];
        for (int i = 1; i < n; i++) {
            auxArr1[i] = Math.max(auxArr1[i-1] + a[i], a[i]);
        }
        for (int i = n-2; i >= 0; i--) {
            auxArr2[i] = Math.max(auxArr2[i+1] + a[i], a[i]);
        }

        result[0] = auxArr2[0];
        result[n-1] = auxArr1[n-1];
        for (int i = 1; i < n - 1; i++) {
            result[i] = Math.max(auxArr1[i], auxArr2[i]);
            result[i] = Math.max(result[i], auxArr1[i-1] + auxArr2[i]);
        }

        for (int i = 0; i < n; i++) {
            out.println(result[i]);
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
