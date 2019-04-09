
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    //private List<String> elems;
    private Chain[] hashBucket;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int)hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }

    private void processQuery(Query query) {
        switch (query.type) {
            case "add":
            	int index = hashFunc(query.s);
                if (!isStringInBucket(query.s, index)) {
                    Chain newChain = new Chain(query.s, hashBucket[index]);
                	hashBucket[index] = newChain;
                }           	
                break;
            case "del":
            	index = hashFunc(query.s);
            	if (hashBucket[index] == null)
            		break;
            	Chain cur = hashBucket[index];
            	if (cur.content.equals(query.s)) {
            		hashBucket[index] = cur.next;
            		break;
            	}
            	Chain next = cur.next;
            	boolean found = false;
            	while (next != null) {
            		if (next.content.equals(query.s)) {
            			found = true;
            			break;
            		}
            		cur = cur.next;
            		next = next.next;
            	}
            	if (found) {
            		cur.next = next.next;	// delete next
            	}
                break;
            case "find":
            	index = hashFunc(query.s);
            	found = isStringInBucket(query.s, index);
				writeSearchResult(found);
                break;
            case "check":
            	StringBuilder output = new StringBuilder();
            	cur = hashBucket[query.ind];
            	while (cur != null) {
            		output.append(cur.content);
            		output.append(" ");
            		cur = cur.next;
            	}
                out.println(output.toString());
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public boolean isStringInBucket(String s, int index) {
    	Chain cur = hashBucket[index];
    	while (cur != null) {
    		if (cur.content.equals(s)) {
    			return true;
    		}
    		cur = cur.next;
    	}
    	return false;
    }
    
    public void processQueries() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
    	hashBucket = new Chain[bucketCount];
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class Chain {
    	String content;
    	Chain next;
    	
    	public Chain(String c, Chain n) {
    		content = c;
    		next = n;
    	}
    }
    
    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
