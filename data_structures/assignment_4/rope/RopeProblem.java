
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class RopeProblem {
	// Splay tree implementation

	// Vertex of a splay tree
	class Vertex {
		int key;
		// Sum of all the keys in the subtree - remember to update
		// it after each operation that changes the tree.
		long sum;
		char letter;
		Vertex left;
		Vertex right;
		Vertex parent;

		Vertex(int key, long sum, char letter, Vertex left, Vertex right, Vertex parent) {
			this.key = key;
			this.sum = sum;
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.letter = letter;
		}
	}

	void update(Vertex v) {
		if (v == null)
			return;
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
			parent.parent = v;
			if (m != null) {
				parent.left.parent = parent;
			}
		} else {
			Vertex m = v.left;
			v.left = parent;
			parent.right = m;
			parent.parent = v;
			if (m != null) {
				parent.right.parent = parent;
			}
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
		} else if (grandparent == null) {
			root = v;
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
		if (v == null)
			return null;
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
		if (left == null)
			return right;
		if (right == null)
			return left;
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
	 */
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
	 */
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

	// Code that uses splay tree to solve the problem

	void insert(int x, char letter) {
		Vertex left = null;
		Vertex right = null;
		Vertex new_vertex = null;
		VertexPair leftRight = split(root, x);
		left = leftRight.left;
		right = leftRight.right;
		if (right == null || right.key != x) {
			new_vertex = new Vertex(x, x, letter, null, null, null);
		}
		root = merge(merge(left, new_vertex), right);
	}

	void erase(int x) {
		// Implement erase yourself
		Vertex found = bFind(x);
		if (found == null || found.key != x) // not found
			return;
		Vertex next = next(found);
		if (next != null) {
			splay(next);
			splay(found);
			// after doing 2 splay, next.left = null
			// delete found and promote next
			root = next;
			next.parent = null;
			next.left = found.left;
			if (next.left != null)
				next.left.parent = next;
			update(root);
		} else {
			// this case found.right = null because next = null
			if (found == root) {
				root = found.left;
				if (root != null)
					root.parent = null;
				update(root);
			} else {
				Vertex parent = found.parent;
				Vertex foundLeft = found.left;
				if (parent.left == found) {
					parent.left = foundLeft;
				} else {
					parent.right = foundLeft;
				}
				if (foundLeft != null)
					foundLeft.parent = parent;
				update(parent);
			}
		}
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
		long ans = 0;
		// Complete the implementation of sum
		if (middle != null) {
			ans = middle.sum;// - left.sum;
		}
		Vertex middleRight1 = merge(middle, right);
		merge(left, middleRight1);
		return ans;
		// return 0;
	}

	// inorder update iterator version
	void inorderUpdate(Vertex vertex, int sub) {
		if (vertex == null) 
            return; 
  
        Stack<Vertex> s = new Stack<Vertex>(); 
        Vertex curr = vertex; 
  
        // traverse the tree 
        while (curr != null || s.size() > 0) 
        { 
  
            /* Reach the left most Node of the 
            curr Node */
            while (curr !=  null) 
            { 
                /* place pointer to a tree node on 
                   the stack before traversing 
                  the node's left subtree */
                s.push(curr); 
                curr = curr.left; 
            } 
  
            /* Current must be NULL at this point */
            curr = s.pop(); 
  
            curr.key -= sub;
            /* we have visited the node and its 
               left subtree.  Now, it's right 
               subtree's turn */
            curr = curr.right;
            update(curr);
        } 
	}
	
	// inorder update iterator version	
	void updateKey(Vertex v, int sub) {
		if (v == null)
			return;
		updateKey(v.left, sub);
		v.key -= sub;
		updateKey(v.right, sub);
		update(v);
		//return key + 1;
	}
	
	
	///// Rope solution
	String s;
	Vertex root = null;
	
	void process(int i, int j, int k) {
		// Replace this code with a faster implementation
		String t = s.substring(0, i) + s.substring(j + 1);
		s = t.substring(0, k) + s.substring(i, j + 1) + t.substring(k);
	}

	void debug(Vertex v) {
		List<Integer> keys = new ArrayList<>();
		List<Character> letters = new ArrayList<>();
		debugKey(v, keys, letters);
		;
	}
	
	void debugKey(Vertex v, List<Integer> keys, List<Character> letters ) {
		if (v == null)
			return;
		debugKey(v.left, keys, letters);
		keys.add(v.key);
		letters.add(v.letter);
		debugKey(v.right, keys, letters);
	}
	
	void fastProcess(int i, int j, int k) {
		// Replace this code with a faster implementation
		VertexPair pair = split(root, i);	// split(0, i) 0-(i-1) 	i-max
		Vertex left = pair.left;
		Vertex middle = pair.right;
		
		pair = split(middle, j+1);		// split(j+1, max)	i-j  (j+1)-max
		Vertex right = pair.right;
		middle = pair.left;

		//updateKey(right, j-i+1);	// update bat dau tu i
		inorderUpdate(right, j-i+1);
		//debug(right);
		Vertex remainRoot = merge(left, right);
		VertexPair pair2 = split(remainRoot, k);
		left = pair2.left;
		right = pair2.right;
		//debug(middle);
		//updateKey(middle, i - k);		// update thu tu moi cho middle
		inorderUpdate(middle, i - k);		// update thu tu moi cho middle
		//debug(middle);
		Vertex tmpRoot = merge(left, middle);	
		
		//updateKey(right, i-j-1); // update thu tu moi cho right
		inorderUpdate(right, i-j-1); // update thu tu moi cho right
		root = merge(tmpRoot, right);
	}
	
	String result() {
		return s;
	}

	String myResult() {
		StringBuilder finalString = new StringBuilder();
		collectResult(root, finalString);
		return finalString.toString();
	}
	
	String inorderGetResult() {
		if (root == null) 
            return ""; 
  
        Stack<Vertex> s = new Stack<Vertex>(); 
        Vertex curr = root; 
        StringBuilder result = new StringBuilder();
        // traverse the tree 
        while (curr != null || s.size() > 0) 
        { 
  
            while (curr !=  null) 
            { 
                s.push(curr); 
                curr = curr.left; 
            } 
            
            curr = s.pop(); 
            result.append(curr.letter);
            curr = curr.right; 
        }
        return result.toString();
	}
	
	void collectResult(Vertex v, StringBuilder finalString) {
		if (v == null)
			return;
		collectResult(v.left, finalString);
		finalString.append(v.letter);
		collectResult(v.right, finalString);
	}
	
	/// End of rope
	
	public static void main(String[] args) throws IOException {
		new RopeProblem().run();
	}

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
	
	public void run() throws IOException {
		FastScanner in = new FastScanner();
		PrintWriter out = new PrintWriter(System.out);
		s = in.next();
		for (int i = 0; i < s.length(); i++) {
			insert(i, s.charAt(i));
		}
		for (int q = in.nextInt(); q > 0; q--) {
			int i = in.nextInt();
			int j = in.nextInt();
			int k = in.nextInt();
			//process(i, j, k);
			fastProcess(i, j, k);
			//out.println(myResult());
			//out.print(b);
			//debug(root);
		}
		//out.println(result());
		out.print(inorderGetResult());
		out.close();
	}
}
