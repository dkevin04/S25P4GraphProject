
/**
 * Hash table class
 *
 * @author <Blake Everman> 
 * @version <5.1.25>
 */

public class Hash<K extends Comparable<K>, E> {

	private KVPair<K, E>[] table;

	private int capacity;

	private int size;

	/**
	 * Compute the hash function
	 * 
	 * @param s      The string that we are hashing
	 * @param length Length of the hash table (needed because this method is static)
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

		return (int) (Math.abs(sum) % length);
	}

	public String find(K key) {
		int home;
		int pos = home = h((String) key, capacity);
		for (int i = 1; table[pos] != null && (!key.equals(table[pos].key())) && i <= capacity; i++) {
			pos = (home + i*i) % capacity;
		}
		if (table[pos] != null && key.equals(table[pos].key())) {
			return (String) table[pos].value();
		}
		else {
			return null;
		}

	}

	public int prints() {
		int count = 0;
		/*
		 * prints all the hash tale values and returns count
		 */
		return count;
	}

	public void expandTable() {
		
	}

	public boolean insert(K key, E element) {
		/*
		 * if expandsion needed...
		 */
		if (size +1 / capacity > 0.5) {
			expandTable();
		}
		/*
		 * if not then proceed to insert
		 */
		int home;
		int pos = home = h((String) key, capacity);
		for (int i = 1; table[pos] != null && (!key.equals(table[pos].key())) && i <= capacity; i++) {
			pos = (home + i*i) % capacity;
		}
		if (table[pos] != null && key.equals(table[pos].key())) {
			table[pos] = new KVPair<>(key, element);
			size++;
			return true;
		}
		return false;
	}

	public void remove(String key) {
		/*
		 * removes using tombstone
		 */
	}

}
