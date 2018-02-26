package com.geometricmethods.intersectiondetection;

import java.util.Comparator;

import com.geometricmethods.geometry.LineSegment;

/**
 * Description of file content.
 *
 * @author harmon.nine
 * 2/1/18
 */
public class LineSegmentClockwiseComparator implements Comparator<LineSegment>
{
	private static LineSegmentClockwiseComparator lineSegmentClockwiseComparator = new LineSegmentClockwiseComparator();

	public static LineSegmentClockwiseComparator get()
	{
		return lineSegmentClockwiseComparator;
	}

	public int compare(LineSegment slg1, LineSegment slg2)
	{
		return (int)Math.signum(slg1.getDiffVertex().crossProductZ(slg2.getDiffVertex()));
	}
}
