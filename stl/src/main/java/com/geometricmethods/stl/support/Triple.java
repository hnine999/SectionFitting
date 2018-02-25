package com.geometricmethods.stl.support;

public class Triple<A, B, C> {
	final public A first;
	final public B second;
	final public C third;
	
	public Triple( A first, B second, C third )
	{
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	public boolean equals(Object object)
	{
		if (!(object instanceof Triple))
		{
			return false;
		}
		
		Triple<?,?,?> triple = (Triple<?,?,?>)object;
		return first.equals(triple.first) && second.equals(triple.second) && third.equals(triple.third);
	}
}
