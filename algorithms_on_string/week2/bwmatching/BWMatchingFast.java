
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Improved version
 * */
public class BWMatchingFast {
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

    private Map<Character, int[]> createCountArray(String bwt) {
        char[] chars = {'$', 'A', 'C', 'G', 'T'};
        Map<Character, int[]> symbolToCountArray = new HashMap<>();
        for (Character c : chars) {
            int[] empty = new int[bwt.length() + 1];
            empty[0] = 0;
            symbolToCountArray.put(c, empty);
        }

        int index = 1;
        for (char c : bwt.toCharArray()) {
            int[] countArr = symbolToCountArray.get(c);
            countArr[index] = countArr[index-1] + 1;

            for (char other : chars) {
                if (other != c) {
                    int[] otherArr = symbolToCountArray.get(other);
                    otherArr[index] = otherArr[index-1];
                }
            }
            index++;
        }

        return symbolToCountArray;
    }

    private int[] solve(String bwt, String[] pattern) {
        char[] firstCol = bwt.toCharArray();
        char[] lastCol = bwt.toCharArray();
        Arrays.sort(firstCol);
        Map<Character, int[]> symbolToCountArrays = createCountArray(bwt);
        int[] startPosFirstCol = getStartPosition(firstCol);
        int[] result = new int[pattern.length];
        for (int i = 0; i < pattern.length; ++i) {
            result[i] = solve(startPosFirstCol, lastCol, pattern[i], symbolToCountArrays);
        }
        return result;
    }

    private int solve(int[] startPosFirstCol, char[] lastCol, String pattern, Map<Character, int[]> symbolToCountArray) {
        int top = 0;
        int bottom = lastCol.length - 1;
        int patIndex = pattern.length() - 1;
        while (top <= bottom) {
            char letter = pattern.charAt(patIndex);
            if (isLetterInRange(symbolToCountArray.get(letter), top, bottom)) { // letter occur between top and bottom
                top = startPosFirstCol[letterToIndex(letter)] + symbolToCountArray.get(letter)[top];
                bottom = startPosFirstCol[letterToIndex(letter)] + symbolToCountArray.get(letter)[bottom + 1] - 1;
                patIndex--;
                if (patIndex == -1) {
                    return bottom - top + 1;
                }
            } else {
                return 0;
            }
        }
        return -1;
    }

    private boolean isLetterInRange(int[] countArray, int top, int bottom) {
        return countArray[bottom + 1] > countArray[top];
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
        new BWMatchingFast().run();
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
        for (int i = 0; i < patternCount; i++)
            patterns[i] = scanner.next();
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

