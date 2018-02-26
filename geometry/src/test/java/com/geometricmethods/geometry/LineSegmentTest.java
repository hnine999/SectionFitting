package com.geometricmethods.geometry;

import java.util.EnumSet;

import org.junit.Assert;
import org.junit.Test;

public class LineSegmentTest {
	
	@Test
	public void closestPointTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(1, 1);
		
		LineSegment lineSegment = new LineSegment(point1, point2);
		Vertex outerPoint1 = new Vertex(4,0);
		
		Vertex closestPoint1 = lineSegment.getClosestPoint(outerPoint1);
		
		Assert.assertEquals(new Vertex(1,1), closestPoint1);
		Assert.assertEquals(Math.sqrt(10), lineSegment.getDistance(outerPoint1), 1e-7);
		
		Vertex outerPoint2 = new Vertex(2, -1);
		
		Vertex closestPoint2 = lineSegment.getClosestPoint(outerPoint2);

		Assert.assertEquals(new Vertex(0.5f,0.5f), closestPoint2);
		Assert.assertEquals(1.5 * Math.sqrt(2), lineSegment.getDistance(outerPoint2), 1e-7);
		
		Vertex outerPoint3 = new Vertex(-1, -4);
		
		Vertex closestPoint3 = lineSegment.getClosestPoint(outerPoint3);

