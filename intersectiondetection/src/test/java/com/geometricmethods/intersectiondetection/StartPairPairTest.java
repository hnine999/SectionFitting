package com.geometricmethods.intersectiondetection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.geometricmethods.geometry.LineSegment;
import com.geometricmethods.geometry.Vertex;

import org.junit.Assert;
import org.junit.Test;

/**
 * Description of file content.
 *
 * @author harmon.nine
 * 2/5/18
 */
public class StartPairPairTest
{
	@Test
	public void UnaryStartPairPair0()
	{
		Vertex vertex0 = new Vertex( 0, 2);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex( 0,-2);
		Vertex vertex3 = new Vertex( 0,-1);
		Vertex vertex4 = new Vertex(-1, 0);
		Vertex vertex5 = new Vertex( 0, 1);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);

		Section section1 = new Section();
		section1.add(polygon1);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;


		// vertex3(0,-2), vertex1(0, 1)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Iterator<ScanLineNode.StartPair> iterator = event.startScanLineNodePairList.iterator();
			{
				ScanLineNode.StartPair startPair = iterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			{
				ScanLineNode.StartPair startPair = iterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			Assert.assertNull(event.process());

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(4, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

		}

		// vertex2(0, -1), vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(4, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		// vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex3, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}

		// vertex1(0, 2), vertex4(1, 0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex5, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		// vertex4(1, 0)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}


		Assert.assertEquals(0, eventQueue.size());
	}

	@Test
	public void unaryStartPairPair1()
	{
		Vertex vertex0 = new Vertex( 0, 2);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex( 0,-1);
		Vertex vertex3 = new Vertex( 0,-2);
		Vertex vertex4 = new Vertex(-1, 0);
		Vertex vertex5 = new Vertex( 0, 1);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);

		Section section1 = new Section();
		section1.add(polygon1);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;


		// vertex3(0,-2), vertex1(0, 1)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Iterator<ScanLineNode.StartPair> iterator = event.startScanLineNodePairList.iterator();
			{
				ScanLineNode.StartPair startPair = iterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			{
				ScanLineNode.StartPair startPair = iterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			Assert.assertEquals(vertex1, event.process());
		}

	}

	@Test
	public void unaryStartPairPair2()
	{
		Vertex vertex0 = new Vertex( 0, 1);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex( 0,-2);
		Vertex vertex3 = new Vertex( 0,-1);
		Vertex vertex4 = new Vertex(-1, 0);
		Vertex vertex5 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);

		Section section1 = new Section();
		section1.add(polygon1);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;


		// vertex3(0,-2), vertex1(0, 1)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Iterator<ScanLineNode.StartPair> iterator = event.startScanLineNodePairList.iterator();
			{
				ScanLineNode.StartPair startPair = iterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			{
				ScanLineNode.StartPair startPair = iterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			Assert.assertEquals(vertex1, event.process());
		}
	}

	@Test
	public void singleBinaryStartPairPair0()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-2, 0);
		Vertex vertex2 = new Vertex(-1,-1);
		Vertex vertex3 = new Vertex( 0,-4);
		Vertex vertex4 = new Vertex( 0,-2);
		Vertex vertex5 = new Vertex(-1,-1);
		Vertex vertex6 = new Vertex(-2, 0);
		Vertex vertex7 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);

		Section section1 = new Section();
		section1.add(polygon1);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;


		// vertex3(0,-2), vertex1(0, 1)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Iterator<ScanLineNode.StartPair> iterator = event.startScanLineNodePairList.iterator();
			{
				ScanLineNode.StartPair startPair = iterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			{
				ScanLineNode.StartPair startPair = iterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			Assert.assertNull(event.process());
		}

		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

			{
				ScanLineGroup scanLineGroup = event.getScanLineGroupPreEventVertexMap().firstEntry().getValue();

				Assert.assertEquals(2, scanLineGroup.size());

				Iterator<ScanLineNode> iterator = scanLineGroup.getScanLineNodeSet().iterator();

				Label label1 = iterator.next().getLabel();
				Label label2 = iterator.next().getLabel();

				Assert.assertEquals(1, label1.getJoins().size());
				Assert.assertEquals(1, label2.getJoins().size());

				if (label1.getJoin(label2) != Label.Wisedness.COUNTERCLOCKWISE)
				{
					Label temp = label1;
					label1 = label2;
					label2 = temp;
				}

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));
			}

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		Assert.assertEquals(4, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex3, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		// vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex4, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}

		// vertex1(0, 2), vertex4(1, 0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex7, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		// vertex4(1, 0)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}


		Assert.assertEquals(0, eventQueue.size());

	}

	@Test
	public void singleBinaryStartPairPair1()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-2, 0);
		Vertex vertex2 = new Vertex(-1,-1);
		Vertex vertex3 = new Vertex( 0,-2);
		Vertex vertex4 = new Vertex( 0,-4);
		Vertex vertex5 = new Vertex(-1,-1);
		Vertex vertex6 = new Vertex(-2, 0);
		Vertex vertex7 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);

		Section section1 = new Section();
		section1.add(polygon1);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;


		// vertex3(0,-2), vertex1(0, 1)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Iterator<ScanLineNode.StartPair> iterator = event.startScanLineNodePairList.iterator();
			{
				ScanLineNode.StartPair startPair = iterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			{
				ScanLineNode.StartPair startPair = iterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			Assert.assertNull(event.process());
		}

		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

			{
				ScanLineGroup scanLineGroup = event.getScanLineGroupPreEventVertexMap().firstEntry().getValue();

				Assert.assertEquals(2, scanLineGroup.size());

				Iterator<ScanLineNode> iterator = scanLineGroup.getScanLineNodeSet().iterator();

				Label label1 = iterator.next().getLabel();
				Label label2 = iterator.next().getLabel();

				Assert.assertEquals(1, label1.getJoins().size());
				Assert.assertEquals(1, label2.getJoins().size());

				if (label1.getJoin(label2) != Label.Wisedness.COUNTERCLOCKWISE)
				{
					Label temp = label1;
					label1 = label2;
					label2 = temp;
				}

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));
			}

			Assert.assertEquals(vertex2, event.process());
		}
	}

	@Test
	public void singleBinaryStartPairPair2()
	{
		Vertex vertex0 = new Vertex( 0, 2);
		Vertex vertex1 = new Vertex(-2, 0);
		Vertex vertex2 = new Vertex(-1,-1);
		Vertex vertex3 = new Vertex( 0,-4);
		Vertex vertex4 = new Vertex( 0,-2);
		Vertex vertex5 = new Vertex(-1,-1);
		Vertex vertex6 = new Vertex(-2, 0);
		Vertex vertex7 = new Vertex( 0, 4);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);

		Section section1 = new Section();
		section1.add(polygon1);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;

		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Iterator<ScanLineNode.StartPair> startPairIterator = event.startScanLineNodePairList.iterator();
			{
				ScanLineNode.StartPair startPair = startPairIterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			{
				ScanLineNode.StartPair startPair = startPairIterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			Assert.assertNull(event.process());

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(3, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

			List<ScanLineGroup> scanLineGroupList = new ArrayList<>();
			ScanLineGroup dualScanLineGroup = null;
			for(ScanLineGroup scanLineGroup : event.getScanLineGroupPostEventVertexMap().values())
			{
				if (scanLineGroup.size() == 1)
				{
					scanLineGroupList.add(scanLineGroup);
				}
				else if (scanLineGroup.size() == 2)
				{
					dualScanLineGroup = scanLineGroup;
				}
			}

			Assert.assertEquals(2, scanLineGroupList.size());
			Assert.assertNotNull(dualScanLineGroup);
			Assert.assertEquals(2, dualScanLineGroup.size());

			Iterator<ScanLineNode> scanLineNodeIterator = dualScanLineGroup.getScanLineNodeSet().iterator();

			Label label1 = scanLineNodeIterator.next().getLabel();
			Label label2 = scanLineNodeIterator.next().getLabel();

			Assert.assertEquals(1, label1.getJoins().size());
			Assert.assertEquals(1, label2.getJoins().size());

			if (!Label.Wisedness.COUNTERCLOCKWISE.equals(label1.getJoin(label2)))
			{
				Label temp = label1;
				label1 = label2;
				label2 = temp;
			}

			Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
			Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));

			for(ScanLineGroup scanLineGroup : scanLineGroupList)
			{
				Assert.assertEquals(1, scanLineGroup.size());
				Assert.assertEquals(0, scanLineGroup.getScanLineNodeSet().first().getLabel().getJoins().size());
			}
		}

		// vertex2(0, -1), vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

			ScanLineGroup scanLineGroup = event.getScanLineGroupPreEventVertexMap().firstEntry().getValue();

			Assert.assertEquals(2, scanLineGroup.size());

			Iterator<ScanLineNode> iterator = scanLineGroup.getScanLineNodeSet().iterator();
			Label label1 = iterator.next().getLabel();
			Label label2 = iterator.next().getLabel();

			Assert.assertEquals(1, label1.getJoins().size());
			Assert.assertEquals(1, label2.getJoins().size());

			if (!Label.Wisedness.COUNTERCLOCKWISE.equals(label1.getJoin(label2)))
			{
				Label temp = label1;
				label1 = label2;
				label2 = temp;
			}

			Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
			Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));

			Assert.assertEquals(vertex2, event.process());
		}

	}

	@Test
	public void singleBinaryStartPairPair3()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-1, 1);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex( 0,-4);
		Vertex vertex4 = new Vertex( 0,-2);
		Vertex vertex5 = new Vertex(-2, 0);
		Vertex vertex6 = new Vertex(-1, 1);
		Vertex vertex7 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertFalse(section1.hasIntersection());
	}

	@Test
	public void singleBinaryStartPairPair4()
	{
		Vertex vertex0 = new Vertex( 0, 2);
		Vertex vertex1 = new Vertex(-1, 1);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex( 0,-4);
		Vertex vertex4 = new Vertex( 0,-2);
		Vertex vertex5 = new Vertex(-2, 0);
		Vertex vertex6 = new Vertex(-1, 1);
		Vertex vertex7 = new Vertex( 0, 4);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);

		Section section1 = new Section();
		section1.add(polygon1);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;

		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

			Iterator<ScanLineNode.StartPair> startPairIterator = event.startScanLineNodePairList.iterator();
			{
				ScanLineNode.StartPair startPair = startPairIterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			{
				ScanLineNode.StartPair startPair = startPairIterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			Assert.assertNull(event.process());

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(3, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

			List<ScanLineGroup> scanLineGroupList = new ArrayList<>();
			ScanLineGroup dualScanLineGroup = null;
			for(ScanLineGroup scanLineGroup : event.getScanLineGroupPostEventVertexMap().values())
			{
				if (scanLineGroup.size() == 1)
				{
					scanLineGroupList.add(scanLineGroup);
				}
				else if (scanLineGroup.size() == 2)
				{
					dualScanLineGroup = scanLineGroup;
				}
			}

			Assert.assertEquals(2, scanLineGroupList.size());
			Assert.assertNotNull(dualScanLineGroup);
			Assert.assertEquals(2, dualScanLineGroup.size());

			Iterator<ScanLineNode> scanLineNodeIterator = dualScanLineGroup.getScanLineNodeSet().iterator();
			Label label1 = scanLineNodeIterator.next().getLabel();
			Label label2 = scanLineNodeIterator.next().getLabel();

			Assert.assertEquals(1, label1.getJoins().size());
			Assert.assertEquals(1, label2.getJoins().size());

			if (!Label.Wisedness.COUNTERCLOCKWISE.equals(label1.getJoin(label2)))
			{
				Label temp = label1;
				label1 = label2;
				label2 = temp;
			}

			Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
			Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));

			for(ScanLineGroup scanLineGroup : scanLineGroupList)
			{
				Assert.assertEquals(1, scanLineGroup.size());
				Assert.assertEquals(0, scanLineGroup.getScanLineNodeSet().first().getLabel().getJoins().size());
			}
		}

		// vertex2(0, -1), vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			ScanLineGroup scanLineGroup = event.getScanLineGroupPreEventVertexMap().firstEntry().getValue();

			Assert.assertEquals(2, scanLineGroup.size());

			Iterator<ScanLineNode> iterator = scanLineGroup.getScanLineNodeSet().iterator();
			Label label1 = iterator.next().getLabel();
			Label label2 = iterator.next().getLabel();

			Assert.assertEquals(1, label1.getJoins().size());
			Assert.assertEquals(1, label2.getJoins().size());

			if (!Label.Wisedness.COUNTERCLOCKWISE.equals(label1.getJoin(label2)))
			{
				Label temp = label1;
				label1 = label2;
				label2 = temp;
			}

			Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
			Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));

			Assert.assertEquals(vertex1, event.process());
		}
	}

	@Test
	public void singleBinaryStartPairPair5()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-1, 1);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex( 0,-2);
		Vertex vertex4 = new Vertex( 0,-4);
		Vertex vertex5 = new Vertex(-2, 0);
		Vertex vertex6 = new Vertex(-1, 1);
		Vertex vertex7 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertTrue(section1.hasIntersection());
	}

	@Test
	public void singleBinaryStartPairPair6()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-2, 0);
		Vertex vertex2 = new Vertex( 0,-4);
		Vertex vertex3 = new Vertex( 0,-2);
		Vertex vertex4 = new Vertex(-1, 0);
		Vertex vertex5 = new Vertex(-2, 0);
		Vertex vertex6 = new Vertex(-1, 0);
		Vertex vertex7 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);

		Section section1 = new Section();
		section1.add(polygon1);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;

		// vertex3(0,-2), vertex1(0, 1)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Iterator<ScanLineNode.StartPair> startPairIterator = event.startScanLineNodePairList.iterator();
			{
				ScanLineNode.StartPair startPair = startPairIterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			{
				ScanLineNode.StartPair startPair = startPairIterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			Assert.assertNull(event.process());

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(3, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		// vertex2(0, -1), vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex4, event.getEventVertex());

			ScanLineGroup scanLineGroup = event.getScanLineGroupPreEventVertexMap().firstEntry().getValue();

			Assert.assertEquals(2, scanLineGroup.size());

			Iterator<ScanLineNode> scanLineNodeIterator =  scanLineGroup.getScanLineNodeSet().iterator();

			Label label1 = scanLineNodeIterator.next().getLabel();
			Label label2 = scanLineNodeIterator.next().getLabel();

			Assert.assertEquals(0, label1.getJoins().size());
			Assert.assertEquals(0, label2.getJoins().size());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

			Iterator<Map.Entry<LineSegment, ScanLineGroup>> iterator = event.getScanLineGroupPostEventVertexMap().entrySet().iterator();

			ScanLineGroup scanLineGroup1 = iterator.next().getValue();
			ScanLineGroup scanLineGroup2 = iterator.next().getValue();

			Assert.assertEquals(1, scanLineGroup1.size());
			Assert.assertEquals(1, scanLineGroup2.size());

			Label label1n = scanLineGroup1.getScanLineNodeSet().first().getLabel();
			Label label2n = scanLineGroup2.getScanLineNodeSet().first().getLabel();

			if (!label1n.equals(label1))
			{
				Label temp = label1;
				label1 = label2;
				label2 = temp;
			}

			Assert.assertEquals(label1, label1n);
			Assert.assertEquals(label2, label2n);

			Assert.assertEquals(0, label1n.getJoins().size());
			Assert.assertEquals(0, label2n.getJoins().size());

		}

		// vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(4, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		// vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex3, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}

		// vertex1(0, 2), vertex4(1, 0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex7, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		// vertex4(1, 0)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}


		Assert.assertEquals(0, eventQueue.size());
	}


	@Test
	public void doubleBinaryStartPairPair0()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-1, 1);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex(-1,-1);
		Vertex vertex4 = new Vertex( 0,-4);
		Vertex vertex5 = new Vertex( 0,-2);
		Vertex vertex6 = new Vertex(-1,-1);
		Vertex vertex7 = new Vertex(-2, 0);
		Vertex vertex8 = new Vertex(-1, 1);
		Vertex vertex9 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);
		polygon1.push_back(vertex8);
		polygon1.push_back(vertex9);

		Section section1 = new Section();
		section1.add(polygon1);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;

		// vertex3(0,-2), vertex1(0, 1)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

			Iterator<ScanLineNode.StartPair> iterator = event.startScanLineNodePairList.iterator();
			{
				ScanLineNode.StartPair startPair = iterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			{
				ScanLineNode.StartPair startPair = iterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			Assert.assertNull(event.process());

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		// vertex2(0, -1), vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex3, event.getEventVertex());

			ScanLineGroup scanLineGroup = event.getScanLineGroupPreEventVertexMap().firstEntry().getValue();

			Assert.assertEquals(2, scanLineGroup.size());

			Iterator<ScanLineNode> scanLineNodeIterator = scanLineGroup.getScanLineNodeSet().iterator();

			Label label1 = scanLineNodeIterator.next().getLabel();
			Label label2 = scanLineNodeIterator.next().getLabel();

			Assert.assertEquals(0, label1.getJoins().size());
			Assert.assertEquals(0, label2.getJoins().size());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

			Iterator<Map.Entry<LineSegment, ScanLineGroup>> iterator = event.getScanLineGroupPostEventVertexMap().entrySet().iterator();

			ScanLineGroup scanLineGroup1 = iterator.next().getValue();
			ScanLineGroup scanLineGroup2 = iterator.next().getValue();

			Assert.assertEquals(1, scanLineGroup1.size());
			Assert.assertEquals(1, scanLineGroup2.size());

			Label label1n = scanLineGroup1.getScanLineNodeSet().first().getLabel();
			Label label2n = scanLineGroup2.getScanLineNodeSet().first().getLabel();

			if (!label1n.equals(label1))
			{
				Label temp = label1;
				label1 = label2;
				label2 = temp;
			}

			Assert.assertEquals(label1, label1n);
			Assert.assertEquals(label2, label2n);

			Assert.assertEquals(0, label1n.getJoins().size());
			Assert.assertEquals(0, label2n.getJoins().size());

			Label startsWithLabel1 = label1.getStartsWithLabel();
			Label startsWithLabel2 = label2.getStartsWithLabel();

			Assert.assertEquals(1, startsWithLabel1.getJoins().size());
			Assert.assertEquals(1, startsWithLabel2.getJoins().size());

			if (!Label.Wisedness.COUNTERCLOCKWISE.equals(startsWithLabel1.getJoin(startsWithLabel2)))
			{
				Label temp = startsWithLabel1;
				startsWithLabel1 = startsWithLabel2;
				startsWithLabel2 = temp;
			}

			Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, startsWithLabel1.getJoin(startsWithLabel2));
			Assert.assertEquals(Label.Wisedness.CLOCKWISE, startsWithLabel2.getJoin(startsWithLabel1));
		}

		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex8, event.getEventVertex());

			ScanLineGroup scanLineGroup = event.getScanLineGroupPreEventVertexMap().firstEntry().getValue();

			Assert.assertEquals(2, scanLineGroup.size());

			Iterator<ScanLineNode> scanLineNodeIterator = scanLineGroup.getScanLineNodeSet().iterator();

			Label label1 = scanLineNodeIterator.next().getLabel();
			Label label2 = scanLineNodeIterator.next().getLabel();

			Assert.assertEquals(1, label1.getJoins().size());
			Assert.assertEquals(1, label2.getJoins().size());

			if (!Label.Wisedness.COUNTERCLOCKWISE.equals(label1.getJoin(label2)))
			{
				Label temp = label1;
				label1 = label2;
				label2 = temp;
			}

			Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
			Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

			Iterator<Map.Entry<LineSegment, ScanLineGroup>> iterator = event.getScanLineGroupPostEventVertexMap().entrySet().iterator();

			ScanLineGroup scanLineGroup1 = iterator.next().getValue();
			ScanLineGroup scanLineGroup2 = iterator.next().getValue();

			Assert.assertEquals(1, scanLineGroup1.size());
			Assert.assertEquals(1, scanLineGroup2.size());

			Label label1n = scanLineGroup1.getScanLineNodeSet().first().getLabel();
			Label label2n = scanLineGroup2.getScanLineNodeSet().first().getLabel();

			if (!label1n.equals(label1))
			{
				Label temp = label1;
				label1 = label2;
				label2 = temp;
			}

			Assert.assertEquals(label1, label1n);
			Assert.assertEquals(label2, label2n);

			Assert.assertEquals(0, label1n.getJoins().size());
			Assert.assertEquals(0, label2n.getJoins().size());
		}

		Assert.assertEquals(4, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex4, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex5, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex9, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		Assert.assertEquals(0, eventQueue.size());
	}

	@Test
	public void doubleBinaryStartPairPair1()
	{
		Vertex vertex0 = new Vertex( 0, 2);
		Vertex vertex1 = new Vertex(-1, 1);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex(-1,-1);
		Vertex vertex4 = new Vertex( 0,-4);
		Vertex vertex5 = new Vertex( 0,-2);
		Vertex vertex6 = new Vertex(-1,-1);
		Vertex vertex7 = new Vertex(-2, 0);
		Vertex vertex8 = new Vertex(-1, 1);
		Vertex vertex9 = new Vertex( 0, 4);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);
		polygon1.push_back(vertex8);
		polygon1.push_back(vertex9);

		Section section1 = new Section();
		section1.add(polygon1);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;

		// vertex3(0,-2), vertex1(0, 1)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

			Iterator<ScanLineNode.StartPair> startPairIterator = event.startScanLineNodePairList.iterator();
			{
				ScanLineNode.StartPair startPair = startPairIterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			{
				ScanLineNode.StartPair startPair = startPairIterator.next();
				ScanLineNode scanLineNode1 = startPair.getFirst();
				ScanLineNode scanLineNode2 = startPair.getSecond();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			Assert.assertNull(event.process());

			Assert.assertEquals(2, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		// vertex2(0, -1), vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex3, event.getEventVertex());

			ScanLineGroup scanLineGroup = event.getScanLineGroupPreEventVertexMap().firstEntry().getValue();

			Assert.assertEquals(2, scanLineGroup.size());

			Iterator<ScanLineNode> scanLineNodeIterator =  scanLineGroup.getScanLineNodeSet().iterator();

			Label label1 = scanLineNodeIterator.next().getLabel();
			Label label2 = scanLineNodeIterator.next().getLabel();

			Assert.assertEquals(0, label1.getJoins().size());
			Assert.assertEquals(0, label2.getJoins().size());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

			Iterator<Map.Entry<LineSegment, ScanLineGroup>> iterator = event.getScanLineGroupPostEventVertexMap().entrySet().iterator();

			ScanLineGroup scanLineGroup1 = iterator.next().getValue();
			ScanLineGroup scanLineGroup2 = iterator.next().getValue();

			Assert.assertEquals(1, scanLineGroup1.size());
			Assert.assertEquals(1, scanLineGroup2.size());

			Label label1n = scanLineGroup1.getScanLineNodeSet().first().getLabel();
			Label label2n = scanLineGroup2.getScanLineNodeSet().first().getLabel();

			if (!label1n.equals(label1))
			{
				Label temp = label1;
				label1 = label2;
				label2 = temp;
			}

			Assert.assertEquals(label1, label1n);
			Assert.assertEquals(label2, label2n);

			Assert.assertEquals(0, label1n.getJoins().size());
			Assert.assertEquals(0, label2n.getJoins().size());

			Label startsWithLabel1 = label1.getStartsWithLabel();
			Label startsWithLabel2 = label2.getStartsWithLabel();

			Assert.assertEquals(1, startsWithLabel1.getJoins().size());
			Assert.assertEquals(1, startsWithLabel2.getJoins().size());

			if (!Label.Wisedness.COUNTERCLOCKWISE.equals(startsWithLabel1.getJoin(startsWithLabel2)))
			{
				Label temp = startsWithLabel1;
				startsWithLabel1 = startsWithLabel2;
				startsWithLabel2 = temp;
			}

			Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, startsWithLabel1.getJoin(startsWithLabel2));
			Assert.assertEquals(Label.Wisedness.CLOCKWISE, startsWithLabel2.getJoin(startsWithLabel1));
		}

		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex8, event.getEventVertex());

			ScanLineGroup scanLineGroup = event.getScanLineGroupPreEventVertexMap().firstEntry().getValue();

			Assert.assertEquals(2, scanLineGroup.size());

			Iterator<ScanLineNode> scanLineNodeIterator =  scanLineGroup.getScanLineNodeSet().iterator();

			Label label1 = scanLineNodeIterator.next().getLabel();
			Label label2 = scanLineNodeIterator.next().getLabel();

			Assert.assertEquals(1, label1.getJoins().size());
			Assert.assertEquals(1, label2.getJoins().size());

			if (!Label.Wisedness.COUNTERCLOCKWISE.equals(label1.getJoin(label2)))
			{
				Label temp = label1;
				label1 = label2;
				label2 = temp;
			}

			Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
			Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));

			Assert.assertEquals(vertex8, event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

			Iterator<Map.Entry<LineSegment, ScanLineGroup>> iterator = event.getScanLineGroupPostEventVertexMap().entrySet().iterator();

			ScanLineGroup scanLineGroup1 = iterator.next().getValue();
			ScanLineGroup scanLineGroup2 = iterator.next().getValue();

			Assert.assertEquals(1, scanLineGroup1.size());
			Assert.assertEquals(1, scanLineGroup2.size());

			Label label1n = scanLineGroup1.getScanLineNodeSet().first().getLabel();
			Label label2n = scanLineGroup2.getScanLineNodeSet().first().getLabel();

			if (!label1n.equals(label1))
			{
				Label temp = label1;
				label1 = label2;
				label2 = temp;
			}

			Assert.assertEquals(label1, label1n);
			Assert.assertEquals(label2, label2n);

			Assert.assertEquals(0, label1n.getJoins().size());
			Assert.assertEquals(0, label2n.getJoins().size());
		}
	}

	@Test
	public void doubleBinaryStartPairPair2()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-1, 1);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex(-1,-1);
		Vertex vertex4 = new Vertex( 0,-2);
		Vertex vertex5 = new Vertex( 0,-4);
		Vertex vertex6 = new Vertex(-1,-1);
		Vertex vertex7 = new Vertex(-2, 0);
		Vertex vertex8 = new Vertex(-1, 1);
		Vertex vertex9 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);
		polygon1.push_back(vertex8);
		polygon1.push_back(vertex9);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertTrue(section1.hasIntersection());
	}


	@Test
	public void ternaryStartPairPair0()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex( 0,-4);
		Vertex vertex4 = new Vertex( 0,-2);
		Vertex vertex5 = new Vertex(-1, 0);
		Vertex vertex6 = new Vertex(-2, 0);
		Vertex vertex7 = new Vertex(-1, 0);
		Vertex vertex8 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);
		polygon1.push_back(vertex8);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertFalse(section1.hasIntersection());
	}

	@Test
	public void ternaryStartPairPair1()
	{
		Vertex vertex0 = new Vertex( 0, 2);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex( 0,-4);
		Vertex vertex4 = new Vertex( 0,-2);
		Vertex vertex5 = new Vertex(-1, 0);
		Vertex vertex6 = new Vertex(-2, 0);
		Vertex vertex7 = new Vertex(-1, 0);
		Vertex vertex8 = new Vertex( 0, 4);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);
		polygon1.push_back(vertex8);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertTrue(section1.hasIntersection());
	}

	@Test
	public void ternaryStartPairPair2()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-2, 0);
		Vertex vertex2 = new Vertex(-1, 0);
		Vertex vertex3 = new Vertex( 0,-4);
		Vertex vertex4 = new Vertex( 0,-2);
		Vertex vertex5 = new Vertex(-1, 0);
		Vertex vertex6 = new Vertex(-2, 0);
		Vertex vertex7 = new Vertex(-1, 0);
		Vertex vertex8 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);
		polygon1.push_back(vertex8);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertFalse(section1.hasIntersection());
	}

	@Test
	public void ternaryStartPairPair3()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-2, 0);
		Vertex vertex2 = new Vertex(-1, 0);
		Vertex vertex3 = new Vertex( 0,-2);
		Vertex vertex4 = new Vertex( 0,-4);
		Vertex vertex5 = new Vertex(-1, 0);
		Vertex vertex6 = new Vertex(-2, 0);
		Vertex vertex7 = new Vertex(-1, 0);
		Vertex vertex8 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);
		polygon1.push_back(vertex8);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertTrue(section1.hasIntersection());
	}

	@Test
	public void quaternaryStartPairPair0()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex(-1, 0);
		Vertex vertex4 = new Vertex( 0,-4);
		Vertex vertex5 = new Vertex( 0,-2);
		Vertex vertex6 = new Vertex(-1, 0);
		Vertex vertex7 = new Vertex(-2, 0);
		Vertex vertex8 = new Vertex(-1, 0);
		Vertex vertex9 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);
		polygon1.push_back(vertex8);
		polygon1.push_back(vertex9);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertFalse(section1.hasIntersection());
	}

	@Test
	public void quaternaryStartPairPair1()
	{
		Vertex vertex0 = new Vertex( 0, 2);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex(-1, 0);
		Vertex vertex4 = new Vertex( 0,-4);
		Vertex vertex5 = new Vertex( 0,-2);
		Vertex vertex6 = new Vertex(-1, 0);
		Vertex vertex7 = new Vertex(-2, 0);
		Vertex vertex8 = new Vertex(-1, 0);
		Vertex vertex9 = new Vertex( 0, 4);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);
		polygon1.push_back(vertex8);
		polygon1.push_back(vertex9);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertTrue(section1.hasIntersection());
	}

	@Test
	public void quaternaryStartPairPair2()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex(-1, 0);
		Vertex vertex4 = new Vertex( 0,-2);
		Vertex vertex5 = new Vertex( 0,-4);
		Vertex vertex6 = new Vertex(-1, 0);
		Vertex vertex7 = new Vertex(-2, 0);
		Vertex vertex8 = new Vertex(-1, 0);
		Vertex vertex9 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex7);
		polygon1.push_back(vertex8);
		polygon1.push_back(vertex9);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertTrue(section1.hasIntersection());
	}
}
