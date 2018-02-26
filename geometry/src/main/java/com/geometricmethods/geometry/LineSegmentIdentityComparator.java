package com.geometricmethods.geometry;

import java.util.Comparator;

public class LineSegmentIdentityComparator implements Comparator<LineSegment>
{
	private static LineSegmentComparator lineSegmentComparator = LineSegmentComparator.get();
	
	@Override
	public int compare(LineSegment ls1, LineSegment ls2)
	{
		int comp = lineSegmentComparator.compare(ls1, ls2);
		if (comp != 0)
		{
			return comp;
		}
		return ls1.getId() - ls2.getId();
	}
}