		Assert.assertEquals(new Vertex(0,0f), closestPoint3);
		Assert.assertEquals(Math.sqrt(17), lineSegment.getDistance(outerPoint3), 1e-7);
	}
	
	@Test
	public void intersectionTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(2, 0);
		Vertex point4 = new Vertex(0, 2);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);
		
		Assert.assertTrue(intersection.isSingle());
		Assert.assertEquals(intersection, new Vertex(1,1));
		Assert.assertEquals(EnumSet.noneOf(Intersection.EndpointOverlap.class), intersection.getEndpointOverlap());
	}
	
	@Test
	public void noIntersectionTest1()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(1, 1);
		
		Vertex point3 = new Vertex(2, 2);
		Vertex point4 = new Vertex(3, 3);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertFalse(intersection.isSingle());
	}
	
	@Test
	public void noIntersectionTest2()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(3, 2);
		Vertex point4 = new Vertex(5, -1);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertFalse(intersection.isSingle());
	}
	
	@Test
	public void noIntersectionTest3()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(3, 2);
		Vertex point4 = new Vertex(5, -1);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertFalse(intersection.isSingle());
	}
	
	@Test
	public void l1v1IntersectionTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(-1, 1);
		Vertex point4 = new Vertex(1, -1);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isSingle());
		Assert.assertEquals(intersection, new Vertex(0,0));
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1), intersection.getEndpointOverlap());
	}
	
	@Test
	public void l1v2IntersectionTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(4, 6);
		Vertex point4 = new Vertex(4, 2);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isSingle());
		Assert.assertEquals(intersection, new Vertex(4,4));
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l2v1IntersectionTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(1, 1);
		Vertex point4 = new Vertex(1, 4);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isSingle());
		Assert.assertEquals(intersection, new Vertex(1,1));
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L2V1), intersection.getEndpointOverlap());
	}

	@Test
	public void l2v2IntersectionTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(0, 2);
		Vertex point4 = new Vertex(2, 2);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isSingle());
		Assert.assertEquals(intersection, new Vertex(2,2));
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1l2v1IntersectionTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(0, 0);
		Vertex point4 = new Vertex(-4, 4);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isSingle());
		Assert.assertEquals(intersection, new Vertex(0,0));
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L2V1), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1l2v2IntersectionTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(4, 0);
		Vertex point4 = new Vertex(0, 0);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isSingle());
		Assert.assertEquals(intersection, new Vertex(0,0));
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v2l2v1IntersectionTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(4, 4);
		Vertex point4 = new Vertex(4, 8);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isSingle());
		Assert.assertEquals(intersection, new Vertex(4,4));
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V2, Intersection.EndpointOverlap.L2V1), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v2l2v2IntersectionTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(1, 2);
		Vertex point4 = new Vertex(4, 4);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isSingle());
		Assert.assertEquals(intersection, new Vertex(4,4));
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V2, Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1_l2v2_SameDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(-1, -1);
		Vertex point4 = new Vertex(2, 2);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1_l2v1_l2v2_SameDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(0, 0);
		Vertex point4 = new Vertex(2, 2);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L2V1, Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l2v1_l2v2_SameDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(1, 1);
		Vertex point4 = new Vertex(2, 2);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L2V1, Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1_l1v2_l2v2_SameDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(-1, -1);
		Vertex point4 = new Vertex(4, 4);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L1V2, Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1_l1v2_l2v1_l2v2_SameDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(0, 0);
		Vertex point4 = new Vertex(4, 4);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.allOf(Intersection.EndpointOverlap.class), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v2_l2v1_l2v2_SameDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(3, 3);
		Vertex point4 = new Vertex(4, 4);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V2, Intersection.EndpointOverlap.L2V1, Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1_l1v2_SameDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(-1, -1);
		Vertex point4 = new Vertex(6, 6);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L1V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1_l1v2_l2v1_SameDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(0, 0);
		Vertex point4 = new Vertex(6, 6);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L1V2, Intersection.EndpointOverlap.L2V1), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v2_l2v1_SameDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(3, 3);
		Vertex point4 = new Vertex(6, 6);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V2, Intersection.EndpointOverlap.L2V1), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1_l2v1_OppositeDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(2, 2);
		Vertex point4 = new Vertex(-1, -1);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L2V1), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1_l2v1_l2v2_OppositeDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(2, 2);
		Vertex point4 = new Vertex(0, 0);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L2V1, Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l2v1_l2v2_OppositeDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(2, 2);
		Vertex point4 = new Vertex(1, 1);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L2V1, Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1_l1v2_l2v1_OppositeDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(4, 4);
		Vertex point4 = new Vertex(-1, -1);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L1V2, Intersection.EndpointOverlap.L2V1), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1_l1v2_l2v1_l2v2_OppositeDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(4, 4);
		Vertex point4 = new Vertex(0, 0);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.allOf(Intersection.EndpointOverlap.class), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v2_l2v1_l2v2_OppositeDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(4, 4);
		Vertex point4 = new Vertex(3, 3);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V2, Intersection.EndpointOverlap.L2V1, Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1_l1v2_OppositeDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(6, 6);
		Vertex point4 = new Vertex(-1, -1);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L1V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v1_l1v2_l2v2_OppositeDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(6, 6);
		Vertex point4 = new Vertex(0, 0);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V1, Intersection.EndpointOverlap.L1V2, Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}

	@Test
	public void l1v2_l2v2_OppositeDirectionExtendedTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(6, 6);
		Vertex point4 = new Vertex(3, 3);
		
		LineSegment lineSegment1 = new LineSegment(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = lineSegment1.getIntersection(lineSegment2);

		Assert.assertTrue(intersection.isExtended());
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L1V2, Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
	}	

	@Test
	public void lineLineSegmentNoIntersectionTest1()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(3, 1);
		Vertex point4 = new Vertex(5, -1);
		
		Line line1 = new Line(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = line1.getIntersection(lineSegment2);

		Assert.assertFalse(intersection.isValid());
	}
	
	@Test
	public void lineLineSegmentNoIntersectionTest2()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(8, 6);
		Vertex point4 = new Vertex(10, 4);
		
		Line line1 = new Line(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = line1.getIntersection(lineSegment2);

		Assert.assertFalse(intersection.isValid());
	}	
	
	@Test
	public void lineLineSegmentParallelNoOverlapTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(0, 4);
		Vertex point4 = new Vertex(1, 5);
		
		Line line1 = new Line(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = line1.getIntersection(lineSegment2);

		Assert.assertFalse(intersection.isValid());
	}
	
	@Test
	public void lineLineSegmentIntersectionTest1()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(0, 4);
		Vertex point4 = new Vertex(5, -1);
		
		Line line1 = new Line(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = line1.getIntersection(lineSegment2);

		Assert.assertEquals(new Vertex(2, 2), intersection);
		Assert.assertEquals(EnumSet.noneOf(Intersection.EndpointOverlap.class), intersection.getEndpointOverlap());
		Assert.assertEquals(Intersection.Type.SINGLE, intersection.getType());
	}	
	
	@Test
	public void lineLineSegmentIntersectionTest2()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(0, -4);
		Vertex point4 = new Vertex(-4, 0);
		
		Line line1 = new Line(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = line1.getIntersection(lineSegment2);

		Assert.assertEquals(new Vertex(-2, -2), intersection);
		Assert.assertEquals(EnumSet.noneOf(Intersection.EndpointOverlap.class), intersection.getEndpointOverlap());
		Assert.assertEquals(Intersection.Type.SINGLE, intersection.getType());
	}	

	@Test
	public void lineLineSegmentExtendedIntersectionTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(5, 5);
		Vertex point4 = new Vertex(10, 10);
		
		Line line1 = new Line(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = line1.getIntersection(lineSegment2);

		Assert.assertEquals(EnumSet.allOf(Intersection.EndpointOverlap.class), intersection.getEndpointOverlap());
		Assert.assertEquals(Intersection.Type.EXTENDED, intersection.getType());
	}
	
	@Test
	public void lineLineSegmentL2V1IntersectionTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(1, 1);
		Vertex point4 = new Vertex(6, -4);
		
		Line line1 = new Line(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = line1.getIntersection(lineSegment2);

		Assert.assertEquals(new Vertex(1, 1), intersection);
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L2V1), intersection.getEndpointOverlap());
		Assert.assertEquals(Intersection.Type.SINGLE, intersection.getType());
	}
	
	@Test
	public void lineLineSegmentL2V2IntersectionTest()
	{
		Vertex point1 = new Vertex(0, 0);
		Vertex point2 = new Vertex(4, 4);
		
		Vertex point3 = new Vertex(-6, 3);
		Vertex point4 = new Vertex(-1, -1);
		
		Line line1 = new Line(point1, point2);
		LineSegment lineSegment2 = new LineSegment(point3, point4);
		
		Intersection intersection = line1.getIntersection(lineSegment2);

		Assert.assertEquals(new Vertex(-1, -1), intersection);
		Assert.assertEquals(EnumSet.of(Intersection.EndpointOverlap.L2V2), intersection.getEndpointOverlap());
		Assert.assertEquals(Intersection.Type.SINGLE, intersection.getType());
	}
}
