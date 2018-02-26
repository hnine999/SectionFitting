package com.geometricmethods.intersectiondetection;

import java.util.Comparator;

import com.geometricmethods.geometry.Vertex;

public class VertexIdComparator implements Comparator<Vertex>
{
	private static VertexIdComparator vertexIdComparator = new VertexIdComparator();
	
	public static VertexIdComparator get()
	{
		return vertexIdComparator;
	}
	
	public int compare(Vertex vertex1, Vertex vertex2)
	{
		return vertex1.getIdentifier() - vertex2.getIdentifier();
	}
}
