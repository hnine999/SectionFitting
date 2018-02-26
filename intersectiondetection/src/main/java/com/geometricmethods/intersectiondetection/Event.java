package com.geometricmethods.intersectiondetection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.geometricmethods.geometry.Intersection;
import com.geometricmethods.geometry.LineSegment;
import com.geometricmethods.geometry.Vertex;
import com.geometricmethods.geometry.VertexCoordinateComparator;
import com.geometricmethods.stl.sequence.ListIterator;

public class Event {

	//
	// scanLineGroupSet CONTAINS THOSE ScanLineGroup'S THAT PARTICIPATE IN THE EVENT.
	// IN PARTICULAR, THESE ScanLineGroup'S ARE INCOMING INTO THE INTERSECTION.
	// A "SET" IS USED TO AVOID DUPLICATES
	//
	// THROUGH A GIVEN VERTEX, NO TWO ScanLineGroup'S CAN BE COINCIDENT, AND SO COME INTO THE EVENT VERTEX
	// AT DIFFERENT ANGLES.  ALSO, NO TWO ScanLineGroup'S HAVE THE SAME SORT VALUE, SO THERE SHOULD BE NO
	// COLLISIONS BETWEEN ScanLineGroup'S IN THE SET, AS THE SORT VALUE IS USED TO SORT THE SET.
	//
	// IN AN EVENT, AND IN PARTICULAR PROCESSING THE ScanLineGroup'S COMING INTO THE EVENT VERTEX, THE
	// scanLineGroupSet IS USED TO REGISTER JOINS AND DEGENERATE SPLITS.  THEREFORE, IT MUST CONTAIN ALL
	// ScanLineNode'S COMING INTO THE EVENT VERTEX IN THESE ScanLineGroup'S.
	//
	// THIS IS DIFFERENT THAN newScanLineGroupSet BELOW
	//
	private TreeMap<LineSegment, ScanLineGroup> scanLineGroupPreEventVertexMap = new TreeMap<>(LineSegmentClockwiseComparator.get());

	private TreeMap<LineSegment, ScanLineGroup> scanLineGroupPostEventVertexMap = new TreeMap<>(LineSegmentCounterClockwiseComparator.get());

	//
	// newScanLineGroupSet CONTAINS THE SAME ScanLineGroup'S AS scanLineGroupSet, EXCEPT WITHOUT ScanLineNode'S THAT
	// ARE ENDING, I.E. ARE PART OF A TERMINAL PAIR.  THAT IS, IT ONLY CONTAINS THOSES ScanLineNode'S THAT CAN BE
	// ADVANCED THROUGH THE EVENT VERTEX.
	//
	private TreeMap<LineSegment, ScanLineGroup> newScanLineGroupPreEventVertexMap = new TreeMap<>(LineSegmentClockwiseComparator.get());


	//
	// startScanLineNodePairList CONTAINS A LIST OF PAIRS OF ScanLineNode'S THAT TOGETHER FORM A LEFT-TO-RIGHT DOWN-TO-UP STARTING
	// POINT OF A POLYGON (A POLYGON CAN HAVE SEVERAL SUCH PAIRS).
	//
	List<ScanLineNode.StartPair> startScanLineNodePairList = new ArrayList<>();

	//
	// terminalScanLineNodePairList CONTAINS A LIST OF PAIRS OF ScanLineNode'S THAT TOGETHER FORM A LEFT-TO-RIGHT DOWN-TO-UP TERMINAL
	// POINT OF A POLYGON (A POLYGON CAN HAVE SEVERAL SUCH PAIRS)
	//
	List<ScanLineNode.TerminalPair> terminalScanLineNodePairList = new ArrayList<>();

	// VERTEX AT WHICH THE EVENT OCCURS
	private Vertex eventVertex;
	
	// REFERENCES TO ScanLine AND EventQueue
	private ScanLine scanLine;
	private EventQueue eventQueue;

	// RANGE OF SORT VALUES FOR SORTING ScanLineGroup'S FROM THIS EVENT INTO THE ScanLine
	private double minSortValue;
	private double maxSortValue;

