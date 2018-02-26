package com.geometricmethods.intersectiondetection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.geometricmethods.geometry.LineSegment;

// A ScanLineGroup IS A SET OF ScanLineNode'S WHOSE ACTIVE LineSegment'S ARE COINCIDENT.

// IMPORTANT PROPERTY(IES) OF THE ScanLineGroup:
// - FOR Intersections'S (WITH OTHER ScanLineGroups'S), A ScanLineGroup IS REPRESENTED BY A SINGLE LineSegment FROM ONE OF ITS
//   ScanLineNode'S, I.E. THE "ACTIVE" LineSegment OF ONE OF ITS ScanLineNode'S. THIS IS THE LineSegment WHOSE SECOND COORDINATE IS
//   "GREATER" THAN THAT OF ALL OTHER SUCH LineSegments.  "GREATER", IN THIS CASE, MEANS "HAS THE GREATEST X-COORDINATE" OR, IF THE
//   ScanLineGroup IS VERTICAL (AND THIS CAN ONLY BE IN THE POSITIVE Y DIRECTION), SO THAT ALL X-COORDINATES ARE EQUAL, "HAS THE
//   GREATEST Y-COORDINATE".
// - TO KEEP Intersections'S WITH OTHER ScanLineGroup'S CONSISTENT, THIS LineSegment MUST BE USED FOR ALL Intersection DETECTION UNTIL
//   THE SWEEP LINE PASSES IT, AT WHICH POINT ANOTHER LineSegment SHOULD BE CHOSEN TO REPRESENT THE ScanLineGroup USING THE SAME CRITERIA.

public class ScanLineGroup
{
	private static int uniqueId = 0;
	private static int getUniqueId()
	{
		return uniqueId++;
	}

	private static double maxValue = 100000.0;
	private static double minValue = -maxValue;

	private static double getMinValue()
	{
		return minValue;
	}

	private static double getMaxValue()
	{
		return maxValue;
	}

	public static double getMaxValue(ScanLineGroup scanLineGroup)
	{
		return scanLineGroup == null ? getMaxValue() : scanLineGroup.getSortValue();
	}

	public static double getMinValue(ScanLineGroup scanLineGroup)
	{
		return scanLineGroup == null ? getMinValue() : scanLineGroup.getSortValue();
	}

	private static List<Set<Label>> getLabelSetList(Map<LineSegment, ScanLineGroup> scanLineGroupMap)
	{
		List<Set<Label>> labelSetList = new ArrayList<>();

		for(ScanLineGroup scanLineGroup : scanLineGroupMap.values())
		{
			labelSetList.add(scanLineGroup.getLabelSet());
		}

		return labelSetList;
	}

	public static void addJoins(Map<LineSegment, ScanLineGroup> scanLineGroupMap, Label.JoinSplitTable joinSplitTable)
	{
		joinSplitTable.addJoins(getLabelSetList(scanLineGroupMap));
	}

	public static void addSplits(Map<LineSegment, ScanLineGroup> scanLineGroupMap, Label.JoinSplitTable joinSplitTable)
	{
		joinSplitTable.addSplits(getLabelSetList(scanLineGroupMap));
	}

	// FOR CHOOSING THE REPRESENTATIVE LineSegment OF THIS ScanLineGroup -- THE ACTIVE LineSegment OF A
	// CONSTITUENT ScanLineNode THAT HAS THE SECOND-POINT THAT IS >= THE SECOND-POINT OF ALL OTHER SUCH
	// LineSegments.
	private TreeSet<ScanLineNode> scanLineNodeSet = new TreeSet<>(ScanLineNode.LS2Comparator.get());

	private EventQueue eventQueue;

	private Double sortValue = null;

	// THE REPRESENTATIVE LineSegment FOR THIS ScanLineGroup
	private LineSegment lineSegment = new LineSegment();
	// THE REPRESENTATIVE LineSegment (lineSegment ABOVE) CAN BE CHOSEN FROM THE scanLineNodeLS2Set AS LONG AS THIS
	// DATA-MEMBER IS false. IF IT IS true, DON'T MESS WITH IT.
	private boolean lineSegmentAccessed = false;
	private int identity = getUniqueId();

	private ScanLineGroup(ScanLineGroup scanLineGroup)
	{
		this.scanLineNodeSet = new TreeSet<>(ScanLineNode.LS2Comparator.get());
		this.scanLineNodeSet.addAll(scanLineGroup.scanLineNodeSet);

		this.eventQueue = scanLineGroup.eventQueue;
		this.sortValue = scanLineGroup.sortValue;
		this.lineSegment = new LineSegment(scanLineGroup.lineSegment);
		this.lineSegmentAccessed = scanLineGroup.lineSegmentAccessed;
		this.identity = scanLineGroup.identity;
	}

	public ScanLineGroup(ScanLineNode scanLineNode, EventQueue eventQueue)
	{
		this.eventQueue = eventQueue;

		addScanLineNode(scanLineNode);
	}

	public ScanLineGroup duplicate()
	{
		return new ScanLineGroup(this);
	}

	public void setSortValue(double sortValue)
	{
		this.sortValue = sortValue;
	}

	public double getSortValue()
	{
		return sortValue;
	}

	public ScanLineGroup addScanLineNode(ScanLineNode scanLineNode)
	{
		scanLineNodeSet.add(scanLineNode);
		return this;
	}

	public void mergeScanLineGroup(ScanLineGroup scanLineGroup)
	{
		for(ScanLineNode scanLineNode : scanLineGroup.getScanLineNodeSet())
		{
			addScanLineNode(scanLineNode);
		}
	}

	public void clearScanLineNodes()
	{
		scanLineNodeSet.clear();
		lineSegmentAccessed = true;
	}

	public boolean isEmpty()
	{
		return scanLineNodeSet.isEmpty();
	}

	public LineSegment getLineSegment()
	{
		if (!lineSegmentAccessed)
		{
			lineSegment.set(scanLineNodeSet.iterator().next().getLineSegment());
			lineSegmentAccessed = true;
		}
		return lineSegment; 
	}
	
	public Set<Label> getLabelSet()
	{
		Set<Label> labelSet = new HashSet<>();
				
		for(ScanLineNode scanLineNode : scanLineNodeSet)
		{
			labelSet.add(scanLineNode.getLabel());
		}
		
		return labelSet;
	}
	
	
	public TreeSet<ScanLineNode> getScanLineNodeSet()
	{
		return scanLineNodeSet;
	}
	
	public int size()
	{
		return scanLineNodeSet.size();
	}
}
