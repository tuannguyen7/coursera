
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class SuffixTree {
    public String FullText;

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

    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding
    // substrings of the text) in any order.
    public List<String> computeSuffixTreeEdges(String text) {
        // Implement this function yourself
        FullText = "^" + text;
        List<String> patterns = makeListPatterns(text);
        patterns = patterns.stream().map(pat -> "^" + pat).collect(Collectors.toList());
        Vertex root = new Vertex(0, 1);
        root.addChild(new Vertex(1, text.length()));
        for (int patternStartingPos = 1; patternStartingPos < patterns.size(); patternStartingPos++) {
            String pattern = patterns.get(patternStartingPos);
            Triple triple = findFirstDiffIndex(root, pattern);

            Vertex foundVertex = triple.vertex;
            int startingIndexOfNewVertex = triple.patDiffIndex + patternStartingPos;
            int lengthOfNewVertex = pattern.length() - triple.patDiffIndex;

            Vertex newVertex = new Vertex(startingIndexOfNewVertex, lengthOfNewVertex);
            if (triple.foundVertexDiffIndex == -1) { // reach end
                foundVertex.addChild(newVertex);
            } else {
                // Thực hiện tách
                // Tách ra tại diff
                // set lại vertex parent cũ
                Vertex tailVertex = new Vertex(triple.foundVertexDiffIndex, foundVertex.length - triple.foundVertexDiffIndex + foundVertex.startIndex);
                tailVertex.setChildren(foundVertex.getChildren());
                foundVertex.setLength(triple.foundVertexDiffIndex - foundVertex.startIndex);
                foundVertex.setChildren(new ArrayList<>());
                foundVertex.addChild(tailVertex);
                foundVertex.addChild(newVertex);
            }
        }
        return buildResult(root, "^" + text);
    }

    private List<String> buildResult(Vertex root, String text) {
        Queue<Vertex> queue = new LinkedList<>();
        List<String> result = new LinkedList<>();
        queue.addAll(root.getChildren());
        while (queue.size() != 0) {
            Vertex vertex = queue.poll();
            queue.addAll(vertex.getChildren());
            result.add(text.substring(vertex.getStartIndex(), vertex.getStartIndex() + vertex.getLength()));
        }
        return result;
    }

    /**
     * Triple - diffIndex: sô thứ tự kí tự bat dau khac nhau giua text va pattern
     *      - vertx: foundVertex
     * */
    public Triple findFirstDiffIndex(Vertex vertex, String text) {
        Vertex curVertex = vertex;
        Vertex prev;
        int curVertexIndex = curVertex.getStartIndex();
        int curTextIndex = 0;
        while (true){
            if (curVertexIndex == curVertex.length + curVertex.startIndex) { // end
                prev = curVertex;
                curVertex = findChildVertex(curVertex, text.charAt(curTextIndex));
                if (curVertex == null)
                    break;
                curVertexIndex = curVertex.startIndex; // reset
            } else if (curVertex.charAt(curVertexIndex) != text.charAt(curTextIndex)) {
                return new Triple(curTextIndex, curVertexIndex, curVertex);
            } else {
                curVertexIndex++;
                curTextIndex++;
            }
        }
        return new Triple(curTextIndex, -1, prev);
    }

    private Vertex findChildVertex(Vertex vertex, char letter) {
        return vertex.getChildStartedBy(letter);
    }

    private List<String> makeListPatterns(String text) {
        List<String> patterns = new LinkedList<>();
        for (int i = 0; i < text.length(); i++) {
            patterns.add(text.substring(i));
        }
        return patterns;
    }


    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void print(List<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        List<String> edges = computeSuffixTreeEdges(text);
        print(edges);
    }

    class Vertex {
        private int startIndex;
        private int length;
        private List<Vertex> children;

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getEndIndex() {
            return length + startIndex;
        }
        public List<Vertex> getChildren() {
            return children;
        }

        public void setChildren(List<Vertex> children) {
            this.children = children;
        }

        public Vertex(int startIndex, int length) {
            this.startIndex = startIndex;
            this.length = length;
            children = new ArrayList<>();
        }

        public void addChild(Vertex vertex) {
            children.add(vertex);
        }

        public Vertex getChildStartedBy(char letter) {
            for (Vertex v: children) {
                if (v.firstLetter() == letter)
                    return v;
            }
            return null;
        }

        public boolean isEnd() {
            return children.get(0).contains('$');
        }

        public int charAt(int index) {
            return FullText.charAt(index);
        }

        public int indexOfLetter(char letter) {
            for (int i = 0; i < this.length; i++) {
                if (FullText.charAt(i + startIndex) == letter)
                    return i;
            }
            return -1;
        }

        private char firstLetter() {
            return FullText.charAt(startIndex);
        }

        public boolean contains(char letter) {
            return indexOfLetter(letter) != -1;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    FullText.substring(startIndex, startIndex + length) +
                    ", children=" + children +
                    '}';
        }
    }

    static class Triple {
        int patDiffIndex; // pattern diff index
        int foundVertexDiffIndex;
        Vertex vertex; // found vertex

        public Triple(int patDiffIndex, int foundVertexDiffIndex, Vertex vertex) {
            this.patDiffIndex = patDiffIndex;
            this.vertex = vertex;
            this.foundVertexDiffIndex = foundVertexDiffIndex;
        }
    }
}


