/**
 * Graph implementation from OpenDsa, slightly tweaked to account for the
 * GraphNode class.
 * 
 * @author Blake Everman
 * 
 * @version 2024-05-6
 */
public class Graph {

    private class Edge {
        private int vertex;
        private int weight;
        private Edge prev, next;

        Edge(int v, int w, Edge p, Edge n) {
            this.vertex = v;
            this.weight = w;
            this.prev = p;
            this.next = n;
        }
    }

    private Edge[] nodeArray; // adjacency list heads
    private Object[] nodeValues; // labels stored at vertex index
    private int numEdge;
    private int capacity;
    private int size;

    /**
     * Left intentionally blank
     */
    public Graph() {
        // constructor intentionally left empty; graph must be initialized with
        // init()
    }


    /**
     * Initialize the graph with n vertices.
     * 
     * @param n
     *            size of the graph
     */
    public void init(int n) {
        capacity = n;
        nodeArray = new Edge[n];
        nodeValues = new Object[n];
        for (int i = 0; i < n; i++) {
            nodeArray[i] = new Edge(-1, -1, null, null);
        }
        numEdge = 0;
        size = 0;
    }


    /**
     * Returns the total size of the graph
     * 
     * @return
     *         length of the node array
     */
    public int nodeCount() {
        return nodeArray.length;
    }


    /**
     * Returns the number of edges in the graph
     * 
     * @return
     *         number of edges
     */
    public int edgeCount() {
        return numEdge;
    }


    /**
     * Returns the node stored at index v
     * 
     * @param v
     *            index of node we are searching for
     * @return
     *         node object stored
     */
    public Object getValue(int v) {
        return nodeValues[v];
    }


    /**
     * Sets the value at index v to the specific val passed
     * 
     * @param v
     *            index of entry we can to edit
     * @param val
     *            New data for the array entry
     */
    public void setValue(int v, Object val) {
        nodeValues[v] = val;
    }


    /**
     * Allocate a new node slot for a given name and return its index.
     * 
     * @param name
     *            name of the node being added
     */
    public int addNode(String name) {
        if (size >= capacity) {
            expand();
        }
        for (int i = 0; i < nodeValues.length; i++) {
            if (nodeValues[i] == null) {
                nodeValues[i] = name;
                size++;
                return i;
            }
        }
        return -1;
    }


    /**
     * EXpands the size of the graph to double the old capacity for both nodes
     * and edges
     */
    private void expand() {
        int newCapacity = capacity * 2;
        Object[] newValues = new Object[capacity];
        Edge[] newEdges = new Edge[capacity];

        for (int i = 0; i < capacity; i++) {
            newValues[i] = nodeValues[i];
            newEdges[i] = nodeArray[i];
        }

        for (int i = nodeValues.length; i < capacity; i++) {
            newEdges[i] = new Edge(-1, -1, null, null);
        }

        nodeValues = newValues;
        nodeArray = newEdges;
        capacity = newCapacity;

    }


    /**
     * Find the Edge node in v's list right before the one with vertex w.
     * 
     * @param v
     *            First index
     * @param w
     *            Second index
     * @return
     *         Head of the adjacency list
     */
    private Edge find(int v, int w) {
        Edge curr = nodeArray[v];
        while (curr.next != null && curr.next.vertex < w) {
            curr = curr.next;
        }
        return curr;
    }


    /**
     * Adds an edge object for the specified indices
     * 
     * @param v
     *            first index
     * @param w
     *            second index
     * @param wgt
     *            weight of the edge
     */
    public void addEdge(int v, int w, int weight) {
        if (weight == 0)
            return;
        insertEdge(v, w, weight);
        insertEdge(w, v, weight);
    }


    /**
     * Helper method for adding an edge
     * 
     * @param v
     *            first index
     * @param w
     *            second index
     * @param wgt
     *            weight of the edge
     */
    private void insertEdge(int v, int w, int weight) {
        Edge curr = find(v, w);
        if (curr.next != null && curr.next.vertex == w) {
            curr.next.weight = weight;
        }
        else {
            curr.next = new Edge(w, weight, curr, curr.next);
            numEdge++;
            if (curr.next.next != null) {
                curr.next.next.prev = curr.next;
            }
        }
    }


    /**
     * Finds the weight of the edge at the indices
     * 
     * @param v
     *            first index
     * @param w
     *            second index
     * @return
     *         the weight of the edge
     */
    public int weight(int v, int w) {
        Edge curr = find(v, w);
        if (curr.next == null || curr.next.vertex != w) {
            return 0;
        }
        return curr.next.weight;
    }


    /**
     * Returns true if the edge exists
     * 
     * @param v
     *            first index
     * @param w
     *            second index
     * @return
     *         true if the edge exists
     */
    public boolean hasEdge(int v, int w) {
        return weight(v, w) != 0;
    }


    /**
     * Removes the edge at the current indices
     * 
     * @param v
     *            first index
     * @param w
     *            second index
     */
    public void removeEdge(int v, int w) {
        Edge curr = find(v, w);
        if (curr.next == null || curr.next.vertex != w) {
            return;
        }
        curr.next = curr.next.next;
        if (curr.next != null) {
            curr.next.prev = curr;
        }
        numEdge--;
    }


    /**
     * Removes a node from the parent tree and removes all edges tied to it
     * 
     * @param index
     *            the index of the removed node
     */
    public void removeNode(int v) {
        int[] neighbors = neighbors(v);
        for (int i = 0; i < neighbors.length; i++) {
            int u = neighbors[i];
            removeEdge(u, v);
        }
        nodeArray[v].next = null;
        nodeValues[v] = null;
        size--;
    }


    /**
     * returns an int array of the edges a vertex has
     * 
     * @param v
     *            index of the head of the adjacency list
     * @return
     *         Array list of indices of the elements in the adjacency list
     */
    public int[] neighbors(int v) {
        int count = 0;
        Edge curr;
        for (curr = nodeArray[v].next; curr != null; curr = curr.next) {
            count++;
        }
        int[] result = new int[count];
        curr = nodeArray[v].next;
        for (int i = 0; i < count; i++) {
            result[i] = curr.vertex;
            curr = curr.next;
        }
        return result;
    }


    /**
     * Prints the number of connected components and how large the largest
     * component is
     * 
     */
    public void printGraph() {
        int n = nodeCount();
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }

        for (int i = 0; i < n; i++) {
            if (nodeValues[i] != null) {
                int[] neighbors = neighbors(i);
                for (int j = 0; j < neighbors.length; j++) {
                    union(parent, i, neighbors[j]);
                }
            }
        }

        int[] sizes = new int[n];
        for (int i = 0; i < n; i++) {
            if (nodeValues[i] != null) {
                int root = find(parent, i);
                sizes[root]++;
            }
        }

        int components = 0;
        int largest = 0;
        for (int i = 0; i < sizes.length; i++) {
            if (sizes[i] > 0) {
                components++;
                if (sizes[i] > largest) {
                    largest = sizes[i];
                }
            }
        }

        System.out.println("There are " + components + " connected components");
        System.out.println("The largest connected component has " + largest
            + " elements");
    }


    private int find(int[] parent, int v) {
        if (parent[v] == -1)
            return v;
        parent[v] = find(parent, parent[v]);
        return parent[v];
    }


    private void union(int[] parent, int a, int b) {
        int ra = find(parent, a);
        int rb = find(parent, b);
        if (ra != rb)
            parent[ra] = rb;
    }
}
