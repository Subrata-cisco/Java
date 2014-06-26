package com.basic;


class Car implements Externalizable {

    String name;
    int year;

    public ExternalizationExample() { super(); }
    
    ExternalizationExample(String n, int y) {
	name = n;
	year = y;
    }

    public void writeExternal(ObjectOutput out) throws IOException  {
	out.writeObject(name);
	out.writeInt(year);
    }

    /** 
     * Mandatory readExternal method. 
     */
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
	name = (String) in.readObject();
	year = in.readInt();
    }

    /** 
     * Prints out the fields. used for testing!
     */
    public String toString() {
        return("Name: " + name + "\n" + "Year: " + year);
    }
}


import java.io.*;

public class ExternExample {
   
    public static void main(String args[]) {

	// create a Car object 
	ExternalizationExample car = new ExternalizationExample("Mitsubishi", 2009);
	ExternalizationExample newCar = null;
	
	//serialize the car
	try {
	    FileOutputStream fo = new FileOutputStream("tmp");
	    ObjectOutputStream so = new ObjectOutputStream(fo);
	    so.writeObject(car);
	    so.flush();
	} catch (Exception e) {
	    System.out.println(e);
	    System.exit(1);
	}

	// de-serialize the Car
	try {
	    FileInputStream fi = new FileInputStream("tmp");
	    ObjectInputStream si = new ObjectInputStream(fi);  	    
	    newCar = (ExternalizationExample) si.readObject();
	}
	catch (Exception e) {
	    System.out.println(e);
	    System.exit(1);
	}

	/* 
	 * Print out the original and new car information
	 */
	System.out.println("The original car is ");
	System.out.println(car);
	System.out.println("The new car is ");
        System.out.println(newCar);
    }
}
