import java.io.PrintWriter;
import java.util.Scanner;

public class Incremental {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String num;
        num = in.next();
        int result = num.length();
        boolean all9 = true;

        for (char n : num.toCharArray()) {
            if (n != '9') {
                all9 = false;
                break;
            }
        }

        if (all9){
            result += 1;
        }

        out.println(result);

        in.close();
        out.close();
    }
}
