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
    private int row = 0; // The global variable, to determine the new size of the weight array, as well as the vertex position.
    private int col = 0; // The global variable, to determine the new size of the weight array, as well as the edges position.
    private Map<String, Integer> vertex = new HashMap<String, Integer>();
    private Map<String, Integer> edges = new HashMap<String, Integer>();
    private int weights[][] = new int[row][col];
    /* 
     * Every time iterate through array use array.length - 1 because of the null extra item
    */
    public IncidenceMatrix() {
        
    } // end of IncidentMatrix()

    public void addVertex(String vertLabel) {
        vertex.put(vertLabel, row);
        row++; 
    } // end of addVertex()

    

    public int[][] extend(int[][] arr, int row, int col) {
        /*
        This function extends the size of the inputted array
        This is done, by receiving the new sizes row and col, and the array to copy. 
        The old array is copied into a new array, for the new one to be returned.
        */
        int returnArr[][] = new int[row][col];

        for (int i = 0; i < arr.length; i++) {
            for(int j = 0; j< arr[i].length; j++){
                returnArr[i][j] = arr[i][j];
            }
        } 
        return returnArr;
    }

    public void addEdge(String srcLabel, String tarLabel, int weight) {
        /*
        This is function creates a new edge in the edge map, defines it's position in weights
        The col value, is the incremented (this is for the next edge position to be determined)
        The [][] coordiante is determined by retrieving the vertex and edge position, 
        The two weights associated with the edge is added into the 2D array.
        */
        
        String newEdge = srcLabel + tarLabel; 
        int indR = -1;
        try {
            indR = vertex.get(tarLabel);
        } catch (Exception e) {
            System.out.println("Error: Vertex " + tarLabel + " doesn't exist");
        }
        if(indR >= 0) {
            try{
                edges.put(newEdge, col);
                col++;
                int indA = vertex.get(srcLabel);
                int indB = edges.get(newEdge);
                weights = extend(weights, row, col); // increases the size of the array.Array
                weights[indA][indB] = weight;
                weights[indR][indB] = -weight;
            } catch (Exception e) {
                System.out.println("Error: Vertex " + srcLabel + " doesn't exist");
            }
           
        }
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
        int colPosition = -1;
        int rowPosition = -1;
        try {
            colPosition = edges.get(edge);
        } catch (Exception e) {
            System.out.println("Error: " + edge + " edge doesn't exist");
        }
    
        try{
            rowPosition = vertex.get(srcLabel);
        } catch (Exception e) {
            System.out.println("Error: " + srcLabel + " vertex doesn't exist.");
        }
        if(rowPosition >= 0 && colPosition >= 0) {
            int edgeWeight = weights[rowPosition][colPosition];
            return edgeWeight;
        }
        return EDGE_NOT_EXIST;
	} // end of existEdge()

    public int[] getPosition(String srcLabel, String tarLabel) {
        String edge = srcLabel + tarLabel;
        int colPosition = -1;
        int rowPosition = -1;
        if(!tarLabel.isEmpty()) {
            try {
                colPosition = edges.get(edge);
            } catch (Exception e) {
                System.out.println("Error: " + edge + " edge doesn't exist");
            }
        }
        try{
            rowPosition = vertex.get(srcLabel);
        } catch (Exception e) {
            System.out.println("Error: " + srcLabel + " vertex doesn't exist.");
        }
        int result[] = new int[]{colPosition, rowPosition};
        return result;
    }

	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
        String edge = srcLabel + tarLabel;
        String oppEdge = tarLabel + srcLabel;
        int colPosition = -1;
        int rowPosition = -1;
        int oppRowPosition = -1;
        try {
            colPosition = edges.get(edge);
        } catch (Exception e) {
            System.out.println("Error: " + edge + " edge doesn't exist");
        }
        
        try{
            rowPosition = vertex.get(srcLabel);
            oppRowPosition = vertex.get(tarLabel);
        } catch (Exception e) {
            System.out.println("Error: vertex doesn't exist.");
        }
        if(rowPosition >= 0 && colPosition >= 0 && oppRowPosition >= 0) {
            weights[rowPosition][colPosition] = weight;
            weights[oppRowPosition][colPosition] = -weight;
        }

    } // end of updateWeightEdge()
    public String printArr(int[] arr) {
        String result = "";
        for (int i = 0; i < arr.length; i++) {
            result += arr[i];
            result += " ";
        }
        return result;
    }
    public int[] getEdgePosition(String vertLabel) {
        int index = 0;
        int result[] = new int[index+1];
        boolean found = false;
        for(Map.Entry<String, Integer> entry: edges.entrySet()) {
            if(entry.getKey().indexOf(vertLabel) >= 0) {
                found = true;
                result[index] = entry.getValue();
                index++;
                result = Arrays.copyOf(result, index+1); 
            }
        }
        result = Arrays.copyOfRange(result, 0, index);
        System.out.println("List of edges: " + printArr(result));
        if(found == false) {
            result[0] = EDGE_NOT_EXIST;
        }
        return result;
    }
    public int getVertexPosition(String vertLabel) {
        for(Map.Entry<String, Integer> entry: vertex.entrySet()) {
            if(entry.getKey().indexOf(vertLabel) >= 0) {
                return entry.getValue();
            }
        }
        return EDGE_NOT_EXIST;
    }

    public boolean checkValidColumn(int index, int[] indexList) {
        /* Check if the index is in the remove indexList. If it's then return True
         * else, return false;
        */
        for(int i = 0; i < indexList.length; i++) {
            if(index == indexList[i]) {
                return false;
            }
        }
        return true;
    }

    public int[][] clearWeight(int[][] weights, int indVertex, int[] indEdges) {
        /* Create a new matrix without the removed vertex and edges */
        System.out.println("Row to remove: " + indVertex + " Column to remove: " + printArr(indEdges));
        int result[][] = new int[row-1][col - indEdges.length];
        int mRow = 0;
        int mCol = 0;
        System.out.println("Result length: " + result.length + " " + result[0].length);
        for (int i = 0; i < weights.length; i++) {
            System.out.println("> Row: " + i);
            if(i != indVertex) {
                for(int j = 0; j < weights[i].length; j++) {
                    System.out.println(">> Checking if " + j + " in " + printArr(indEdges));
                    if(checkValidColumn(j, indEdges)) {
                        mRow %= result.length;
                        mCol %= result[0].length;
                        result[mRow][mCol] = weights[i][j];
                        System.out.println("+++ Adding " + weights[i][j] + "into [" + mRow + ", " + mCol + "]");
                        mCol++;
                    }
                }
                mRow++;
            }
        }
        for (int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[i].length; j++) {
                System.out.printf("%3d",result[i][j]);
            }
            System.out.println();
        }
        return result;
    }

    public void removeEdge(String vertLabel) {
        /* Find all the edges that contains vertLabel and remove it from the edges map */
        int index = 0;
        String removeLabel[] = new String[index+1];
        for(Map.Entry<String, Integer> entry: edges.entrySet()) {
            if(entry.getKey().indexOf(vertLabel) >= 0) {
                removeLabel[index] = entry.getKey();
                index++;
                removeLabel = Arrays.copyOf(removeLabel, index+1); 
            }
        }
        removeLabel = Arrays.copyOfRange(removeLabel, 0, index);
        if(removeLabel.length > 0) {
            for (int i = 0; i < removeLabel.length; i++) {
                edges.remove(removeLabel[i]);
            }
        }
    }

    public void removeVertex(String vertLabel) {
        int rowPosition = getVertexPosition(vertLabel);
        int[] colPosition = getEdgePosition(vertLabel);
        if(rowPosition > 0) {
            /* If there is a vertex, remove that vertex and all the edges contains that vertex */
            vertex.remove(vertLabel);
            removeEdge(vertLabel);
        } else {
            System.out.println("Can't find vertex of " + vertLabel);
        }
        if(colPosition[0] != EDGE_NOT_EXIST) {
            /* Update the weights array */
            weights = clearWeight(weights, rowPosition, colPosition);
        }
    } // end of removeVertex()

    public List<MyPair> sortMyPairs(List<MyPair> myPair) {
        /* Using Bubble Sort to sort MyPair list*/
        for (int i = 0; i <= myPair.size()-2; i++) {
            for(int j = 0; j <= myPair.size() - 2 - i; j++) {
                if(myPair.get(j+1).getValue() > myPair.get(j).getValue()) {
                    Collections.swap(myPair,j,j+1);
                }
            }
        }
        return myPair;
    }

	public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();
        int index = getPosition(vertLabel, "")[1];

        for(int i = 0; i < weights[index].length; i++) {
            if(weights[index][i] < 0) {
                String label = "";
                for(Map.Entry<String, Integer> entry: edges.entrySet()) {
                    int value = entry.getValue();
                    if(value == i) {
                        label = entry.getKey().substring(0,1);
                    }
                }
                System.out.println("Label: " + label);
                if(!label.isEmpty())
                    neighbours.add(new MyPair(label,weights[index][i]));
            }
        }
        neighbours = sortMyPairs(neighbours);
        return neighbours;
    } // end of inNearestNeighbours()


    public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();
        // Implement me!
        int index = getPosition(vertLabel, "")[1];

        for(int i = 0; i < weights[index].length; i++) {
            if(weights[index][i] > 0) {
                String label = "";
                for(Map.Entry<String, Integer> entry: edges.entrySet()) {
                    int value = entry.getValue();
                    if(value == i) {
                        label = entry.getKey().substring(1,2);
                    }
                }
                System.out.println("Label: " + label);
                if(!label.isEmpty())
                    neighbours.add(new MyPair(label,weights[index][i]));
            }
        }
        neighbours = sortMyPairs(neighbours);
        return neighbours;
    } // end of outNearestNeighbours()


    public void printVertices(PrintWriter os) {
        for (Map.Entry<String, Integer> entry : vertex.entrySet()) {
            String key = entry.getKey();
            System.out.println(key);
       }

    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        for(int i = 0; i < weights.length; i++) {
            System.out.print("[");
            for(int j = 0; j < weights[i].length; j++) {
                System.out.printf("%3d ", weights[i][j]);
            }
            System.out.println("]");
        }
        for (Map.Entry<String, Integer> entry : edges.entrySet()) {
            String key = entry.getKey();
            String a = key.substring(0, 1);
            String b = key.substring(1, 2);
            int weight = getEdgeWeight(a, b);
            System.out.printf("[+] Edge: %s [+] Weight: %s\n", key, weight);
        }

    } // end of printEdges()

} // end of class IncidenceMatri¥¥¥¥¥
