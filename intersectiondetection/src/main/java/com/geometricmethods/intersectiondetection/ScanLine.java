package com.geometricmethods.intersectiondetection;

import java.util.Comparator;

import com.geometricmethods.geometry.LineSegment;
import com.geometricmethods.geometry.Vertex;
import com.geometricmethods.stl.set.SetIterator;
import com.geometricmethods.stl.set.SetStl;


//
// Scanline STARTS AT LEFT-MOST (DIRECTION OF NEGATIVE X-AXIS), LOWEST (DIRECTION OF NEGATIVE Y-AXIS) VERTEX(ES) OF POLYGON(S).
// EVENT VERTEXES PROCESS FROM LEFT-TO-RIGHT, BOTTOM-TO-TOP
//
public class ScanLine
{
	// EACH ScanLineGroup HAS A UNIQUE SORT VALUE THAT POSITIONS IT WITHIN THE SCANLINE ACCORDING TO THIS VALUE.  THE RESPECTIVE
	// SORT VALUE'S OF TWO ScanLineGroup'S WILL CAUSE THEM TO BE POSITIONED IN THE SCANLINE SUCH THAT THE ScanLineGroup WHOSE
	// LineSegment IS ABOVE (I.E. HAS HIGHER Y-COORDINATE VALUES THAN) THE LineSegment OF THE OTHER ScanLineGroup WILL COME AFTER
	// THIS LATTER ScanLineGroup.  NOTE THAT THE SORT VALUE OF A ScanLineGroup IS NOT BASED ON THE Y-COORDINATES OF ITS
	// LineSegment.  RATHER IT IS AN ARBITRARY VALUE THAT, WHEN USED TO SORT THE ScanLineGroup'S IN THE SCANLINE, CAUSES THE
	// ScanLineGroup'S TO BE SORTED IN THIS WAY.
	public static class ScanLineGroupComparator implements Comparator<ScanLineGroup>
	{
		private static ScanLineGroupComparator scanLineGroupComparator = new ScanLineGroupComparator();

		public static ScanLineGroupComparator get()
		{
			return scanLineGroupComparator;
		}

		public int compare(ScanLineGroup yak1, ScanLineGroup yak2)
		{
			return (int)Math.signum(yak1.getSortValue() - yak2.getSortValue());
		}
	}

	public static class ScanLineGroupTriple
	{
		private ScanLineGroup first;
		private ScanLineGroup second;
		private ScanLineGroup third;

		public ScanLineGroupTriple(ScanLineGroup first, ScanLineGroup second, ScanLineGroup third)
		{
			this.first = first;
			this.second = second;
			this.third = third;
		}

		public ScanLineGroup getFirst()
		{
			return first;
		}

		public ScanLineGroup getSecond()
		{
			return second;
		}

		public ScanLineGroup getThird()
		{
			return third;
		}
	}


	// ScanLine MUST BE REPRESENTED BY A MAP BECAUSE LOOKUP BY Y VALUE IS NEEDED
	private SetStl<ScanLineGroup> scanLineGroupSet = new SetStl<>(ScanLineGroupComparator.get());
	private SetIterator<ScanLineGroup> sgsItr_end = scanLineGroupSet.end();

	public ScanLine() {}

	public void addScanLineGroup(ScanLineGroup scanLineGroup)
	{
		scanLineGroupSet.insert(scanLineGroup);
	}
	 
	public void deleteScanLineGroup(ScanLineGroup scanLineGroup)
	{
		scanLineGroupSet.erase(scanLineGroup);
	}

	public SetIterator<ScanLineGroup> getEnd()
	{
		return scanLineGroupSet.end();
	}

	public ScanLineGroup getNextScanLineGroup(ScanLineGroup scanLineGroup)
	{
		SetIterator<ScanLineGroup> setIterator = scanLineGroupSet.find(scanLineGroup);
		if (setIterator.equals(sgsItr_end))
		{
			return null;
		}
		setIterator.increment();
		return setIterator.equals(sgsItr_end) ? null : setIterator.dereference();
	}

	public ScanLineGroup getPreviousScanLineGroup(ScanLineGroup scanLineGroup)
	{
		SetIterator<ScanLineGroup> setIterator = scanLineGroupSet.find(scanLineGroup);
		if (setIterator.equals(sgsItr_end) || setIterator.equals(scanLineGroupSet.begin()))
		{
			return null;
		}
		return setIterator.decrement().dereference();
	}

	public ScanLineGroupTriple getScanLineGroupTriple(Vertex vertex)
	{
		ScanLineGroup first = null;
		ScanLineGroup second = null;
		ScanLineGroup third = null;

		float xValue = vertex.getX();
		float yValue = vertex.getY();

		for(SetIterator<ScanLineGroup> sgsItr = scanLineGroupSet.begin() ; !sgsItr.equals(sgsItr_end) ; sgsItr.increment())
		{
			ScanLineGroup scanLineGroup = sgsItr.dereference();
			LineSegment lineSegment = scanLineGroup.getLineSegment();

			float lineSegmentYValue = lineSegment.getYgivenX(xValue);
			if (lineSegmentYValue < yValue)
			{
				first = scanLineGroup;
			}
			else if (lineSegmentYValue == yValue)
			{
				second = scanLineGroup;
			}
			else if (lineSegmentYValue > yValue)
			{
				third = scanLineGroup;
				break;
			}
		}

		return new ScanLineGroupTriple(first, second, third);
	}
}
