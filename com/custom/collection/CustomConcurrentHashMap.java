package com.custom.collection;

public class CustomConcurrentHashMap {

	private static final int HASH_SIZE = 128; // bucket size
	HashEntry[] table;
	Object[] locks;

	CustomConcurrentHashMap() {
		table = new HashEntry[HASH_SIZE];
		locks = new Object[HASH_SIZE];
	}

	public void set(int key, int value) {
		// Step1: get the hash based on the key
		int hash = key % (HASH_SIZE);

		if (table[hash] == null) { // bucket is empty. Data can be inserted
			// Before setting the table with the key, lock the appropriate lock
			// array element
			synchronized (locks[hash]) {
				System.out.println("set(): No Collision for key = " + key
						+ " hash = " + hash);
				table[hash] = new HashEntry(key, value);
				locks[hash].notifyAll(); // notify all waiting threads and
											// release the lock
			}
		}
		// Else is the collision issue. In the linear probing, Keep checking
		// next array locations
		else {
			boolean locationFound = false;
			for (int i = hash + 1; i < HASH_SIZE; i++) {
				if (table[i] == null) {
					synchronized (locks[i]) {
						table[i] = new HashEntry(key, value);
						locationFound = true;
						locks[i].notifyAll();
					}
				}
			}

			if (!locationFound) {
				// There was no space from the hash location to the end of the
				// array. Check
				// the begining of the array instead
				for (int i = 0; i < hash; i++) {
					synchronized (locks[i]) {
						table[i] = new HashEntry(key, value);
						locationFound = true;
						locks[i].notifyAll();
					}
				}

				if (!locationFound) {
					System.out
							.println("HashMap is full, we can not store any more data thowing HashMapOverFlowException");
				}
			}
		}
	}

	public int get(int key) {
		// Step1: Generate the Hash from the key
		int hash = key % (HASH_SIZE);
		if (table[hash] != null) {
			// Check if this key matches the key stored in the object at this
			// location
			HashEntry entry = table[hash];
			if (entry.getKey() == key) {
				synchronized (locks[hash]) {
					// We can again check here to see the key is still same
					// correct entry found return this object
					System.out.println("get(): No Collision for key = " + key
							+ " hash = " + hash);
					return entry.getValue();
				}
			}
			/*
			 * else we have a collision and wrong value is placed in this
			 * location due to linear probing. So keep searching the array until
			 * we find the object with the matching key
			 */
			else {
				System.out.println("get(): Collision detected for key = " + key
						+ " hash = " + hash);
				for (int i = hash + 1; i < HASH_SIZE; i++) {
					if (table[i] != null) {
						entry = table[i];
						if (entry.getKey() == key) {

							System.out
									.println("get(): Linear probling key found at location = "
											+ i + " for hash = " + hash);
							// correct entry found return this object
							return entry.getValue();
						}
					}
				}
				// We reached here that means, the key is not found from the
				// hash location till the end of the array
				for (int i = 0; i < hash; i++) {
					if (table[i] != null) {
						entry = table[i];
						if (entry.getKey() == key) {
							synchronized (locks[i]) {
								System.out
										.println("get(): Linear probling key found at location = "
												+ i + " for hash = " + hash);
								// correct entry found return this object
								return entry.getValue();
							}
						}
					}
				}

				// Now if we reached here that means the element was never added
				// to the hashmap. So just return the lowest integer
				return Integer.MIN_VALUE;
			}
		} else {
			// If the entry at that location is null that means there is no
			// object in the array matching that key
			return Integer.MIN_VALUE;
		}
	}

	public static void main(String[] args) {
		CustomConcurrentHashMap map = new CustomConcurrentHashMap();

		map.set(5, 5);
		System.out.println("map.get(5) = " + map.get(5));

		map.set(128, 128);
		System.out.println("map.get(128) = " + map.get(128));
		map.set(0, 0);
		System.out.println("map.get(0) = " + map.get(0));

	}

	class HashEntry {

		int key;
		int value;

		public HashEntry(int key, int value) {
			this.key = key;
			this.value = value;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}
}
