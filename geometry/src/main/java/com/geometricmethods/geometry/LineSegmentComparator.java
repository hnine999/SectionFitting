package com.geometricmethods.geometry;

import java.util.Comparator;

public class LineSegmentComparator implements Comparator<LineSegment>
{
	private static VertexCoordinateComparator vertexComparator = VertexCoordinateComparator.get();
	
	private static LineSegmentComparator lineSegmentComparator = new LineSegmentComparator();
	public static LineSegmentComparator get()
	{
		return lineSegmentComparator;
	}
	
	@Override
	public int compare(LineSegment ls1, LineSegment ls2)
	{
		int comp = vertexComparator.compare(ls1.getFirstVertex(), ls2.getFirstVertex());
		if (comp != 0)
		{
			return comp;
		}
		return vertexComparator.compare(ls1.getSecondVertex(), ls2.getSecondVertex());
	}
}
