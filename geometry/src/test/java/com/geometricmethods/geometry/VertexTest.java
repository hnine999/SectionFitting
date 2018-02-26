package com.geometricmethods.geometry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VertexTest
{
	private Vertex vertex1, vertex2, vertex3, vertex4;

	@Before
	public void setup()
	{
		vertex1 = new Vertex(0, 0);
		vertex2 = new Vertex(1, 1);
		vertex3 = new Vertex(2, 1);
		vertex4 = new Vertex(0, 5);
	}
	
	@Test
	public void testToString()
	{
		String testString = vertex1.toString();
		Assert.assertEquals("(0,0)", testString);
		
		testString = vertex2.toString();
		Assert.assertEquals("(1,1)", testString);
		
		testString = vertex3.toString();
		Assert.assertEquals("(2,1)", testString);
	}
	
	@Test
	public void testAddSubEq()
	{
		Vertex add12 = new Vertex(3,2);
		Vertex sum12 = new Vertex(vertex2.plus(vertex3));
		Assert.assertEquals(add12, sum12);
		
		Vertex sub12 = new Vertex(-1,0);
		Vertex diff12 = new Vertex(vertex2.minus(vertex3));
		Assert.assertEquals(sub12, diff12);
	}
	
	@Test
	public void testAngle()
	{
		double baseAngle = Math.acos(1 / Math.sqrt(2));
		Assert.assertEquals(vertex2.angle(), baseAngle, 1e-07);
		Assert.assertEquals(vertex2.angle( (float)Math.PI ), baseAngle + 2 * Math.PI, 1e-7);
		Assert.assertEquals(vertex2.angle(-2.5f *(float) Math.PI), baseAngle - 2 * Math.PI, 1e-6);
	}
	
	@Test
	public void testAngleVector()
	{
		double baseAngle = -Math.acos(1/Math.sqrt(2)) + 2 * Math.PI;
		Assert.assertEquals(vertex2.angle(vertex4), baseAngle, 1e-6);
		Assert.assertEquals(vertex2.angle(vertex4, 2 * (float)Math.PI), baseAngle + 2 * Math.PI, 1e-6);
		Assert.assertEquals(vertex2.angle(vertex4, -0.5f * (float)Math.PI), baseAngle - 2 * Math.PI, 1e-6);
	}
	
	@Test
	public void testDotProduct()
	{
		Assert.assertEquals(vertex2.dotProduct(vertex3), 3, 0);
	}
	
	@Test
	public void testCrossProductZ()
	{
		Assert.assertEquals(vertex2.crossProductZ(vertex3), -1, 0);
		Assert.assertEquals(vertex3.crossProductZ(vertex2), 1, 0);
	}
}
