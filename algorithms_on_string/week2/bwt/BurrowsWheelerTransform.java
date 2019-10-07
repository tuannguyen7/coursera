
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BurrowsWheelerTransform {
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

    String BWT(String text) {

        // write your code here
        List<String> mutations = createMutation(text);
        mutations.sort(String::compareTo);
        Collector<Character, StringBuilder, String> collector = Collector.of(StringBuilder::new, (result, letter) -> result.append(letter),
                (result1, result2) -> result1.append(result2), result -> result.toString());
        return mutations.stream().map(mutation -> mutation.charAt(mutation.length() - 1))
                .collect(collector);
    }

    List<String> createMutation(String text) {
        String doubleText = text + text;
        List<String> mutations = new LinkedList<>();
        for (int i = 0; i < text.length(); i++) {
            mutations.add(doubleText.substring(i, i + text.length()));
        }
        return mutations;
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
}

