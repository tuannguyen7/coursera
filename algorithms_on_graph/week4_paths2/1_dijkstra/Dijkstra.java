
import java.util.*;

public class Dijkstra {

    public static int INFINITY = 999999;

    // naive implement
    private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {

        boolean[] visited = new boolean[adj.length];  // known religion
        int[] dist = new int[adj.length];

        //// init
        Arrays.fill(dist, INFINITY);
        Arrays.fill(visited, false);
        dist[s] = 0;
        visited[s] = true;
        PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparetator());
        // do algorithm
        queue.add(new Node(s, 0));

        while (!queue.isEmpty()) {
            Node nextMinVertex = queue.poll();
            int curNode = nextMinVertex.nodeID;
            visited[curNode] = true;

            ArrayList<Integer> adjOfNode = adj[curNode]; // dinh ke
            for (int adjIndex = 0; adjIndex < adjOfNode.size(); adjIndex++) {
                if (!visited[adjOfNode.get(adjIndex)]) {    // not visited adjection Node
                    relax(dist, cost[curNode].get(adjIndex), curNode, adjOfNode.get(adjIndex));
                    Node adjVertex = new Node(adjOfNode.get(adjIndex), dist[adjOfNode.get(adjIndex)]);
                    queue.remove(adjVertex);
                    queue.add(adjVertex);
                }
            }

        }
        if (dist[t] == INFINITY)
            return -1;
        return dist[t];
    }

    // wUV: weight of edge U->V. Because it's directed graph so be careful with the order of u and v.
    // in simple words, relax(..., u, v) not same as relax(..., v, u)
    private static boolean relax(int[] dist, int wUV, int u, int v) {
        if (dist[v] > dist[u] + wUV) {
            dist[v] = dist[u] + wUV;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, cost, x, y));
    }

    public static class Node {
        public int nodeID;
        public int curDistance;
        public Node(int node, int dist) {
            this.nodeID = node;
            this.curDistance = dist;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return nodeID == node.nodeID;
        }

        @Override
        public int hashCode() {
            return Objects.hash(nodeID, curDistance);
        }
    }

    public static class NodeComparetator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            if (o1.curDistance == o2.curDistance)
                return o1.nodeID - o2.nodeID; // node
            return o1.curDistance - o2.curDistance;
        }
    }
}


