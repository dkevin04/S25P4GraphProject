
/**
 * Hash table class
 *
 * @author <Blake Everman>
 * @author Kevin Dong
 * @version 05/05/2025
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
        Graph element = new Graph();
        tombstone = new KVPair<K, E>((K)"TOMBSTONE", (E)element);
    }


    public E find(K key) {
        int home;
        int pos = home = h((String)key, capacity);
        for (int i = 1; table[pos] != null && (!key.equals(table[pos].key()))
            && i <= capacity; i++) {
            pos = (home + i * i) % capacity;
        }
        if (table[pos] != null && key.equals(table[pos].key())) {
            return table[pos].value();
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
        for (int i = 0; i < table.length; i++) {
            KVPair<K, E> pair = table[i];
            if (pair != null && !pair.key().equals("TOMBSTONE")) {
                System.out.println(i + ": |" + pair.key() + "|");
                count++;
            }
            else if (pair != null && pair.key().equals("TOMBSTONE")) {
                System.out.println(i + ": TOMBSTONE");
            }
        }
        return count;
    }


    @SuppressWarnings("unchecked")
    public void expandTable() {
        KVPair<K, E>[] oldTable = table;
        int oldCapacity = capacity;

        capacity = oldCapacity * 2;
        table = new KVPair[capacity];
        size = 0;

        for (int i = 0; i < oldCapacity; i++) {
            KVPair<K, E> pair = oldTable[i];
            if (pair != null && !pair.key().equals("TOMBSTONE")) {
                insert(pair.key(), pair.value());
            }
        }
    }


    public boolean insert(K key, E element) {
        // if expansion needed.
        if (size + 1 > (capacity / 2)) {
            expandTable();
            System.out.println("Song hash table size doubled.");
        }
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