	// ScanLineGroup'S IMMEDIATELY BEFORE AND AFTER THE ScanLineGroup'S OF THIS EVENT.
	// BOTH ARE NEEDED:
	//
	// previousScanLineGroup BECAUSE THERE MAY BE MORE THAN ONE EVENT ON THE ScanLine, AND EVENTS CLOSER TO Y = -INF
	// CAN'T DETECT INTERSECTION OF THEIR UPPERMOST ScanLineGroup WITH THE NEXT EVENT'S (IN POSITIVE Y DIRECTION)
	// LOWEST ScanLineGroup (A.K.A "nextScanLineGroup") BECAUSE IT HASN'T BEEN DETERMINED YET.  BECAUSE OF THIS,
	// THE LOWEST ScanLineGroup OF THE NEXT EVENT CAN ONLY CHECK FOR AN INTERSECTION WITH THE HIGHEST ScanLineGroup
	// OF THE PREVIOUS EVENT (A.K.A ITS previousScanLineGroup).
	//
	// nextScanLineGroup BECAUSE THERE MIGHT NOT BE ANOTHER HIGHER EVENT ON THE ScanLine, SO THE ONLY WAY TO SEE IF
	// THERE IS A ScanLineGroup ABOVE THE CURRENT EVENT IS TO SEE IF ONE IS THERE.  THEN, YOU CHECK FOR AN INTERSECTION
	// WITH IT.
	private ScanLineGroup previousScanLineGroup = null;
	private ScanLineGroup nextScanLineGroup = null;

	
	public Event(Vertex eventVertex, ScanLine scanLine, EventQueue eventQueue)
	{
		this.eventVertex = new Vertex(eventVertex);
		this.scanLine = scanLine;
		this.eventQueue = eventQueue;
	}


	public void addScanLineGroup(ScanLineGroup scanLineGroup)
	{
		// NO ScanLineGroup IN THE scanLineGroupPreEventVertexMap CAN HAVE AN EXTENSION EVENT
		deleteExtensionEvent(scanLineGroup);

		// IN ADDING A ScanLineGroup, ADDING ITS EXTENSION EVENT IS UNNECESSARY, AS THIS EXTENSION EVENT WILL BE REMOVED
		// WHEN THE EVENT IS PROCESSED.
		//
		// IF FACT, NO ScanLineGroup IN THE scanLineGroupPreEventVertexMap SHOULD HAVE AN EXTENSION EVENT.
		//
		// EXTERNSION EVENTS OF OUTGOING ScanLineGroup'S WILL BE ADDED WHEN THE EVENT IS PROCESSED.

		// SEE IF THIS ScanLineGroup NEEDS TO BE MERGED WITH AN ALREADY EXISTING ScanLineGroup THAT HAS THE SAME ORIENTATION

		LineSegment lineSegment = scanLineGroup.getLineSegment();
		ScanLineGroup currentScanLineGroup = scanLineGroupPreEventVertexMap.get(lineSegment);

		// IF NO MERGE IS NEEDED, JUST ADD IT.
		if (currentScanLineGroup == null)
		{
			scanLineGroupPreEventVertexMap.put(lineSegment, scanLineGroup);
		}
		// IF A MERGE IS NEEDED
		else
		{
			currentScanLineGroup.mergeScanLineGroup(scanLineGroup);
			scanLineGroupPreEventVertexMap.put(scanLineGroup.getLineSegment(), scanLineGroup);
		}
	}

	private void deleteExtensionEvent(ScanLineGroup scanLineGroup)
	{
		Vertex vertex = scanLineGroup.getLineSegment().getSecondVertex();
		if (!vertex.equals(eventVertex))
		{
			Event event = eventQueue.getEvent(vertex);
			event.removeScanLineGroup(scanLineGroup);
		}
	}

	private boolean isEmpty()
	{
		return startScanLineNodePairList.isEmpty() && scanLineGroupPreEventVertexMap.isEmpty();
	}

