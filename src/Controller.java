/**
 * The Class Controller.
 */
/**
 * class used for much like database from p2, handles all the different method
 * calls for graph and hash table.
 * 
 * @author Blake Everman
 * 
 * @version 05/06/2025
 */
public class Controller {

    /** The graph object. */
    private Graph graph;

    /** The artist table holding a string name and GraphNode object. */
    private Hash<String, GraphNode> artistTable;

    /** The song table holding a string name and GraphNode object. */
    private Hash<String, GraphNode> songTable;

    /**
     * Instantiates a new controller.
     *
     * @param capacity
     *            the capacity given at runtime.
     */
    public Controller(int capacity) {
        graph = new Graph();
        graph.init(capacity * 2);
        artistTable = new Hash<>(capacity);
        songTable = new Hash<>(capacity);
    }


    /**
     * Attempts to find an artistNode, if found then a duplicate message
     * appears. If
     * not, then we add the node in the graph and then adds it to the hash
     * table.
     * The same is done for song. If it is added, then a new edge is also added.
     *
     * @param artist
     *            the artist being added
     * @param song
     *            the song being added
     */
    public void insert(String artist, String song) {
        GraphNode artistNode = artistTable.find(artist);
        if (artistNode == null) {
            int index = graph.addNode(artist);
            artistNode = new GraphNode(artist, index);
            artistTable.insert(artist, artistNode);
            System.out.println("|" + artist
                + "| is added to the Artist database.");
        }
        GraphNode songNode = songTable.find(song);
        if (songNode == null) {
            int index = graph.addNode(song);
            songNode = new GraphNode(song, index);
            songTable.insert(song, songNode);
            System.out.println("|" + song + "| is added to the Song database.");
        }
        if (graph.hasEdge(artistNode.getIndex(), songNode.getIndex())) {
            System.out.println("|" + artist + "<SEP>" + song
                + "| duplicates a record already in the database.");
            return;
        }

        graph.addEdge(artistNode.getIndex(), songNode.getIndex(), 1);

    }


    /**
     * Much like insert logic, removing from graph and hash table if found.
     * Also, we
     * take in a int value from command processor telling us if its a song or
     * artist
     * so that we can print correct error message if its not found.
     *
     * @param name
     *            the name
     * @param song
     *            the song
     */
    public void remove(String name, int song) {
        GraphNode node = artistTable.find(name);
        if (node != null) {
            graph.removeNode(node.getIndex());
            artistTable.remove(name);
            System.out.println("|" + name
                + "| is removed from the Artist database.");
            return;

        }
        node = songTable.find(name);
        if (node != null) {
            graph.removeNode(node.getIndex());
            songTable.remove(name);
            System.out.println("|" + name
                + "| is removed from the Song database.");
            return;
        }
        if (song == 1) {
            System.out.println("|" + name
                + "| does not exist in the Song database.");
            return;
        }
        System.out.println("|" + name
            + "| does not exist in the Artist database.");

    }


    /**
     * Prints the artist, song, or graph depending on argument passed.
     *
     * @param name
     *            the name
     */
    public void print(String name) {
        if (name.equals("artist")) {
            int count = artistTable.prints();
            System.out.println("total artists: " + count);
        }
        else if (name.equals("song")) {
            int count = songTable.prints();
            System.out.println("total songs: " + count);
        }
        else if (name.equals("graph")) {
            graph.printGraph();
        }
        else {
            System.out.println("Not allowed");
        }

    }

}
