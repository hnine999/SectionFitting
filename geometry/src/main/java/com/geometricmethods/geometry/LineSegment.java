package com.geometricmethods.geometry;

import java.util.EnumSet;

public class LineSegment extends TwoPointDefinition {

	private static VertexCoordinateComparator vertexComparator = VertexCoordinateComparator.get();
	public LineSegment() { }

	public LineSegment(Vertex firstVertex, Vertex secondVertex)
	{
		super(firstVertex, secondVertex);
	}

	public LineSegment(LineSegment lineSegment)
	{
		this(lineSegment.getFirstVertex(), lineSegment.getSecondVertex());
	}

	public boolean equals(Object other)
	{
		if (!(other instanceof LineSegment))
		{
			return false;
		}
		LineSegment otherLineSegment = (LineSegment)other;
		return getFirstVertex() == otherLineSegment.getFirstVertex() && getSecondVertex() == otherLineSegment.getSecondVertex() ||
				getFirstVertex() == otherLineSegment.getSecondVertex() && getSecondVertex() == otherLineSegment.getFirstVertex();
	}
	
	private static boolean vertexBetween(Vertex v1, Vertex vertex, Vertex v2)
	{
		return vertexComparator.compare(v1, vertex) <= 0 && vertexComparator.compare(vertex, v2) <= 0;				
	}
	
	private EnumSet<Intersection.EndpointOverlap> getOtherLineSegmentOverlap(LineSegment other)
	{
		EnumSet<Intersection.EndpointOverlap> intersectionProperties = EnumSet.noneOf(Intersection.EndpointOverlap.class);
		Vertex firstVertex = getFirstVertex();
		Vertex secondVertex = getSecondVertex();
		if (vertexComparator.compare(firstVertex, secondVertex) > 0)
		{
			firstVertex = getSecondVertex();
			secondVertex = getFirstVertex();
		}
		
		if (vertexBetween(firstVertex, other.getFirstVertex(), secondVertex))
		{
			intersectionProperties.add(Intersection.EndpointOverlap.L2V1);
		}

		if (vertexBetween(firstVertex, other.getSecondVertex(), secondVertex))
		{
			intersectionProperties.add(Intersection.EndpointOverlap.L2V2);
		}
		
		return intersectionProperties;
	}

	private EnumSet<Intersection.EndpointOverlap> getParallelLineSegmentOverlap(LineSegment other)
	{
		EnumSet<Intersection.EndpointOverlap> intersectionProperties = getOtherLineSegmentOverlap(other);
		intersectionProperties.addAll(flipEnumSet(other.getOtherLineSegmentOverlap(this)));
		return intersectionProperties;
		
	}
	
	public boolean containsVertex(Vertex vertex)
	{
		Vertex firstVertex = getFirstVertex();
		Vertex secondVertex = getSecondVertex();
		if (vertexComparator.compare(firstVertex, secondVertex) > 0)
		{
			firstVertex = getSecondVertex();
			secondVertex = getFirstVertex();
		}
		return vertexBetween(firstVertex, vertex, secondVertex);		
	}
	
	public Intersection getIntersection( LineSegment other )
	{
		if (parallelTo(other))
		{
			if (!parallelOverlap(other))
			{
				return new Intersection();
			}

			EnumSet<Intersection.EndpointOverlap> intersectionProperties = getParallelLineSegmentOverlap(other);
			Vertex intersectionVertex = new Vertex();
			Intersection.Type intersectionType = Intersection.Type.EXTENDED;
			
			if (intersectionProperties.size() == 2)
			{
				if (intersectionProperties.contains(Intersection.EndpointOverlap.L1V1))
				{
					intersectionVertex = getFirstVertex();
					if (intersectionProperties.contains(Intersection.EndpointOverlap.L2V1))
					{
						if (getFirstVertex().equals(other.getFirstVertex()))
						{
							intersectionType = Intersection.Type.SINGLE;
						}
					}
					else if (intersectionProperties.contains(Intersection.EndpointOverlap.L2V2))
					{
						if (getFirstVertex().equals(other.getSecondVertex()))
						{
							intersectionType = Intersection.Type.SINGLE;
						}
					}
				}
				else if (intersectionProperties.contains(Intersection.EndpointOverlap.L1V2))
				{
					intersectionVertex = getSecondVertex();
					if (intersectionProperties.contains(Intersection.EndpointOverlap.L2V1))
					{
						if (getSecondVertex().equals(other.getFirstVertex()))
						{
							intersectionType = Intersection.Type.SINGLE;
						}
					}
					else if (intersectionProperties.contains(Intersection.EndpointOverlap.L2V2))
					{
						if (getSecondVertex().equals(other.getSecondVertex()))
						{
							intersectionType = Intersection.Type.SINGLE;
						}
					}
				}
			}
			return new Intersection(intersectionVertex, intersectionType, intersectionProperties);
		}
		
		if (  getFirstVertex().equals( other.getFirstVertex() )  )
		{
			return new Intersection( new Vertex(getFirstVertex()), Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L2V1 );
		}
		if (  getFirstVertex().equals( other.getSecondVertex() )  )
		{
			return new Intersection( new Vertex(getFirstVertex()), Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L2V2 );
		}
		if (  getSecondVertex().equals( other.getFirstVertex() )  )
		{
			return new Intersection( new Vertex(getSecondVertex()), Intersection.EndpointOverlap.L1V2, Intersection.EndpointOverlap.L2V1 );
		}
		if (  getSecondVertex().equals( other.getSecondVertex() )  )
		{
			return new Intersection( new Vertex(getSecondVertex()), Intersection.EndpointOverlap.L1V2, Intersection.EndpointOverlap.L2V2 );
		}
		
		Vertex intersection = getIntersectionVertex(other);

		if (!containsVertex(intersection) || !other.containsVertex(intersection))
		{
			return new Intersection();
		}
		
		if (  getFirstVertex().equals( intersection )  )
		{
			return new Intersection( new Vertex(getFirstVertex()), Intersection.EndpointOverlap.L1V1 );
		}
		if (  getSecondVertex().equals( intersection )  )
		{
			return new Intersection( new Vertex(getSecondVertex()), Intersection.EndpointOverlap.L1V2 );
		}
		if (  other.getFirstVertex().equals( intersection )  )
		{
			return new Intersection( new Vertex(other.getFirstVertex()), Intersection.EndpointOverlap.L2V1 );
		}
		if (  other.getSecondVertex().equals( intersection )  )
		{
			return new Intersection( new Vertex(other.getSecondVertex()), Intersection.EndpointOverlap.L2V2 );
		}

		return new Intersection(intersection);
	}
	
	public Intersection getIntersection(Line other)
	{
		Intersection intersection = other.getIntersection(this);
		return new Intersection((Vertex)intersection, intersection.getType(), flipEnumSet(intersection.getEndpointOverlap()));
	}

	public Vertex getClosestPoint(Vertex vertex)
	{
		Vertex closestLinePoint = super.getClosestPoint(vertex);
		return containsVertex(closestLinePoint) ? closestLinePoint : vertex.distance(getFirstVertex()) < vertex.distance(getSecondVertex()) ? getFirstVertex() : getSecondVertex();
	}
	
	public float getDistance(Vertex vertex)
	{
		return vertex.minus(getClosestPoint(vertex)).magnitude();
	}
	
}
