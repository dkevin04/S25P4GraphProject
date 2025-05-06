/**
 * Graph implemented as adjacency list with vertex labels. Designed for use with
 * GraphNode objects holding index and name.
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

	public Graph() {
		// constructor does nothing; graph must be initialized with init()
	}

	/**
	 * Initialize the graph with n vertices.
	 */
	public void init(int n) {
		nodeArray = new Edge[n];
		nodeValues = new Object[n];
		for (int i = 0; i < n; i++) {
			nodeArray[i] = new Edge(-1, -1, null, null); // dummy header
		}
		numEdge = 0;
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
		for (int i = 0; i < nodeValues.length; i++) {
			if (nodeValues[i] == null) {
				nodeValues[i] = name;
				return i;
			}
		}
		throw new RuntimeException("Graph full: expansion not implemented.");
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
		for (int u : neighbors) {
			removeEdge(u, v);
		}
		nodeArray[v].next = null;
		nodeValues[v] = null;
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
		for (int i = 0; i < n; i++)
			parent[i] = -1;

		for (int i = 0; i < n; i++) {
			if (nodeValues[i] == null)
				continue;
			for (int neighbor : neighbors(i)) {
				union(parent, i, neighbor);
			}
		}

		int[] sizes = new int[n];
		for (int i = 0; i < n; i++) {
			if (nodeValues[i] != null) {
				int root = find(parent, i);
				sizes[root]++;
			}
		}

		int components = 0, largest = 0;
		for (int size : sizes) {
			if (size > 0) {
				components++;
				largest = Math.max(largest, size);
			}
		}

		System.out.println("Number of connected components: " + components);
		System.out.println("Size of largest component: " + largest);
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
