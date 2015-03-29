package com.thread.cyclicbarrier.genie;

/**
 * Setup creation exception.
 * @author Subrata Saha
 *
 */
public class SetupGenerationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String mesg;
	
	public SetupGenerationException(String id, String mesg) {
		super(mesg);
		this.id = id;
		this.mesg = mesg;
	}

	public String toString() {
		return "SetupGenerationException [id=" + id + ", mesg=" + mesg + "]";
	}
}
