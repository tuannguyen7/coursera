
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrences(Data input) {
        String s = input.pattern; 
        String t = input.text;
        Random r = new Random();
        long bigPrime = 1000000007;
        long x = 31;
        //x = r.nextInt(10007);
        List<Integer> occurrences = new ArrayList<Integer>();
        long patHash = polyHash(s, bigPrime, x);
        long[] hashes = precomputeHash(t, s.length(), bigPrime, x);
        for (int i = 0; i < t.length() - s.length() + 1; i++) {
            if (hashes[i] != patHash)
                continue;
            if (areEqual(s, t, i, i + s.length()))
                occurrences.add(i);
        }
        
        return occurrences;
    }

    private static boolean areEqual(String pat, String text, int start, int end) {
        for (int i = start; i < end; i++) {
            if (pat.charAt(i-start) != text.charAt(i))
                return false;
        }
        return true;
    }
    
    private static long polyHash(String pat, long bigPrime, long x) {
        long hashValue = 0;
        long y = 1;
        for (int i = 0; i < pat.length(); i++) {
            hashValue += ((pat.charAt(i)-64)*y)%bigPrime;
            hashValue = hashValue%bigPrime;
            y = (y*x)%bigPrime;
        }
        return hashValue;
    }
    
    private static long[] precomputeHash(String text, int patLen, long bigPrime, long x) {
        long[] hashes = new long[text.length() - patLen + 1];
        String s = text.substring(text.length()-patLen, text.length());
        hashes[hashes.length-1] = polyHash(s, bigPrime, x);
        long y = 1;
        for (int i = 0; i < patLen; i++)
            y = (y*x)%bigPrime;
        for (int i = text.length() - patLen - 1; i >= 0 ; i--) {
            hashes[i] = (hashes[i+1]*x - ((text.charAt(i+patLen)-64)*y)%bigPrime + (text.charAt(i)-64) + bigPrime)%bigPrime;
        }
        return hashes;
    }
    
    static class Data {
        String pattern;
        String text;

        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