	private void removeScanLineGroup(ScanLineGroup scanLineGroup)
	{
		LineSegment lineSegment = scanLineGroup.getLineSegment();
		scanLineGroupPreEventVertexMap.remove(lineSegment);

		if (isEmpty())
		{
			eventQueue.removeEvent(this);
		}

		// NO NEED TO ERASE scanLineGroup FROM ITS EXTENSION EVENT -- THIS IS DONE IN addScanLineGroup WHEN THIS
		// ScanLineGroup IS ADDED TO scanLineGroupPreEventVertexMap
	}

//	public boolean hasScanLineGroup(ScanLineGroup scanLineGroup)
//	{
//		return !scanLineGroupSet.find(scanLineGroup).equals(sgsItr_end);
//	}

	// USES ITERATOR OVER POLYGON TO CONSTRUCT PAIR OF ScanLineNode'S THAT EMINATE FROM A LEFT-TO-RIGHT DOWN-TO-UP STARTING POINT
	// OF THE POLYGON, I.E. A START-PAIR
	public void addStartVertexIterator(ListIterator<Vertex> vertexIterator)
	{
		startScanLineNodePairList.add(ScanLineNode.getStartPair(vertexIterator));
	}

	// RENDER A COPY OF THE EVENT VERTEX TO TRY TO KEEP IT CONSTANT
	public Vertex getEventVertex()
	{
		return new Vertex(eventVertex);
	}

	// THE SET OF ScanLineGroup'S IMPINGING ON THE EVENT VERTEX
	public TreeMap<LineSegment, ScanLineGroup> getScanLineGroupPreEventVertexMap()
	{
		return scanLineGroupPreEventVertexMap;
	}

	// THE SET OF ScanLineGroup'S IMPINGING ON THE EVENT VERTEX
	public TreeMap<LineSegment, ScanLineGroup> getScanLineGroupPostEventVertexMap()
	{
		return scanLineGroupPostEventVertexMap;
	}

	// SWITCH OVER TO THE newScanLineGroupSet ONCE ALL JOINS IN THE ORIGINAL ScanLineGroupSet HAVE BEEN RECORDED
	private void updateScanLineGroupSet()
	{
		scanLineGroupPreEventVertexMap = newScanLineGroupPreEventVertexMap;
	}
	//
	// PROCESS THE EVENT
	//
	public Vertex process()
	{
//		// SET EVENT VERTEX OF ScanLine TO VERTEX OF THIS EVENT
//		scanLine.setEventVertex(eventVertex);

		//
		// SET BOUNDARY VALUES THIS EVENT:
		// * MAXIMUM AND MINIMUM SORT VALUES OF OUTGOING ScanLineGroup'S
		// * ScanLineGroup'S THAT COME IMMEDIATELY BEFORE AND AFTER THE ScanLineGroup'S IN THIS EVENT
		//
		setBoundaryValues();

		//
		// TAKE ALL ScanLineGroups OUT OF ScanLine THAT ARE PART OF THIS EVENT.
		// ScanLineGroup'S ARE TAKEN OUT OF ScanLine BECAUSE CONSTITUENT ScanLineNode'S MAY BE COMPLETELY RECONFIGURED,
		// CROSSING AND REGROUPING INTO DIFFERENT ScanLineGroup'S
		//
		// THE ScanLineGroup'S IN THIS Event'S scanLineGroupSet ARE IN CLOCKWISE ORDER, I.E. AS THEY ARE COMING IN TO THE
		// EVENT VERTEX FROM THE LEFT (LOWER X VALUE), THEY ARE ORDERED FROM LOWER TO HIGHER Y VALUE ACCORDING TO THEIR
		// PORTIONS THAT ARE TO THE LEFT OF THE EVENT VERTEX.
		//
		extractScanLineGroups();



		//
		// ScanLineGroup'S, COMING IN FROM THE LEFT (I.E. NEGATIVE X DIRECTION), CAN CREATE "DEGENERATE" SPLITS AND JOINS WITH THE
		// StartPair'S AND TerminalPair'S, RESPECTITVELY, OF THE EVENT.  RECORD THEM.
		//
		registerDegenerateIntersections();


		Label.JoinSplitTable joinSplitTable = new Label.JoinSplitTable(getEventLabelSet());

		//
		// ADD JOINS BETWEEN LABELS THAT RESULT FROM THE ScanLineGroup'S COMING IN TO THE EVENT VERTEX
		addPreceedingJoins(joinSplitTable);

		// ADVANCE ScanLineNode'S IN ScanLineGroup'S.  AS WELL, FORM terminalScanLineNodePairList
		advanceScanLineGroups();

		addSucceedingSplits(joinSplitTable);


		if (joinSplitTable.addJoinsAndSplits() || retireTerminalPairs())
		{
			return eventVertex;
		}

		// ADD ALL ScanLineGroup'S OUTGOING FROM INTERSECTION TO ScanLine
		for(ScanLineGroup scanLineGroup : scanLineGroupPostEventVertexMap.values())
		{
			scanLine.addScanLineGroup(scanLineGroup);
		}

		
		// REGISTER INTERSECTION EVENTS BETWEEN ScanLineGroup'S ABOVE AND BELOW SET OF ScanLineGroup'S JUST ADDED TO ScanLine
		return intersectionCheck();
	}

