import javax.swing.*;
import java.util.LinkedList;

/**
 * Created by Andreas on 2015-10-28.
 * MaxFlow lets the user create a graph with connections between "U" and "V", and let
 * the Ford Fulkerson algorithm calculate the maximum flow. Returns/Prints Maximum Flow and
 * input Graph.
 */
public class MaxFlow {

        private int[] parent;
        private LinkedList<Integer> queue;
        private int size;
        private boolean[] visited;
        private int[][] graph;

    /**
     * Constructor for MaxFlow. Intialize/Create Graph, Make connections between vertexes, and return
     * Maxflow.
     */
        public MaxFlow() {

            int nodesLeft,nodesRight;
            //Number of left and right nodes.
            nodesLeft = Integer.parseInt(JOptionPane.showInputDialog("Ange antal vänster noder."));
            nodesRight = Integer.parseInt(JOptionPane.showInputDialog("Ange antal höger noder."));

            size=nodesLeft + nodesRight + 2;

            graph = new int[size][size];
            this.queue = new LinkedList<Integer>();
            parent = new int[size ];
            visited = new boolean[size];

            //Intialize Array
            for (int i = 1; i <= nodesLeft; i++) {
                graph[0][i] = 1;
            }

            //Intialize Array
            for (int i = nodesLeft + 1; i < graph.length - 1; i++) {
                graph[i][graph.length - 1] = 1;
            }

            boolean input = true;

            //Create connections between left and right nodes/Vertexes.
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

    /**
     * This Method tries to find a path from Source to Sink vertex.
     * Recieves a Graph, Source vertex and Sink vertex. Returns Boolean
     * whether there is a path or not.
     * @param source -source/Start Vertex.
     * @param sink  -Sink/End Vertex.
     * @param graph -Graph to find path in.
     * @return boolean
     */
        public boolean bfs(int source, int sink, int graph[][]) {
            boolean path = false;
            int destination, element;

            //Initialize Parent[] and Visited[]
            for(int i = 0; i < size; i++) {
                parent[i] = -1;
                visited[i] = false;
            }

            queue.add(source);
            parent[source] = -1;
            visited[source] = true;

            //While there is a Queue
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
            if(visited[sink]){
                path = true;
            }

            return path;
        }

    /**
     * Ford Fulkersons Algorithm. This method uses a Graph(int [][]), a source vertex and
     * a sink vertex. The algorithm finds matchings between left and right nodes/vertexes.
     * Finds Max Flow.
     * Returns number of matchings.
     * @param graph -graph
     * @param source -source/start vertex
     * @param sink -Sink/End vertex
     * @return int
     */
        public int fordFulkerson(int graph[][], int source, int sink) {

            int u, v;
            int maxFlow = 0;
            int pathFlow;

            //Copy Graph to ResidualGraph.
            int[][] residualGraph = new int[size ][size ];
            for (int i = 0; i < size; i++){
                for (int k = 0; k < size; k++) {
                    residualGraph[i][k] = graph[i][k];
                }
            }
            //While there is a path
            while (bfs(source ,sink, residualGraph)) {
                pathFlow = 2;


                for (v = sink; v != source; v = parent[v]){
                    u = parent[v];
                    pathFlow = Math.min(pathFlow, residualGraph[u][v]);
                }

                for (v = sink; v != source; v = parent[v]){
                    u = parent[v];
                    residualGraph[u][v] -= pathFlow;
                    residualGraph[v][u] += pathFlow;
                }
                maxFlow += pathFlow;
            }
            System.out.println("\nMaxflow är: "+maxFlow);
            return maxFlow;
        }

    /**
     * Prints the Graph and takes a graph int [][] as input.
     * @param graph - graph to be printed.
     */
        public void printGraph(int [][] graph) {

            for (int i = 0; i < graph.length; i++) {
                for (int k = 0; k < graph[i].length; k++) {
                    System.out.print(graph[i][k] + " ");
                }
                System.out.println();
            }
        }

    /**
     * Main method.
     * @param args
     */
        public static void main(String...args) {
            new MaxFlow();
        }
    }


