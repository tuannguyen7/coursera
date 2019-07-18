
import java.text.DecimalFormat;
import java.util.*;

public class Clustering {
    private static double clustering(int[] x, int[] y, int k) {
        //write your code here
        int n = x.length; // number of node
        int curCluster = n;
        DisjoinSet disjoinSet = new DisjoinSet(n);


        PriorityQueue<Vertex> queue = new PriorityQueue<>(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return Double.compare(o1.distance, o2.distance);
            }
        });
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++) {
                Vertex v = new Vertex(i, j, calEulerDis(x[i], y[i], x[j], y[j]));
                queue.add(v);
            }

        while (curCluster > k) {
            Vertex v = queue.poll();
            if (disjoinSet.find(v.firstIndex, v.secondIndex)) {
                continue;
            }
            disjoinSet.union(v.firstIndex, v.secondIndex);
            curCluster--;
        }


        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            if (!disjoinSet.find(v.firstIndex, v.secondIndex)) {
                return v.distance;
            }
        }
        return 0.0;
     }

    public static double calEulerDis (int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        int k = scanner.nextInt();
        DecimalFormat numberFormat = new DecimalFormat("#.00000000");
        System.out.println(numberFormat.format(clustering(x, y, k)));
    }

    public static class Vertex {
        int firstIndex;
        int secondIndex;
        double distance;

        public Vertex(int first, int second, double distance) {
            this.firstIndex = first;
            this.secondIndex = second;
            this.distance = distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return firstIndex == vertex.firstIndex &&
                    secondIndex == vertex.secondIndex;
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstIndex, secondIndex, distance);
        }
    }

    public static class DisjoinSet {
        private HashMap<Integer, Integer> parents;

        public DisjoinSet (int n) {
            parents = new HashMap<>();
            for (int i = 0; i < n; i++)
                parents.put(i, i);
        }

        public void union(int x, int y) {
            int yParent = findParent(y);
            int xParent = findParent(x);
            parents.put(xParent, yParent);
        }

        public boolean find(int x, int y) {
            return findParent(x) == findParent(y);
        }

        private int findParent(int x) {
            if (parents.get(x) == x)
                return  x;
            int p = findParent(parents.get(x));
            parents.put(x, p);
            return p;
        }
    }
}