	//
	// GETS THE BOUNDARY VALUES FOR SETTING THE "sortValue"'S OF THE ScanLineGroup'S COMING OUT OF THIS EVENT
	//
	private void setBoundaryValues()
	{
		// NOTE: THIS IS NOT NEEDED FOR TerminalPair'S, AS THE INTERSECTION OF A "THROUGH" ScanLineNode AND THE TERMINAL VERTEX
		// OF A TERMINAL PAIR SHOULD BE DETECTED VIA INTERSECTION DETECTION.
		//
		// IF THIS EVENT ONLY HAS StartPair'S OF ScanLineNode'S, A ScanLineGroup (ONLY ONE) MIGHT IMPINGE ON THE EVENT VERTEX
		// FROM THE LEFT.  CHECK TO SEE IF THIS IS THE CASE.
		// THE REASON THERE CAN ONLY BE ONE SUCH ScanLineGroup IS, IF THERE WERE MORE THAN ONE, THEY WOULD HAVE
		// AN INTERSECTION EVENT AT THIS EVENT VERTEX, AND SO THERE WOULD BE ScanLineGroup'S ALREADY IN THIS EVENT
		//
		// IT IS ALSO THE CASE THAT, IF THIS EVENT HAS ONE OR MORE TerminalPair'S, ANY ScanLineGroup'S THAT IMPINGE
		// THIS EVENT VERTEX WOULD ALREADY BE IN THE EVENT, AS THEIR INTERSECTION WITH THE RIGHT-MOST VERTEX OF THE
		// TerminalPair('S) WOULD HAVE BEEN DETECTED.
		if (scanLineGroupPreEventVertexMap.isEmpty())
		{
			ScanLine.ScanLineGroupTriple scanLineGroupTriple = scanLine.getScanLineGroupTriple(eventVertex);
			previousScanLineGroup = scanLineGroupTriple.getFirst();
			nextScanLineGroup = scanLineGroupTriple.getThird();

			if (scanLineGroupTriple.getSecond() != null)
			{
				addScanLineGroup(scanLineGroupTriple.getSecond()); // EXTENSION EVENT OF THIS ScanLineGroup REMOVED IN "addScanLineGroup"
			}
		}
		else
		{
			previousScanLineGroup = scanLine.getPreviousScanLineGroup(scanLineGroupPreEventVertexMap.firstEntry().getValue());
			nextScanLineGroup = scanLine.getNextScanLineGroup(scanLineGroupPreEventVertexMap.lastEntry().getValue());
		}

		maxSortValue = ScanLineGroup.getMaxValue(nextScanLineGroup);
		minSortValue = ScanLineGroup.getMinValue(previousScanLineGroup);
	}

