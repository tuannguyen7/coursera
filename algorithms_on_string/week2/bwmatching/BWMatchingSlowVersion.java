
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Slow version
 * */
public class BWMatchingSlowVersion {
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

    private Map<Integer, Integer> createLastToFirst(String bwt, char[] firstCol) {
        Map<Integer, Integer> lastToFirst = new HashMap<>();
        int[] position = {0, 0, 0, 0, 0};
        int[] startPos = getStartPosition(firstCol);
        int lastIndex = 0;
        for (char c : bwt.toCharArray()) {
            int firstIndex = startPos[letterToIndex(c)] + position[letterToIndex(c)];
            lastToFirst.put(lastIndex, firstIndex);
            position[letterToIndex(c)]++;
            lastIndex++;
        }
        return lastToFirst;
    }

    private int[] solve(String bwt, String[] patterns) {
        char[] firstCol = bwt.toCharArray();
        char[] lastCol = bwt.toCharArray();
        Arrays.sort(firstCol);
        Map<Integer, Integer> lastToFirst = createLastToFirst(bwt, firstCol);
        int[] result = new int[patterns.length];
        for (int i = 0; i < patterns.length; ++i) {
            result[i] = solve(firstCol, lastCol, patterns[i], lastToFirst);
        }
        return result;
    }

    private int solve(char[] firstCol, char[] lastCol, String pattern, Map<Integer, Integer> lastToFirst) {
        int top = 0;
        int bottom = firstCol.length - 1;
        int patIndex = pattern.length() - 1;
        while (top <= bottom) {
            Pair pair = findTopIndexAndLastIndexOfLetter(lastCol, pattern.charAt(patIndex), top, bottom);
            if (pair == null) {
                return 0;
            }
            int topIndex = pair.left;
            int botIndex = pair.right;
            top = Math.min(lastToFirst.get(topIndex), lastToFirst.get(botIndex));
            bottom = Math.max(lastToFirst.get(topIndex), lastToFirst.get(botIndex));
            patIndex--;
            if (patIndex == -1) {
                return bottom - top + 1;
            }
        }
        return -1;
    }

    private Pair findTopIndexAndLastIndexOfLetter(char[] lastCol, char letter, int begin, int end) {
        int topIndex = -1;
        int lastIndex = -1;
        for (int index = begin; index <= end; index++) {
            if (lastCol[index] == letter) {
                topIndex = index;
                break;
            }
        }

        if (topIndex == -1) {
            return null;
        }

        for (int index = end; index >= topIndex; index--) {
            if (lastCol[index] == letter) {
                lastIndex = index;
                break;
            }
        }

        return new Pair(topIndex, lastIndex);
    }

    private int[] getStartPosition(char[] firstCol) {
        int[] startPos = {0, 0, 0, 0, 0};
        for (int i = 1; i < firstCol.length; i++) {
            if (firstCol[i] != firstCol[i-1]) {
                startPos[letterToIndex(firstCol[i])] = i;
            }
        }
        return startPos;
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
        new BWMatchingSlowVersion().run();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        int patternCount = scanner.nextInt();
        String[] patterns = new String[patternCount];
        for (int i = 0; i < patternCount; ++i) {
            patterns[i] = scanner.next();
        }
        int[] result = solve(bwt, patterns);
        print(result);
    }

    static class Pair {
        int left;
        int right;

        public Pair(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

}

