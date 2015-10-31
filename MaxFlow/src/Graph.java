import javax.swing.*;
import java.util.LinkedList;

/**
 * Created by Andreas on 2015-10-28.
 */
public class Graph {


   // private int[][] graph;
    private int nodesLeft, nodesRight;
    private LinkedList<Integer> queue = new LinkedList<Integer>();
    private boolean[] visited;
    private int[] path;

    public Graph() {

//        nodesLeft = Integer.parseInt(JOptionPane.showInputDialog("Ange antal vänster noder."));
//        nodesRight = Integer.parseInt(JOptionPane.showInputDialog("Ange antal höger noder."));
//
//        graph = new int[nodesLeft + nodesRight + 2][nodesLeft + nodesRight + 2];
//
//        //Definiera kopplingen från source till vänsternoder
//        for (int i = 1; i <= nodesLeft; i++) {
//            graph[0][i] = 1;
//        }
//
//        //Definera kopplingen från högernoder till sink.
//        for (int i = nodesLeft + 1; i < graph.length - 1; i++) {
//            graph[i][graph.length - 1] = 1;
//        }
//        boolean input = true;
//
//        // nodesLeft noder/värden = 1 till nodesleft value.
//        // nodesRight noder/värden= nodesleft+1 till nodesleft+nodesright.
//        while (input) {
//            String left = (JOptionPane.showInputDialog("Skapa en connection från en av vänster noderna.\n"
//                    + "En siffra från 1-" + nodesLeft + "\nTryck endast OK för att avsluta."));
//
//            String right = (JOptionPane.showInputDialog("Skapa en connection till en av höger noderna.\n"
//                    + "En siffra från " + (nodesLeft + 1) + "-" + (nodesLeft + nodesRight) + "\nTryck endast OK för att avsluta."));
//
//            if (left.equals("") || right.equals("")) {
//                input = false;
//            } else {
//                int nodeLeft = Integer.parseInt(left);
//                int nodeRight = Integer.parseInt(right);
//                System.out.println("Gör connection från " + nodeLeft + " till " + nodeRight);
//
//                graph[nodeLeft][nodeRight] = 1;
//            }
//        }
    }

    public void printGraph(int [][] graph) {

        for (int i = 0; i < graph.length; i++) {
            for (int k = 0; k < graph[i].length; k++) {
                System.out.print(graph[i][k] + " ");
            }
            System.out.println();
        }
    }

    public boolean bfs(int[][] graph, int source, int sink) {
         boolean pathFound = false;
        path = new int[graph.length];
        visited = new boolean[graph.length];

        for (int i = 0; i < graph.length; i++) {
            visited[i] = false;
        }
        queue.add(source);
        visited[source] = true;
        path[source] = -1;

        while (queue.size() != 0) {
            int element = queue.removeFirst();

            for (int k = 0; k < graph.length; k++) {
                if (!visited[k] && graph[element][k] > 0) {
                    visited[k] = true;
                    path[k] = element;
                    queue.add(k);
                }
            }

            if (visited[sink]) {
                pathFound=true;
            }
        }
        return pathFound;
    }

    public int fordFulkerson(int[][] graph, int source, int sink) {
        int f = 0;
     //   while (bfs(graph, 0, graph.length - 1)) {
            while (bfs(graph, source,sink)) {
            int fc = 2;
           // int v = path[graph.length - 1];
            int v=sink;
            while (v != source) {

                int u = path[v];
                int min = graph[u][v];
                fc = Math.min(fc, min);
                v = path[v];

            }
            v = path[graph.length - 1];

            while (v != 0) {

                int u = path[v];
                graph[v][u] -= fc;
                graph[u][v] += fc;
                v = path[v];
            }
            f += fc;

        }


        return f;
    }

    public static void main(String[] args) {
        Graph g = new Graph();

       // System.out.println(graph.fordFulkerson());

        int[][] graph = {
                {0,1,1,0,0,0},
                {0,0,0,0,1,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,1},
                {0,0,0,0,0,1},
                {0,0,0,0,0,0}
        };
        g.printGraph(graph);
        System.out.println("Maxflow för din graf är!:" + g.fordFulkerson(graph, 0, 5));


      //  Graph graph = new Graph();
       // graph.printGraph();
      //  System.out.println(graph.fordFulkerson());
    }
}
