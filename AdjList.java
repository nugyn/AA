import java.io.*;
import java.util.*;

/**
 * Adjacency list implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class AdjList extends AbstractAssocGraph
{

    /**
	 * Contructs empty graph.
	 */

    /* Reference to the head of the node */
    protected Node mHead; 

    /* A dictionary of the sizes of the lists*/
    protected Map<String, Int> lengthDictionary = new HashMap();
   
   
    /* Array of Linkedlists   */
    protected Node arrayAdjList = new AdjList();

    /* Array of vertices*/
    int i = 0;
    protected String vertices[] = new vertices[i];

    
    

    public AdjList() {
         mHead = null;
         mlength = 0;
         lengthDictionary(null, 0);
        
    } // end of AdjList()


    public void addVertex(String vertLabel) {
    
        verticles[i] = vertLabel;
        lengthDictionary.entry(vertLabel, 1);
        i++;

        Node newNode = new Node();
        mHead = newNode();

        arrayAdjList
        
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel, int weight) {
        
        String newValue = (String)weight;
        String head = srcLabel;
        String keyVert = tarLabel;

        Node newNode = Node(vertLabel);

        if(mHead == null){
            mHead = newNode;
          }
        else{
           newNode.setNext(mHead); //set the reference next element 
           mHead = newNode;
          }

        mlength++; //increase size of list

        // list is not empty

            Node currNode = mHead;
            for (int i = 0; i < mLength; ++i) {
                if(keyVert.equalTo(currNode.getValue())){
                 String insertValue = keyVert + ", "+ newValue;
                 currNode(insertValue);
                 System.out.println("Added edge to vertice:" + keyVert);
                }
                currNode = currNode.getNext();
            
    
    

    } // end of addEdge()


    public int getEdgeWeight(String srcLabel, String tarLabel) {
		    // Implement me!

            /*

            1st Traverse Linked List
            Find the vertice (head of the linked list)
                Traverse through until you find the target edge
                    Update the value inside the node

            */

            String head = srcLabel;
            String keyVert = tarLavel;

            for(int i=0; i < mLength; i++){
                Node currNode = mHead;

            }







		    // update return value
		    return EDGE_NOT_EXIST;
    } // end of existEdge()


    public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
        // Implement me!
    } // end of updateWeightEdge()


    public void removeVertex(String vertLabel) {
        // Implement me!
    } // end of removeVertex()


    public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();

        // Implement me!

        return neighbours;
    } // end of inNearestNeighbours()


    public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();

        // Implement me!

        return neighbours;
    } // end of outNearestNeighbours()


    public void printVertices(PrintWriter os) {
        // Implement me!
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        // Implement me!
    } // end of printEdges()


    protected class Node {
    
        protected String mValue; //Stored value of the node

        protected Node mNext; //Reference to the next node

        public Node(String value) {
            mValue = value;
            mNext = null;
        }

        public int getValue() {
            return mValue;
        }


        public Node getNext() {
            return mNext;
        }


        public void setValue(int value) {
            mValue = value;
        }


        public void setNext(Node next) {
            mNext = next;
        }
    }
} // end of class AdjList
