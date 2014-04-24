package com.basic;


public class TricksWithBits {

	/**
	 * Swap 2 variables;
	 */
	private static void printSwap(){
		int a = 4;
		int b = 1;
		System.out.println("Before value of a ::"+a+" b ::"+b);
		a ^= b; 
		b ^= a; 
		a ^= b;
		
		System.out.println("Aftre value of  a ::"+a+" b ::"+b);
	}
	
	private static void printFirstBitSet(){
		int c = 3;
		System.out.println("Before value of c ::"+c);
		c = c & (~c + 1);
		//c = c & -c;
		System.out.println("Aftre value of c ::"+c);
	}
	
	private static void printFirstBitUnSet(){
		int c = 3;
		System.out.println("Before value of c ::"+c);
		c = ~c & (c + 1);
		System.out.println("Aftre value of c ::"+c);
	}
	
	/**
	 * Detect if two integers have opposite signs
	 * @param args
	 */
	private static void printIfOpposite(){
		int x = 3;
		int y = -5;
		
		boolean f = ((x ^ y) < 0);
		
		System.out.println(" x and y has opposite sign ::"+f);
	}
	
	/**
	 * Compute the minimum (min) or maximum (max) of two integers without branching
	 * @param args
	 */
	private static void printMinMax(){
		int x = 3;  // we want to find the minimum of x and y
		int y = 7;   
		int r = 0;  // the result goes here 

		r = y ^ ((x ^ y) & -(x < y?1:0)); // min(x, y)
		r = x ^ ((x ^ y) & -(x < y?1:0)); // max(x, y)
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////  ASSUMING b7, b6, b5, b4, b3, b3, b2, b1 and b0. The bit b7 is the sign bit (the most significant bit), and b0 is the least significant. /////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Bit Hack #1. Check if the integer is even or odd.
	 *  The idea here is that an integer is odd if and only if the least significant bit b0 is 1. It follows 
	 *  from the binary representation of 'x', where bit b0 contributes to either 1 or 0. By AND-ing 'x' with 
	 *  1 we eliminate all the other bits than b0. If the result after this operation is 0, then 'x' was even 
	 *  because bit b0 was 0. Otherwise 'x' was odd.
	 * @param args
	 */
	private static void printIfEvenOrOdd(){
		int x = 43;
		boolean odd = false;
		odd = ((x & 1) == 0) ? false :true ; 
		System.out.println(" x is odd ::"+odd);
	}
	
	/**
	 * Bit Hack #2. Test if the n-th bit is set.
	 * In the previous bit hack we saw that (x & 1) tests if the first bit is set. This bit hack improves 
	 * this result and tests if n-th bit is set. It does it by shifting that first 1-bit n positions to the
	 * left and then doing the same AND operation, which eliminates all bits but n-th.
	 * @param args
	 */
	private static void printIfNthBitIsSet(){
		int x = 122;
		int n = 5;
		
	     x = x & (1<<n);
		
		boolean isSet = (x == 0) ? true : false;
		System.out.println(" In x nth bit is set ::"+isSet);
	}
	
	/**
	 * Bit Hack #3. Set the n-th bit.
	 * This bit hack combines the same (1<<n) trick of setting n-th bit by shifting with OR operation.
	 *  The result of OR-ing a variable with a value that has n-th bit set is turning that n-th bit on. 
	 *  It's because OR-ing any value with 0 leaves the value the same; but OR-ing it with 1 changes 
	 *  it to 1 (if it wasn't already). Let's see how that works in action:
	 * @param args
	 */
	private static void printAndSetNthBit(){
		int x = 122;
		int n = 5;
		
	     x = x | (1<<n);
		
		System.out.println(" Aftre setting nth bit of x ::"+x);
	}
	
	/**
	 * Bit Hack #4. Unset the n-th bit.
	 * The important part of this bithack is the ~(1<<n) trick. It turns on all the bits except n-th.
	 * @param args
	 */
	private static void printAndUnSetNthBit(){
		int x = 122;
		int n = 5;
		
	     x = x & ~(1<<n);
		
		System.out.println(" Aftre unsetting nth bit of x ::"+x);
	}
	
	/**
	 * Bit Hack #5. Toggle the n-th bit.
	 * This bit hack also uses the wonderful "set n-th bit shift hack" but this time it XOR's it with the variable 'x'. 
	 * The result of XOR-ing something with something else is that if both bits are the same, the result is 0, otherwise it's 1. 
	 * How does it toggle n-th bit? Well, if n-th bit was 1, then XOR-ing it with 1 changes it to 0; 
	 * conversely, if it was 0, then XOR-ing with with 1 changes it to 1. See, the bit got flipped.
	 * @param args
	 */
	private static void printAndFlipNthBit(){
		int x = 122;
		int n = 5;
		
	     x = x ^ (1<<n);
		
		System.out.println(" Aftre flipping only nth bit of x ::"+x);
	}
	
	/**
	 * Bit Hack #6. Turn off the rightmost 1-bit.
	 * Now it finally gets more interesting!!! Bit hacks #1 - #5 were kind of boring to be honest.
	 * This bit hack turns off the rightmost one-bit. For example, given an integer 00101010 (the rightmost 1-bit in bold) 
	 * it turns it into 00101000. Or given 00010000 it turns it into 0, as there is just a single 1-bit.
	 * 
	 * Why does it work?

		If you look at the examples and think for a while, you'll realize that there are two possible scenarios:
		
		1. The value has the rightmost 1 bit. In this case subtracting one from it sets all the lower bits to one 
		and changes that rightmost bit to 0 (so that if you add one now, you get the original value back). 
		This step has masked out the rightmost 1-bit and now AND-ing it with the original value zeroes that rightmost 1-bit out.
		
		2. The value has no rightmost 1 bit (all 0). In this case subtracting one underflows the value (as it's signed) and sets all bits to 1. 
		AND-ing all zeroes with all ones produces 0.
	 * @param args
	 */
	private static void printAndFlipRightMost1Bit(){
		int x = 122;
		x = x & (x-1);
		System.out.println("Aftre flipping the riht most bit of x ::"+x);
	}
	
	/**
	 * Bit Hack #7. Isolate the rightmost 1-bit.
	 * This bit hack finds the rightmost 1-bit and sets all the other bits to 0. 
	 * The end result has only that one rightmost 1-bit set.
	 * For example, 01010 <b>1</b> 00 (rightmost bit in bold) gets turned into 00000100.
	 * @param args
	 */
	private static void printAndIsolateRightMost1Bit(){
		int x = 122;
		x = x & (-x);
		System.out.println("Aftre flipping the riht most bit of x ::"+x);
	}
	
	/**
	 * Bit Hack #8. Right propagate the rightmost 1-bit.
	 * This is best understood by an example. Given a value 01010000 it turns it into 01011111.
	 * All the 0-bits right to the rightmost 1-bit got turned into ones.
	 * This is not a clean hack, tho, as it produces all 1's if x = 0.
	 * @param args
	 */
	private static void printAndMakeallZerosFromRightMost1Bit(){
		int x = 122;
		x = x | (x-1);
		System.out.println("Aftre flipping the riht most bit of x ::"+x);
	}
	
	/**
	 * Bit Hack #9. Isolate the rightmost 0-bit.
	 * This bithack does the opposite of #7. It finds the rightmost 0-bit, turns off all bits, 
	 * and sets this bit to 1 in the result. 
	 * For example, it finds the zero in bold in this number 10101  0  11, producing 00000100.
	 * @param args
	 */
	private static void printAndIsolateRightMostZerothBit(){
		int x = 122;
		x = ~x & (x+1);
		System.out.println("Aftre flipping the riht most bit of x ::"+x);
	}
	
	/**
	 * Bit Hack #10. Turn on the rightmost 0-bit.
	 * This hack changes the rightmost 0-bit into 1. For example, given an integer 10100 0 11 it turns it into 10100 1 11.
	 * @param args
	 */
	private static void printAndTurnOnRightMostZerothBit(){
		int x = 122;
		x = x | (x+1);
		System.out.println("Aftre flipping the riht most bit of x ::"+x);
	}
	
	/**
	 * Nice and very useful tips. I have another one to add to your collection :) 
	 * Sometimes ago I have learned how to convert binary to decimal in mind very easily. 
	 * You only need to remember first 8 numbers: 
		000 0
		001 1
		010 2
		011 3
		100 4
		101 5
		110 6
		111 7
		
		Then, you need to know that shifting to left by one digit multiplies the number by 2: 
		1010b = 101b x 10b = 5 x 2 = 10d
		10100b = 101b x 100b = 5 x 8 = 40
		So, when you see something like 101011, you can process it this way:
		101000b + 11b = 5 * 8 + 3 = 43
		1110101 = 7 * 16 + 5 = 117
		and so on.
		
		It's easier than it looks at first glance :)
	 * @param args
	 */
	
	public static void main(String[] args) {
		//printSwap();
		//printFirstBitSet();
		//printFirstBitUnSet();
		//printIfOpposite();
		//printMinMax();
		//printIfEvenOrOdd();
		int oldCapacity = 2110;
		int newCapacity = (oldCapacity << 1) + 1;
		System.out.println(" newCapacity ::"+newCapacity);
	}
}
