import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class SuffixArray {
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

    public class Suffix implements Comparable {
        String suffix;
        int start;

        Suffix(String suffix, int start) {
            this.suffix = suffix;
            this.start = start;
        }

        @Override
        public int compareTo(Object o) {
            Suffix other = (Suffix) o;
            return suffix.compareTo(other.suffix);
        }
    }

    // Build suffix array of the string text and
    // return an int[] result of the same length as the text
    // such that the value result[i] is the index (0-based)
    // in text where the i-th lexicographically smallest
    // suffix of text starts.
    public List<Integer> computeSuffixArray(String text) {
        // write your code here
        List<Pair> mutation = makeMutation(text);
        mutation.sort((a, b) -> {
            return a.text.compareTo(b.text);
        });
        return mutation.stream().map(a -> a.index).collect(Collectors.toList());
    }

    private List<Pair> makeMutation(String text) {
        List<Pair> mutation = new LinkedList<>();
        for (int i = 0; i < text.length(); i++) {
            String tmp = text.substring(i);
            mutation.add(new Pair(i, tmp));
        }
        return mutation;
    }

    static public void main(String[] args) throws IOException {
        new SuffixArray().run();
    }

    public void print(List<Integer> x) {
        for (Integer a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        List<Integer> SuffixArray = computeSuffixArray(text);
        print(SuffixArray);
    }

    static class Pair {
        int index;
        String text;

        public Pair(int index, String text) {
            this.index = index;
            this.text = text;
        }
    }
}


