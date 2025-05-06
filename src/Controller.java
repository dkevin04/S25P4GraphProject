/*
 * class used for much like database from p2, handles all the different method calls for graph and hashtable.
 */
public class Controller {
	private Graph graph;
	private Hash<String, Graph.GNode> artistTable;
	private Hash<String, Graph.GNode> songTable;

	public Controller(int capacity) {
		graph = new Graph();
		artistTable = new Hash<>(capacity);
		artistTable = new Hash<>(capacity);
	}

	public void insert(String artist, String song) {
		Graph.GNode artistNode = artistTable.find(artist);
		if (artistNode == null) {
			artistNode = graph.addNode(artist);
			artistTable.insert(artist, artistNode);
		}
		Graph.GNode songNode = songTable.find(song);
		if (songNode == null) {
			songNode = graph.addNode(song);
			songTable.insert(song, songNode);
		}
		graph.addEdge(artist, song);

	}

	public void remove(String name) {
		// Graph.GNode node = artistTable.find(name);
		if (artistTable.find(name) != null) {
			graph.removeNode(name);
			artistTable.remove(name);

		} else if (songTable.find(name) != null) {
			graph.removeNode(name);
			songTable.remove(name);
		} else {
			System.out.println("Name not found");
		}
	}

	public void print(String name) {
		// TODO Auto-generated method stub

	}

}
