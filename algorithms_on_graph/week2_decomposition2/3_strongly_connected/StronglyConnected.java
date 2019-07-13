
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class StronglyConnected {
    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj, ArrayList<Integer>[] adjReverse) {
        //write your code here
        Stack<Integer> postOrder = new Stack<>();
        boolean[] visited = new boolean[adj.length];

        // init visited
        for (int i = 0; i < visited.length; i++)
            visited[i] = false;

        for (int i = 0; i < adj.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                DFS(adjReverse, i, visited, postOrder);
            }
        }

        // reset visited
        for (int i = 0; i < visited.length; i++)
            visited[i] = false;

        int count = 0;
        while(!postOrder.isEmpty()) {
            int curE = postOrder.pop();
            if (!visited[curE]) {
                count++;
                visited[curE] = true;
                DFS(adj, curE, visited, new Stack<>());
            }
        }
        return count;
    }

    private static void DFS(ArrayList<Integer>[] adjReverse, int curEdge, boolean[] visited, Stack<Integer> postOrder) {
        ArrayList<Integer> adjE = adjReverse[curEdge];
        for (int edge : adjE) {
            if (!visited[edge]) {
                visited[edge] = true;
                DFS(adjReverse, edge, visited, postOrder);
            }
        }
        postOrder.add(curEdge);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] adjReverse = (ArrayList<Integer>[])new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            adjReverse[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adjReverse[y-1].add(x-1);
            adj[x - 1].add(y - 1);
        }
        System.out.println(numberOfStronglyConnectedComponents(adj, adjReverse));
    }
}


