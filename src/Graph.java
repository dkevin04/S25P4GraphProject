/**
 * Graph class largely from OpenDsa, slightly tweaked to account for the
 * GraphNode class.
 */
public class Graph {

	private class Edge {
		int vertex;
		int weight;
		Edge prev, next;

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

	public Graph() {
		// constructor does nothing; graph must be initialized with init()
	}

	/**
	 * Initialize the graph with n vertices.
	 */
	public void init(int n) {
		capacity = n;
		nodeArray = new Edge[n];
		nodeValues = new Object[n];
		for (int i = 0; i < n; i++) {
			nodeArray[i] = new Edge(-1, -1, null, null); // dummy header
		}
		numEdge = 0;
		size = 0;
	}

	public int nodeCount() {
		return nodeArray.length;
	}

	public int edgeCount() {
		return numEdge;
	}

	public Object getValue(int v) {
		return nodeValues[v];
	}

	public void setValue(int v, Object val) {
		nodeValues[v] = val;
	}

	/**
	 * Allocate a new node slot for a given name and return its index.
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
	 */
	private Edge find(int v, int w) {
		Edge curr = nodeArray[v];
		while (curr.next != null && curr.next.vertex < w) {
			curr = curr.next;
		}
		return curr;
	}

	public void addEdge(int v, int w, int weight) {
		if (weight == 0)
			return;
		insertEdge(v, w, weight);
		insertEdge(w, v, weight);
	}

	private void insertEdge(int v, int w, int weight) {
		Edge curr = find(v, w);
		if (curr.next != null && curr.next.vertex == w) {
			curr.next.weight = weight;
		} else {
			curr.next = new Edge(w, weight, curr, curr.next);
			numEdge++;
			if (curr.next.next != null) {
				curr.next.next.prev = curr.next;
			}
		}
	}

	public int weight(int v, int w) {
		Edge curr = find(v, w);
		if (curr.next == null || curr.next.vertex != w) {
			return 0;
		}
		return curr.next.weight;
	}

	public boolean hasEdge(int v, int w) {
		return weight(v, w) != 0;
	}

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
		System.out.println("The largest connected component has " + largest + " elements");
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
