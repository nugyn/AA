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
        head.setHeader();
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
        int removeIndex = -1;
        Node originalHeaders[] = headers.clone();
        boolean found = false;

        for(int i = 0; i < headers.length-1; i++) { 
            System.out.println("[...] Headers: " + headers[i].getLabel());
            if(headers[i].getLabel().equals(vertLabel)) {
                removeIndex = i;
            }
            removeEdge(headers[i],vertLabel);
        }
        for(int i=0; i< originalHeaders.length -1; i++)
        {
            if(i == removeIndex) {
                found=true;
            } else {
                if(found) { 
                    headers[i-1] = originalHeaders[i];
                } else {
                    headers[i] = originalHeaders[i];
                }
            }
        }
        if(removeIndex != -1) {
            headers = Arrays.copyOf(headers,headers.length-1);
            i--;
        }
    } // end of removeVertex()

    public void removeEdge(Node header, String vertLabel) {
        /* Loop throught the header and delete vertLabel */
        while(header.getNext()!= null) { 
            System.out.println("[....] We are in label: " + header.getLabel());
            header = header.getNext();
        }
        if(header.getLabel().equals(vertLabel) && !header.isHeader()) {
            Node nextNode = header.getNext();
            Node prevNode = header.getPrev();
            if(nextNode != null) {
                nextNode.setPrev(prevNode);
                prevNode.setNext(nextNode);
            } else {
                prevNode.setNext(null);
            }
        }
    }

    public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();
        for(int i = 0; i < headers.length - 1; i++){
            if(!headers[i].getLabel().equals(vertLabel)){
                Node currNode = headers[i];
                currNode = currNode.getNext();
                while(currNode != null){
                    if(currNode.getLabel().equals(vertLabel)){
                        MyPair nearestNeighbour = 
                        new MyPair(headers[i].getLabel(), currNode.getWeight());
                        neighbours.add(nearestNeighbour);
                    }
                    currNode = currNode.getNext();
                }
            }
        }
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

    public List<MyPair> sortMyPairs(List<MyPair> myPair) {
        for (int i = 0; i <= myPair.size()-2; i++) {
            for(int j = 0; j <= myPair.size() - 2 - i; j++) {
                if(myPair.get(j+1).getValue() < myPair.get(j).getValue()) {
                    Collections.swap(myPair,j,j+1);
                }
            }
        }
        return myPair;
    }

    public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();
        for(int i = 0; i < headers.length - 1; i++) {
            if(headers[i].getLabel().equals(vertLabel)) {
                Node currNode = headers[i];
                currNode = currNode.getNext();
                while(currNode != null) {
                    MyPair nearestNeighbour = 
                    new MyPair(currNode.getLabel(), currNode.getWeight());
                    neighbours.add(nearestNeighbour);
                    currNode = currNode.getNext();
                }
                break;
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
    }
     // end of outNearestNeighbours()
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
        private String label; // Stored value of the node
        private Node mPrev; // Stored the value of the previous node
        private Node mNext; //Reference to the next node
        private int weight;
        private boolean isHeader = false;

        public Node(String value) {
            label = value;
            mNext = null;
            mPrev = null;
        }

        public boolean setHeader() {
            this.isHeader = true;
            return true;
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

        public boolean isHeader(){
            return isHeader;
        }
    }
} // end of class AdjList
