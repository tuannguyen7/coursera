
import java.util.*;
import java.io.*;

public class is_bst {
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

    public class IsBST {
        class Node {
            int key;
            int left;
            int right;
            
            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        boolean isBinarySearchTree() {
            // Implement correct algorithm here
            if (tree.length == 0)
                return true;
            Node root = tree[0];
            return isBinary(root, Integer.MAX_VALUE, true);
            //return isBinary(tree[root.left], root.key, true) && isBinary(tree[root.right], root.key, false);
        }

        boolean isBinary(Node n, int parentKey, boolean leftSide) {
            boolean leftResult = true;
            boolean rightResult = true;
            if (n.left != -1) {
                if (tree[n.left].key >= n.key)
                    return false;
                if (leftSide) {
                    if (tree[n.left].key >= parentKey)
                        return false;
                } else {
                    if (tree[n.left].key <= parentKey)
                        return false;
                }               
                leftResult = isBinary(tree[n.left], n.key, true);
            }
            if (!leftResult)
                return false;
            if (n.right != -1) {
                if (tree[n.right].key <= n.key)
                    return false;
                if (leftSide) {
                    if (tree[n.right].key >= parentKey)
                        return false;
                } else {
                    if (tree[n.right].key <= parentKey)
                        return false;
                }   
                rightResult = isBinary(tree[n.right], n.key, false);
            }
            return leftResult && rightResult;
        }
        
        public boolean solve() {
            return isBinarySearchTree();
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        ArrayList<String> a = new ArrayList<>();
        if (tree.solve()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}