	//
	// TAKE ScanLineGroup'S OF THIS EVENT (THAT ARE TO THE LEFT OF THE EVENT VERTEX) OUT OF THE ScanLine SINCE
	// THE ScanLineNode'S THAT MAKE THEM UP WILL REGROUP TO THE RIGHT OF THE EVENT VERTEX, FORMING NEW ScanLineGroup'S
	// (WHICH WILL BE INSERTED INTO THE ScanLine).
	//
	// TAKE ScanLineGroup OUT OF EVENT PERTAINING TO ITS SECOND VERTEX (I.E. AN EXTENSION EVENT), UNLESS THIS SECOND VERTEX
	// EQUALS THE EVENT VERTEX.
	// THIS IS BECAUSE THE ScanLineGroup WILL GENERALLY NO LONGER EXIST AFTER PROCESSING THIS EVENT -- I.E. AS STATED ABOVE
	// THE ScanLineNode'S THAT MAKE THEM UP WILL REARRANGE THEMSELVES INTO OTHER ScanLineGroup'S, WHICH WILL BE GIVEN
	// EXTENSION EVENTS IN THEIR OWN RIGHT.
	//
	private void extractScanLineGroups()
	{
		for(ScanLineGroup scanLineGroup : scanLineGroupPreEventVertexMap.values())
		{
			scanLine.deleteScanLineGroup(scanLineGroup);

			// REMOVAL OF EXTENSION EVENTS OF ScanLineGroup'S IN scanLineGroupPreEventVertexMap IS UNNECESSARY -- IT IS
			// DONE WHEN THEY ARE ADDED TO THE scanLineGroupPreEventVertexMap.
		}
	}

	//
	// THIS METHOD REGISTERS "DEGENERATE" INTERSECTIONS BETWEEN START AND TERMINAL PAIRS, AND ANY "THRU" ScanLineNodes.  THAT IS,
	// IF A ScanLineNode PROCEEDS "THRU" THE EVENT Vertex, THEN IT FORMS A DEGENERATE INTERSECTION WITH ANY START OR TERMINAL PAIRS
	// THAT IMPINGE ON THE EVENT Vertex.
	//
	private void registerDegenerateIntersections()
	{
		// LIST OF ScanLineNode'S THAT PROCEED THROUGH THE EVENT Vertex
		List<ScanLineNode> degenerateScanLineNodeList = formAndFilterTerminalPairs();
		
		// REGISTER THE DEGENERATE INTERSECTIONS (IN THE ScanLineNode'S Label'S).
		for(ScanLineNode scanLineNode : degenerateScanLineNodeList)
		{
			for(ScanLineNode.TerminalPair terminalPair : terminalScanLineNodePairList)
			{
				terminalPair.getFirst().getLabel().addDegenerateSplit(scanLineNode.getLabel());
			}

			for(ScanLineNode.StartPair startPair : startScanLineNodePairList)
			{
				startPair.getFirst().getLabel().addDegenerateJoin(scanLineNode.getLabel());
			}
		}

	}

