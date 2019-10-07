
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InverseBWT {
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

    String inverseBWT(String bwt) {
        StringBuilder result = new StringBuilder();

        // write your code here
        String lastColumn = bwt;
        char[] tmp = bwt.toCharArray();
        Arrays.sort(tmp);
        String firstColumn = new StringBuilder().append(tmp).toString();

        // begin init
        fillStartingIndexOfChar(firstColumn);
        fillIndexToOrderLastCol(lastColumn);
        // end

        result.append('$');
        int nextCharPos = 0;
        for (int i = 0; i < bwt.length() - 1; i++) {
            char nextChar = lastColumn.charAt(nextCharPos);
            result.append(nextChar);
            int orderOfNextChar = getOrderOfIndex(nextCharPos); // order in last column, 0-base
            // then finding index of corresponding char and order in first column
            nextCharPos = getIndexOfCharOrderAt(nextChar, orderOfNextChar);
        }
        return result.reverse().toString();
    }

    // Begin Last column
    int[] indexToOrderLastCol;

    void fillIndexToOrderLastCol(String lastColumn) {
        int[] order = {0, 0, 0, 0, 0};
        indexToOrderLastCol = new int[lastColumn.length()];
        for (int i = 0; i < lastColumn.length(); i++) {
            switch (lastColumn.charAt(i)) {
                case '$': indexToOrderLastCol[i] = 0; break;
                case 'A': indexToOrderLastCol[i] = order[1]; order[1]++; break;
                case 'C': indexToOrderLastCol[i] = order[2]; order[2]++; break;
                case 'G': indexToOrderLastCol[i] = order[3]; order[3]++; break;
                case 'T': indexToOrderLastCol[i] = order[4]; order[4]++; break;
            }
        }
    }

    int getOrderOfIndex(int index) {
        return indexToOrderLastCol[index];
    }

    // End Last column

    // Begin first column
    int[] startingIndexOfCharInFirstCol = {0, 0, 0, 0, 0};
    /**
     * orderAt: 0-base
     * */
    int getIndexOfCharOrderAt(char letter, int orderAt) {
        switch (letter) {
            case '$': return startingIndexOfCharInFirstCol[0] + orderAt;
            case 'A': return startingIndexOfCharInFirstCol[1] + orderAt;
            case 'C': return startingIndexOfCharInFirstCol[2] + orderAt;
            case 'G': return startingIndexOfCharInFirstCol[3] + orderAt;
            case 'T': return startingIndexOfCharInFirstCol[4] + orderAt;
            default:
                throw new RuntimeException("not found letter");
        }
    }

    private void fillStartingIndexOfChar(String firstColumn) {
        for (int i = 1; i < firstColumn.length(); i++) {
            if (firstColumn.charAt(i) != firstColumn.charAt(i-1)) {
                startingIndexOfCharInFirstCol[charToIndex(firstColumn.charAt(i))] = i;
            }
        }
    }

    private int charToIndex(char c) {
        switch (c) {
            case '$': return 0;
            case 'A': return 1;
            case 'C': return 2;
            case 'G': return 3;
            case 'T': return 4;
            default:
                throw new RuntimeException("not found letter");
        }
    }
    // End of First column

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }
}


