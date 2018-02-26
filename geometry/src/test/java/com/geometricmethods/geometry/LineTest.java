package com.geometricmethods.geometry;


import org.junit.Assert;
import org.junit.Test;

public class LineTest {

	@Test
    public void intersectionTest()
    {
    	Vertex vertex1 = new Vertex(0, 0);
    	Vertex vertex2 = new Vertex(4, 4);
    	
    	Vertex vertex3 = new Vertex(2, 0);
    	Vertex vertex4 = new Vertex(0, 2);
    	
    	Line line1 = new Line(vertex1, vertex2);
    	Line line2 = new Line(vertex3, vertex4);
    	
    	Intersection intersection = line1.getIntersection(line2);
    	
    	Assert.assertEquals(new Vertex(1,1), intersection);
    	Assert.assertTrue(intersection.isValid());
    	Assert.assertTrue(intersection.getEndpointOverlap().isEmpty());    	
    }
	
	@Test
	public void parallelNoOverlapTest()
	{
    	Vertex vertex1 = new Vertex(0, 0);
    	Vertex vertex2 = new Vertex(4, 4);
    	
    	Vertex vertex3 = new Vertex(2, 1);
    	Vertex vertex4 = new Vertex(5, 4);
    	
    	Line line1 = new Line(vertex1, vertex2);
    	Line line2 = new Line(vertex3, vertex4);
    	
    	Intersection intersection = line1.getIntersection(line2);
    	
    	Assert.assertTrue(!intersection.isValid());
	}
	
	@Test
	public void parallelOverlapTest()
	{
    	Vertex vertex1 = new Vertex(0, 0);
    	Vertex vertex2 = new Vertex(4, 4);
    	
    	Vertex vertex3 = new Vertex(1, 1);
    	Vertex vertex4 = new Vertex(3, 3);
    	
    	Line line1 = new Line(vertex1, vertex2);
    	Line line2 = new Line(vertex3, vertex4);
    	
    	Intersection intersection = line1.getIntersection(line2);
    	
    	Assert.assertTrue(intersection.isExtended());
	}
	
	@Test
	public void closestPointTest()
	{
		Vertex vertex1 = new Vertex(0, 0);
		Vertex vertex2 = new Vertex(1, 1);
		
		Line line = new Line(vertex1, vertex2);
		Vertex outerPoint = new Vertex(4,0);
		
		Vertex closestPoint = line.getClosestPoint(outerPoint);
		
		Assert.assertEquals(new Vertex(2, 2), closestPoint);
		Assert.assertEquals(2 * Math.sqrt(2), line.getDistance(outerPoint),1e-7);
	}
}
