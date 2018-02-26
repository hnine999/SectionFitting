package com.geometricmethods.geometry;

import java.util.Comparator;

import com.geometricmethods.geometry.Vertex;

public class VertexCoordinateComparator implements Comparator<Vertex> {

	private static VertexCoordinateComparator vertexCoordinateComparator = new VertexCoordinateComparator();
	
	public static VertexCoordinateComparator get()
	{
		return vertexCoordinateComparator;
	}
	
	@Override
	public int compare(Vertex v1, Vertex v2)
	{
		int comp = (int)Math.signum(v1.getX() - v2.getX());
		if (comp != 0)
		{
			return comp;
		}
		
		return (int)Math.signum(v1.getY() - v2.getY());
	}
}
