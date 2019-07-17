
import java.util.*;

public class ShortestPaths {

    public static long INFINITY = Long.MAX_VALUE;          // assumed infinity

    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, long[] distance, int[] reachable, int[] shortest) {
        //write your code here
        int numOfNode = adj.length;
        //int[] dist = new int[adj.length];
        //Arrays.fill(dist, INFINITY);
        distance[s] = 0;

        for (int i = 0; i < numOfNode-1; i++) {
            // Duyet tat ca cac canh
            for (int node = 0; node < adj.length; node++) {
                ArrayList<Integer> adjOfNode = adj[node];
                // Duyet qua tung dinh ke
                for (int adjIndex = 0; adjIndex < adjOfNode.size(); adjIndex++) {
                    // relax canh, tra ve true neu relax dc
                    relax(distance, node, adjOfNode.get(adjIndex), cost[node].get(adjIndex));
                }
            }
        }

        // not reachable (no path )
        for (int i = 0; i < numOfNode; i++)
            if (distance[i] != INFINITY)
                reachable[i] = 1;

        // kiem tra cycle bang cach duyet lan cuoi xem co dinh nao bi relax ko
        // 2 vong lap de duyet danh sach ke
        Queue<Integer> negativeVertex = new LinkedList<>();
        for (int node = 0; node < adj.length; node++) {
            ArrayList<Integer> adjOfNode = adj[node];
            for (int adjIndex = 0; adjIndex < adjOfNode.size(); adjIndex++) {
                // relax canh, tra ve true neu relax dc
                if (relax(distance, node, adjOfNode.get(adjIndex), cost[node].get(adjIndex))) {
                    //shortest[adjOfNode.get(adjIndex)] = 0;
                    negativeVertex.add(adjOfNode.get(adjIndex));
                }
            }
        }

        // construct negative cycle by BFS through all negative vertexes
        int[] notVisited = shortest; // visited act like shortest
        while (!negativeVertex.isEmpty()) {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(negativeVertex.poll());
            while (!queue.isEmpty()) {
                int curNode = queue.poll();
                if (notVisited[curNode] == 1) { // not visited
                    notVisited[curNode] = 0;
                    ArrayList<Integer> adjOfCurNode = adj[curNode];
                    queue.addAll(adjOfCurNode);
                }
            }
        }

    }

    // cost: cost tu sourceNode toi targetNode
    // kiem tra xem co relax dc canh srcNode-targetNode ko
    private static boolean relax(long[] dist, int srcNode, int targetNode, int cost) {
        if (dist[srcNode] == INFINITY)
            return false;
        if (dist[targetNode] > dist[srcNode] + cost) {
            dist[targetNode] = dist[srcNode] + cost;
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
        int s = scanner.nextInt() - 1;
        long distance[] = new long[n];
        int reachable[] = new int[n];
        int shortest[] = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
            reachable[i] = 0;
            shortest[i] = 1;
        }
        shortestPaths(adj, cost, s, distance, reachable, shortest);
        for (int i = 0; i < n; i++) {
            if (reachable[i] == 0) {
                System.out.println('*');
            } else if (shortest[i] == 0) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
    }

}


