/**
 * MFNodeByKindFilter.java
 *
 * This file was generated by MapForce 2017sp2.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the MapForce Documentation for further details.
 * http://www.altova.com/mapforce
 */

package com.altova.mapforce;

public class MFNodeByKindFilter implements IEnumerable
{
	public static class Enumerator implements IEnumerator 
	{
		IEnumerator baseEnum;
		int nodeKind;
		int pos = 0;
		
		public Enumerator(IEnumerator baseEnum, int nodeKind)
		{
			this.baseEnum = baseEnum;
			this.nodeKind = nodeKind;
		}
		
		public boolean moveNext()  throws Exception
		{
			while (baseEnum.moveNext())
			{
				IMFNode node = baseEnum.current() instanceof IMFNode ? (IMFNode) baseEnum.current() : null;
				if (node != null)
				{
					if ((node.getNodeKind() & nodeKind) != 0)
					{
						pos++;
						return true;
					}
				}
				else
				{
					// simple value
					if ((nodeKind & IMFNode.MFNodeKind_Text) != 0)
					{
						pos++;
						return true;
					}
				}
			}
			return false;
		}
		
		static class ContentNode implements IMFNode 
		{
			private Object o;
		
			public ContentNode(Object o) {this.o = o;}
			
			public int getNodeKind() {return IMFNode.MFNodeKind_Text; }
			public String getLocalName() {return ""; }
			public String getNodeName() {return ""; }
			public String getNamespaceURI() {return ""; }
			public String getPrefix() {return ""; }
			public javax.xml.namespace.QName getQName() {return new javax.xml.namespace.QName("", "");}
			public IEnumerable select(int mfQueryKind, Object query) {return new MFSingletonSequence(o);}
			public String value() throws Exception 
			{ 
				return (o instanceof javax.xml.namespace.QName) ? com.altova.CoreTypes.castToString((javax.xml.namespace.QName) o) : o.toString(); 
			}	
			public javax.xml.namespace.QName qnameValue() {return (javax.xml.namespace.QName) o;}
			public Object typedValue() throws Exception 
			{
				return o;
			}
		}
		
		public Object current() 
		{
			Object o = baseEnum.current();
			return (o instanceof IMFNode) ? o : new ContentNode(o);
		}
		
		public int position() {return pos;}
		
		public void close() {baseEnum.close();}
	}
	
	private IEnumerable baseSet;
	private int nodeKind;
	
	public MFNodeByKindFilter(IEnumerable baseSet, int nodeKind) 
	{
		this.baseSet = baseSet;
		this.nodeKind = nodeKind;
	}
	
	public IEnumerator enumerator() throws Exception
	{
		return new Enumerator(baseSet.enumerator(), nodeKind);
	}
}
