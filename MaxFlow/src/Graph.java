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

    public boolean bfs(int[][] graph, int source, int sink) {   //returnera ifall det finns en väg

        boolean pathFound = false;
        path = new int[graph.length];
        visited = new boolean[graph.length];

        for (int i = 0; i < graph.length; i++) {
            path[i]=-1;
            visited[i] = false;
        }
        queue.add(source);
        visited[source] = true;
        path[source] = -1;

        while (queue.size() != 0) {
            int element = queue.removeFirst();

            for (int k = 1; k < graph.length; k++) {
                if (!visited[k] && graph[element][k] > 0) {
                    visited[k] = true;
                    path[k] = element;
                    queue.add(k);
                }
            }
        }   //?
            if (visited[sink]) {    //Om vi nått sink-node finns det en väg.
                pathFound=true;     //return true.
            }
      //  }
        return pathFound;   //Returnera om det fanns en väg
    }

   // public int fordFulkerson(int[][] graph, int source, int sink) { //Hitta matchningar?
     /*   int f = 0;
     //   while (bfs(graph, 0, graph.length - 1)) {
            while (bfs(graph, source,sink)) {
            int flowCapacity = 200;
           // int v = path[graph.length - 1];
            int v=sink;     //sista index i arrayen. Sinkposition.
            while (v != source) {   //v!= 0, dvs sourceposition/start

                int u = path[v];    //u=sista värdet i path.
                int min = graph[u][v];  //Hämta värdet 0/1
                flowCapacity = Math.min(flowCapacity, min); //FC= det lägsta talet.
                v = path[v];    //v= sista värdet i path. uppdatera loop

            }
           // v = path[graph.length - 1]; // v=path[sink] ??
              //  v=path[sink];
                v=sink;
            while (v != source) { //v!=0 startnod

                int u = path[v];    //u=sista nod i path
                graph[v][u] -= flowCapacity;    //Gör en förflyttning i grafen?
                graph[u][v] += flowCapacity;    //Gör en förflyttning i grafen?
                v = path[v];    //sista värdet i path...
            }

            f += flowCapacity;  //f+ fc
                System.out.println(f+" ?");
        }


        return f;   //returnera antalet matches.*/
   public int fordFulkerson(int[][] graph, int source, int sink) { //Hitta matchningar?

        int u, v;
        int maxFlow = 0;
        int pathFlow;

        int[][] residualGraph = new int[graph.length ][graph.length ];
        for (int sourceVertex = 1; sourceVertex <graph.length; sourceVertex++)
        {
            for (int destinationVertex = 1; destinationVertex < graph.length; destinationVertex++)
            {
                residualGraph[sourceVertex][destinationVertex] = graph[sourceVertex][destinationVertex];
            }
        }

        while (bfs(graph,source ,sink)) {
            System.out.println("tja");
            pathFlow = 2;
            for (v = sink; v != source; v = path[v])
            {
                System.out.println("tja1");
                u = path[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            for (v = sink; v != source; v = path[v])
            {
                System.out.println("tj2a");
                u = path[v];
                graph[u][v] -= pathFlow;
                graph[v][u] += pathFlow;
            }
            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    public static void main(String[] args) {
        Graph g = new Graph();

       // System.out.println(graph.fordFulkerson());

        int[][] graph = {
                {0,1,1,0,0,0},
                {0,0,0,0,1,0},
                {0,0,0,0,1,0},
                {0,0,0,0,0,1},
                {0,0,0,0,1,1},
                {0,0,0,0,0,0}
        };
        g.printGraph(graph);
        System.out.println("Maxflow för din graf är!: " + g.fordFulkerson(graph, 0, 5));


      //  Graph graph = new Graph();
       // graph.printGraph();
      //  System.out.println(graph.fordFulkerson());
    }
}
