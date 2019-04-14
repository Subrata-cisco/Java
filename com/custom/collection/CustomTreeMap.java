package com.custom.collection;

public class CustomTreeMap<K, V> {

	private transient Entry<K, V> root;
	private transient int size = 0;
	private transient int modCount = 0;

	public CustomTreeMap() {
	}

	private static final boolean RED = false;
	private static final boolean BLACK = true;

	final Entry<K, V> getEntry(Object key) {
		if (key == null)
			throw new NullPointerException();
		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) key;
		Entry<K, V> p = root;
		while (p != null) {
			int cmp = k.compareTo(p.key);
			if (cmp < 0)
				p = p.left;
			else if (cmp > 0)
				p = p.right;
			else
				return p;
		}
		return null;
	}

	public V put(K key, V value) {
		Entry<K, V> t = root;
		if (t == null) {
			root = new Entry<>(key, value, null);
			size = 1;
			modCount++;
			return null;
		}

		int cmp;
		Entry<K, V> parent;
		if (key == null)
			throw new NullPointerException();

		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) key;
		do {
			parent = t;
			cmp = k.compareTo(t.key);
			if (cmp < 0)
				t = t.left;
			else if (cmp > 0)
				t = t.right;
			else
				return t.setValue(value);
		} while (t != null);
		Entry<K, V> e = new Entry<>(key, value, parent);
		if (cmp < 0)
			parent.left = e;
		else
			parent.right = e;
		fixAfterInsertion(e);
		size++;
		modCount++;
		return null;
	}
	
	private void fixAfterInsertion(Entry<K,V> x) {
		
	}
	
	/*private void fixAfterInsertion(Entry<K,V> x) {
        x.color = RED;

        while (x != null && x != root && x.parent.color == RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                Entry<K,V> y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                Entry<K,V> y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
    }*/

	static final class Entry<K, V> {// implements Map.Entry<K,V> {
		K key;
		V value;
		Entry<K, V> left;
		Entry<K, V> right;
		Entry<K, V> parent;
		boolean color = BLACK;

		/**
		 * Make a new cell with given key, value, and parent, and with
		 * {@code null} child links, and BLACK color.
		 */
		Entry(K key, V value, Entry<K, V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}

		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}
	}

}
