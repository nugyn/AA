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
    /* Array of NODES ie ArrayLists*/
    int i = 0;
    protected Node headers[] = new Node[i+1];
    
    public AdjList(){
        //
    }

    public void addVertex(String vertLabel) {
        /* Make a new linklist */
        System.out.println("Called");
        Node head = new Node(vertLabel);
        headers[i] = head;
        i++;
        headers = Arrays.copyOf(headers, i+1);
    } // end of addVertex()

    public void addEdge(String srcLabel, String tarLabel, int weight) {
        /* Update the Head, and Tail*/
        for(int i = 0; i < headers.length - 1; i++) {
            System.out.println("Checking header: " + headers[i].getLabel());
            if(headers[i].getLabel().equals(srcLabel)){
                Node currNode = headers[i];
                while(currNode.getNext() != null) {
                    /* This is the tail */
                    currNode = currNode.getNext();
                }

                System.out.println("[+] we are at " + currNode.getLabel());
                if(currNode.getPrev() != null)
                    System.out.println("[+] The previous is " + currNode.getPrev().getLabel());

                Node newNode = new Node(tarLabel);
                newNode.setWeight(weight);
                newNode.setPrev(currNode);
                currNode.setNext(newNode); 
                break;
            }
        }
    }
     // end of addEdge()


    public int getEdgeWeight(String srcLabel, String tarLabel) {

		    return EDGE_NOT_EXIST;
    } // end of existEdge()


    public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
        // Implement me!
        for(int i = 0; i < headers.length - 1; i++) {
            if(headers[i].getLabel().equals(srcLabel)) {
                Node currNode = headers[i];
                while(!currNode.getLabel().equals(tarLabel)) {
                    currNode = currNode.getNext();
                }
                currNode.setWeight(weight);
                break;
            }
        }
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
        for(int i = 0; i < headers.length - 1; i++) {
            System.out.printf("%s\n", headers[i].getLabel());
        }
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        // Implement me!
        for(int i = 0; i < headers.length - 1; i++) {
            Node currNode = headers[i];
            while(currNode != null) {
                if(currNode.getWeight() != 0) {
                    System.out.printf("%s %s %d",headers[i].getLabel(), currNode.getLabel(), currNode.getWeight());
                    System.out.println("");
                }
                currNode = currNode.getNext();
            }
        }
    } // end of printEdges()


    protected class Node {
    
        protected String label; // Stored value of the node
        protected Node mPrev; // Stored the value of the previous node
        protected Node mNext; //Reference to the next node
        protected int weight;

        public Node(String value) {
            label = value;
            mNext = null;
            mPrev = null;
        }

        public String getLabel() {
            return label;
        }

        public int getWeight() {
            return weight;
        }

        public Node getPrev() {
            return mPrev;
        }

        public Node getNext() {
            return mNext;
        }


        public void setWeight(int value) {
            weight = value;
        }


        public void setNext(Node next) {
            mNext = next;
        }

        public void setPrev(Node prev) {
            mPrev = prev;
        }

        @Override
        public String toString() {
            return String.format("Label: %s\n Weight: %d Next: %s\n Previous: \n", this.label, this.weight, 
            this.mNext.label, this.mPrev.label);
        }
    }
} // end of class AdjList
