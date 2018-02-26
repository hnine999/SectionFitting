package com.geometricmethods.geometry;

import java.util.EnumSet;

public class TwoPointDefinition
{
	private static int uniqueInt = 0;

	protected static int getUniqueId()
	{
		return uniqueInt++;
	}

	protected int identifier = getUniqueId();
	
	protected Vertex firstVertex;
	protected Vertex secondVertex;
	protected Vertex diffVertex;

	public TwoPointDefinition()
	{
		this(new Vertex(), new Vertex());
	}
	
	public TwoPointDefinition(Vertex firstVertex, Vertex secondVertex)
	{
		this.firstVertex = firstVertex;
		this.secondVertex = secondVertex;
		setDiffVertex();
	}
	
	public int getId()
	{
		return identifier;
	}

	public void setFirstVertex( Vertex vertex )
	{
		firstVertex = vertex;
		setDiffVertex();
	}
	public Vertex getFirstVertex()
	{
		return firstVertex;
	}
	
	public void setSecondVertex( Vertex vertex )
	{
		secondVertex = vertex;
		setDiffVertex();
	}
	public Vertex getSecondVertex()
	{
		return secondVertex;
	}

	public void set( Vertex firstVertex, Vertex secondVertex )
	{
		this.firstVertex = firstVertex;
		this.secondVertex = secondVertex;
		setDiffVertex();
	}
	
	public void set(TwoPointDefinition twoPointDefinition)
	{
		set(twoPointDefinition.getFirstVertex(), twoPointDefinition.getSecondVertex());
	}
	
	public Vertex getDiffVertex()
	{
		return diffVertex;
	}
	
	protected void setDiffVertex()
	{
		diffVertex = secondVertex.minus( firstVertex );		
	}

	public float getXgivenY( float y )
	{
		if (getDiffVertex().getX() == 0)
		{
			return getFirstVertex().getX();
		}
		if (getFirstVertex().getY() == y)
		{
			return getFirstVertex().getX();
		}
		if (getSecondVertex().getY() == y)
		{
			return getSecondVertex().getX();
		}
		return (y - getFirstVertex().getY())*getDiffVertex().getX()/getDiffVertex().getY() + getFirstVertex().getX();
	}

	public float getYgivenX( float x )
	{
		if (getDiffVertex().getY() == 0)
		{
			return getFirstVertex().getY();
		}
		if (getFirstVertex().getX() == x)
		{
			return getFirstVertex().getY();
		}
		if (getSecondVertex().getX() == x)
		{
			return getSecondVertex().getY();
		}
		return (x - getFirstVertex().getX())*getDiffVertex().getY()/getDiffVertex().getX() + getFirstVertex().getY();
	}
	
	public boolean isVertical()
	{
		return getDiffVertex().getX() == 0;
	}
	
	public boolean isHorizontal()
	{
		return getDiffVertex().getY() == 0;
	}
	
	public boolean contains(Vertex vertex)
	{
		return	(diffVertex.getX() == 0 && firstVertex.getX() == vertex.getX()) || (diffVertex.getY() == 0 && firstVertex.getY() == vertex.getY()) ||
				firstVertex.equals(vertex) || secondVertex.equals(vertex) ||
				getYgivenX(vertex.getX()) == vertex.getY() || getXgivenY(vertex.getY()) == vertex.getX();
	}
	
	// THIS METHOD SHOULD ONLY BE USED ON TwoPointDefinition'S THAT ARE KNOWN TO BE PARALLEL
	protected boolean parallelOverlap(TwoPointDefinition other)
	{
		return contains(other.getFirstVertex()) || contains(other.getSecondVertex()) ||
				other.contains(getFirstVertex()) || other.contains(getSecondVertex());
	}

	protected EnumSet<Intersection.EndpointOverlap> otherOverlap(TwoPointDefinition twoPointDefinition)
	{
		EnumSet<Intersection.EndpointOverlap> intersectionProperties = EnumSet.noneOf(Intersection.EndpointOverlap.class);
		if (contains(twoPointDefinition.getFirstVertex()))
		{
			intersectionProperties.add(Intersection.EndpointOverlap.L1V2);
		}
		if (contains(twoPointDefinition.getSecondVertex()))
		{
			intersectionProperties.add(Intersection.EndpointOverlap.L2V2);
		}
		return intersectionProperties;
	}
	
	protected EnumSet<Intersection.EndpointOverlap> flipEnumSet(EnumSet<Intersection.EndpointOverlap> intersectionProperties)
	{
		EnumSet<Intersection.EndpointOverlap> newIntersectionProperties = EnumSet.noneOf(Intersection.EndpointOverlap.class);
		if (intersectionProperties.contains(Intersection.EndpointOverlap.L1V1))
		{
			newIntersectionProperties.add(Intersection.EndpointOverlap.L2V1);
		}
		if (intersectionProperties.contains(Intersection.EndpointOverlap.L1V2))
		{
			newIntersectionProperties.add(Intersection.EndpointOverlap.L2V2);
		}
		if (intersectionProperties.contains(Intersection.EndpointOverlap.L2V1))
		{
			newIntersectionProperties.add(Intersection.EndpointOverlap.L1V1);
		}
		if (intersectionProperties.contains(Intersection.EndpointOverlap.L2V2))
		{
			newIntersectionProperties.add(Intersection.EndpointOverlap.L1V2);
		}
		return newIntersectionProperties;
	}
	
	protected Vertex getIntersectionVertex(TwoPointDefinition other)
	{
		float denominator = getDiffVertex().crossProductZ(other.getDiffVertex());
		float coef1 = other.getFirstVertex().crossProductZ( other.getSecondVertex() );
		float coef2 = getFirstVertex().crossProductZ( getSecondVertex() );
		
		float interX = ( getDiffVertex().getX() * coef1 - other.getDiffVertex().getX() * coef2 ) / denominator;
		float interY = ( getDiffVertex().getY() * coef1 - other.getDiffVertex().getY() * coef2 ) / denominator;
		
		return new Vertex( interX, interY );
	}
	
	public boolean parallelTo(TwoPointDefinition other)
	{
		return getDiffVertex().parallelTo(other.getDiffVertex());
	}
	
	public Vertex getClosestPoint(Vertex other)
	{
		float diffVertexEndpointCrossProduct = getFirstVertex().crossProductZ(getSecondVertex());
		float diffVertexXSquared = getDiffVertex().getX() * getDiffVertex().getX();
		float diffVertexYSquared = getDiffVertex().getY() * getDiffVertex().getY();
		float diffVertexXYProduct = getDiffVertex().getX() * getDiffVertex().getY();
		
		float diffVectorMagnitudeSquared = diffVertexXSquared + diffVertexYSquared;
		
		float x = (diffVertexXSquared * other.getX() + diffVertexXYProduct * other.getY() - diffVertex.getY() * diffVertexEndpointCrossProduct ) /
				diffVectorMagnitudeSquared;
		float y = (diffVertexYSquared * other.getY() + diffVertexXYProduct * other.getX() + diffVertex.getX() * diffVertexEndpointCrossProduct ) /
				diffVectorMagnitudeSquared;
		
		return new Vertex(x,y);
	}
	
	public float getDistance(Vertex other)
	{
		return Math.abs(diffVertex.getY() * other.getX() + diffVertex.getX() * other.getY() + getSecondVertex().crossProductZ(getFirstVertex())) /
				diffVertex.magnitude();
	}
}
