////////////////////////////////////////////////////////////////////////
//
// Command.java
//
// This file was generated by MapForce 2017sp2.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the MapForce Documentation for further details.
// http://www.altova.com/mapforce
//
////////////////////////////////////////////////////////////////////////

package com.altova.text.flex;

public class Command {
	public static final char CR = '\r';
	public static final char LF = '\n';
	public static final char TAB = '\t';

	protected Command next;
	final protected String name;
	
	public Command(String name) {
		this.name = name;
		this.next = null;
	}
	
	public boolean readText(DocumentReader doc) {
		if (hasNext())
			next.readText(doc);
		return true;
	}
	
	public boolean writeText(DocumentWriter doc) {
		if (hasNext())
			next.writeText(doc);
		return true;
	}
	
	public void setNext(Command next) {
		this.next = next;
	}
	
	public String getName()	{
		return name;
	}
	
	public Command getNext() {
		return next;
	}
	
	public boolean hasNext() {
		return next != null;
	}
}
