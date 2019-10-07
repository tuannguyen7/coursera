
import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixArrayLong {
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
    public int[] computeSuffixArray(String text) {
        int[] result = new int[text.length()];

        // write your code here
        int[] order = sortCharacter(text);
        int[] classes = createClass(order, text);
        int L = 1; // cur partial
        while (L < text.length()) {
            order = sortDouble(text, order, classes, L);
            classes = updateClasses(order, classes, L);
            L = L * 2;
        }

        return order;
    }

    public int[] sortDouble(String text, int[] order, int[] classes, int L) {
        int[] newOrder = new int[order.length];
        int LEN = text.length();
        int[] count = new int[text.length()];
        int[] startPos = new int[text.length()];
        for (int clazz : classes) {
            count[clazz]++;
        }
        startPos[0] = 0;
        for (int i = 1; i < count.length; i++) {
            startPos[i] = startPos[i - 1] + count[i - 1];
        }

        // sắp xếp theo nữa phần đầu tức là i - L
        for (int i = 0; i < LEN; i++) {
            int start = (order[i] - L + LEN) % LEN;
            int classOf = classes[start];
            newOrder[startPos[classOf]] = start;
            startPos[classOf]++;
        }
        return newOrder;
    }

    public int[] updateClasses(int[] newOrder, int[] classes, int L) {
        int LEN = newOrder.length;
        int[] newClasses = new int[classes.length];
        newClasses[newOrder[0]] = 0;
        for (int i = 1; i < classes.length; i++) {
            int cur = newOrder[i];
            int prev = newOrder[i - 1];
            int mid = (cur + L) % LEN;
            int midPrev = (prev + L) % LEN;
            if (classes[cur] != classes[prev] || classes[mid] != classes[midPrev]) {
                newClasses[cur] = newClasses[prev] + 1;
            } else {
                newClasses[cur] = newClasses[prev];
            }

        }
        return newClasses;
    }

    /* sap xep theo index cua tung character trong text
    * */
    public int[] sortCharacter(String text) {
        int[] result = new int[text.length()];
        int[] startPos = new int[5];
        int[] count = new int[5];
        for (char c : text.toCharArray()) {
            count[letterToIndex(c)]++;
        }
        startPos[0] = 0;
        for (int i = 1; i < startPos.length; i++) {
            startPos[i] += startPos[i-1] + count[i-1];
        }

        // sort
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            result[startPos[letterToIndex(c)]] = i;
            startPos[letterToIndex(c)]++;
        }
        for (char c : text.toCharArray()) {
            startPos[letterToIndex(c)]++;
        }

        return result;
    }

    public int[] createClass(int[] order, String text) {
        int[] classes = new int[text.length()];
        int curClass = 0;
        classes[order[0]] = 0;
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(order[i]) != text.charAt(order[i - 1])) {
                curClass++;
            }
            classes[order[i]] = curClass;
        }
        return classes;
    }

    private int letterToIndex(char letter) {
        switch (letter) {
            case '$': return 0;
            case 'A': return 1;
            case 'C': return 2;
            case 'G': return 3;
            case 'T': return 4;
            default: return -1;
        }
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayLong().run();
        //new SuffixArrayLong().test();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffix_array = computeSuffixArray(text);
        print(suffix_array);
    }

    public void test() {
        String text = "ACACAA$";
        int[] order = {6, 0, 2, 4, 5, 1, 3};
        int[] classes = {1, 2, 1, 2, 1, 1, 0};
        int[] classes2 = sortDouble(text, order, classes, 1);
        print(classes2);
    }
}