	//
	// THIS METHOD DETECTS AND FORMS TERMINAL PAIRS, I.E. PAIRS OF ScanLineNode'S THAT END ON A PARTICULAR VERTEX.  IT RETURNS THE LIST
	// THOSE ScanLineNode'S THAT ARE NOT PART OF A TERMINAL PAIR
	//
	// IT ALSO FORMS THE newScanLineGroup DATA MEMBER OF THIS EVENT -- THE DATA MEMBER THAT CONTAINS THE ORIGINAL ScanLineGroup'S
	// MINUS ANY ScanLineNode'S THAT TERMINATED.
	//
	private List<ScanLineNode> formAndFilterTerminalPairs()
	{
		// THIS MAP KEEPS TRACK OF ScanLineNode'S THAT TERMINATE ON THE EVENT Vertex.  IT DOES THIS BY Vertex-ID SO THAT
		// ScanLineNode'S THAT END ON THE *EXACT* SAME Vertex (THE Vertex'S ID MUST BE USED TO DETERMINE THIS) ARE PAIRED INTO
		// A TERMINAL PAIR
		Map<Vertex, ScanLineNode> vertexScanLineNodeMap = new TreeMap<>(VertexIdComparator.get());

		// LIST OF ScanLineNode'S THAT PROCEED "THROUGH" THE EVENT Vertex, I.E. ARE NOT PART OF A TERMINAL PAIR (OR START PAIR, FOR THAT MATTER)
		List<ScanLineNode> degenerateScanLineNodeList = new ArrayList<>();

		// PROCESS ScanLineGroup'S THAT ARE INCOMING TO THE EVENT Vertex
		for(ScanLineGroup scanLineGroup : scanLineGroupPreEventVertexMap.values())
		{
			TreeSet<ScanLineNode> scanLineNodeSet = scanLineGroup.getScanLineNodeSet();

			List<ScanLineNode> localDegenerateScanLineNodeList = new ArrayList<>();

			// PROCESS EACH ScanLineNode IN EACH ScanLineGroup
			for(ScanLineNode scanLineNode : scanLineNodeSet)
			{
				// ANY ScanLineNode THAT IS AT IT'S END (I.E. LAST Vertex) IS PART OF A TERMINAL PAIR (I.E. THERE IS ANOTHER
				// ScanLineNode THAT ENDS ON THE EXACT SAME Vertex).  THIS IS DUE TO THE FACT THAT WE ARE PROCESSING Polygon'S.
				if (scanLineNode.isAtEnd(eventVertex))
				{
					Vertex vertex = scanLineNode.getLineSegment().getSecondVertex();
					if (vertexScanLineNodeMap.containsKey(vertex))
					{
						terminalScanLineNodePairList.add(new ScanLineNode.TerminalPair(scanLineNode, vertexScanLineNodeMap.remove(vertex)));
					}
					else
					{
						vertexScanLineNodeMap.put(vertex, scanLineNode);
					}
				}
				// ANY ScanLineNode THAT IS NOT AT IT'S END FORMS A DEGENERATE INTERSECTION WITH ALL ScanLineNode'S THAT ARE IN
				// START OR TERMINAL PAIRS.
				else
				{
					localDegenerateScanLineNodeList.add(scanLineNode);
					degenerateScanLineNodeList.add(scanLineNode);
				}
			}

			// FORM newScanLineGroupSet -- SAME AS scanLineGroupSet EXCEPT ONLY HAS ScanLineNode'S THAT HAVE NOT
			// TERMINATED.  THESE ScanLineNode'S CAN BE ADVANCED TO THE RIGHT OF THE EVENT VERTEX.
			if (!localDegenerateScanLineNodeList.isEmpty())
			{
				ScanLineGroup newScanLineGroup = scanLineGroup.duplicate();
				newScanLineGroup.clearScanLineNodes();
				for(ScanLineNode scanLineNode : localDegenerateScanLineNodeList)
				{
					newScanLineGroup.addScanLineNode(scanLineNode);
				}
				newScanLineGroupPreEventVertexMap.put(newScanLineGroup.getLineSegment(), newScanLineGroup);
			}

		}

		return degenerateScanLineNodeList;
	}

	private Set<Label> getEventLabelSet()
	{
		Set<Label> labelSet = new HashSet<>();

		for(ScanLineNode.TerminalPair terminalPair : terminalScanLineNodePairList)
		{
			labelSet.add(terminalPair.scanLineNode1.getLabel());
			labelSet.add(terminalPair.scanLineNode2.getLabel());
		}

		for(ScanLineGroup scanLineGroup : scanLineGroupPreEventVertexMap.values())
		{
			labelSet.addAll(scanLineGroup.getLabelSet());
		}

		for(ScanLineNode.StartPair startPair : startScanLineNodePairList)
		{
			labelSet.add(startPair.scanLineNode1.getLabel());
			labelSet.add(startPair.scanLineNode2.getLabel());
		}

		return labelSet;
	}

	//
	// THIS METHOD PROCESSES TERMINAL ScanLineNode PAIRS, TO TRANSFER JOINS IN WHICH THEY ARE INVOLVED TO
	// ScanLineNode'S THAT ARE ADJACENT TO THE ScanLineNode'S IN THE PAIR
	//
	private void addPreceedingJoins(Label.JoinSplitTable joinSplitTable)
	{
		// ADD JOINS (CLOCKWISE) BETWEEN ALL OF THE ScanLineNode'S
		ScanLineGroup.addJoins(scanLineGroupPreEventVertexMap, joinSplitTable);

		joinSplitTable.processDegenerateSplits();
	}

