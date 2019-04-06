import java.io.*;
import java.util.*;


/**
 * Incident matrix implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class IncidenceMatrix extends AbstractAssocGraph
{

	/**
	 * Contructs empty graph.
	 */
    private int row = 0; //  to Tranverse through vertex
    private int col = 0; // to Traverse through edges
    private Map<String, Integer> vertex = new HashMap<String, Integer>();
    private Map<String, Integer> edges = new HashMap<String, Integer>();
    private int weights[][] = new int[4][4];
    
    /* 
     * Every time iterate through array use array.length - 1 because of the null extra item
    */
    public IncidenceMatrix() {
        System.out.println("hihi");
    	// Implement me!
    } // end of IncidentMatrix()

    public void addVertex(String vertLabel) {
        // Implement me!
        vertex.put(vertLabel, row);
        row++;
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel, int weight) {
        String newEdge = srcLabel + tarLabel; 
        int colPosition = vertex.get(srcLabel);
        edges.put(newEdge, col);

            for(int row = 0; row < 4; row++){
                if(vertex.get(srcLabel) == row){
                    System.out.printf("[..] (addEdge) rows: %s\n [...] col: %s\n", row, col);
                    weights[row][col] = weight;
                }
            }
        col++;
    } // end of addEdge()

    // public void debug(String srcLabel, String tarLabel, MyPair pair) {
    //     System.out.printf("srcLabel: %s\ntarLabel: %s\nMyPairKey: %s\n"+
    //     "MyPairValue: %s\nsrcLabel+tarLabel: %s\n", srcLabel, tarLabel, 
    //     pair.getKey(), pair.getValue(), srcLabel+tarLabel);
    // }

    // public void printArray(MyPair arrPair[]) {
    //     System.out.println("Array length:" + arrPair.length);
    //     for(int i = 0; i < arrPair.length-1; i++) {
    //         System.out.printf("key: %s  Value: %s\n",arrPair[i].getKey(), arrPair[i].getValue());
    //     }
    // }

	public int getEdgeWeight(String srcLabel, String tarLabel) {
        // Implement me!
        String edge = srcLabel + tarLabel;
        int colPosition = edges.get(edge);
        System.out.printf("[+] Getting weight of edge: %s \n", edge);
        int rowPosition = vertex.get(srcLabel);
        int edgeWeight = weights[rowPosition][colPosition];
        System.out.printf("[...] (getEdgeWeight) rows: %s\n[...] col: %s\n", rowPosition, colPosition);
        System.out.printf("[...] Retrieved weight is: %s \n", edgeWeight);
        return edgeWeight;
        
	} // end of existEdge()


	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
        String findLabel = srcLabel + tarLabel;
        edges.remove(findLabel);
        edges.put(findLabel, weight);
    } // end of updateWeightEdge()



    public void removeVertex(String vertLabel) {
        
        for (Map.Entry<String, Integer> entry : edges.entrySet()) {
            String key = entry.getKey();

            if(key.equals(vertLabel)){
                String value = entry.getValue();
            } 
       }


        vertex.remove(vertLabel);

    } // end of removeVertex()


	public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();
        
        

        return neighbours;
    } // end of inNearestNeighbours()


    public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();
            
        // Implement me!

        return neighbours;
    } // end of outNearestNeighbours()


    public void printVertices(PrintWriter os) {
        for (Map.Entry<String, Integer> entry : vertex.entrySet()) {
            String key = entry.getKey();
            System.out.println(key);
       }

    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        for (Map.Entry<String, Integer> entry : edges.entrySet()) {
            String key = entry.getKey();
            
            String a = key.substring(0, 1);
            String b = key.substring(1, 2);
            int weight = getEdgeWeight(a, b);
            System.out.printf("[+} Edge is : %s\n, [...] Weight is:  %s\n", key, weight);
;        }

    } // end of printEdges()

} // end of class IncidenceMatrix
