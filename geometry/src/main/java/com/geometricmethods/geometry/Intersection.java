package com.geometricmethods.geometry;

import java.util.Arrays;
import java.util.EnumSet;

public class Intersection extends Vertex
{
	private static final long serialVersionUID = 1L;

	public enum Type { NONE, SINGLE, EXTENDED };
	public enum EndpointOverlap { L1V1, L1V2, L2V1, L2V2 }; 
	
	private final Type type;
	private final EnumSet<EndpointOverlap> endpointOverlap;
	
	public Intersection()
	{
		this.type = Type.NONE;
		this.endpointOverlap = EnumSet.noneOf(EndpointOverlap.class);
	}
	
	public Intersection(Vertex intersection, EndpointOverlap ... endpointOverlap)
	{
		super(intersection);
		this.type = Type.SINGLE;
		this.endpointOverlap = endpointOverlap.length == 0 ? EnumSet.noneOf(Intersection.EndpointOverlap.class) : EnumSet.copyOf(Arrays.asList(endpointOverlap));
	}
	
	public Intersection(Vertex intersection, EnumSet<EndpointOverlap> endpointOverlap)
	{
		super(intersection);
		this.type = Type.SINGLE;
		this.endpointOverlap = EnumSet.copyOf(endpointOverlap);
	}
	
	public Intersection(Vertex intersection, Type type, EndpointOverlap ... endpointOverlap)
	{
		super(intersection);
		this.type = type;
		this.endpointOverlap = endpointOverlap.length == 0 ? EnumSet.noneOf(Intersection.EndpointOverlap.class) : EnumSet.copyOf(Arrays.asList(endpointOverlap));
	}
	
	public Intersection(Vertex intersection, Type type, EnumSet<EndpointOverlap> endpointOverlap)
	{
		super(intersection);
		this.type = type;
		this.endpointOverlap = EnumSet.copyOf(endpointOverlap);
	}
	
	public boolean isValid()
	{
		return type != Type.NONE;
	}

	public boolean isSingle()
	{
		return type == Type.SINGLE;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public EnumSet<EndpointOverlap> getEndpointOverlap()
	{
		return endpointOverlap;
	}
	
	public boolean isExtended()
	{
		return type == Type.EXTENDED;
	}
	
	public boolean hasL1V1()
	{
		return endpointOverlap.contains(EndpointOverlap.L1V1);
	}

	public boolean hasL1V2()
	{
		return endpointOverlap.contains(EndpointOverlap.L1V2);
	}
	
	public boolean hasL2V1()
	{
		return endpointOverlap.contains(EndpointOverlap.L2V1);
	}
	
	public boolean hasL2V2()
	{
		return endpointOverlap.contains(EndpointOverlap.L2V2);
	}
	
	public boolean equals(Object other)
	{
		return super.equals(other);
	}
}
