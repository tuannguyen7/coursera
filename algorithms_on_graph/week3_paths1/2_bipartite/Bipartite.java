
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Bipartite {
    private static int bipartite(ArrayList<Integer>[] adj) {
        //write your code here
        boolean[] colour = new boolean[adj.length]; // black white color
        boolean[] visited = new boolean[adj.length];
        for (int i = 0; i < visited.length; i++)
            visited[i] = false;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        visited[0] = true;
        colour[0] = true;   // mark it black
        while (!queue.isEmpty()) {
            int edge = queue.poll();
            for (int adjE : adj[edge]) {
                if (visited[adjE]) {
                    if (colour[adjE] == colour[edge])
                        return 0;
                } else  {
                    visited[adjE] = true;
                    colour[adjE] = !colour[edge];
                    queue.add(adjE);
                }
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        System.out.println(bipartite(adj));
    }
}


