package com.custom.collection;

import java.util.Arrays;

public class CustomArrayList<E> {

	Object[] elementData;
	int size = 0;

	public CustomArrayList(int initialCapacity) {
		if (initialCapacity <= 0) {
			initialCapacity = 10;
		}
		this.elementData = new Object[initialCapacity];
	}
	
	public boolean add(E data){
		if ((size+1) - elementData.length > 0){
			 int oldCapacity = elementData.length;
		     int newCapacity = oldCapacity + (oldCapacity >> 1);
		     if(newCapacity > Integer.MAX_VALUE - 8){
		    	 throw new OutOfMemoryError();
		     }
		     elementData = Arrays.copyOf(elementData, newCapacity);
		}
        elementData[size++] = data;
        return true;
	}
	
	@SuppressWarnings("unchecked")
	public E get(int index){
		if (index >= size)
            throw new IndexOutOfBoundsException();
		
		return (E) elementData[index];
	}
	
	@SuppressWarnings("unchecked")
	public E remove(int index){
		if (index >= size)
            throw new IndexOutOfBoundsException();
		
		E oldValue = (E) elementData[index];
		int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,numMoved);
        elementData[--size] = null;
        
        return oldValue;
	}

}
