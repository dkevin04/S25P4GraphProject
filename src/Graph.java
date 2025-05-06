import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

	// GNode represents a node in the graph (artist or song)
	public static class GNode {
		private String name;
		private List<GNode> neighbors;

		public GNode(String name) {
			this.name = name;
			this.neighbors = new ArrayList<>();
		}

		public String getName() {
			return name;
		}

		public List<GNode> getNeighbors() {
			return neighbors;
		}

		public void addNeighbor(GNode other) {
			if (!neighbors.contains(other)) {
				neighbors.add(other);
			}
		}

		public void removeNeighbor(GNode other) {
			neighbors.remove(other);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(name).append(":");
			for (GNode neighbor : neighbors) {
				sb.append(" ").append(neighbor.getName());
			}
			return sb.toString();
		}
	}

	private List<GNode> nodes;
	private HashMap<String, GNode> nodeMap;

	public Graph() {
		nodes = new ArrayList<>();
		nodeMap = new HashMap<>();
	}

	public GNode addNode(String name) {
		if (!nodeMap.containsKey(name)) {
			GNode newNode = new GNode(name);
			nodes.add(newNode);
			nodeMap.put(name, newNode);
			return newNode;
		}
		return nodeMap.get(name);
	}

	public void addEdge(String name1, String name2) {
		GNode node1 = addNode(name1);
		GNode node2 = addNode(name2);
		node1.addNeighbor(node2);
		node2.addNeighbor(node1);
	}

	public void removeNode(String name) {
		GNode node = nodeMap.get(name);
		if (node != null) {
			for (GNode neighbor : new ArrayList<>(node.getNeighbors())) {
				neighbor.removeNeighbor(node);
			}
			nodes.remove(node);
			nodeMap.remove(name);
		}
	}

	public GNode getNode(String name) {
		return nodeMap.get(name);
	}

	public List<GNode> getAllNodes() {
		return nodes;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (GNode node : nodes) {
			sb.append(node.toString()).append("\n");
		}
		return sb.toString();
	}
}
