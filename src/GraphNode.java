/**
 * The Class GraphNode to store graph components.
 * 
 * @author Blake Everman
 * 
 * @version 05/06/2025
 */
public class GraphNode {

    /** The name. */
    private String name;

    /** The index in adjacency list. */
    private int index;

    /**
     * Instantiates a new graph node.
     *
     * @param name
     *            the name
     * @param index
     *            the index
     */
    public GraphNode(String name, int index) {
        this.name = name;
        this.index = index;
    }


    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }


    /**
     * To string.
     *
     * @return the string
     */
    public String toString() {
        return name + " (Index " + index + ")";
    }
}
