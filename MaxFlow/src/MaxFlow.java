import javax.swing.*;
import java.util.LinkedList;

/**
 * Created by Andreas on 2015-10-28.
 */
public class MaxFlow {


        private int[] parent;
        private LinkedList<Integer> queue;
        private int size;
        private boolean[] visited;

        private int nodesLeft,nodesRight;
        private int[][] graph;


        public MaxFlow() {

            nodesLeft = Integer.parseInt(JOptionPane.showInputDialog("Ange antal vänster noder."));
            nodesRight = Integer.parseInt(JOptionPane.showInputDialog("Ange antal höger noder."));

            size=nodesLeft + nodesRight + 2;

            graph = new int[size][size];

            this.queue = new LinkedList<Integer>();
            parent = new int[size ];
            visited = new boolean[size];

            //Definiera kopplingen från source till vänsternoder
            for (int i = 1; i <= nodesLeft; i++) {
                graph[0][i] = 1;
            }

            //Definera kopplingen från högernoder till sink.
            for (int i = nodesLeft + 1; i < graph.length - 1; i++) {
                graph[i][graph.length - 1] = 1;
            }
            boolean input = true;

            // nodesLeft noder/värden = 1 till nodesleft value.
            // nodesRight noder/värden= nodesleft+1 till nodesleft+nodesright.
            while (input) {
                String left = (JOptionPane.showInputDialog("Skapa en connection från en av vänster noderna.\n"
                        + "En siffra från 1-" + nodesLeft + "\nTryck endast OK för att avsluta."));

                String right = (JOptionPane.showInputDialog("Skapa en connection till en av höger noderna.\n"
                        + "En siffra från " + (nodesLeft + 1) + "-" + (nodesLeft + nodesRight) + "\nTryck endast OK för att avsluta."));

                if (left.equals("") || right.equals("")) {
                    input = false;
                } else {
                    int nodeLeft = Integer.parseInt(left);
                    int nodeRight = Integer.parseInt(right);
                    System.out.println("Gör connection från " + nodeLeft + " till " + nodeRight);

                    graph[nodeLeft][nodeRight] = 1;
                }
            }
            printGraph(graph);
            fordFulkerson(graph,0,graph.length-1);
        }
        public boolean bfs(int source, int goal, int graph[][]) {
            boolean pathFound = false;
            int destination, element;

            for(int vertex = 0; vertex < size; vertex++) {
                parent[vertex] = -1;
                visited[vertex] = false;
            }

            queue.add(source);
            parent[source] = -1;
            visited[source] = true;

            while (!queue.isEmpty()) {
                element = queue.remove();
                destination = 1;

                while (destination < size) {
                    if (graph[element][destination] > 0 &&  !visited[destination]){
                        parent[destination] = element;
                        queue.add(destination);
                        visited[destination] = true;
                    }
                    destination++;
                }
            }
            if(visited[goal]){
                pathFound = true;
            }

            return pathFound;
        }

        public int fordFulkerson(int graph[][], int source, int destination)
        {
            int u, v;
            int maxFlow = 0;
            int pathFlow;

            int[][] residualGraph = new int[size ][size ];
            for (int sourceVertex = 0; sourceVertex < size; sourceVertex++){
                for (int destinationVertex = 0; destinationVertex < size; destinationVertex++) {
                    residualGraph[sourceVertex][destinationVertex] = graph[sourceVertex][destinationVertex];
                }
            }

            while (bfs(source ,destination, residualGraph)) {
                pathFlow = Integer.MAX_VALUE;
                for (v = destination; v != source; v = parent[v]){
                    u = parent[v];
                    pathFlow = Math.min(pathFlow, residualGraph[u][v]);
                }
                for (v = destination; v != source; v = parent[v]){
                    u = parent[v];
                    residualGraph[u][v] -= pathFlow;
                    residualGraph[v][u] += pathFlow;
                }
                maxFlow += pathFlow;
            }
            System.out.println("\nMaxflow är: "+maxFlow);
            return maxFlow;
        }
        public void printGraph(int [][] graph) {

            for (int i = 0; i < graph.length; i++) {
                for (int k = 0; k < graph[i].length; k++) {
                    System.out.print(graph[i][k] + " ");
                }
                System.out.println();
            }
        }
        public static void main(String...arg) {
            new MaxFlow();
        }
    }


