package com.geometricmethods.intersectiondetection;

import java.util.Comparator;

import com.geometricmethods.geometry.LineSegment;
import com.geometricmethods.geometry.Vertex;
import com.geometricmethods.geometry.VertexCoordinateComparator;
import com.geometricmethods.stl.iterator.BidirectionalIterator;
import com.geometricmethods.stl.sequence.ListIterator;
import com.geometricmethods.stl.sequence.ReverseListIterator;
import org.apache.commons.lang3.tuple.Pair;

// IMPORTANT:
// EACH ScanLineNode *MUST* HAVE A UNIQUE LABEL THAT CAN BE PAIRED WITH THE LABEL OF THE ScanLineNode THAT
// STARTS AT THE SAME POINT.  THIS IS BECAUSE SPLITS AND JOINS, WHEN DETECTED, MUST BE PAIRED WITH AN ADJACENT
// JOIN/SPLIT IN ORDER TO PROPERLY DETECT AN EXTENDED INTERSECTION.
// IF ALL ScanLineNode'S FROM A PARTICULAR POLYGON ALL HAVE THE SAME LABEL, IT IS POSSIBLE THAT THE SWEEP LINE
// WILL DETECT NON-ADJACENT CLOCKWISE-CLOCKWISE OR COUNTER-CLOCKWISE--COUNTER-CLOCKWISE PAIRS, AND IN DOING SO
// INCORRECTLY DETECT AN ERRONEOUS "INTERSECTION"
//

public class ScanLineNode
{
//	public static int orientationToleranceReciprocal = 10000000;


	public static class LS2Comparator implements Comparator<ScanLineNode>
	{
		private static VertexCoordinateComparator vertexComparator = VertexCoordinateComparator.get();

		private static LS2Comparator scanLineNodeComparator = new LS2Comparator();

		public static LS2Comparator get()
		{
			return scanLineNodeComparator;
		}

		// WANT LOWEST SECOND VERTEX TO DELAY INTERSECTION DETECTION AS LONG AS POSSIBLE
		@Override
		public int compare(ScanLineNode sln1, ScanLineNode sln2)
		{
			int comp = vertexComparator.compare(sln1.getLineSegment().getSecondVertex(), sln2.getLineSegment().getSecondVertex());
			if (comp != 0)
			{
				return comp;
			}
			return sln1.getId() - sln2.getId();
		}
	}

	public static abstract class ScanLineNodePair
	{
		protected ScanLineNode scanLineNode1;
		protected ScanLineNode scanLineNode2;


		protected ScanLineNodePair() {}

		public ScanLineNodePair(ScanLineNode scanLineNode1, ScanLineNode scanLineNode2)
		{
			this.scanLineNode1 = scanLineNode1;
			this.scanLineNode2 = scanLineNode2;
		}

		public ScanLineNode getFirst()
		{
			return scanLineNode1;
		}

		public ScanLineNode getSecond()
		{
			return scanLineNode2;
		}

		public Pair<Label, Label> getLabelPair()
		{
			return Pair.of(getFirst().getLabel(), getSecond().getLabel());
		}
	}

	public static class StartPair extends ScanLineNodePair
	{
//		public StartPair(ScanLineNode scanLineNode1, ScanLineNode scanLineNode2)
//		{
//			super(scanLineNode1, scanLineNode2);
//		}

		private StartPair(ListIterator<Vertex> vertexIterator)
		{
			Label.StartPair labelPair = Label.getLabelStartPair();
			scanLineNode1 = new ScanLineNode(new ListIterator<>(vertexIterator), labelPair.getFirstLabel());
			scanLineNode2 = new ScanLineNode(new ReverseListIterator<>(vertexIterator).decrement(), labelPair.getSecondLabel());
		}
	}

	public static class TerminalPair extends ScanLineNodePair
	{
		public TerminalPair(ScanLineNode scanLineNode1, ScanLineNode scanLineNode2)
		{
			super(scanLineNode1, scanLineNode2);
			Label.terminatesWith(scanLineNode1.getLabel(), scanLineNode2.getLabel());
		}
	}

	private static int uniqueId = 0;
	private static int getUniqueId()
	{
		return uniqueId++;
	}

	public static StartPair getStartPair(ListIterator<Vertex> vertexIterator)
	{
		return new StartPair(vertexIterator);
	}


	// THIS BidirectionalIterator ITERATES THROUGHT THE Vertex'S THAT ARE ALONG THE LENGTH OF THE ScanLineNode.
	private BidirectionalIterator<Vertex> iterator;

	// THIS Vertex IS TWO BEHIND THE iterator AND IS THE FIRST Vertex OF THE ACTIVE LineSegment OF THE ScanLineNode
	private Vertex vertex1;

	// THIS Vertex IS ONE BEHIND THE iterator AND IS THE SECOND Vertex OF THE ACTIVE LineSegment OF THE ScanLineNode
	private Vertex vertex2;
	
	// THIS Vertex IS AT THE iterator AND IS USED AS A LOOK-AHEAD Vertex TO SEE IF THE iterator HAS GONE PAST THE END
	// OF THE ScanLineNode
	private Vertex vertex3;
	
	// THE ACTIVE LineSegment OF THIS ScanLineNode.  IT'S FIRST AND SECOND Vertex'S ARE vertex1 AND vertex2 ABOVE,
	// RESPECTIVELY.
	private LineSegment lineSegment = new LineSegment();
//	private int orientationValue;
	
	private final int identity;
	
	// UNIQUE Label THAT IDENTIFIES THIS ScanLineNode AND IS USED TO DETERMINE IF IT HAS ANY TANGLES WITH OTHER
	// ScanLineNode'S
	private Label label;

	public int getId()
	{
		return identity;
	}
	
	public Label getLabel()
	{
		return label;
	}

	private ScanLineNode(BidirectionalIterator<Vertex> iterator, Label label)
	{
		this.iterator = iterator;
		this.label = label;
		this.identity = getUniqueId();

		vertex1 = iterator.dereference();
		vertex2 = iterator.increment().dereference();
		vertex3 = iterator.increment().dereference();

		lineSegment.set(vertex1, vertex2);
//		setOrientationValue();
	}
	
	public LineSegment getLineSegment()
	{
		return lineSegment;
	}

	public boolean isAtEnd(Vertex eventVertex)
	{
		return vertex2.equals(eventVertex) && VertexCoordinateComparator.get().compare(vertex2, vertex3) > 0;
	}
	
	public void advance(Vertex eventVertex)
	{
		if (vertex2.equals(eventVertex))
		{
			vertex1 = vertex2;
			vertex2 = vertex3;
			vertex3 = iterator.increment().dereference();
		
			lineSegment.set(vertex1, vertex2);
		}
	}
}
