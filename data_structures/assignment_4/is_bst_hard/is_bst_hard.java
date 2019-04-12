import java.util.*;
import java.io.*;

public class is_bst_hard {
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

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }

    public class IsBST {
        class Node {
            long key;
            int left;
            int right;

            Node(long key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;
        long curMax = Long.MIN_VALUE;
        long curLeftMin = Long.MAX_VALUE;
        long curMIn = Long.MAX_VALUE;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextLong(), in.nextInt(), in.nextInt());
            }
        }

        boolean isBinarySearchTree() {
            // Implement correct algorithm here
            if (tree.length == 0)
                return true;
            Node root = tree[0];
            return inOrder(root, true);
        }

        long findMax(Node n) {
            if (n.right != -1)
                return findMax(tree[n.right]);
            return n.key;
        }

        long findMin(Node n) {
            if (n.left != -1)
                return findMin(tree[n.left]);
            return n.key;
        }

        boolean inOrder(Node n, boolean fromLeft) {
            boolean leftRes = true;
            if (n.left != -1) {
                long maxLeft = findMax(tree[n.left]);
                if (n.key <= maxLeft) {
                    return false;
                }
                leftRes = inOrder(tree[n.left], fromLeft);
            }
            if (!leftRes)
                return false;
            // curMax = n.key;
            if (n.right != -1) {
//              boolean rightRes = inOrder(tree[n.right], false);
                long minRight = findMin(tree[n.right]);
                if (n.key > minRight || (n.key == minRight && tree[n.right].key != minRight)) {
                    return false;
                }
                return inOrder(tree[n.right], false);
            }
            return true;
        }

        public boolean solve() {
            return isBinarySearchTree();
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst_hard().run();
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
