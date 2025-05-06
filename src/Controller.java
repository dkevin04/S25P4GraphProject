/*
 * class used for much like database from p2, handles all the different method calls for graph and hashtable.
 */
public class Controller {
	private Graph graph;
	private Hash<String, Graph> artistTable;
	private Hash<String, Graph> songTable;
	
	public Controller(int capacity) {
		graph = new Graph();
		artistTable = new Hash<>(capacity);
		artistTable = new Hash<>(capacity);
	}

	public void insert(String artist, String song) {
		
	}

	public void remove(String name) {
		// TODO Auto-generated method stub
		
	}

	public void print(String name) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
