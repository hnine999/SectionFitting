package com.geometricmethods.geometry;

import java.util.EnumSet;

public class Line extends TwoPointDefinition
{
	public Line() {}

	public Line(Vertex vertex1, Vertex vertex2)
	{
		super(vertex1, vertex2);
	}

	public boolean equals(Object other)
	{
		return other instanceof Line ? getIntersection((Line)other).isExtended() : false;
	}
	
	public Intersection getIntersection(Line other)
	{
		return parallelTo(other) ?
				parallelOverlap(other) ?
						new Intersection(new Vertex(), Intersection.Type.EXTENDED, EnumSet.allOf(Intersection.EndpointOverlap.class)) :
							new Intersection() :
								new Intersection(getIntersectionVertex(other), Intersection.Type.SINGLE);
	}

	public Intersection getIntersection(LineSegment other)
	{
		if (parallelTo(other))
		{
			return parallelOverlap(other) ?
				new Intersection(new Vertex(), Intersection.Type.EXTENDED, EnumSet.allOf(Intersection.EndpointOverlap.class)) :
					new Intersection();
		}
		Vertex intersectionVertex = getIntersectionVertex(other);
		return other.containsVertex(intersectionVertex) ?
				new Intersection(intersectionVertex, Intersection.Type.SINGLE,
						intersectionVertex.equals(other.getFirstVertex()) ?
							EnumSet.of(Intersection.EndpointOverlap.L2V1) :
							intersectionVertex.equals(other.getSecondVertex()) ?
								EnumSet.of(Intersection.EndpointOverlap.L2V2) :
								EnumSet.noneOf(Intersection.EndpointOverlap.class)) :
				new Intersection();
	}
}
