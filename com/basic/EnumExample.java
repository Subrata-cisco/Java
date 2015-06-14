package com.basic;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Usage of Enum in java.
 * @author Subrata Saha.
 *
 */
public class EnumExample {

	/**
	 * Flavor 1;
	 * @author Subrata Saha.
	 *
	 */
	public enum Direction1 {
		 EAST,
		 WEST,
		 NORTH,
		 SOUTH;
	 }
	
	/**
	 * Flavor 2
	 * @author Subrata Saha.
	 *
	 */
	enum Direction2 {
		// Enum types
		EAST(0), WEST(180), NORTH(90), SOUTH(270);

		// Constructor
		private Direction2(final int angle) {
			this.angle = angle;
		}

		// Internal state
		private int angle;

		public int getAngle() {
			return angle;
		}
	}
	
	public enum Direction3 {
		// Enum types
		EAST(0) {
			@Override
			public void shout() {
				System.out.println("Direction is East !!!");
			}
		},
		WEST(180) {
			@Override
			public void shout() {
				System.out.println("Direction is West !!!");
			}
		},
		NORTH(90) {
			@Override
			public void shout() {
				System.out.println("Direction is North !!!");
			}
		},
		SOUTH(270) {
			@Override
			public void shout() {
				System.out.println("Direction is South !!!");
			}
		};
		// Constructor
		private Direction3(final int angle) {
			this.angle = angle;
		}

		// Internal state
		private int angle;

		public int getAngle() {
			return angle;
		}

		// Abstract method which need to be implemented
		public abstract void shout();
	}
	
	/*
	 * Another usage.
	 */
	enum Direction {
		// Enum types
		EAST(0), WEST(180), NORTH(90), SOUTH(270);
		// Constructor
		private Direction(final int angle) {
			this.angle = angle;
		}

		// Internal state
		private int angle;

		public int getAngle() {
			return angle;
		}

		// Lookup table
		private static final Map<Object,Direction> lookup = new HashMap<>();

		// Populate the lookup table on loading time
		static {
			for (Direction s : EnumSet.allOf(Direction.class))
				lookup.put(s.getAngle(), s);
		}

		// This method can be used for reverse lookup purpose
		public static Direction get(int angle) {
			return lookup.get(angle);
		}
	}
	


	public enum SIZE1 {BIG,SMALL}
	
	public enum SIZE2 {
		
		BIG(10),SMALL(1);
		
		int actualSize = -1;
	
		SIZE2(int size){
		  this.actualSize = size ;
		}
	
	    int getActualSize(){
	    	return actualSize;
	    }
	
	}
	
	
	public enum SIZE3 {
		
		BIG(10){
			public String getDefaultSize(){
		    	return "A" ;
		    }
		},SMALL(1){
			public String getDefaultSize(){
		    	return "B" ;
		    }
		};
		
		int actualSize = -1;
	
		SIZE3(int size){
		  this.actualSize = size ;
		}
	
	    int getActualSize(){
	    	return actualSize;
	    }
	    
	    public abstract String getDefaultSize();
	
	}
	
	public static void main(String[] args) {
		// Angle lookup
				System.out.println(Direction.NORTH.getAngle());
				// Reverse lookup by angle
				System.out.println(Direction.get(90));
	}
}
