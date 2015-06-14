package com.basic;

public class CollectionBasics {
   // https://weblogs.java.net/blog/2006/05/04/understanding-weak-references
	
	
	
	/*
	  ArrayList vs Linked List
	  
	  1) As explained above the insert and remove operations give good performance (O(1)) in LinkedList compared to 
	  ArrayList(O(n)). Hence if there is a requirement of frequent addition and deletion in application then LinkedList 
	  is a best choice.

      2) Search (get method) operations are fast in Arraylist (O(1)) but not in LinkedList (O(n)) so If there are less 
      add and remove operations and more search operations requirement, ArrayList would be your best bet
	
	
	  Implementing generic array :
	  
		public class GenSet<E> {
	
		    private E[] a;
		
		    public GenSet(Class<E> c, int s) {
		        // Use Array native method to create array of a type only known at run time
		        @SuppressWarnings("unchecked")
		        final E[] a = (E[]) Array.newInstance(c, s);
		        this.a = a;
		    }
		
		    E get(int i) {
		        return a[i];
		    }
		}
	
	
	 */
	
}
