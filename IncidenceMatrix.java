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
    private int i = 0; //  to Tranverse for vertext
    private int j = 0; // to traverse arrPair 
    private Map<String> vertex = new HashMap<String>;
    private MyPair arrPair[] = new MyPair[1];
    private int incidenceMatrix[][] = new int[4][4];
    /* 
     * Every time iterate through array use array.length - 1 because of the null extra item
    */
    public IncidenceMatrix() {
        System.out.println("hihi");
    	// Implement me!
    } // end of IncidentMatrix()
        
    

    public void addVertex(String vertLabel) {
        // Implement me!
        vertices[i] = vertLabel;
        i++;
        vertices = Arrays.copyOf(vertices, i+1);
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel, int weight) {
        String newVert = srcLabel + tarLabel; 
        MyPair newPair = new MyPair(newVert, weight);
        arrPair[j] = newPair;
        j++;
        arrPair = Arrays.copyOf(arrPair, j+1);
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
        for(int i = 0; i < arrPair.length - 1; i++) {
            if(arrPair[i].getKey().equals(srcLabel+tarLabel)) {
                return arrPair[i].getValue();
            }
        }
		// update return value
		return EDGE_NOT_EXIST;
	} // end of existEdge()


	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
        String findLabel = srcLabel + tarLabel;
        for(int i = 0; i < arrPair.length - 1; i++)
        {
            if(arrPair[i].getKey().equals(findLabel))
            {
                MyPair newPair = new MyPair(findLabel, weight);
                arrPair[i] = newPair;  
            }
        }

    } // end of updateWeightEdge()

//MyPair leftHand[] = Arrays.copyOfRange(arrPair, 0, i);
//MyPair rightHand[] = Arrays.copyOfRange(arrPair, i+1, arrPair.length);

    public void removeVertex(String vertLabel) {
        // One of the approach using O(n)
        int removeIndex = -1;
        String originalArray[] = vertices.clone();
        for(int i = 0; i < vertices.length-1; i++) { 
            if(vertices[i].equals(vertLabel)) {
                removeIndex = i;
            }
        }
        System.out.println("Remove index:" + removeIndex);

        boolean found = false;
        for(int i=0; i< originalArray.length -1; i++)
        {
            if(i == removeIndex) {
                found=true;
            } else {
                if(found) { 
                    vertices[i-1] = originalArray[i];
                } else {
                    vertices[i] = originalArray[i];
                }
            }
        }
        vertices =  (removeIndex != -1) ?  
        Arrays.copyOf(vertices,vertices.length-1) : vertices;
        // int removeIndex = String.join("",Arrays.copyOfRange(vertices,0,vertices.length - 1)).indexOf(vertLabel); 
        // convert anything from 0 to lastIndex - 1 to a string;
        // String leftHand[] = Arrays.copyOfRange(vertices,0,removeIndex);
        // String rightHand[] = Arrays.copyOfRange(vertices,removeIndex+1,vertices.length-1);
        
        // System.out.println("Lefthand: " + leftHand.toString());
        // System.out.println("Righthand: " + rightHand.toString());

        // vertices =  Arrays.copyOf(vertices,vertices.length-2);
        // System.out.println("vertices: " + vertices.toString());

        // for(int i = 0; i < vertices.length ; i++) { 
        //     if(i < leftHand.length) {
        //         vertices[i] = leftHand[i];
        //     } else {
        //         vertices[i] = rightHand[i];
        //     }
        // }
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
       for(int i=0; i < vertices.length - 1; i++)
       {
           os.println(vertices[i]);
       }
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        for(int i=0; i < arrPair.length - 1; i++)
        {
            os.println(arrPair[i].getKey());
        }
    } // end of printEdges()

} // end of class IncidenceMatrix
