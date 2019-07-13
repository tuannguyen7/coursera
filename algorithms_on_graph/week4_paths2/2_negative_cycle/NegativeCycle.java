
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NegativeCycle {

    public static int INFINITY = 999999;          // assumed infinity

    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        int numOfNode = adj.length;
        int[] dist = new int[adj.length];
        Arrays.fill(dist, INFINITY);
        dist[numOfNode-1] = 0;

        for (int i = 0; i < numOfNode-1; i++) {
            // Duyet tat ca cac canh
            for (int node = 0; node < adj.length; node++) {
                ArrayList<Integer> adjOfNode = adj[node];
                // Duyet qua tung dinh ke
                for (int adjIndex = 0; adjIndex < adjOfNode.size(); adjIndex++) {
                    // relax canh, tra ve true neu relax dc
                    relax(dist, node, adjOfNode.get(adjIndex), cost[node].get(adjIndex));
                }
            }
        }

        // kiem tra cycle bang cach duyet lan cuoi xem co dinh nao bi relax ko

        for (int node = 0; node < adj.length; node++) {
            ArrayList<Integer> adjOfNode = adj[node];
            // Duyet qua tung dinh ke
            for (int adjIndex = 0; adjIndex < adjOfNode.size(); adjIndex++) {
                // relax canh, tra ve true neu relax dc
                if (relax(dist, node, adjOfNode.get(adjIndex), cost[node].get(adjIndex)))
                    return 1;
            }
        }
        return 0;
    }

    // cost - cost tu sourceNode toi targetNode
    private static boolean relax(int[] dist, int srcNode, int targetNode, int cost) {
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
        int n = scanner.nextInt() + 1;  // + 1 tricky node to the last
        int m = scanner.nextInt();
        // To solve this problem I add a tricky node act as a source node.
        // This new node must be connected to all other node
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

        // init distance from tricky node to all other nodes
        // tricky node is at the end
        for (int i = 0; i < n-1; i++) {
            adj[n-1].add(i);
            cost[n-1].add(1); // mark distance between tricky node the the others 1
        }
        System.out.println(negativeCycle(adj, cost));
    }
}

