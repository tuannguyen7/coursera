import java.io.*;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;
    
    // Heap
    private long[] freeTimes;
    private int[] indexes;
    
    // 
    private int[] assignedWorker;
    private long[] startTime;
    private int curSize;
    
    private FastScanner in;
    private PrintWriter out;

/*  Heap start	 */
    private void siftDown(int index) {
    	if (index > numWorkers) {
			return;
    	}
		int maxIndex = index;
		int leftIndex = index*2;
		int rightIndex = index*2+1;
		if (leftIndex <= numWorkers && comparePriority(leftIndex, maxIndex) > 0)
			maxIndex = leftIndex;
		if (rightIndex <= numWorkers && comparePriority(rightIndex, maxIndex) > 0)
			maxIndex = rightIndex;
		if (maxIndex != index) {
			swap(index, maxIndex);
			siftDown(maxIndex);
		}
    }
    
    public void siftUp(int index) {
		int parentIndex = index/2;
		while (comparePriority(index, parentIndex) > 0 && index > 1) {
			// swap
			swap(index, parentIndex);
			index = parentIndex;
			parentIndex = index/2;
		}
	}
    
    private int comparePriority(int left, int right) {
    	if (freeTimes[left] < freeTimes[right])
    		return 1;
    	if (freeTimes[left] > freeTimes[right])
    		return -1;
    	return indexes[right] - indexes[left];
    }
    
    private int comparePriority(int freeTime1, int freeTime2, int index1, int index2) {
    	if (freeTime1 < freeTime2)
    		return 1;
    	if (freeTime1 > freeTime2)
    		return -1;
    	return index2 - index1;
    }
    
    public void swap(int i, int j) {
		int tmp = indexes[i];
		indexes[i] = indexes[j];
		indexes[j] = tmp;
		long tmp2 = freeTimes[i];
		freeTimes[i] = freeTimes[j];
		freeTimes[j] = tmp2;
	}
    
    public void initHeap() {
    	curSize = 0;
    	indexes = new int[numWorkers + 1];
    	freeTimes = new long[numWorkers + 1];
    }
    
    public void changePiority(int index, long freeTime) {
		//int tmpWorkerIndex = indexes[index];
		long tmpFreeTime = freeTimes[index];
		//indexes[index] = tmpWorkerIndex;
		freeTimes[index] = freeTime;
		if (freeTime > tmpFreeTime) {	// change lower priority
			siftDown(index);
		} else {
			siftUp(index);
		}
	}

    public void insert(int freeTime, int workerIndex) {
		if (curSize == numWorkers+1)
			return;
		curSize += 1;
		freeTimes[curSize] = freeTime;
		indexes[curSize] = workerIndex;
		siftUp(curSize);
	}
    
    public int getCurrMaxPriorityWorkerIndex() {
    	return indexes[1];
    }
/*  Heap end    */
    
    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

//    private void assignJobs() {
//        // TODO: replace this code with a faster algorithm.
//        assignedWorker = new int[jobs.length];
//        startTime = new long[jobs.length];
//        long[] nextFreeTime = new long[numWorkers];
//        for (int i = 0; i < jobs.length; i++) {
//            int duration = jobs[i];
//            int bestWorker = 0;
//            for (int j = 0; j < numWorkers; ++j) {
//                if (nextFreeTime[j] < nextFreeTime[bestWorker])
//                    bestWorker = j;
//            }
//            assignedWorker[i] = bestWorker;
//            startTime[i] = nextFreeTime[bestWorker];
//            nextFreeTime[bestWorker] += duration;
//        }
//    }

    private void assignJobs() {
    	buildHeap();
    	assignedWorker = new int[jobs.length];
    	startTime = new long[jobs.length];
//      long[] nextFreeTime = new long[numWorkers];
    	for (int i = 0; i < jobs.length; i++) {
    		int maxWorkerIndex = getCurrMaxPriorityWorkerIndex();
    		assignedWorker[i] = maxWorkerIndex;
    		startTime[i] = freeTimes[1];
    		changePiority(1, freeTimes[1] + jobs[i]);
    	}
    	
    }
    
    private void buildHeap() {
    	initHeap();
    	for (int i = 1; i < numWorkers+1; i++) {
    		insert(0, i-1);
    	}
    }
    
    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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
