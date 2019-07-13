
import java.util.*;

public class Dijkstra {


    private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {

        int[] prev = new int[adj.length];
        int[] dist = new int[adj.length];

        //// init
        for (int i = 0; i < dist.length; i++)
            dist[i] = 999999;
        dist[s] = 0;

        //// free all
        boolean atLeastOneRelaxed = false;
        do {
            atLeastOneRelaxed = false;
            for (int i = 0; i < adj.length; i++)    //
                for (int j = 0; j < adj[i].size(); j++) {
                    int u = i;
                    int v = adj[i].get(j);
                    int wUV = cost[i].get(j);
                    if (relax(dist, prev, wUV, u, v)) {
                        atLeastOneRelaxed = true;
                    }
                }
        } while (atLeastOneRelaxed);
        if (dist[t] == 999999)
            return -1;
        return dist[t];
    }

    // wUV: weight of edge U->V. Because it's directed graph so be careful with the order of u and v.
    // in simple words, relax(..., u, v) not same as relax(..., v, u)
    private static boolean relax(int[] dist, int[] prev, int wUV, int u, int v) {
        if (dist[v] > dist[u] + wUV) {
            dist[v] = dist[u] + wUV;
            prev[v] = u;
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
}


