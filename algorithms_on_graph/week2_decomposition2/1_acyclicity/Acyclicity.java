
import java.util.ArrayList;
import java.util.Scanner;

public class Acyclicity {


    private static boolean[] visited;
    private static boolean[] deleted;
    //private boolean[]

    private static int acyclic(ArrayList<Integer>[] adj) {
        //write your code here
        for (int i = 0; i < adj.length; i++)
            DFS(adj, i);
        for (boolean tmp : deleted) {
            if (!tmp)
                return 1;
        }
        return 0;
    }

    private static void DFS(ArrayList<Integer>[] adj, int curEdge) {
        ArrayList<Integer> edgeAdj = adj[curEdge];
        boolean found = false;
        if (!canGoFuther(adj, curEdge)) {   // neu khong the di tiep thi xoa va return
            deleted[curEdge] = true;
            return;
        }
        for (int adjE : edgeAdj) {
            if (!visited[adjE]) {
                explore(adjE);
                DFS(adj, adjE);
            }
        }
        if (!canGoFuther(adj, curEdge)) {   // neu khong the di tiep thi xoa va return
            deleted[curEdge] = true;
        }

    }

    private static void explore(int edge) {
        visited[edge] = true;
    }

    private static boolean canGoFuther(ArrayList<Integer>[] adj, int curEdge) {
        ArrayList<Integer> edgeAdj = adj[curEdge];
        for (Integer e : edgeAdj) {
            if (!deleted[e.intValue()]) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllAdjVisited(ArrayList<Integer>[] adj, int curEdge) {
        ArrayList<Integer> edgeAdj = adj[curEdge];
        if (edgeAdj.isEmpty())
            return false;
        boolean isAllVisited = false;
        for (Integer e: edgeAdj) {
            if (deleted[e]) {
                continue;
            }
            isAllVisited = true;

            if (!visited[e]) {
                return false;
            }
        }
        return isAllVisited;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        visited = new boolean[n];
        deleted = new boolean[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            visited[i] = false;
            deleted[i] = false;
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
        }
        System.out.println(acyclic(adj));
    }
}