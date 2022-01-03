import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class FrequentSymbol {
    public static void main(String[] arg) {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String s;
        s = in.next();
        int q;
        q = in.nextInt();
        int[] l = new int[q];
        int[] r = new int[q];
        for (int i = 0; i < q; ++i) {
            l[i] = in.nextInt();
            r[i] = in.nextInt();
        }

        char[] result = new char[q];

        // your code
        HashMap<Character, Integer>[] prefixSum = makePrefix(s);
        for (int i = 0; i < q; i++) {
            int li = l[i];
            int ri = r[i];
            HashMap<Character, Integer> lPrefix = prefixSum[li - 1];
            HashMap<Character, Integer> rPrefix = prefixSum[ri];
            int maxCountChar = 0;
            char curChr = '0';
            for (Map.Entry<Character, Integer> entry : rPrefix.entrySet()) {
                char chr = entry.getKey();
                int count = entry.getValue() - lPrefix.getOrDefault(chr, 0);
                if (count > maxCountChar) {
                    maxCountChar = count;
                    curChr = chr;
                }
            }
            result[i] = curChr;
        }

        for (int i = 0; i < result.length; ++i) {
            out.println(result[i]);
        }

        out.close();
    }

    private static HashMap<Character, Integer>[] makePrefix(String s) {
        HashMap<Character, Integer>[] prefixSum = new HashMap[s.length() + 1];
        prefixSum[0] = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            HashMap<Character, Integer> m = (HashMap<Character, Integer>) prefixSum[i].clone();
            m.put(s.charAt(i), m.getOrDefault(s.charAt(i), 0)  + 1);
            prefixSum[i + 1] = m;
        }
        return prefixSum;
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