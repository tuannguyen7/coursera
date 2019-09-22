
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Trie {
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

    List<Map<Character, Integer>> buildTrie(String[] patterns) {
        List<Map<Character, Integer>> trie = new ArrayList<>();

        // write your code here
        int index = 0;
        trie.add(new HashMap<>());
        for (String pattern : patterns) {
            int parentIndex = 0;
            for (int i = 0; i < pattern.length(); i++) {

                char letter = pattern.charAt(i);
                Map<Character, Integer> curLayerMap = trie.get(parentIndex);
                if (!curLayerMap.containsKey(letter)) {
                    index++;
                    curLayerMap.put(letter, index);
                    parentIndex = index;
                    trie.add(new HashMap<>());
                } else {
                    parentIndex = curLayerMap.get(letter);
                }
            }
        }

        return trie;
    }

    static public void main(String[] args) throws IOException {
        new Trie().run();
    }

    public void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        List<Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);
    }

    static class Vertex {
        public char letter;
        public List<Vertex> children;

        public Vertex(char letter) {
            this.letter = letter;
            children = new ArrayList<>();
        }

        public void addChild(Vertex vertex) {
            children.add(vertex);
        }

        public Vertex getChild(char letter) {
            for (Vertex v : children) {
                if (v.getLetter() == letter)
                    return v;
            }

            return null;
        }

        public boolean isEnd() {
            return children.size() == 0;
        }

        public char getLetter() {
            return letter;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "letter=" + letter +
                    '}';
        }
    }
}

