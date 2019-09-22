
import java.io.*;
import java.util.*;

public class TrieMatchingExtended implements Runnable {

    List <Integer> solve (String text, int n, List <String> patterns) {
        List <Integer> result = new ArrayList <Integer> ();

        // write your code here
        Vertex root = makeTrie(patterns);
        for (int i = 0; i < text.length(); i++){
            Vertex cur = root;
            for (int charIndex = i; charIndex < text.length(); charIndex++) {
                cur = cur.getChild(text.charAt(charIndex));
                if (cur == null || cur.isEnd()) {
                    break;
                }
            }
            // not found
            if (cur != null && cur.isEnd()) {
                result.add(i);
            }
        }
        return result;
    }

    Vertex makeTrie(List<String> patterns) {
        Vertex root = new Vertex('^');
        for (String pat : patterns) {
            Vertex parent = root;
            for (char letter : pat.toCharArray()) {
                Vertex found = parent.getChild(letter);
                if (found == null) {
                    found = new Vertex(letter);
                    parent.addChild(found);
                }
                parent = found;
            }
            // add ending letter to head of array
            parent.children.add(0, new Vertex('$'));
        }
        return root;
    }

    public void run () {
        try {
            BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
            String text = in.readLine ();
            int n = Integer.parseInt (in.readLine ());
            List <String> patterns = new ArrayList <String> ();
            for (int i = 0; i < n; i++) {
                patterns.add (in.readLine ());
            }

            List <Integer> ans = solve (text, n, patterns);

            for (int j = 0; j < ans.size (); j++) {
                System.out.print ("" + ans.get (j));
                System.out.print (j + 1 < ans.size () ? " " : "\n");
            }
        }
        catch (Throwable e) {
            e.printStackTrace ();
            System.exit (1);
        }
    }

    public static void main (String [] args) {
        new Thread (new TrieMatchingExtended ()).start ();
    }


    class Vertex {
        public char letter;
        public List<Vertex> children;

        public Vertex(char letter) {
            this.letter = letter;
            children = new ArrayList<>();
        }

        public void addChild(Vertex vertex) {
            children.add(vertex);
        }

        public boolean hasChild(char letter) {
            return children.stream().map(Vertex::getLetter).anyMatch(l -> l == letter);
        }

        public Vertex getChild(char letter) {
            for (Vertex v: children) {
                if (v.getLetter() == letter)
                    return v;
            }

            return null;
        }

        public boolean isEnd() {
            return children.get(0).getLetter() == '$';
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

