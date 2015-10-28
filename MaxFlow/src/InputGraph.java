import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Andreas on 2015-10-28.
 */
public class InputGraph {

    private ArrayList<ArrayList>  graph;
    private int size;
    public InputGraph(int size){
        this.size=size;
        graph= new ArrayList<ArrayList>();

        for(int i=0;i<size;i++){
            graph.add(new ArrayList());
        }
    }
    public void setGraph(){

        for(int i=0;i<graph.size();i++){
            graph.get(i).add(JOptionPane.showInputDialog("Skriv in nyckeln/värdet för nod " + i + " i grafen."));
        }
        for(int i=0;i<graph.size();i++){
            boolean input=true;
            while(input) {

                String str= JOptionPane.showInputDialog("Skriv in nyckeln/värdet för en nod som är" +
                        "kopplad till nod: " + graph.get(i).get(0) + " i grafen, skirv '*' för att ta nästa" +
                        " nod/ avbryta om det är sista noden.");
                if(str.equals("*")){
                    input=false;
                }else{
                    graph.get(i).add(str);
                }
                 }
            }
    }

    public void printGraph(){

        for(int i=0;i<graph.size();i++){
            for(int k=0;k<graph.get(i).size();k++) {
                if (k == 0) {
                    System.out.print(graph.get(i).get(k) + " -Relaterar till: ");
                } else {
                    System.out.print(graph.get(i).get(k) + ", ");
                }
            }
            System.out.println();
            System.out.println();
        }
    }

    public static void main(String [] args){
        InputGraph ig = new InputGraph(5);
        ig.setGraph();
        ig.printGraph();
    }

}
