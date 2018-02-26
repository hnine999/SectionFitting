package com.geometricmethods.intersectiondetection;

import com.geometricmethods.geometry.Vertex;

import org.junit.Assert;
import org.junit.Test;

/**
 * Description of file content.
 *
 * @author harmon.nine
 * 2/5/18
 */
public class TerminalPairPairTest
{
	@Test
	public void UnaryTerminalPairPair0()
	{
		Vertex vertex0 = new Vertex(0, 2);
		Vertex vertex1 = new Vertex(1, 0);
		Vertex vertex2 = new Vertex(0,-2);
		Vertex vertex3 = new Vertex(0,-1);
		Vertex vertex4 = new Vertex(1, 0);
		Vertex vertex5 = new Vertex(0, 1);

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
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

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

		// vertex2(0, -1), vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex3, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		// vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex5, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		// vertex1(0, 2), vertex4(1, 0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		// vertex4(1, 0)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(4, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		Assert.assertEquals(0, eventQueue.size());
	}

	@Test
	public void unaryTerminalPairPair1()
	{
		Vertex vertex0 = new Vertex(0, 2);
		Vertex vertex1 = new Vertex(1, 0);
		Vertex vertex2 = new Vertex(0, -1);
		Vertex vertex3 = new Vertex(0, -2);
		Vertex vertex4 = new Vertex(1, 0);
		Vertex vertex5 = new Vertex(0, 1);

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
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex3, event.getEventVertex());

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

