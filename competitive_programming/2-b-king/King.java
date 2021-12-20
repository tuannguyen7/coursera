import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class King {

    private static int[][] nextMoves = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int r, c;
        r = in.nextInt();
        c = in.nextInt();

        int result = 0;

        // your code
        int totalEmptyCells = (int)(Math.ceil(((float)r)/3)*Math.ceil(((float)c)/3));
        result = r*c - totalEmptyCells;

        out.println(result);

        in.close();
        out.close();
    }
}