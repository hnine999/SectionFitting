package com.geometricmethods.intersectiondetection;

import java.util.Comparator;

import com.geometricmethods.geometry.LineSegment;

// LineSegment Cross-Product Comparator
public class LineSegmentCounterClockwiseComparator implements Comparator<LineSegment>
{
	private static LineSegmentCounterClockwiseComparator lineSegmentCounterClockwiseComparator = new LineSegmentCounterClockwiseComparator();
	
	public static LineSegmentCounterClockwiseComparator get()
	{
		return lineSegmentCounterClockwiseComparator;
	}
	
	public int compare(LineSegment slg1, LineSegment slg2)
	{
		return (int)Math.signum(-slg1.getDiffVertex().crossProductZ(slg2.getDiffVertex()));
	}
}
