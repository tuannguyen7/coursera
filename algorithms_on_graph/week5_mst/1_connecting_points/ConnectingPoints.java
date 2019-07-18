
import java.text.DecimalFormat;
import java.util.*;

public class ConnectingPoints {
    private static double minimumDistance(int[] x, int[] y) {
        double result = 0.;
        boolean[] visited = new boolean[x.length];
        Arrays.fill(visited, false);
        Point[] points = new Point[x.length];
        for (int i = 0; i < x.length; i++) {
            points[i] = new Point(x[i], y[i], i, 9999999.);
        }
        //write your code here
        PriorityQueue<Point> queue = new PriorityQueue<>(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.dis, o2.dis);
            }
        });
        points[0].dis = 0;
        visited[0] = true;
        queue.add(points[0]);
        while (!queue.isEmpty()) {
            Point nearestPoint = queue.poll();
            visited[nearestPoint.index] = true;
            for (int i = 0; i < points.length; i++) {
                if (visited[i])
                    continue;
                double eulerDis = calEulerDis(nearestPoint.x, nearestPoint.y, points[i].x, points[i].y);
                if (Double.compare(points[i].dis, eulerDis) > 0) {
                    queue.remove(points[i]);
                    points[i].dis = eulerDis;
                    queue.add(points[i]);
                }
            }
        }
        for (int i = 0; i < points.length; i++)
            result += points[i].dis;
        return result;
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
        DecimalFormat numberFormat = new DecimalFormat("#.00000000");
        System.out.println(numberFormat.format(minimumDistance(x, y)));
    }

    public static class Point {
        int x;
        int y;
        int index;
        double dis;

        public Point(int x, int y, int index, double dis) {
            this.x = x;
            this.y = y;
            this.dis = dis;
            this.index = index;
        }

        //public double

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, dis);
        }
    }
}

