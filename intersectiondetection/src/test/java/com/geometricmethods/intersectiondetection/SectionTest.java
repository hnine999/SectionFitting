package com.geometricmethods.intersectiondetection;

import java.util.Iterator;
import java.util.Map;

import com.geometricmethods.geometry.LineSegment;
import com.geometricmethods.geometry.Vertex;

import org.junit.Assert;
import org.junit.Test;

/**
 * Description of file content.
 *
 * @author harmon.nine
 * 1/14/18
 */
public class SectionTest
{

	@Test
	public void squashTest()
	{
		Vertex vertex0 = new Vertex(0, 0);
		Vertex vertex1 = new Vertex(1, 1);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(3, 0);
		Vertex vertex4 = new Vertex(4, 1);
		Vertex vertex5 = new Vertex(5, 0);
		Vertex vertex6 = new Vertex(4,-1);
		Vertex vertex7 = new Vertex(3, 0);
		Vertex vertex8 = new Vertex(2, 0);
		Vertex vertex9 = new Vertex(1,-1);

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


		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			ScanLineNode.StartPair startPair = event.startScanLineNodePairList.get(0);
			ScanLineNode scanLineNode1 = startPair.getFirst();
			ScanLineNode scanLineNode2 = startPair.getSecond();

			Label label1 = scanLineNode1.getLabel();
			Label label2 = scanLineNode2.getLabel();

			Assert.assertEquals(label2, label1.getStartsWithLabel());
			Assert.assertEquals(label1, label2.getStartsWithLabel());

			Assert.assertTrue(label1.getJoins().isEmpty());
			Assert.assertTrue(label2.getJoins().isEmpty());

			Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());

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


		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

			ScanLineNode scanLineNode1;
			ScanLineNode scanLineNode2;

