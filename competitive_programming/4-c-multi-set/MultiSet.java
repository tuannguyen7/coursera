import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MultiSet {

    public static void main(String[] arg) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n;
        n = in.nextInt();
        int[] l = new int[n];
        int[] r = new int[n];
        int[] occurrences = new int[100002];
        Arrays.fill(occurrences, 0);
        for (int i = 0; i < n; ++i) {
            l[i] = in.nextInt();
            r[i] = in.nextInt();
        }
        int minNumber = l[0];
        int maxNumber = r[0];

        // your code
        for (int i = 0; i < n; i++) {
            occurrences[l[i]] += 1;
            occurrences[r[i] + 1] -= 1;
            minNumber = Math.min(minNumber, l[i]);
            maxNumber = Math.max(maxNumber, r[i]);
        }

        print(occurrences, minNumber, maxNumber, out);
        out.close();
    }

    private static void print(int[] occurrences, int min, int max, PrintWriter out) {
        int occ = 0;
        for (int i = min; i <= max; i++) {
            occ += occurrences[i];
            if (occ != 0) {
                out.printf("%d %d\n", i, occ);
            }
        }
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
