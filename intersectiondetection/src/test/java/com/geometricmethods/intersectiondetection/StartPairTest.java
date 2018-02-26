package com.geometricmethods.intersectiondetection;

import com.geometricmethods.geometry.Vertex;

import org.junit.Assert;
import org.junit.Test;

/**
 * Description of file content.
 *
 * @author harmon.nine
 * 2/12/18
 */
public class StartPairTest
{
	@Test
	public void startPair0()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex( 0,-4);
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

		Assert.assertFalse(section1.hasIntersection());
	}

	@Test
	public void startPair1()
	{
		Vertex vertex0 = new Vertex( 0, 2);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex( 0,-4);
		Vertex vertex4 = new Vertex(-1, 0);
		Vertex vertex5 = new Vertex( 0, 4);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertTrue(section1.hasIntersection());
	}

	@Test
	public void unaryTerminalPair0()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex(-3, 0);
		Vertex vertex4 = new Vertex( 0,-4);
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

		Assert.assertFalse(section1.hasIntersection());
	}

	@Test
	public void unaryTerminalPair1()
	{
		Vertex vertex0 = new Vertex( 0, 2);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex(-3, 0);
		Vertex vertex4 = new Vertex( 0,-4);
		Vertex vertex5 = new Vertex(-2, 0);
		Vertex vertex6 = new Vertex(-1, 0);
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

		Assert.assertTrue(section1.hasIntersection());
	}

	@Test
	public void binaryTerminalPair0()
	{
		Vertex vertex0 = new Vertex( 0, 4);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex(-1, 0);
		Vertex vertex4 = new Vertex( 0,-4);
		Vertex vertex5 = new Vertex(-1, 0);
		Vertex vertex6 = new Vertex( 0, 2);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertFalse(section1.hasIntersection());
	}

	@Test
	public void binaryTerminalPair1()
	{
		Vertex vertex0 = new Vertex( 0, 2);
		Vertex vertex1 = new Vertex(-1, 0);
		Vertex vertex2 = new Vertex(-2, 0);
		Vertex vertex3 = new Vertex(-1, 0);
		Vertex vertex4 = new Vertex( 0,-4);
		Vertex vertex5 = new Vertex(-1, 0);
		Vertex vertex6 = new Vertex( 0, 4);

		Polygon polygon1 = new Polygon();
		polygon1.push_back(vertex0);
		polygon1.push_back(vertex1);
		polygon1.push_back(vertex2);
		polygon1.push_back(vertex3);
		polygon1.push_back(vertex4);
		polygon1.push_back(vertex5);
		polygon1.push_back(vertex6);

		Section section1 = new Section();
		section1.add(polygon1);

		Assert.assertTrue(section1.hasIntersection());
	}
}