		// vertex2(0, -1), vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		// vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex5, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		// vertex1(0, 2), vertex4(1, 0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());
		}


		// vertex4(1, 0)
		Assert.assertEquals(1, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(4, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Assert.assertEquals(vertex1, event.process());
		}
	}

	@Test
	public void unaryTerminalPairPair2()
	{
		Vertex vertex0 = new Vertex(0, 1);
		Vertex vertex1 = new Vertex(1, 0);
		Vertex vertex2 = new Vertex(0,-2);
		Vertex vertex3 = new Vertex(0,-1);
		Vertex vertex4 = new Vertex(1, 0);
		Vertex vertex5 = new Vertex(0, 2);

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
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex2, event.getEventVertex());

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

		// vertex2(0, -1), vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(3, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(0, event.startScanLineNodePairList.size());
			Assert.assertEquals(1, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex3, event.getEventVertex());

			Assert.assertNull(event.process());
		}

		// vertex1(0, 1), vertex4(1, 0)
		Assert.assertEquals(2, eventQueue.size());

		{
			Event event = eventQueue.getNextEvent();

			Assert.assertEquals(1, event.startScanLineNodePairList.size());
			Assert.assertEquals(0, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex0, event.getEventVertex());

			Assert.assertNull(event.process());
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
			Assert.assertEquals(4, event.getScanLineGroupPreEventVertexMap().size());
			Assert.assertEquals(0, event.terminalScanLineNodePairList.size());
			Assert.assertEquals(vertex1, event.getEventVertex());

			Assert.assertEquals(vertex1, event.process());
		}
	}

	@Test
	public void singleBinaryTerminalPairPair0()
	{
		Vertex vertex0 = new Vertex(0, 4);
		Vertex vertex1 = new Vertex(2, 0);
		Vertex vertex2 = new Vertex(1,-1);
		Vertex vertex3 = new Vertex(0,-4);
		Vertex vertex4 = new Vertex(0,-2);
		Vertex vertex5 = new Vertex(1,-1);
		Vertex vertex6 = new Vertex(2, 0);
		Vertex vertex7 = new Vertex(0, 2);

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
	public void singleBinaryTerminalPairPair1()
	{
		Vertex vertex0 = new Vertex(0, 4);
		Vertex vertex1 = new Vertex(2, 0);
		Vertex vertex2 = new Vertex(1,-1);
		Vertex vertex3 = new Vertex(0,-2);
		Vertex vertex4 = new Vertex(0,-4);
		Vertex vertex5 = new Vertex(1,-1);
		Vertex vertex6 = new Vertex(2, 0);
		Vertex vertex7 = new Vertex(0, 2);

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
	public void singleBinaryTerminalPairPair2()
	{
		Vertex vertex0 = new Vertex(0, 2);
		Vertex vertex1 = new Vertex(2, 0);
		Vertex vertex2 = new Vertex(1,-1);
		Vertex vertex3 = new Vertex(0,-4);
		Vertex vertex4 = new Vertex(0,-2);
		Vertex vertex5 = new Vertex(1,-1);
		Vertex vertex6 = new Vertex(2, 0);
		Vertex vertex7 = new Vertex(0, 4);

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
	public void singleBinaryTerminalPairPair3()
	{
		Vertex vertex0 = new Vertex(0, 4);
		Vertex vertex1 = new Vertex(1, 1);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(0,-4);
		Vertex vertex4 = new Vertex(0,-2);
		Vertex vertex5 = new Vertex(2, 0);
		Vertex vertex6 = new Vertex(1, 1);
		Vertex vertex7 = new Vertex(0, 2);

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
	public void singleBinaryTerminalPairPair4()
	{
		Vertex vertex0 = new Vertex(0, 2);
		Vertex vertex1 = new Vertex(1, 1);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(0, -4);
		Vertex vertex4 = new Vertex(0, -2);
		Vertex vertex5 = new Vertex(2, 0);
		Vertex vertex6 = new Vertex(1, 1);
		Vertex vertex7 = new Vertex(0, 4);

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
	public void singleBinaryTerminalPairPair5()
	{
		Vertex vertex0 = new Vertex(0, 4);
		Vertex vertex1 = new Vertex(1, 1);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(0, -2);
		Vertex vertex4 = new Vertex(0, -4);
		Vertex vertex5 = new Vertex(2, 0);
		Vertex vertex6 = new Vertex(1, 1);
		Vertex vertex7 = new Vertex(0, 2);

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
	public void singleBinaryTerminalPairPair6()
	{
		Vertex vertex0 = new Vertex(0, 4);
		Vertex vertex1 = new Vertex(2, 0);
		Vertex vertex2 = new Vertex(0,-4);
		Vertex vertex3 = new Vertex(0,-2);
		Vertex vertex4 = new Vertex(1, 0);
		Vertex vertex5 = new Vertex(2, 0);
		Vertex vertex6 = new Vertex(1, 0);
		Vertex vertex7 = new Vertex(0, 2);

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
	public void doubleBinaryTerminalPairPair0()
	{
		Vertex vertex0 = new Vertex(0, 4);
		Vertex vertex1 = new Vertex(1, 1);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(1,-1);
		Vertex vertex4 = new Vertex(0,-4);
		Vertex vertex5 = new Vertex(0,-2);
		Vertex vertex6 = new Vertex(1,-1);
		Vertex vertex7 = new Vertex(2, 0);
		Vertex vertex8 = new Vertex(1, 1);
		Vertex vertex9 = new Vertex(0, 2);

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
	public void doubleBinaryTerminalPairPair1()
	{
		Vertex vertex0 = new Vertex(0, 2);
		Vertex vertex1 = new Vertex(1, 1);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(1,-1);
		Vertex vertex4 = new Vertex(0,-4);
		Vertex vertex5 = new Vertex(0,-2);
		Vertex vertex6 = new Vertex(1,-1);
		Vertex vertex7 = new Vertex(2, 0);
		Vertex vertex8 = new Vertex(1, 1);
		Vertex vertex9 = new Vertex(0, 4);

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
	public void doubleBinaryTerminalPairPair2()
	{
		Vertex vertex0 = new Vertex(0, 4);
		Vertex vertex1 = new Vertex(1, 1);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(1,-1);
		Vertex vertex4 = new Vertex(0,-2);
		Vertex vertex5 = new Vertex(0,-4);
		Vertex vertex6 = new Vertex(1,-1);
		Vertex vertex7 = new Vertex(2, 0);
		Vertex vertex8 = new Vertex(1, 1);
		Vertex vertex9 = new Vertex(0, 2);

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
	public void ternaryTerminalPairPair0()
	{
		Vertex vertex0 = new Vertex(0, 4);
		Vertex vertex1 = new Vertex(1, 0);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(0,-4);
		Vertex vertex4 = new Vertex(0,-2);
		Vertex vertex5 = new Vertex(1, 0);
		Vertex vertex6 = new Vertex(2, 0);
		Vertex vertex7 = new Vertex(1, 0);
		Vertex vertex8 = new Vertex(0, 2);

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
	public void ternaryTerminalPairPair1()
	{
		Vertex vertex0 = new Vertex(0, 2);
		Vertex vertex1 = new Vertex(1, 0);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(0,-4);
		Vertex vertex4 = new Vertex(0,-2);
		Vertex vertex5 = new Vertex(1, 0);
		Vertex vertex6 = new Vertex(2, 0);
		Vertex vertex7 = new Vertex(1, 0);
		Vertex vertex8 = new Vertex(0, 4);

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
	public void ternaryTerminalPairPair2()
	{
		Vertex vertex0 = new Vertex(0, 4);
		Vertex vertex1 = new Vertex(2, 0);
		Vertex vertex2 = new Vertex(1, 0);
		Vertex vertex3 = new Vertex(0,-4);
		Vertex vertex4 = new Vertex(0,-2);
		Vertex vertex5 = new Vertex(1, 0);
		Vertex vertex6 = new Vertex(2, 0);
		Vertex vertex7 = new Vertex(1, 0);
		Vertex vertex8 = new Vertex(0, 2);

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
	public void ternaryTerminalPairPair3()
	{
		Vertex vertex0 = new Vertex(0, 4);
		Vertex vertex1 = new Vertex(2, 0);
		Vertex vertex2 = new Vertex(1, 0);
		Vertex vertex3 = new Vertex(0,-2);
		Vertex vertex4 = new Vertex(0,-4);
		Vertex vertex5 = new Vertex(1, 0);
		Vertex vertex6 = new Vertex(2, 0);
		Vertex vertex7 = new Vertex(1, 0);
		Vertex vertex8 = new Vertex(0, 2);

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
	public void quaternaryTerminalPairPair0()
	{
		Vertex vertex0 = new Vertex(0, 4);
		Vertex vertex1 = new Vertex(1, 0);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(1, 0);
		Vertex vertex4 = new Vertex(0,-4);
		Vertex vertex5 = new Vertex(0,-2);
		Vertex vertex6 = new Vertex(1, 0);
		Vertex vertex7 = new Vertex(2, 0);
		Vertex vertex8 = new Vertex(1, 0);
		Vertex vertex9 = new Vertex(0, 2);

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
	public void quaternaryTerminalPairPair1()
	{
		Vertex vertex0 = new Vertex(0, 2);
		Vertex vertex1 = new Vertex(1, 0);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(1, 0);
		Vertex vertex4 = new Vertex(0,-4);
		Vertex vertex5 = new Vertex(0,-2);
		Vertex vertex6 = new Vertex(1, 0);
		Vertex vertex7 = new Vertex(2, 0);
		Vertex vertex8 = new Vertex(1, 0);
		Vertex vertex9 = new Vertex(0, 4);

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
	public void quaternaryTerminalPairPair2()
	{
		Vertex vertex0 = new Vertex(0, 4);
		Vertex vertex1 = new Vertex(1, 0);
		Vertex vertex2 = new Vertex(2, 0);
		Vertex vertex3 = new Vertex(1, 0);
		Vertex vertex4 = new Vertex(0,-2);
		Vertex vertex5 = new Vertex(0,-4);
		Vertex vertex6 = new Vertex(1, 0);
		Vertex vertex7 = new Vertex(2, 0);
		Vertex vertex8 = new Vertex(1, 0);
		Vertex vertex9 = new Vertex(0, 2);

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
