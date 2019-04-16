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
    private Map<String, Integer> vertex = new LinkedHashMap<String, Integer>();
    private Map<String, Integer> edges = new LinkedHashMap<String, Integer>();
    private int weights[][] = new int[row][col];
    /* 
     * Every time iterate through array use array.length - 1 because of the null extra item
    */
    public IncidenceMatrix() {
        
    } // end of IncidentMatrix()

    public void addVertex(String vertLabel) {
        boolean duplicated = false;
        if(!vertex.containsKey(vertLabel)){
            vertex.put(vertLabel, row);
            row++; 
        } else {
            System.out.println("Error: vertex already exists");
        }
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
        int indR = getVertexPosition(tarLabel);
        int indA = getVertexPosition(srcLabel);
        boolean duplicated = false;
        if(edges.containsKey(srcLabel + tarLabel)){
            /* If in the edges */
            duplicated = true;
        }
        if(duplicated == false) {
            if(indR >= 0 && indA >= 0) {
                edges.put(newEdge, col);
                col++;
                int indB = edges.get(newEdge);
                weights = extend(weights, row, col);
                weights[indA][indB] = weight;
                weights[indR][indB] = -weight;
            } else {
                System.out.println("Error: Vertex " + (indR < 0 ? tarLabel : indA < 0 ? srcLabel : "undefined") + " doesn't exist");
            }
        } else {
            System.out.println("Error: Edge already exists");
        }
       
    } // end of addEdge()

 
	public int getEdgeWeight(String srcLabel, String tarLabel) {
        /* Call getPosition with srcLabel and TarLabel to find the position and return
        the index according to the array */
        int[] index = getPosition(srcLabel, tarLabel);
        if(index[0] >= 0 && index[1] >= 0) {
            int edgeWeight = weights[index[0]][index[1]];
            return edgeWeight;
        }
        return EDGE_NOT_EXIST;
	} // end of existEdge()

    public int[] getPosition(String srcLabel, String tarLabel) {
        /* Get position of vertex and it's relative edge with tarLabel. This position will return
        an Array, first item is its row position and second item is its column position. */
        String edge = srcLabel + tarLabel;
        int colPosition = -1;
        int rowPosition = getVertexPosition(srcLabel);
        try {
            colPosition = edges.get(edge);
        } catch (Exception e) {
            /* DO NOTHING */
        }
        int result[] = new int[]{rowPosition,colPosition};
        return result;
    }

	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
        /* 
         * Get the reverse Row position and row position, update according to its column position
         */
        String edge = srcLabel + tarLabel;
        int indR = getVertexPosition(tarLabel);
        int indA = getVertexPosition(srcLabel);
     
        if(indA >= 0 && indR >= 0) {
            if(edges.containsKey(edge)) {
                int indB = edges.get(edge);
                weights[indA][indB] = weight;
                weights[indR][indB] = -weight;
            } else {
                System.out.println("Error: " + edge + " does not exist in edges");
            }
        } else {
            System.out.println("Error: Vertex " + (indR < 0 ? tarLabel : indA < 0 ? srcLabel : "undefined") + " doesn't exist");
        }

    } // end of updateWeightEdge()
    
    public int[] getEdgePosition(String vertLabel) {
        /* get list of edges index that contains vertLabel*/
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
        result = Arrays.copyOfRange(result, 0, (index > 0 ? index : 1));
        if(found == false) {
            result[0] = EDGE_NOT_EXIST;
        }
        return result;
    }
    public int getVertexPosition(String vertLabel) {
        /* Get the vertex position from vertex map */
        if(vertex.containsKey(vertLabel)) {
            return vertex.get(vertLabel);
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
        int result[][] = new int[row][col];

        int mRow = 0;
        int mCol = 0;
        for (int i = 0; i < weights.length; i++) {
            if(i != indVertex) {
                /* If row isn't the vertLabel */
                for(int j = 0; j < weights[i].length; j++) {
                    if(checkValidColumn(j, indEdges)) {
                        mRow %= result.length;
                        mCol %= result[0].length;
                        result[mRow][mCol] = weights[i][j];
                        mCol++;
                    }
                }
                mRow++;
            }
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
        if(rowPosition >= 0) {
            /* If there is a vertex, remove that vertex and all the edges contains that vertex */
            vertex.remove(vertLabel);
            reallocateVertex();
            // removeEdge(vertLabel);
        } else {
            System.out.println("Can't find vertex of " + vertLabel);
        }
        if(colPosition[0] != EDGE_NOT_EXIST) {
            /* We reallocate all the edges to copy to new array*/
            removeEdge(vertLabel);
            reallocateEdge();

            /* If there is index of edges, update the weights array */
            weights = clearWeight(weights, rowPosition, colPosition);
        }
    } // end of removeVertex()
    public void reallocateVertex() {
        /* This function reallocates the index of vertex. It will update
        the correct index of each item after remove Vertex */
        int j = 0;
        for(Map.Entry<String, Integer> vertlabel: vertex.entrySet()) {
            vertex.put(vertlabel.getKey(),j);
            j++;
        }
        row = j;
    }
    public void reallocateEdge() {
        /* This function reallocates the index of edges. It will update
        the correct index of each item after remove Edge  */
        int i = 0;
        for(Map.Entry<String, Integer> edge: edges.entrySet()) {
            edges.put(edge.getKey(), i);
            i++;
        }       
        col = i;
    }
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
        /* Return in nearest neighbours, search for the row that contains vertLabel. 
        And search for any weights that smaller than 0 */
        List<MyPair> neighbours = new ArrayList<MyPair>();
        int index = getVertexPosition(vertLabel);
        if(index != -1) {
            for(int i = 0; i < weights[index].length; i++) {
                if(weights[index][i] < 0) {
                    String label = "";
                    for(Map.Entry<String, Integer> entry: edges.entrySet()) {
                        int value = entry.getValue();
                        if(value == i) {
                            label = entry.getKey().substring(0,1);
                        }
                    }
                    if(!label.isEmpty())
                        /* Because weight < 0. We need to convert it back to > 0*/ 
                        neighbours.add(new MyPair(label,-weights[index][i]));
                }
            }
        }
        neighbours = sortMyPairs(neighbours);
        if(k != -1) {
            try {
                neighbours = neighbours.subList(0,k);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Out of bound Index, please choose less than "
                 + neighbours.size());
            }
        }
        return neighbours;
    } // end of inNearestNeighbours()


    public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
          /* Return out nearest neighbours, search for the row that contains vertLabel. 
        And search for any weights that larger than 0 */
        List<MyPair> neighbours = new ArrayList<MyPair>();
        int index = getVertexPosition(vertLabel);
        if(index != -1) {
            for(int i = 0; i < weights[index].length; i++) {
                if(weights[index][i] > 0) {
                    String label = "";
                    for(Map.Entry<String, Integer> entry: edges.entrySet()) {
                        int value = entry.getValue();
                        if(value == i) {
                            label = entry.getKey().substring(1,2);
                        }
                    }
                    if(!label.isEmpty())
                        neighbours.add(new MyPair(label,weights[index][i]));
                }
            }
        }
        neighbours = sortMyPairs(neighbours);
        if(k != -1) {
            try {
                neighbours = neighbours.subList(0,k);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Out of bound Index, please choose less than "
                 + neighbours.size());
            }
        }
        return neighbours;
    } // end of outNearestNeighbours()


    public void printVertices(PrintWriter os) {
        /* Print all the vertices in vertex map */
        for (Map.Entry<String, Integer> entry : vertex.entrySet()) {
            String key = entry.getKey();
            os.printf("%s ", key);
        }
        os.println();
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        /* Print all of the edges */
        for (Map.Entry<String, Integer> entry : edges.entrySet()) {
            String key = entry.getKey();
            String a = key.substring(0, 1);
            String b = key.substring(1, 2);
            int weight = getEdgeWeight(a, b);
            if(weight != 0)
                os.printf("%s %s %s\n", a, b, weight);
        }

    } // end of printEdges()

} // end of class IncidenceMatri¥¥¥¥¥
