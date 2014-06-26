package GarbageCollection;

public class Notes {
	
	/*
	Reference Counting :
		
		Needs help from compiler and the program to maintain a reference count
		Compiler adds code to increment/decrement reference count when the object is referenced/dereferenced
		If reference count is zero it is garbage collected and reference count of all objects it references is decremented by one
		Used by ANSI C++ library classes like string.
		
		Advantages: 
			Garbage collection can be immediate
	    Disadvantages:
			Cannot handle cyclic references   
			Need extra memory for the reference counter
			Incrementing and decrementing reference counts every time a reference is created or destroyed can significantly impede performance. Example: array processing


	 Mark-Sweep Collector:
		All application threads are stopped
		Mark: from the roots (objects referenced directly) every referenced object is visited and marked
		Sweep: entire heap is scanned and all unmarked objects are collected. Next all marked objects are reset

		Advantages:
			Can handle cyclic references
			No burden on the compiler or the application
			Disadvantages
			As entire heap is scanned, pauses would be longer
			If heap is paged can have performance issues
			Causes heap fragmentation which could lead of out of memory errors

	 Copying Collector:
		The heap is divided into two equal spaces
		One contains active data and the other is inactive
		When the active half gets full, the world is stopped, live objects are moved to the inactive half and then the active half is cleared
		For the next cycle, roles are reversed, the inactive half becomes the active half

		Advantages:
			Only live objects are visited, garbage objects are not visited
			Data compaction is achieved which reduces cost of object allocation and out of memory errors
		Disadvantages:
			Requires twice the heap size than other collectors
			Overhead of copying objects from one space to another
			Overhead of adjusting all references to the new copy
			Long lived objects are copied back and forth on every collection


	Mark-Compact Collector:
		All application threads are stopped
		Mark: from the roots (objects referenced directly) every referenced object is visited and marked
		Compact: entire heap is scanned and all unmarked objects are collected. Next all marked objects compacted at the bottom of the heap and then the flags are reset

		Advantages:
			Compaction is achieved 
			Without the hassle of long lived objects being copied back and forth
			Without the need for double the heap size
		Disadvantages
			Overhead of copying objects (for compaction)
			Overhead of adjusting all references to the new copy


		Generational Collector
		98% of the objects die young 
		Copying Collectors perform well with short-lived objects
		Mark-Compact Collectors perform well with long-lived objects
		Can we use different GC algorithms based on object’s age?

			The heap is divided into generations (usually 2 or 3)
			Objects are created in the young generation (gen 0)
			When memory is needed, a GC of gen 0 is performed & live objects are moved to the next generation (gen 1) & dead objects are collected (Copying Collector). If enough memory is now available GC is stopped
			Else the next older generation (gen 1) is collected. This goes on till enough memory is released or till the last (oldest) generation is reached. 
			The last generation (old objects) uses Mark-Compact Collector algorithm

			
			Advantages:
				GC cycles are small as all objects are not collected
				Only old objects are copied from one generation to another
				Entire heap is not scanned
			Disadvantages
				Overhead of copying objects (for compaction)
				Overhead of adjusting all references to the new copy


		  	1) Difference between Serial Garbage collector and Parallel Garbage Collector?
			2) What is ConcurrentMarkSweep Garbage Collector, Can you explain how Concurrent Mark Sweep GC works?
			3) What is Garbage collection tuning ?
			4) What is difference between major collection and minor collection in GC ?
			5) Can we run Garbage collector explicitly? How do you recognize full garbage collection because of Runtime.gc() or System.gc()
			6) What is difference between CMS and G1 garbage collector?
			7) What is Eden space in Heap?
			8) Have you ever done Garbage collection tuning? What are the heap sizes you have used?
			9) have you used -XX:+UseCompressedOops in 64 bit JVM? why you should use it?
			10) Can you extend Garbage Collection mechanism to provide your own Garbage collector ?
			
			Note that in HotSpot VM, two techniques are used for faster memory allocations.One is called "bump-the-pointer," and the other is called "TLABs (Thread-Local Allocation Buffers)." 
			
			Bump-the-pointer technique tracks the last object allocated to the Eden space. That object will be located on top of the Eden space. And if there is an object created afterwards,
			 it checks only if the size of the object is suitable for the Eden space.
			
			TLABs is the solution to this problem in HotSpot VM. This allows each thread to have a small portion of its Eden space that corresponds to its own share.
			 As each thread can only access to their own TLAB, even the bump-the-pointer technique will allow memory allocations without a lock. 
			 
			 GC for old generation :
			    Serial GC :
			      This GC type was created when there was only one CPU core on desktop computers. Using this serial GC will drop the application performance significantly. 
				Parallel GC:
				
				Parallel Old GC (Parallel Compacting GC):
				
				Concurrent Mark & Sweep GC  (or "CMS"):
				
				Garbage First (G1) GC:
				http://www.cubrid.org/blog/dev-platform/understanding-java-garbage-collection/
		 */
	
}
