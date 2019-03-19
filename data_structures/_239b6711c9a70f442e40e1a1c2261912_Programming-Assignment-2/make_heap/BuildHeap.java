
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
    private int[] data;
    
    private List<Swap> swaps;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        data = new int[n];
        for (int i = 0; i < n; ++i) {
          data[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
          out.println(swap.index1 + " " + swap.index2);
        }
    }

    private void generateSwaps() {
      swaps = new ArrayList<Swap>();
      for (int i = data.length/2; i >= 0; i--)
          siftDownOrigin(i);
    }

    private void siftDownOrigin(int index) {
        int leftIndex = index*2+1;
        int rightIndex = index*2+2;
        int newIndex = index;
        if (rightIndex < data.length && data[newIndex] > data[rightIndex])
            newIndex = rightIndex;  // rightIndex
        if (leftIndex < data.length && data[newIndex] > data[leftIndex])
            newIndex = leftIndex;   // leftIndex
        if (newIndex != index) {
            swap(newIndex, index);
            siftDownOrigin(newIndex);
        }
    }
    
    
    private void siftUp(int index) {
        int parentIndex = (index-1)/2;
        while (data[index] < data[parentIndex] && index > 0) {
//          int tmp = heapData[index];
//          heapData[index] = heapData[parentIndex];
//          heapData[parentIndex] = tmp;
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index-1)/2;
        }
    }
    
    private int comparePriority(int i, int j) {
        if (data[i] < data[j])
            return 1;
        else if (data[i] > data[j])
            return -1;
        return 0;
    }
    
    private void swap(int i1, int i2) {
        int tmp = data[i1];
        data[i1] = data[i2];
        data[i2] = tmp;
        swaps.add(new Swap(i1, i2));
    }
    
    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwaps();
        writeResponse();
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
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