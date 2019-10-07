
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class KnuthMorrisPratt {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    // Find all the occurrences of the pattern in the text and return
    // a list of all positions in the text (starting from 0) where
    // the pattern starts in the text.
    public List<Integer> findPattern(String pattern, String text) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        // Implement this function yourself
        String combine = pattern + "$" + text;
        int[] prefixArray = makePrefixArray(combine);
        for (int i = pattern.length(); i < prefixArray.length; i++) {
            if (prefixArray[i] == pattern.length())
                result.add(i - pattern.length()*2);
        }
        return result;
    }

    public int[] makePrefixArray(String pattern) {
        int[] prefixArray = new int[pattern.length()];
        prefixArray[0] = 0;
        int border = 0;
        for (int i = 1; i < pattern.length(); i++) {
            if (pattern.charAt(i) == pattern.charAt(border)) {
                prefixArray[i] = prefixArray[i - 1] + 1;
                border++;
                continue;
            }
            // charAt i not equals charAt border
            int prevPrefix = prefixArray[i-1];
            while(prevPrefix > 0) {
                if (pattern.charAt(prevPrefix) == pattern.charAt(i)) {
                    break;
                }
                prevPrefix = prefixArray[prevPrefix - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(prevPrefix)) {
                prefixArray[i] = prevPrefix + 1;
                border = prevPrefix + 1;
            } else {
                prefixArray[i] = 0;
                border = 0;
            }
        }

        return prefixArray;
    }

    static public void main(String[] args) throws IOException {
        new KnuthMorrisPratt().run();
    }

    public void print(List<Integer> x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String pattern = scanner.next();
        String text = scanner.next();
        List<Integer> positions = findPattern(pattern, text);
        print(positions);
    }
}


