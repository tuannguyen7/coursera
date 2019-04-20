import java.io.*;
import java.util.*;

public class SetRangeSum {

    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;

    // Splay tree implementation

    // Vertex of a splay tree
    class Vertex {
        int key;
        // Sum of all the keys in the subtree - remember to update
        // it after each operation that changes the tree.
        long sum;
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

    void update(Vertex v) {
        if (v == null) return;
        v.sum = v.key + (v.left != null ? v.left.sum : 0) + (v.right != null ? v.right.sum : 0);
        if (v.left != null) {
            v.left.parent = v;
        }
        if (v.right != null) {
            v.right.parent = v;
        }
    }

    void smallRotation(Vertex v) {
        Vertex parent = v.parent;
        if (parent == null) {
            return;
        }
        Vertex grandparent = v.parent.parent;
        if (parent.left == v) {
            Vertex m = v.right;
            v.right = parent;
            parent.left = m;
        } else {
            Vertex m = v.left;
            v.left = parent;
            parent.right = m;
        }
        update(parent);
        update(v);
        v.parent = grandparent;
        if (grandparent != null) {
            if (grandparent.left == parent) {
                grandparent.left = v;
            } else {
                grandparent.right = v;
            }
        }
    }

    void bigRotation(Vertex v) {
        if (v.parent.left == v && v.parent.parent.left == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else {
            // Zig-zag
            smallRotation(v);
            smallRotation(v);
        }
    }

    // Makes splay of the given vertex and returns the new root.
    Vertex splay(Vertex v) {
        if (v == null) return null;
        while (v.parent != null) {
            if (v.parent.parent == null) {
                smallRotation(v);
                break;
            }
            bigRotation(v);
        }
        return v;
    }

    class VertexPair {
        Vertex left;
        Vertex right;
        VertexPair() {
        }
        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }
    }

    // Searches for the given key in the tree with the given root
    // and calls splay for the deepest visited node after that.
    // Returns pair of the result and the new root.
    // If found, result is a pointer to the node with the given key.
    // Otherwise, result is a pointer to the node with the smallest
    // bigger key (next value in the order).
    // If the key is bigger than all keys in the tree,
    // then result is null.
    VertexPair find(Vertex root, int key) {
        Vertex v = root;
        Vertex last = root;
        Vertex next = null;
        while (v != null) {
            if (v.key >= key && (next == null || v.key < next.key)) {
                next = v;
            }
            last = v;
            if (v.key == key) {
                break;
            }
            if (v.key < key) {
                v = v.right;
            } else {
                v = v.left;
            }
        }
        root = splay(last);
        return new VertexPair(next, root);
    }

    VertexPair split(Vertex root, int key) {
        VertexPair result = new VertexPair();
        VertexPair findAndRoot = find(root, key);
        root = findAndRoot.right;
        result.right = findAndRoot.left;
        if (result.right == null) {
            result.left = root;
            return result;
        }
        result.right = splay(result.right);
        result.left = result.right.left;
        result.right.left = null;
        if (result.left != null) {
            result.left.parent = null;
        }
        update(result.left);
        update(result.right);
        return result;
    }

    Vertex merge(Vertex left, Vertex right) {
        if (left == null) return right;
        if (right == null) return left;
        while (right.left != null) {
            right = right.left;
        }
        right = splay(right);
        right.left = left;
        update(right);
        return right;
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
	Vertex bFind(int key) {
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
		Vertex found = bFind(key);
		return found !=null && found.key == key;
	}
	
	/**
	 * insert key into BST.
	 * */
	Vertex bInsert(int key) {
		Vertex found = bFind(key);
		Vertex newNode = new Vertex(key, 0, null, null, found);
		if (found == null) {
			root = newNode;
			return newNode;
		}
		if (found.key == key) {
			
		} else if (found.key < key) {
			found.right = newNode; 
		} else {
			found.left = newNode;
		}
		//update(newNode);
		return newNode;
	}
	
//	void erase(int key) {
//		bDelete(key);
//	}
	
	void bDelete(int key) {
		Vertex found = bFind(key);
		if (found == null || found.key != key)
			return;	// not found
		
		if (found.right == null) {
			// promote found.left
			if (found != root) { // not root
				if (found.left != null) {
					found.left.parent = found.parent;
				}
				if (found.parent.left == found) {	// found is left child of its parent
					found.parent.left = found.left;
				} else {	// found is right child of its parent
					found.parent.right = found.left;
				}
			} else {
				// found is root
				root = found.left;
				if (root != null)
					root.parent = null;
			}
			//update(oldPar);
		} else {
			Vertex next = next(found); // next nerver null this case because it has right child

			// next.left always null this case
			// promote next.right
			if (next == next.parent.left) {
				if (next.right != null) {
					next.right.parent = next.parent;
				}
				next.parent.left = next.right;
			} else {
				// next is right child of found
				next.parent.right = next.right;
			}
			// replace next to found
			replace(next, found);
			//update(oldPar);
		}
	}
	
	/**
	 * replace vertex A to vertex B
	 * */
	void replace(Vertex a, Vertex b) {
		a.parent = b.parent;
		a.left = b.left;
		if (a.left != null)
			a.left.parent = a;
		a.right = b.right;
		if (a.right != null)
			a.right.parent = a;
		if (b == root)
			root = a;
		else {
			if (b.parent.left == b)
				b.parent.left = a;
			else
				b.parent.right = a;
		}
	}
	
    
    // Code that uses splay tree to solve the problem

    Vertex root = null;

    void insert(int x) {
        Vertex left = null;
        Vertex right = null;
        Vertex new_vertex = null;
        VertexPair leftRight = split(root, x);
        left = leftRight.left;
        right = leftRight.right;
        if (right == null || right.key != x) {
            new_vertex = new Vertex(x, x, null, null, null);
        }
        root = merge(merge(left, new_vertex), right);
    }

    
    
    void erase(int x) {
        // Implement erase yourself
    	Vertex found = bFind(x);
    	if (found == null)	// not found
    		return;
    	Vertex next = next(found);
    	splay(next);
    	splay(found);
    	bDelete(found.key);
    }

    boolean find(int x) {
        // Implement find yourself
    	VertexPair pair = find(root, x);
    	Vertex found = pair.left;
    	root = pair.right;
    	if (found == null || found.key != x)
    		return false;
        return true;
    }

    long sum(int from, int to) {
        VertexPair leftMiddle = split(root, from);
        Vertex left = leftMiddle.left;
        Vertex middle = leftMiddle.right;
        VertexPair middleRight = split(middle, to + 1);
        middle = middleRight.left;
        Vertex right = middleRight.right;
        //long ans = 0;
        // Complete the implementation of sum
        if (from > to) {
			return 0;
		}
        long ans = 0;
        Vertex startV = bFind(from);
        
        while (startV != null && startV.key <= to) {
        	if (startV.key < from) {
        		startV = next(startV);
        		continue;
        	}
        	ans = (ans + startV.key);
        	startV = next(startV);
        }
        
        return ans;
        //return ans;
    }


    public static final int MODULO = 1000000001;

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
                    out.println(find((x + last_sum_result) % MODULO) ? "Found" : "Not found");
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

    SetRangeSum() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new SetRangeSum();
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