			{
				Iterator<Map.Entry<LineSegment, ScanLineGroup>> iterator = event.getScanLineGroupPreEventVertexMap().entrySet().iterator();

				ScanLineGroup scanLineGroup1 = iterator.next().getValue();
				ScanLineGroup scanLineGroup2 = iterator.next().getValue();

				Assert.assertEquals(1, scanLineGroup1.size());
				Assert.assertEquals(1, scanLineGroup2.size());

				scanLineNode1 = scanLineGroup1.getScanLineNodeSet().first();
				scanLineNode2 = scanLineGroup2.getScanLineNodeSet().first();

				if (!scanLineNode1.getLineSegment().getFirstVertex().equals(vertex1))
				{
					ScanLineNode temp = scanLineNode1;
					scanLineNode1 = scanLineNode2;
					scanLineNode2 = temp;
				}

				Assert.assertEquals(vertex1, scanLineNode1.getLineSegment().getFirstVertex());
				Assert.assertEquals(vertex9, scanLineNode2.getLineSegment().getFirstVertex());

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			Assert.assertNull(event.process());

			{
				Assert.assertEquals(0, event.startScanLineNodePairList.size());
				Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
				Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

				ScanLineGroup scanLineGroup1 = event.getScanLineGroupPostEventVertexMap().firstEntry().getValue();

				Assert.assertEquals(2, scanLineGroup1.size());

				Iterator<ScanLineNode> iterator = scanLineGroup1.getScanLineNodeSet().iterator();

				ScanLineNode scanLineNode1A = iterator.next();
				ScanLineNode scanLineNode2A = iterator.next();

				if (scanLineNode1.equals(scanLineNode1A))
				{
					Assert.assertEquals(scanLineNode1, scanLineNode1A);
				}
				else
				{
					Assert.assertEquals(scanLineNode1, scanLineNode2A);
					Assert.assertEquals(scanLineNode2, scanLineNode1A);
				}

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(1, label1.getJoins().size());
				Assert.assertEquals(1, label2.getJoins().size());
				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}
		}


		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex3, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex6, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex4, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex5, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}


		Assert.assertEquals(0, eventQueue.size());
	}

	@Test
	public void squashTwistTest()
	{
		Vertex vertex0 = new Vertex(0, 0);
		Vertex vertex1 = new Vertex(1, 1);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(3, 0);
		Vertex vertex4 = new Vertex(4, 1);
		Vertex vertex5 = new Vertex(5, 0);
		Vertex vertex6 = new Vertex(4,-1);
		Vertex vertex7 = new Vertex(3, 0);
		Vertex vertex8 = new Vertex(2, 0);
		Vertex vertex9 = new Vertex(1,-1);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex6);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex7);
		polygon1.push_back(vertex8);
		polygon1.push_back(vertex9);

		Section section1 = new Section();
		section1.add(polygon1);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;


		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			ScanLineNode.StartPair startPair = event.startScanLineNodePairList.get(0);
			ScanLineNode scanLineNode1 = startPair.getFirst();
			ScanLineNode scanLineNode2 = startPair.getSecond();

			Label label1 = scanLineNode1.getLabel();
			Label label2 = scanLineNode2.getLabel();

			Assert.assertEquals(label2, label1.getStartsWithLabel());
			Assert.assertEquals(label1, label2.getStartsWithLabel());

			Assert.assertTrue(label1.getJoins().isEmpty());
			Assert.assertTrue(label2.getJoins().isEmpty());

			Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());

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


		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

			ScanLineNode scanLineNode1;
			ScanLineNode scanLineNode2;

			{
				Iterator<Map.Entry<LineSegment, ScanLineGroup>> iterator = event.getScanLineGroupPreEventVertexMap().entrySet().iterator();

				ScanLineGroup scanLineGroup1 = iterator.next().getValue();
				ScanLineGroup scanLineGroup2 = iterator.next().getValue();

				Assert.assertEquals(1, scanLineGroup1.size());
				Assert.assertEquals(1, scanLineGroup2.size());

				scanLineNode1 = scanLineGroup1.getScanLineNodeSet().first();
				scanLineNode2 = scanLineGroup2.getScanLineNodeSet().first();

				if (!scanLineNode1.getLineSegment().getFirstVertex().equals(vertex1))
				{
					ScanLineNode temp = scanLineNode1;
					scanLineNode1 = scanLineNode2;
					scanLineNode2 = temp;
				}

				Assert.assertEquals(vertex1, scanLineNode1.getLineSegment().getFirstVertex());
				Assert.assertEquals(vertex9, scanLineNode2.getLineSegment().getFirstVertex());

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(label2, label1.getStartsWithLabel());
				Assert.assertEquals(label1, label2.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertTrue(label2.getJoins().isEmpty());

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}

			Assert.assertNull(event.process());

			{
				Assert.assertEquals(0, event.startScanLineNodePairList.size());
				Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
				Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

				ScanLineGroup scanLineGroup1 = event.getScanLineGroupPostEventVertexMap().firstEntry().getValue();

				Assert.assertEquals(2, scanLineGroup1.size());

				Iterator<ScanLineNode> iterator = scanLineGroup1.getScanLineNodeSet().iterator();

				ScanLineNode scanLineNode1A = iterator.next();
				ScanLineNode scanLineNode2A = iterator.next();

				if (scanLineNode1.equals(scanLineNode1A))
				{
					Assert.assertEquals(scanLineNode1, scanLineNode1A);
				}
				else
				{
					Assert.assertEquals(scanLineNode1, scanLineNode2A);
					Assert.assertEquals(scanLineNode2, scanLineNode1A);
				}

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(1, label1.getJoins().size());
				Assert.assertEquals(1, label2.getJoins().size());
				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));

				Assert.assertTrue(label1.getDegenerateSplitSet().isEmpty());
			}
		}


		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(vertex3, event.getEventVertex());
			Assert.assertEquals(vertex3, event.process());
		}
	}

	@Test
	public void slantedDoubleSquareTest()
	{
		Vertex vertex0 = new Vertex(0, 0);
		Vertex vertex1 = new Vertex(1,-1);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(1, 1);
		Vertex vertex4 = new Vertex(1,-1);
		Vertex vertex5 = new Vertex(2, 0);
		Vertex vertex6 = new Vertex(3,-1);
		Vertex vertex7 = new Vertex(2,-2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);

		Polygon polygon2 = new Polygon();
		polygon2.push_back(vertex4);
		polygon2.push_back(vertex5);
		polygon2.push_back(vertex6);
		polygon2.push_back(vertex7);

		Section section1 = new Section();
		section1.add(polygon1);
		section1.add(polygon2);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;

		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());

			{
				Iterator<Map.Entry<LineSegment, ScanLineGroup>> entryIterator = event.getScanLineGroupPostEventVertexMap().entrySet().iterator();

				ScanLineGroup scanLineGroup1 = entryIterator.next().getValue();
				ScanLineGroup scanLineGroup2 = entryIterator.next().getValue();

				if (scanLineGroup1.size() != 2)
				{
					ScanLineGroup temp = scanLineGroup1;
					scanLineGroup1 = scanLineGroup2;
					scanLineGroup2 = temp;
				}

				Assert.assertEquals(2, scanLineGroup1.size());
				Assert.assertEquals(1, scanLineGroup2.size());

				Iterator<ScanLineNode> scanLineNodeIterator = scanLineGroup1.getScanLineNodeSet().iterator();
				ScanLineNode scanLineNode1 = scanLineNodeIterator.next();
				ScanLineNode scanLineNode2 = scanLineNodeIterator.next();
				ScanLineNode scanLineNode3 = scanLineGroup2.getScanLineNodeSet().first();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();
				Label label3 = scanLineNode3.getLabel();

				if (!label2.getStartsWithLabel().equals(label3))
				{
					{
						Label temp = label1;
						label1 = label2;
						label2 = temp;
					}
				}

				Assert.assertEquals(1, label1.getJoins().size());

				Assert.assertEquals(label3, label2.getStartsWithLabel());
				Assert.assertEquals(label2, label3.getStartsWithLabel());

				Assert.assertEquals(1, label2.getJoins().size());
				Assert.assertTrue(label3.getJoins().isEmpty());

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));
			}

		}

		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex3, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex7, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex5, event.getEventVertex());

			{
				Iterator<Map.Entry<LineSegment, ScanLineGroup>> iterator = event.getScanLineGroupPreEventVertexMap().entrySet().iterator();

				ScanLineGroup scanLineGroup1 = iterator.next().getValue();
				ScanLineGroup scanLineGroup2 = iterator.next().getValue();

				if (scanLineGroup2.size() != 2)
				{
					ScanLineGroup temp = scanLineGroup1;
					scanLineGroup1 = scanLineGroup2;
					scanLineGroup2 = temp;
				}

				Assert.assertEquals(1, scanLineGroup1.size());
				Assert.assertEquals(2, scanLineGroup2.size());

				ScanLineNode scanLineNode1 = scanLineGroup1.getScanLineNodeSet().first();
				Iterator<ScanLineNode> scanLineNodeIterator = scanLineGroup2.getScanLineNodeSet().iterator();
				ScanLineNode scanLineNode2 = scanLineNodeIterator.next();
				ScanLineNode scanLineNode3 = scanLineNodeIterator.next();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();
				Label label3 = scanLineNode3.getLabel();

				if (!label2.getStartsWithLabel().equals(label1))
				{
					{
						Label temp = label2;
						label2 = label3;
						label3 = temp;
					}
				}

				Assert.assertEquals(label1, label2.getStartsWithLabel());
				Assert.assertEquals(label2, label1.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertEquals(1, label2.getJoins().size());
				Assert.assertEquals(1, label3.getJoins().size());

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label2.getJoin(label3));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label3.getJoin(label2));
			}

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}

		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex6, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}

		Assert.assertEquals(0, eventQueue.size());
	}

	@Test
	public void slantedFigureEightTest()
	{
		Vertex vertex0 = new Vertex(0, 0);
		Vertex vertex1 = new Vertex(1, -1);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(3, -1);
		Vertex vertex4 = new Vertex(2, -2);
		Vertex vertex5 = new Vertex(1, -1);
		Vertex vertex6 = new Vertex(2, 0);
		Vertex vertex7 = new Vertex(1, 1);

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

		// vertx0(0, 0), vertex1(1,-1)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		// vertex1(1,-1), vertx7(1, 1)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());

			{
				Iterator<Map.Entry<LineSegment, ScanLineGroup>> entryIterator = event.getScanLineGroupPostEventVertexMap().entrySet().iterator();

				ScanLineGroup scanLineGroup1 = entryIterator.next().getValue();
				ScanLineGroup scanLineGroup2 = entryIterator.next().getValue();

				if (scanLineGroup1.size() != 2)
				{
					ScanLineGroup temp = scanLineGroup1;
					scanLineGroup1 = scanLineGroup2;
					scanLineGroup2 = temp;
				}

				Assert.assertEquals(2, scanLineGroup1.size());
				Assert.assertEquals(1, scanLineGroup2.size());

				Iterator<ScanLineNode> scanLineNodeIterator = scanLineGroup1.getScanLineNodeSet().iterator();
				ScanLineNode scanLineNode1 = scanLineNodeIterator.next();
				ScanLineNode scanLineNode2 = scanLineNodeIterator.next();
				ScanLineNode scanLineNode3 = scanLineGroup2.getScanLineNodeSet().first();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();
				Label label3 = scanLineNode3.getLabel();

				if (!label2.getStartsWithLabel().equals(label3))
				{
					{
						Label temp = label1;
						label1 = label2;
						label2 = temp;
					}
				}

				Assert.assertTrue(label1.getStartsWithLabel().getJoins().isEmpty());

				Assert.assertEquals(label3, label2.getStartsWithLabel());
				Assert.assertEquals(label2, label3.getStartsWithLabel());

				Assert.assertTrue(label3.getJoins().isEmpty());
				Assert.assertEquals(1, label1.getJoins().size());
				Assert.assertEquals(1, label2.getJoins().size());

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));
			}

		}


		// vertex7(1, 1), vertex4(2,-2), vertex2(2,0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex7, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		// vertex4(2,-2), vertex2(2,0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex4, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		// vertex2(2,0), vertex3(3,-1)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex6, event.getEventVertex());

			{
				Iterator<Map.Entry<LineSegment, ScanLineGroup>> iterator = event.getScanLineGroupPreEventVertexMap().entrySet().iterator();

				ScanLineGroup scanLineGroup1 = iterator.next().getValue();
				ScanLineGroup scanLineGroup2 = iterator.next().getValue();

				if (scanLineGroup2.size() != 2)
				{
					ScanLineGroup temp = scanLineGroup1;
					scanLineGroup1 = scanLineGroup2;
					scanLineGroup2 = temp;
				}

				Assert.assertEquals(1, scanLineGroup1.size());
				Assert.assertEquals(2, scanLineGroup2.size());

				ScanLineNode scanLineNode1 = scanLineGroup1.getScanLineNodeSet().first();
				Iterator<ScanLineNode> scanLineNodeIterator = scanLineGroup2.getScanLineNodeSet().iterator();
				ScanLineNode scanLineNode2 = scanLineNodeIterator.next();
				ScanLineNode scanLineNode3 = scanLineNodeIterator.next();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();
				Label label3 = scanLineNode3.getLabel();

				if (!label2.getStartsWithLabel().equals(label1))
				{
					{
						Label temp = label2;
						label2 = label3;
						label3 = temp;
					}
				}

				Assert.assertEquals(label1, label2.getStartsWithLabel());
				Assert.assertEquals(label2, label1.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertEquals(1, label2.getJoins().size());
				Assert.assertEquals(1, label3.getJoins().size());
				Assert.assertTrue(label3.getStartsWithLabel().getJoins().isEmpty());

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label2.getJoin(label3));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label3.getJoin(label2));
			}

			Assert.assertEquals(vertex6, event.process());
		}
	}

	@Test
	public void slantedOffsetDoubleSquareTest()
	{
		Vertex vertex0 = new Vertex(0, 0);
		Vertex vertex1 = new Vertex(2,-2);
		Vertex vertex2 = new Vertex(4, 0);
		Vertex vertex3 = new Vertex(2, 2);
		Vertex vertex4 = new Vertex(3,-1);
		Vertex vertex5 = new Vertex(5, 1);
		Vertex vertex6 = new Vertex(7,-1);
		Vertex vertex7 = new Vertex(5,-3);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);

		Polygon polygon2 = new Polygon();
		polygon2.push_back(vertex4);
		polygon2.push_back(vertex5);
		polygon2.push_back(vertex6);
		polygon2.push_back(vertex7);

		Section section1 = new Section();
		section1.add(polygon1);
		section1.add(polygon2);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;

		// vertex0(0, 0), vertex4(3,-1)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		// vertex1(2,-2), vertex3(2, 2), vertex4(3,-1)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		// vertex3(2, 2), vertex4(3,-1), vertex2(4, 0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex3, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		// vertex4(3,-1), vertex2(4, 0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex4, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());

			{
				Iterator<Map.Entry<LineSegment, ScanLineGroup>> entryIterator = event.getScanLineGroupPostEventVertexMap().entrySet().iterator();

				ScanLineGroup scanLineGroup1 = entryIterator.next().getValue();
				ScanLineGroup scanLineGroup2 = entryIterator.next().getValue();

				if (scanLineGroup1.size() != 2)
				{
					ScanLineGroup temp = scanLineGroup1;
					scanLineGroup1 = scanLineGroup2;
					scanLineGroup2 = temp;
				}

				Assert.assertEquals(2, scanLineGroup1.size());
				Assert.assertEquals(1, scanLineGroup2.size());

				Iterator<ScanLineNode> scanLineNodeIterator = scanLineGroup1.getScanLineNodeSet().iterator();
				ScanLineNode scanLineNode1 = scanLineNodeIterator.next();
				ScanLineNode scanLineNode2 = scanLineNodeIterator.next();
				ScanLineNode scanLineNode3 = scanLineGroup2.getScanLineNodeSet().first();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();
				Label label3 = scanLineNode3.getLabel();

				if (!label2.getStartsWithLabel().equals(label3))
				{
					{
						Label temp = label1;
						label1 = label2;
						label2 = temp;
					}
				}

				Assert.assertEquals(1, label1.getJoins().size());

				Assert.assertEquals(label3, label2.getStartsWithLabel());
				Assert.assertEquals(label2, label3.getStartsWithLabel());

				Assert.assertEquals(1, label2.getJoins().size());
				Assert.assertTrue(label3.getJoins().isEmpty());

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));
			}

		}

		// vertex2(4, 0), vertex4(5, -3)
		// vertex4(5, 1) NOT INCLUDED AS IT WAS ERASED WHEN THE LINE SEGMENT BECAME A PART OF THE (4,0) EVENT
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

			{
				Iterator<Map.Entry<LineSegment, ScanLineGroup>> entryIterator = event.getScanLineGroupPreEventVertexMap().entrySet().iterator();

				ScanLineGroup scanLineGroup1 = entryIterator.next().getValue();
				ScanLineGroup scanLineGroup2 = entryIterator.next().getValue();

				if (scanLineGroup2.size() != 2)
				{
					ScanLineGroup temp = scanLineGroup1;
					scanLineGroup1 = scanLineGroup2;
					scanLineGroup2 = temp;
				}

				Assert.assertEquals(1, scanLineGroup1.size());
				Assert.assertEquals(2, scanLineGroup2.size());

				ScanLineNode scanLineNode1 = scanLineGroup1.getScanLineNodeSet().first();
				Iterator<ScanLineNode> scanLineNodeIterator = scanLineGroup2.getScanLineNodeSet().iterator();
				ScanLineNode scanLineNode2 = scanLineNodeIterator.next();
				ScanLineNode scanLineNode3 = scanLineNodeIterator.next();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();
				Label label3 = scanLineNode3.getLabel();

				if (!label2.getStartsWithLabel().equals(label1))
				{
					{
						Label temp = label2;
						label2 = label3;
						label3 = temp;
					}
				}

				Assert.assertEquals(label1, label2.getStartsWithLabel());
				Assert.assertEquals(label2, label1.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertEquals(1, label2.getJoins().size());
				Assert.assertEquals(1, label3.getJoins().size());

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label2.getJoin(label3));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label3.getJoin(label2));
			}

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}


		// vertex4(5, -3), vertex4(5, 1)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex7, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		// vertex4(5, 1), vertex4(7, -1)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex5, event.getEventVertex());


			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		// vertex4(7, -1)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex6, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}

		Assert.assertEquals(0, eventQueue.size());
	}

	@Test
	public void slantedOffsetFigureEightTest()
	{
		Vertex vertex0 = new Vertex(0, 0);
		Vertex vertex1 = new Vertex(2,-2);
		Vertex vertex2 = new Vertex(5, 1);
		Vertex vertex3 = new Vertex(7,-1);
		Vertex vertex4 = new Vertex(5,-3);
		Vertex vertex5 = new Vertex(3,-1);
		Vertex vertex6 = new Vertex(4, 0);
		Vertex vertex7 = new Vertex(2, 2);

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


		// Vertex0(0, 0), vertex5(3, -1)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}


		// vertex1(2,-2), vertex7(2, 2), vertex5(3,-1)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}


		// vertex7(2, 2), vertex5(3,-1), vertex6(4,0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex7, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		// vertex5(3,-1), vertex6(4,0)
		// vertex3(5, 0) was removed when scanLine was intersected with (4,0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex5, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

			Iterator<Map.Entry<LineSegment, ScanLineGroup>> iterator = event.getScanLineGroupPostEventVertexMap().entrySet().iterator();

			ScanLineGroup scanLineGroup1 = iterator.next().getValue();
			ScanLineGroup scanLineGroup2 = iterator.next().getValue();

			if (scanLineGroup1.size() != 2)
			{
				ScanLineGroup temp = scanLineGroup1;
				scanLineGroup1 = scanLineGroup2;
				scanLineGroup2 = temp;
			}

			Assert.assertEquals(2, scanLineGroup1.size());
			Assert.assertEquals(1, scanLineGroup2.size());

			Iterator<ScanLineNode> scanLineNodeIterator = scanLineGroup1.getScanLineNodeSet().iterator();
			ScanLineNode scanLineNode1 = scanLineNodeIterator.next();
			ScanLineNode scanLineNode2 = scanLineNodeIterator.next();
			ScanLineNode scanLineNode3 = scanLineGroup2.getScanLineNodeSet().first();

			Label label1 = scanLineNode1.getLabel();
			Label label2 = scanLineNode2.getLabel();
			Label label3 = scanLineNode3.getLabel();

			if (!label2.getStartsWithLabel().equals(label3))
			{
				Label temp = label1;
				label1 = label2;
				label2 = temp;
			}

			Assert.assertEquals(label3, label2.getStartsWithLabel());
			Assert.assertEquals(label2, label3.getStartsWithLabel());

			Assert.assertEquals(1, label1.getJoins().size());
			Assert.assertEquals(1, label2.getJoins().size());

			Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
			Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));
		}


		// vertex6(4,0), vertex4(5,-3)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex6, event.getEventVertex());

			{
				Iterator<Map.Entry<LineSegment, ScanLineGroup>> entryIterator = event.getScanLineGroupPreEventVertexMap().entrySet().iterator();

				ScanLineGroup scanLineGroup1 = entryIterator.next().getValue();
				ScanLineGroup scanLineGroup2 = entryIterator.next().getValue();

				if (scanLineGroup2.size() != 2)
				{
					ScanLineGroup temp = scanLineGroup1;
					scanLineGroup1 = scanLineGroup2;
					scanLineGroup2 = temp;
				}

				Assert.assertEquals(1, scanLineGroup1.size());
				Assert.assertEquals(2, scanLineGroup2.size());

				ScanLineNode scanLineNode1 = scanLineGroup1.getScanLineNodeSet().first();
				Iterator<ScanLineNode> scanLineNodeIterator = scanLineGroup2.getScanLineNodeSet().iterator();
				ScanLineNode scanLineNode2 = scanLineNodeIterator.next();
				ScanLineNode scanLineNode3 = scanLineNodeIterator.next();

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();
				Label label3 = scanLineNode3.getLabel();

				if (!label2.getStartsWithLabel().equals(label1))
				{
					{
						Label temp = label2;
						label2 = label3;
						label3 = temp;
					}
				}

				Assert.assertEquals(label1, label2.getStartsWithLabel());
				Assert.assertEquals(label2, label1.getStartsWithLabel());

				Assert.assertTrue(label1.getJoins().isEmpty());
				Assert.assertEquals(1, label2.getJoins().size());
				Assert.assertEquals(1, label3.getJoins().size());

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label2.getJoin(label3));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label3.getJoin(label2));
			}

			Assert.assertEquals(vertex6, event.process());
		}

	}

	@Test
	public void slantedReverseOffsetDoubleSquareTest()
	{
		Vertex vertex0 = new Vertex(1, 0);
		Vertex vertex1 = new Vertex(3,-2);
		Vertex vertex2 = new Vertex(5, 0);
		Vertex vertex3 = new Vertex(3, 2);
		Vertex vertex4 = new Vertex(2,-3);
		Vertex vertex5 = new Vertex(4,-1);
		Vertex vertex6 = new Vertex(6,-3);
		Vertex vertex7 = new Vertex(4,-5);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);

		Polygon polygon2 = new Polygon();
		polygon2.push_back(vertex4);
		polygon2.push_back(vertex5);
		polygon2.push_back(vertex6);
		polygon2.push_back(vertex7);

		Section section1 = new Section();
		section1.add(polygon1);
		section1.add(polygon2);

		section1.primeEventQueue();

		EventQueue eventQueue = section1.eventQueue;

		// vertex0(1, 0), vertex4(2,-3)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		// vertex4(2,-3), vertex1(3,-2), vertex3(3, 2)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex4, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		// vertex1(3,-2), vertex3(3, 2), vertex7(4,-5)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

			{
				ScanLineGroup scanLineGroup = event.getScanLineGroupPostEventVertexMap().firstEntry().getValue();

				Assert.assertEquals(2, scanLineGroup.size());

				Iterator<ScanLineNode> iterator = scanLineGroup.getScanLineNodeSet().iterator();
				ScanLineNode scanLineNode1 = iterator.next();
				ScanLineNode scanLineNode2 = iterator.next();

				if (!scanLineNode1.getLineSegment().getFirstVertex().equals(vertex1))
				{
					ScanLineNode temp = scanLineNode1;
					scanLineNode1 = scanLineNode2;
					scanLineNode2 = temp;
				}

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(1, label1.getJoins().size());
				Assert.assertEquals(1, label2.getJoins().size());

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));
			}

		}

		// vertex3(3, 2), vertex7(4,-5), vertex5(4,-1)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex3, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		// vertex7(4,-5), vertex5(4,-1), vertex3(5, 0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex7, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}


		// vertex5(4,-1), vertex3(5, 0), vertex6(6,-3)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex5, event.getEventVertex());

			{
				ScanLineGroup scanLineGroup = event.getScanLineGroupPreEventVertexMap().firstEntry().getValue();

				Assert.assertEquals(2, scanLineGroup.size());

				Iterator<ScanLineNode> iterator = scanLineGroup.getScanLineNodeSet().iterator();
				ScanLineNode scanLineNode1 = iterator.next();
				ScanLineNode scanLineNode2 = iterator.next();

				if (!scanLineNode1.getLineSegment().getFirstVertex().equals(vertex1))
				{
					ScanLineNode temp = scanLineNode1;
					scanLineNode1 = scanLineNode2;
					scanLineNode2 = temp;
				}

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(1, label1.getJoins().size());
				Assert.assertEquals(1, label2.getJoins().size());

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));
			}

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}


		// vertex2(5, 0), vertex6(6,-3)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());


			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}

		// vertex4(6,-3)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex6, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(1, event.terminalScanLineNodePairList.size());
		}

		Assert.assertEquals(0, eventQueue.size());
	}

	@Test
	public void slantedReverseOffsetFigureEightTest()
	{
		Vertex vertex0 = new Vertex(1, 0);
		Vertex vertex1 = new Vertex(3,-2);
		Vertex vertex2 = new Vertex(4,-1);
		Vertex vertex3 = new Vertex(6,-3);
		Vertex vertex4 = new Vertex(4,-5);
		Vertex vertex5 = new Vertex(2,-3);
		Vertex vertex6 = new Vertex(5, 0);
		Vertex vertex7 = new Vertex(3, 2);

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

		// vertex0(1, 0), vertex5(2,-3)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		// vertex5(2,-3), vertex1(3,-2), vertex7(3, 2)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex5, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		// vertex1(3,-2), vertex7(3, 2), vertex4(4,-5)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(2, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());

			{
				ScanLineGroup scanLineGroup = event.getScanLineGroupPostEventVertexMap().firstEntry().getValue();

				Assert.assertEquals(2, scanLineGroup.size());

				Iterator<ScanLineNode> iterator = scanLineGroup.getScanLineNodeSet().iterator();
				ScanLineNode scanLineNode1 = iterator.next();
				ScanLineNode scanLineNode2 = iterator.next();

				if (!scanLineNode1.getLineSegment().getFirstVertex().equals(vertex1))
				{
					ScanLineNode temp = scanLineNode1;
					scanLineNode1 = scanLineNode2;
					scanLineNode2 = temp;
				}

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(1, label1.getJoins().size());
				Assert.assertEquals(1, label2.getJoins().size());

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));
			}

		}

		// vertex7(3, 2), vertex4(4,-5), vertex2(4,-1)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex7, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}

		// vertex4(4,-5), vertex2(4,-1), vertex6(5, 0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex4, event.getEventVertex());

			Assert.assertNull(event.process());

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPostEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
		}


		// vertex2(4,-1), vertex6(5, 0), vertex3(6,-3)
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
				ScanLineNode scanLineNode1 = iterator.next();
				ScanLineNode scanLineNode2 = iterator.next();

				if (!scanLineNode1.getLineSegment().getFirstVertex().equals(vertex1))
				{
					ScanLineNode temp = scanLineNode1;
					scanLineNode1 = scanLineNode2;
					scanLineNode2 = temp;
				}

				Label label1 = scanLineNode1.getLabel();
				Label label2 = scanLineNode2.getLabel();

				Assert.assertEquals(1, label1.getJoins().size());
				Assert.assertEquals(1, label2.getJoins().size());

				Assert.assertEquals(Label.Wisedness.COUNTERCLOCKWISE, label1.getJoin(label2));
				Assert.assertEquals(Label.Wisedness.CLOCKWISE, label2.getJoin(label1));
			}

			Assert.assertEquals(vertex2, event.process());
		}

	}

}
