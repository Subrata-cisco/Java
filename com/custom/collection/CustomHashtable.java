package com.custom.collection;

import java.util.Hashtable;


public class CustomHashtable<K,V> {
	
	private transient Entry<?,?>[] table;
	private float loadFactor = .75f;
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
	private int threshold;
	private int count;
	
	public CustomHashtable(){
		Hashtable<K, V>
		this.loadFactor = .75f;
        table = new Entry<?,?>[11];
        threshold = (int)Math.min(11 * loadFactor, MAX_ARRAY_SIZE + 1);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized V put(K key, V value){
		if (value == null) {
            throw new NullPointerException();
        }
		
		// check if it there , then return
		Entry<K,V>[] tab = (Entry<K,V>[])table;
		int keyIndex = (key.hashCode() & 0x7FFFFFFF)% tab.length;
		Entry<K,V> e = tab[keyIndex];
		for(;e!= null;e=e.next){
			if(key.hashCode() == e.hash && key.equals(e.key)){
				V old = e.value;
                e.value = value;
                return old;
			}
		}
		// if size is more then resize and rehash.
		if (count >= threshold) {
			rehash();
            keyIndex = (key.hashCode() & 0x7FFFFFFF) % tab.length;
		}
		
		// add entry
		@SuppressWarnings("unchecked")
        Entry<K,V> item = (Entry<K,V>) tab[keyIndex];
        tab[keyIndex] = new Entry<>(key.hashCode(), key, value, e);
        count++;
        
        return null;
	}
	
	private void rehash(){
		Entry<K,V>[] oldTable = (Entry<K,V>[])table;
		int oldCapacity = table.length;
		if(oldCapacity == MAX_ARRAY_SIZE){
			throw new OutOfMemoryError();
		}
		
		int newCapacity = oldCapacity << 1 ;
		if(newCapacity > MAX_ARRAY_SIZE){
			newCapacity = MAX_ARRAY_SIZE;
		}
		
		threshold = (int)Math.abs(newCapacity * loadFactor);
		Entry<K,V>[] newTable = new Entry[newCapacity];
		
		for (int i = oldCapacity ; i-- > 0 ;) {
            for (Entry<K,V> old = (Entry<K,V>)oldTable[i] ; old != null ; ) {
                Entry<K,V> e = old;
                old = old.next;

                int index = (e.hash & 0x7FFFFFFF) % newCapacity;
                e.next = (Entry<K,V>)newTable[index];
                newTable[index] = e;
            }
        }
		
		table = newTable;
		
	}
	
	public synchronized V remove(Object key) {
        Entry<?,?> tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        @SuppressWarnings("unchecked")
        Entry<K,V> e = (Entry<K,V>)tab[index];
        for(Entry<K,V> prev = null ; e != null ; prev = e, e = e.next) {
            if ((e.hash == hash) && e.key.equals(key)) {
                if (prev != null) {
                    prev.next = e.next;
                } else {
                    tab[index] = e.next;
                }
                count--;
                V oldValue = e.value;
                e.value = null;
                return oldValue;
            }
        }
        return null;
    }
	
	public synchronized boolean containsKey(Object key) {
        Entry<?,?> tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (Entry<?,?> e = tab[index] ; e != null ; e = e.next) {
            if ((e.hash == hash) && e.key.equals(key)) {
                return true;
            }
        }
        return false;
    }
	
	
	private static class Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Entry<K,V> next;

        protected Entry(int hash, K key, V value, Entry<K,V> next) {
            this.hash = hash;
            this.key =  key;
            this.value = value;
            this.next = next;
        }
	}
}
