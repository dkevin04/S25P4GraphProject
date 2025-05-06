
/**
 * Hash table class
 *
 * @author <Blake Everman>
 * @version <5.1.25>
 */

public class Hash<K extends Comparable<K>, E> {

    private KVPair<K, E>[] table;
    private KVPair<K, E> tombstone;

    private int capacity;

    private int size;

    /**
     * Compute the hash function
     * 
     * @param s
     *            The string that we are hashing
     * @param length
     *            Length of the hash table (needed because this method is
     *            static)
     * @return The hash function value (the home slot in the table for this key)
     */
    public static int h(String s, int length) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % length);
    }


    @SuppressWarnings("unchecked")
    public Hash(int hashSize) {
        table = new KVPair[hashSize];
        capacity = hashSize;
        size = 0;
        Graph element = new Graph();
        tombstone = new KVPair<K, E>((K)"TOMBSTONE", (E)element);
    }


    @SuppressWarnings("unchecked")
    public Hash() {
        table = new KVPair[1000];
        capacity = 1000;
        size = 0;
        E element = null;
        tombstone = new KVPair<K, E>((K)"TOMBSTONE", element);
    }


    public String find(K key) {
        int home;
        int pos = home = h((String)key, capacity);
        for (int i = 1; table[pos] != null && (!key.equals(table[pos].key()))
            && i <= capacity; i++) {
            pos = (home + i * i) % capacity;
        }
        if (table[pos] != null && key.equals(table[pos].key())) {
            return table[pos].value().toString();
        }
        else {
            return null;
        }
    }


    public int search(K key) {
        int home;
        int pos = home = h((String)key, capacity);
        for (int i = 1; table[pos] != null && (!key.equals(table[pos].key()))
            && i <= capacity; i++) {
            pos = (home + i * i) % capacity;
        }
        if (table[pos] != null && key.equals(table[pos].key())) {
            return pos;
        }
        else {
            return -1;
        }
    }


    public int prints() {
        int count = 0;
        /*
         * prints all the hash tale values and returns count
         */
        int index = 0;
        while (index < table.length) {
            if (table[index] != null) {
                System.out.println(table[index]);
                count++;
            }
            index++;
        }
        return count;
    }


    public void expandTable() {
        @SuppressWarnings("unchecked")
        KVPair<K, E>[] newTable = new KVPair[capacity * 2];
        int index = 0;
        while (index < newTable.length) {
            if (newTable[index] != null) {
                int newHome = h(newTable[index].key().toString(), table.length);
                newTable[newHome] = table[index];
            }
            index++;
        }
        table = newTable;
        capacity = capacity * 2;
    }


    public boolean insert(K key, E element) {
        /*
         * if expansion needed...
         */
        if (size + 1 > (capacity / 2)) {
            expandTable();
        }
        /*
         * if not then proceed to insert
         */
        int home;
        int pos = home = h((String)key, capacity);
        for (int i = 1; table[pos] != null && i <= capacity; i++) {
            pos = (home + i * i) % capacity;
        }
        if (table[pos] == null) {
            table[pos] = new KVPair<>(key, element);
            size++;
            return true;
        }
        return false;
    }


    public void remove(K key) {
        /*
         * removes using tombstone
         */
        int index = search(key);
        if (index >= 0) {
            table[index] = tombstone;
            size--;
            return;
        }
    }


    public int getSize() {
        return size;
    }


    public KVPair<K, E>[] getTable() {
        return table;
    }

}
