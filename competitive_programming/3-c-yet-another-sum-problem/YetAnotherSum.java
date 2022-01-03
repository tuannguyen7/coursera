import java.io.PrintWriter;
import java.util.Scanner;

public class YetAnotherSum {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextDouble();
        }

        double result = 0;

        // your code
        for (int i = 0; i < n; i++) {
            result += arr[i];
        }
        for (int i = 0; i < n; i++) {
            result += 1.0/arr[i];
        }

        out.println(result);

        in.close();
        out.close();
    }
}
