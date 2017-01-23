////////////////////////////////////////////////////////////////////////
//
// Group.java
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

package com.altova.text.edi;

import java.io.IOException;
import com.altova.text.ITextNode;
import com.altova.text.Generator;

public class Group extends StructureItem 	{

	public Group (String name, String conditionPath, String conditionValue, Particle[] children) {
		super(name, ITextNode.Group, conditionPath, conditionValue, children);
	}

	public boolean read (Parser.Context context)
	{
		if (!isAtGroup (context))
			return false;

		Generator generator = context.getGenerator();
		generator.enterElement (context.getParticle().getName(), mNodeClass);

		if( mName.equals("Group") )
		{
			context.getParser().resetTransactionSetCount();
			context.getParser().resetTransactionSetAccepted();
		}

		if( mName.equals("Message") )
		{
			context.getParser().setCurrentMessageType( context.getParser().getFirstMessage().getMessageType() );
		}

		if (!readChildren (context, ServiceChars.None))
		{
			ITextNode node = generator.getCurrentNode();
    		if( mName.equals("Message") )
    		{
    			context.getParser().setCurrentMessageType( null );
    		}
			generator.leaveElement (context.getParticle().getName());
			if( mName.equals("Batch") && node.getChildren().size() == 0 )
			{
				generator.getCurrentNode().getChildren().removeAt( generator.getCurrentNode().getChildren().size() - 1 );
			}
			return false;
		}

		if( mName.equals("Message") )
		{
			context.getParser().setCurrentMessageType( null );
		}

		generator.leaveElement (context.getParticle().getName());
		return true;
	}

	public void write (Writer writer, ITextNode node, Particle particle) throws IOException {
		writeChildren (writer, node, ServiceChars.None);
	}
}
