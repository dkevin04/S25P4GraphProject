/*
 * class used for much like database from p2, handles all the different method calls for graph and hashtable.
 */
public class Controller {
	private Graph graph;
	private Hash<String, GraphNode> artistTable;
	private Hash<String, GraphNode> songTable;

	public Controller(int capacity) {
		graph = new Graph();
		graph.init(capacity * 2);
		artistTable = new Hash<>(capacity);
		songTable = new Hash<>(capacity);
	}

	public void insert(String artist, String song) {
		GraphNode artistNode = artistTable.find(artist);
		if (artistNode == null) {
			int index = graph.addNode(artist);
			artistNode = new GraphNode(artist, index);
			artistTable.insert(artist, artistNode);
		}
		GraphNode songNode = songTable.find(song);
		if (songNode == null) {
			int index = graph.addNode(song);
			songNode = new GraphNode(song, index);
			songTable.insert(song, songNode);
		}
		graph.addEdge(artistNode.getIndex(), songNode.getIndex(), 1);

	}

	public void remove(String name) {
		GraphNode node = artistTable.find(name);
		if (node != null) {
			graph.removeNode(node.getIndex());
			artistTable.remove(name);
			return;

		}
		node = songTable.find(name);
		if (node != null) {
			graph.removeNode(node.getIndex());
			songTable.remove(name);
			return;
		}
		System.out.println("Name not found");
	}

	public void print(String name) {
		if (name.equals("artist")) {
			System.out.println("Artist Hash Table:");
			int count = artistTable.prints();
			System.out.println("total artists: "+count);
		}
		else if (name.equals("song")) {
			System.out.println("Song Hash Table:");
			int count = songTable.prints();
			System.out.println("total songs: "+count);
		}
		else if (name.equals("graph")) {
			graph.printGraph();
		}
		else {
			System.out.println("Not allowed");
		}

	}

}
