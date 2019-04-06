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

    int i = 0;  // Headers array traverse variable
    protected Node headers[] = new Node[i+1]; // Array of headers of the LinkList. +1 for extra slot
    
    public AdjList(){
        /* Empty constructor*/
    }

    public void addVertex(String vertLabel) {
        /* Make a new linklist and add it to the headers array. Then extends
         * the length of the headers array.
         */
        Node head = new Node(vertLabel);
        head.setHeader();
        headers[i] = head;
        i++;
        headers = Arrays.copyOf(headers, i+1); 
    } // end of addVertex()

    public void addEdge(String srcLabel, String tarLabel, int weight) {
        /* Traverse through the headers, look for the header and add the edge to 
         * its linklist
         */
        for(int i = 0; i < headers.length - 1; i++) {
            if(headers[i].getLabel().equals(srcLabel)){
                Node currNode = headers[i];
                while(currNode.getNext() != null) {
                    /* Reach to the end of the linklist */
                    currNode = currNode.getNext();
                }
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
        /* Traverse through the headers, find the header that matches srcLabel. 
         * Look for each node in the LinkList and then Update its weight.
         */
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
        /* Loop through the headers list, find the removeIndex. IF can't find, 
         * removeIndex = -1
         * 
         * Looping through each Node from the Header, find the Node which connected to that
         * Vertex and remove it.
         * 
         * Update the original headers array so that the target vertex is removed.
         * 
        */
        int removeIndex = -1;
        Node originalHeaders[] = headers.clone();
        boolean found = false;

        for(int i = 0; i < headers.length-1; i++) { 
            /* Find remove Index and remove related Node each linklist using
             * removeEdge()
             */
            if(headers[i].getLabel().equals(vertLabel)) {
                removeIndex = i;
            }
            removeEdge(headers[i],vertLabel);
        }

        for(int i=0; i< originalHeaders.length -1; i++)
        {
            /* Remove the vertex from the headers array */
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
            /* Once the header is removed, adjust the size of the original
             * headers array by 1.
             */
            headers = Arrays.copyOf(headers,headers.length-1);
            i--;
        }
    } // end of removeVertex()

    public void removeEdge(Node header, String vertLabel) {
        /* Loop throught the the header node and delete vertLabel in its nodes */
        while(header.getNext()!= null) { 
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

    public List<MyPair> sortMyPairs(List<MyPair> myPair) {
        /* Using Bubble Sort to sort MyPair list*/
        for (int i = 0; i <= myPair.size()-2; i++) {
            for(int j = 0; j <= myPair.size() - 2 - i; j++) {
                if(myPair.get(j+1).getValue() < myPair.get(j).getValue()) {
                    Collections.swap(myPair,j,j+1);
                }
            }
        }
        return myPair;
    }

    public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
        /* Loop through every other headers that's not vertLabel, and find
         * if it's pointing to vertLabel. If yes then add to the MyPair neighbours List.
         * 
         * This List is then also be sorted using sortMyPairs();
         * And return according to k parameter.
         */
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
        /* Loop through every headers in neighbours. If the header is the same 
         * as vertLabel, look at its child and add all the Node into neighbours list.
         *
         * This list is then being sorted uisng sortMyPairs()
         * and return according to K parameter;
         */
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
        /* Loop through all the header in headers array and print the label */
        for(int i = 0; i < headers.length - 1; i++) {
            System.out.printf("%s\n", headers[i].getLabel());
        }
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        /* Loop through the headers, for each header, print all of the nodes */
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
        private String label; // Stored label of current node
        private Node mPrev; // Reference to the previous node
        private Node mNext; // Reference to the next node
        private int weight; // Weight of the current node
        private boolean isHeader = false; 

        public Node(String value) {
            /* Constructor to initialise the node*/
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

        public boolean isHeader(){
            return isHeader;
        }
    }
} // end of class AdjList
