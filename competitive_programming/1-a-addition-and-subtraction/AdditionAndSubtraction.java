import java.io.PrintWriter;
import java.util.Scanner;

public class AdditionAndSubtraction {
    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int x, y, z;
        x = in.nextInt();
        y = in.nextInt();
        z = in.nextInt();

        int result = solve(x, y, z);

        out.println(result);

        in.close();
        out.close();
    }

    private static int solve(int x, int y, int z) {
        int result = -1;
        if (x == y) {
            if (z == 0) return 0;
            if (z == x) return 1;
            return -1;
        }
        // your code
        if (z%(x-y) == 0) {
            int thuong = z/(x-y);
            if (thuong >= 0) {
                result = thuong*2;
            }
        }
        if ((z-x)%(x-y) == 0) {
            int thuong = (z-x)/(x-y);
            if (thuong >= 0) {
                if (result != -1) {
                    result = Math.min(thuong*2 + 1, result);
                } else {
                    result = thuong*2 + 1;
                }
            }
        }
        return result;
    }
}