	//
	// THIS METHOD ADVANCES ALL ScanLineGroup'S PAST THE EVENT VERTEX
	//
	private void advanceScanLineGroups()
	{
		// THIS MAP IS FOR PARTITIONING ADVANCED ScanLineNode'S INTO ScanLineGroup'S BASED ON THE ANGLE OF THEIR ACTIVE LineSegment
		// (THE LineSegment THAT IMPINGES ON THE EVENT Vertex)
		// LINE SEGMENTS ARE SORTED BY ANGLE, BASED ON COUNTERCLOCKWISEDNESS.  I.E. BETWEEN TWO LINE SEGMENTS, THAT WHICH IS MOST
		// CLOCKWISE COMES FIRST.

		// ADVANCE ALL ScanLineGroup'S FOR THIS EVENT, I.E. IN scanLineGroupSet, AND ADD THEM TO THE linSegmentScanLineGroupMap

		updateScanLineGroupSet();

		for(ScanLineGroup scanLineGroup : scanLineGroupPreEventVertexMap.values())
		{
			TreeSet<ScanLineNode> scanLineNodeSet = scanLineGroup.getScanLineNodeSet();
			for (ScanLineNode scanLineNode : scanLineNodeSet)
			{
				scanLineNode.advance(eventVertex);

				addScanLineNode(scanLineGroupPostEventVertexMap, scanLineNode);
			}
		}

		// ADD ScanLineNode'S FROM StartPair'S
		for(ScanLineNode.StartPair startPair : startScanLineNodePairList)
		{
			addScanLineNode(scanLineGroupPostEventVertexMap, startPair.getFirst());
			addScanLineNode(scanLineGroupPostEventVertexMap, startPair.getSecond());
		}

		// ASSIGN SORT VALUES TO ALL OF THE ScanLineGroup'S
		double increment = (maxSortValue - minSortValue) / (scanLineGroupPostEventVertexMap.size() + 1);
		int localIndex = 1;
		for(ScanLineGroup scanLineGroup : scanLineGroupPostEventVertexMap.values())
		{
			scanLineGroup.setSortValue(minSortValue + localIndex * increment);
			++localIndex;

			Vertex secondVertex = scanLineGroup.getLineSegment().getSecondVertex();
			eventQueue.getEvent(secondVertex).addScanLineGroup(scanLineGroup);
		}
	}

	//
	// ADD ScanLineNode TO ScanLineGroup WITH SAME LineSegment ORIENTATION
	//
	private void addScanLineNode(TreeMap<LineSegment, ScanLineGroup> lineSegmentScanLineGroupMap, ScanLineNode scanLineNode)
	{
		lineSegmentScanLineGroupMap.compute(scanLineNode.getLineSegment(), (k,v) -> v == null ? new ScanLineGroup(scanLineNode, eventQueue) : v.addScanLineNode(scanLineNode));
	}

	//
	// ADD SPLITS TO LABELS OF OUTGOING ScanLineNode'S FROM THE EVENT VERTEX
	//
	private void addSucceedingSplits(Label.JoinSplitTable joinSplitTable)
	{
		// ADD SPLITS BETWEEN LABELS OF PAIRS OF ScanLineNode'S
		ScanLineGroup.addSplits(scanLineGroupPostEventVertexMap, joinSplitTable);

		joinSplitTable.processDegenerateJoins();
	}

	private boolean retireTerminalPairs()
	{
		for (ScanLineNode.TerminalPair terminalPair : terminalScanLineNodePairList)
		{
			if (Label.retireTerminalPair(terminalPair.getLabelPair()))
			{
				return true;
			}
		}
		return false;
	}

