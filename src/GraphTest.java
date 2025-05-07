/**
 * Tests the graph class
 * 
 * @author Kevin Dong
 * 
 * @version 05/07/2025
 */
public class GraphTest extends student.TestCase {
    private Graph graph;

    /**
     * Intializes graph
     */
    public void setUp() {
        graph = new Graph();
        graph.init(5);
    }


    /**
     * Tests the add nodes and edges methods
     */
    public void testAdds() {
        assertEquals(graph.addNode("Queen"), 0);
        assertEquals(graph.addNode("Killer Queen"), 1);
        assertEquals(graph.addNode("Aerosmith"), 2);
        graph.addEdge(0, 1, 9);
        assertTrue(graph.hasEdge(0, 1));
        assertFalse(graph.hasEdge(1, 2));
        assertEquals(graph.weight(0, 1), 9);
        assertEquals(graph.getSize(), 3);
        assertEquals(graph.edgeCount(), 1);
        graph.addNode("David Bowie");
        graph.addNode("Under Pressure");
        assertEquals(graph.addNode("AC/DC"), 6);
    }


    /**
     * Tests the removes edges and nodes
     */
    public void testRemoves() {
        graph.addNode("Queen");
        graph.addNode("Killer Queen");
        graph.addNode("Aerosmith");
        graph.addEdge(0, 1, 9);
        assertEquals(graph.getSize(), 3);
        assertEquals(graph.edgeCount(), 1);
        graph.removeNode(2);
        graph.removeEdge(0, 1);
        assertEquals(graph.getSize(), 2);
        assertEquals(graph.edgeCount(), 0);
        assertFalse(graph.hasEdge(0, 1));
    }
}
