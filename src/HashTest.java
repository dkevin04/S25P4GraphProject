import student.TestCase;

/**
 * @author Kevin Dong
 * @version 05/05/2025
 */
public class HashTest extends TestCase {
    private Hash hash;
    private Hash smallHash;
    private Hash emptyHash;
    private Graph graph;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    @SuppressWarnings("rawtypes")
    public void setUp() {
        hash = new Hash(10000);
        smallHash = new Hash(10);
        emptyHash = new Hash(100);
        graph = new Graph();
    }


    /**
     * Check out the sfold method
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testSfold() throws Exception {
        assertTrue(Hash.h("a", 10000) == 97);
        assertTrue(Hash.h("b", 10000) == 98);
        assertTrue(Hash.h("aaaa", 10000) == 1873);
        assertTrue(Hash.h("aaab", 10000) == 9089);
        assertTrue(Hash.h("baaa", 10000) == 1874);
        assertTrue(Hash.h("aaaaaaa", 10000) == 3794);
        assertTrue(Hash.h("Long Lonesome Blues", 10000) == 4635);
        assertTrue(Hash.h("Long   Lonesome Blues", 10000) == 4159);
        assertTrue(Hash.h("long Lonesome Blues", 10000) == 4667);
        assertTrue(Hash.h("q'", 10000) == 97);
    }


    /**
     * Tests the insert method and by extension the search method
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void testInsert() throws Exception {
        assertEquals(hash.getSize(), 0);
        hash.insert("a", graph);
        assertEquals(hash.search("a"), 97);
        assertEquals(hash.getSize(), 1);
        hash.insert("b", graph);
        assertEquals(hash.search("b"), 98);
        hash.insert("aaaa", graph);
        assertEquals(hash.search("aaaa"), 1873);
        hash.insert("aaab", graph);
        assertEquals(hash.search("aaab"), 9089);
        assertEquals(hash.getSize(), 4);
        hash.insert("baaa", graph);
        assertEquals(hash.search("baaa"), 1874);
        hash.insert("Long Lonesome Blues", graph);
        assertEquals(hash.search("Long Lonesome Blues"), 4635);
        hash.insert("Long   Lonesome Blues", graph);
        assertEquals(hash.search("Long   Lonesome Blues"), 4159);
        hash.insert("long Lonesome Blues", graph);
        assertEquals(hash.search("long Lonesome Blues"), 4667);
        assertEquals(hash.getSize(), 8);
        hash.insert("a", graph);
        assertTrue(hash.getTable()[101].key().toString().equals("a"));
        hash.insert("a", graph);
        assertTrue(hash.getTable()[106].key().toString().equals("a"));
        assertEquals(hash.getSize(), 10);
        hash.insert("q'", graph);
        assertEquals(hash.search("q'"), 113);
        assertEquals(hash.getSize(), 11);
        assertEquals(hash.search("Beatles"), -1);
        assertEquals(smallHash.getTable().length, 10);
        smallHash.insert("a", graph);
        smallHash.insert("b", graph);
        smallHash.insert("c", graph);
        smallHash.insert("d", graph);
        smallHash.insert("e", graph);
        smallHash.insert("f", graph);
        smallHash.prints();
        assertEquals(smallHash.getTable().length, 20);
        emptyHash.prints();
        smallHash = new Hash(10);
        smallHash.insert("c", graph);
        smallHash.insert("c", graph);
        smallHash.insert("c", graph);
    }


    /**
     * Tests the remove method
     */
    @SuppressWarnings("unchecked")
    public void testRemove() throws Exception {
        hash.insert("a", graph);
        assertTrue(hash.getTable()[97].key().toString().equals("a"));
        hash.insert("b", graph);
        assertEquals(hash.search("a"), 97);
        assertEquals(hash.search("b"), 98);
        assertFalse(hash.getTable()[98].key().toString().equals("TOMBSTONE"));
        hash.remove("b");
        assertEquals(hash.search("b"), -1);
        assertEquals(hash.getSize(), 1);
        assertTrue(hash.getTable()[98].key().toString().equals("TOMBSTONE"));
        System.out.println(hash.getTable()[98].key().toString());
        assertTrue(hash.getTable()[98].key().toString().equals("TOMBSTONE"));
        assertFalse(hash.getTable()[97].key().toString().equals("TOMBSTONE"));
        hash.remove("Queen");
        assertEquals(hash.search("Queen"), -1);
        smallHash.insert("a", graph);
        smallHash.insert("b", graph);
        smallHash.insert("c", graph);
        smallHash.remove("c");
        assertEquals(smallHash.getSize(), 2);
    }


    /**
     * Tests the find method
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void testFind() {
        smallHash.insert("a", graph);
        smallHash.insert("b", graph);
        smallHash.insert("c", graph);
        assertEquals(smallHash.find("b"), graph);
        assertNotNull(smallHash.find("b"));
        smallHash.insert("d", graph);
        smallHash.insert("e", graph);
        smallHash.insert("f", graph);
        assertEquals(smallHash.find("b"), graph);
        assertNotNull(smallHash.find("b"));
        assertEquals(smallHash.find("f"), graph);
        assertNotNull(smallHash.find("f"));
        assertNull(smallHash.find("F"));
        smallHash = new Hash(10);
        smallHash.insert("c", graph);
        smallHash.insert("c", graph);
        smallHash.insert("c", graph);
        assertEquals(smallHash.find("c"), graph);
        assertNotNull(smallHash.find("c"));
        assertNull(smallHash.find("F"));
    }


    /**
     * Tests the prints method
     */
    @SuppressWarnings("unchecked")
    public void testPrints() {
        smallHash.insert("a", graph);
        smallHash.insert("b", graph);
        smallHash.insert("c", graph);
        smallHash.insert("d", graph);
        smallHash.insert("e", graph);
        smallHash.insert("f", graph);
        assertEquals(smallHash.prints(), 6);
    }
}
