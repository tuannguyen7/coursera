import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class EvaluateExpression {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String expr = in.nextLine();
        // your code

        out.println(solve(expr));

        in.close();
        out.close();
    }

    private static long solve(String expr) {
        long curNum = 0;
        LinkedList<Long> numbers = new LinkedList<>();
        LinkedList<Character> operators = new LinkedList<>();
        for (char c : expr.toCharArray()) {
            if (c == '+' || c == '-') {
                numbers.addLast(curNum);
                operators.addLast(c);
                curNum = 0;
            } else {
                curNum = curNum*10 + (c-'0');
            }
        }
        numbers.addLast(curNum);
        while (!operators.isEmpty()) {
            char op = operators.pollFirst();
            long l = numbers.pollFirst();
            long r = numbers.pollFirst();
            if (op == '+') {
                numbers.addFirst(l + r);
            } else  {
                numbers.addFirst(l - r);
            }
        }

        return numbers.pop();
    }
}
