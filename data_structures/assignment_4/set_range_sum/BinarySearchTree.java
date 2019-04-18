
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class BinarySearchTree {

	private Vertex root;
	
	class Vertex {
        int key;
        // Sum of all the keys in the subtree - remember to update
        // it after each operation that changes the tree.
        long sum;
        long leftSum;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(int key, long sum, Vertex left, Vertex right, Vertex parent) {
            this.key = key;
            this.sum = sum;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }
	/////////////////////////////////////////////////////
	// binary search tree basic operations
	
	/**
	 * return next largest element, return null if input vertex is max of tree
	 * */
	Vertex next(Vertex v) {
		Vertex tmp;
		if (v.right != null) {
			tmp = v.right;
			while (tmp.left != null)
				tmp = tmp.left;
			
		} else {
			tmp = v.parent;
			while (tmp != null && v.key > tmp.key) {
				v = tmp;
				tmp = tmp.parent;
			}
		}
		
		return tmp;
	}
	
	/**
	 * return Vertex with given key. if not found, return nearest
	 * */
	Vertex find(int key) {
		Vertex iter = root;
		if (root == null)
			return null;
		while (iter.key != key) {
			if (iter.key < key) {
				if (iter.right == null)
					return iter;
				iter = iter.right;
			} else {
				if (iter.left == null)
					return iter;
				iter = iter.left;
			}
		}
		return iter;
	}
	
	boolean isKeyInBST(int key) {
		Vertex found = find(key);
		return found !=null && found.key == key;
	}
	
	/**
	 * insert key into BST.
	 * */
	Vertex insert(int key) {
		Vertex found = find(key);
		Vertex newNode = new Vertex(key, 0, null, null, found);
		if (found == null) {
			root = newNode;
			return newNode;
		}
		
		if (found.key < key) {
			found.right = newNode; 
		} else {
			found.left = newNode;
		}
		update(newNode);
		return newNode;
	}
	
	/**
	 * update leftSum and its parent
	 * */
	void update(Vertex v) {
		if (v == null)
			return;
		v.leftSum = (v.left != null ? v.left.key : 0) + (v.right != null ? v.right.key : 0);
		update(v.parent);
	}
	
	
	void erase(int key) {
		delete(key);
	}
	
	void delete(int key) {
		Vertex found = find(key);
		if (found == null || found.key != key)
			return;	// not found
		if (found.right == null) {
			Vertex oldPar = found.parent;
			// promote found.left
			if (found != root) { // not root
				if (found.parent.left == found) {	// found is left child of its parent
					found.parent.left = found.left;
				} else {	// found is right child of its parent
					found.parent.right = found.right;
				}
			} else {
				// found is root
				root = found.left;
			}
			update(oldPar);
		} else {
			Vertex next = next(found); // next nerver null this case
			Vertex oldPar = next.parent;

			// next.left always null this case
			// promote next.right
			if (next == next.parent.left) {
				next.parent.left = next.right;
			} else {
				next.parent.right = next.right;
			}
			// replace next to found
			replace(next, found);
			update(oldPar);
		}
	}
	
	/**
	 * replace vertex A to vertex B
	 * */
	void replace(Vertex a, Vertex b) {
		a.parent = b.parent;
		a.left = b.left;
		a.right = b.right;
		if (b == root)
			root = a;
		else {
			if (b.parent.left == b)
				b.parent.left = a;
			else
				b.parent.right = b;
		}
	}
	
	long sum(int from, int to) {
//        VertexPair leftMiddle = split(root, from);
//        Vertex left = leftMiddle.left;
//        Vertex middle = leftMiddle.right;
//        VertexPair middleRight = split(middle, to + 1);
//        middle = middleRight.left;
//        Vertex right = middleRight.right;
        if (from > to) {
			return 0;
		}
        long ans = 0;
        Vertex startV = find(from);

        while (startV != null && startV.key <= to) {
        	if (startV.key < from) {
        		startV = next(startV);
        		continue;
        	}
        	ans = (ans + startV.key);
        	startV = next(startV);
        }
        
        return ans;
    }
	
	// checking
	
    boolean isBinarySearchTree() {
        // Implement correct algorithm here
        if (root == null)
            return true;
        return inOrder(root);
    }
    
    long curMax = Integer.MIN_VALUE;
    
    boolean inOrder(Vertex n) {
        boolean left = true;
        if (n.left != null) {
            left = inOrder(n.left);
        }
        if (!left)
            return false;
        if (n.key < curMax)
            return false;
        curMax = n.key;
        if (n.right != null) {
            return inOrder(n.right);
        }
        return true;
    }
    
    ////////////////////////////////////////////////////
    // input output
    
    public static final int MODULO = 1000000001;
    
    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;
    
    void solve() throws IOException {
        int n = nextInt();
        int last_sum_result = 0;
        for (int i = 0; i < n; i++) {
            char type = nextChar();
            switch (type) {
                case '+' : {
                    int x = nextInt();
                    insert((x + last_sum_result) % MODULO);
                } break;
                case '-' : {
                    int x = nextInt();
                    erase((x + last_sum_result) % MODULO);
                } break;
                case '?' : {
                    int x = nextInt();
                    out.println(isKeyInBST((x + last_sum_result) % MODULO) ? "Found" : "Not found");
                } break;
                case 's' : {
                    int l = nextInt();
                    int r = nextInt();
                    long res = sum((l + last_sum_result) % MODULO, (r + last_sum_result) % MODULO);
                    out.println(res);
                    last_sum_result = (int)(res % MODULO);
                }
            }
        }
    }
    
    public BinarySearchTree() throws IOException {
    	br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
	}
    
    public static void main(String[] args) throws IOException {
        new BinarySearchTree();
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
    char nextChar() throws IOException {
        return nextToken().charAt(0);
    }
    
    
}