	//
	// CHECK FOR AN INTERSECTION BETWEEN THE OUTGOING ScanLineGroup'S OF THIS EVENT AND THE ScanLineGroup'S IMMEDIATELY ABOVE AND BELOW THE ScanLineGroup'S OF THIS EVENT.
	//
	private Vertex intersectionCheck()
	{
		// IF THIS EVENT HAS NO ScanLineGroup'S JUST CHECK FOR INTERSECTION BETWEEN ScanLineGroup'S IMMEDIATELY ABOVE AND BELOW THIS EVENT ON THE ScanLine.
		if (scanLineGroupPostEventVertexMap.isEmpty())
		{
			return intersectionCheck(previousScanLineGroup, nextScanLineGroup);
		}


		if (previousScanLineGroup != null)
		{
			ScanLineGroup minScanLineGroup = scanLineGroupPostEventVertexMap.firstEntry().getValue();
			Vertex intersectionVertex = intersectionCheck(previousScanLineGroup, minScanLineGroup);
			if (intersectionVertex != null)
			{
				return intersectionVertex;
			}
		}

		// GET LAST ScanLineGroup IN scanLineGroupSet TO CHECK IF IT INTERSECTS WITH nextScanLineGroup
		ScanLineGroup maxScanLineGroup = scanLineGroupPostEventVertexMap.lastEntry().getValue();

		// IF VERTEX OF NEXT EVENT IS ON ScanLine WITH ITS CURRENT X COORDINATE:
		// A) SEE IF THE MAXIMUM ScanLineGroup OF THIS EVENT IS VERTICAL (UPWARD).
		//    IF IT IS, SEE IF IT INSERSECTS THE VERTEX OF THE NEXT EVENT, AND IF SO, ADD IT TO THAT EVENT
		// B) ANY INTERSECTION WITH THE NEXT HIGHEST ScanLineGroup IN THE ScanLine IS INDETERMINATE
		//    BECAUSE THE ScanLineGroup'S COMING OUT FROM THE NEXT EVENT HAVEN'T BEEN DETERMINED YET.
		//    SKIP ANY DETERMINATION OF ANY INTERSECTION UPWARD.
		Event nextEvent = eventQueue.peekNextEvent();
		if (nextEvent != null)
		{
			Vertex nextEventVertex = nextEvent.getEventVertex();
			if (nextEventVertex.getX() == eventVertex.getX())
			{
				// IS maxScanLineGroup VERTICAL UPWARDS?  NOTE: CAN'T BE VERTICAL DOWNWARDS BECAUSE ScanLineGroup'S CAN ONLY BE RIGHTWARD OR UPWARD
				if (maxScanLineGroup.getLineSegment().getDiffVertex().getX() == 0)
				{
					// IF Y COORDINATE OF SECOND VERTEX OF maxScanLineGroup IS GREATER THAN Y COORDINATE OF NEXT Event,
					// THEN THE SECOND VERTEX INTERSECTS, AND IS THEREFORE PART OF, THE Event
					// NOTE: IF SECOND VERTEX IS LESS THAN (OR EQUAL TO) Y COORDINATE OF NEXT Event, THEN EVENT VERTEX OF NEXT EVENT IS
					// EQUAL TO THE SECOND VERTEX!
					Vertex secondVertex = maxScanLineGroup.getLineSegment().getSecondVertex();
					if (nextEventVertex.getY() <= secondVertex.getY())
					{
						nextEvent.addScanLineGroup(maxScanLineGroup);
					}
				}
				// NO CHECK IF INTERSECTION WITH maxScanLineGroup -- nextScanLineGroup IS NOT DETERMINED YET.
				return null;
			}
		}


		return intersectionCheck(maxScanLineGroup, nextScanLineGroup);
	}

	private Vertex intersectionCheck(ScanLineGroup scanLineGroup1, ScanLineGroup scanLineGroup2)
	{
		if (scanLineGroup1 == null || scanLineGroup2 == null)
		{
			return null;
		}
		LineSegment lineSegment1 = scanLineGroup1.getLineSegment();
		LineSegment lineSegment2 = scanLineGroup2.getLineSegment();
		Intersection intersection = lineSegment2.getIntersection(lineSegment1);
		if (!intersection.isValid() || VertexCoordinateComparator.get().compare(intersection, eventVertex) <= 0)
		{
			return null;
		}
		if (intersection.getEndpointOverlap().isEmpty())
		{
			return intersection;
		}
		eventQueue.addIntersectionEvent(intersection, scanLineGroup1, scanLineGroup2);
		return null;

	}
	
}
