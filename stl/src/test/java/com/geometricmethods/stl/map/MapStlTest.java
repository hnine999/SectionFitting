package com.geometricmethods.stl.map;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

import com.geometricmethods.stl.support.Pair;

public class MapStlTest {

	public static class IntegerComparator implements Comparator< Integer >
	{
		public int compare( Integer i1, Integer i2 )
		{
			return i1 - i2;
		}
	}
	
	private void prefix( MapStlNode<Integer,Integer> mapStlNode, ArrayList<MapStlNode<Integer, Integer>> retval )
	{
		if ( mapStlNode == null )
		{
			return;
		}
		retval.add(mapStlNode);
		prefix(mapStlNode.getLeftChild(),retval);
		prefix(mapStlNode.getRightChild(),retval);
	}

	private ArrayList<MapStlNode<Integer,Integer>> prefix( MapStl<Integer,Integer> mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> retval = new ArrayList<MapStlNode<Integer,Integer>>();
		prefix( mapStl.getRoot(), retval );
		return retval;
	}
	
	private void test0( MapStl< Integer, Integer > mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> mapStlNodeList = prefix( mapStl );

		Assert.assertEquals( 1L, mapStl.size() );
		Assert.assertEquals( 3L, mapStlNodeList.size() );

		MapStlNode<Integer, Integer> mapStlNode = mapStlNodeList.get( 0 );		
		Pair<Integer, Integer> intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 0L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );
		
		mapStlNode = mapStlNodeList.get( 1 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
		
		mapStlNode = mapStlNodeList.get( 2 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.END );		
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
	}
	
	private void test1( MapStl< Integer, Integer > mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> mapStlNodeList = prefix( mapStl );

		Assert.assertEquals( 2L, mapStl.size() );
		Assert.assertEquals( 4L, mapStlNodeList.size() );

		MapStlNode<Integer, Integer> mapStlNode = mapStlNodeList.get( 0 );		
		Pair<Integer, Integer> intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 0L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );
		
		mapStlNode = mapStlNodeList.get( 1 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
		
		mapStlNode = mapStlNodeList.get( 2 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.END );		
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 3 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 1L, (long)intIntPair.first );
		Assert.assertEquals( 7L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
	}
	
	private void test2( MapStl< Integer, Integer > mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> mapStlNodeList = prefix( mapStl );

		Assert.assertEquals( 3L, mapStl.size() );
		Assert.assertEquals( 5L, mapStlNodeList.size() );

		MapStlNode<Integer, Integer> mapStlNode = mapStlNodeList.get( 0 );		
		Pair<Integer, Integer> intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 0L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );
		
		mapStlNode = mapStlNodeList.get( 1 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
		
		mapStlNode = mapStlNodeList.get( 2 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 2L, (long)intIntPair.first );
		Assert.assertEquals(-1L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 3 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 1L, (long)intIntPair.first );
		Assert.assertEquals( 7L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 4 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.END );		
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
	}
	
	private void test3( MapStl< Integer, Integer > mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> mapStlNodeList = prefix( mapStl );

		Assert.assertEquals( 4L, mapStl.size() );
		Assert.assertEquals( 6L, mapStlNodeList.size() );

		MapStlNode<Integer, Integer> mapStlNode = mapStlNodeList.get( 0 );		
		Pair<Integer, Integer> intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 2L, (long)intIntPair.first );
		Assert.assertEquals(-1L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 1 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 0L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );
		
		mapStlNode = mapStlNodeList.get( 2 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );		

		mapStlNode = mapStlNodeList.get( 3 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 1L, (long)intIntPair.first );
		Assert.assertEquals( 7L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 4 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.END );		
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 5 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 3L, (long)intIntPair.first );
		Assert.assertEquals( 15L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
	}
	
	private void test4( MapStl< Integer, Integer > mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> mapStlNodeList = prefix( mapStl );

		Assert.assertEquals( 5L, mapStl.size() );
		Assert.assertEquals( 7L, mapStlNodeList.size() );

		MapStlNode<Integer, Integer> mapStlNode = mapStlNodeList.get( 0 );		
		Pair<Integer, Integer> intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 2L, (long)intIntPair.first );
		Assert.assertEquals(-1L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 1 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 0L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );
		
		mapStlNode = mapStlNodeList.get( 2 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );		

		mapStlNode = mapStlNodeList.get( 3 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 1L, (long)intIntPair.first );
		Assert.assertEquals( 7L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 4 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 4L, (long)intIntPair.first );
		Assert.assertEquals( 6L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 5 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 3L, (long)intIntPair.first );
		Assert.assertEquals( 15L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 6 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.END );		
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
	}

	private void test12( MapStl< Integer, Integer > mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> mapStlNodeList = prefix( mapStl );

		Assert.assertEquals( 13L, mapStl.size() );
		Assert.assertEquals( 15L, mapStlNodeList.size() );

		MapStlNode<Integer, Integer> mapStlNode = mapStlNodeList.get( 0 );		
		Pair<Integer, Integer> intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 6L, (long)intIntPair.first );
		Assert.assertEquals(-6L, (long)intIntPair.second );
		Assert.assertEquals( 4L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 1 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 2L, (long)intIntPair.first );
		Assert.assertEquals(-1L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 2 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 0L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );
		
		mapStlNode = mapStlNodeList.get( 3 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );		

		mapStlNode = mapStlNodeList.get( 4 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 1L, (long)intIntPair.first );
		Assert.assertEquals( 7L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 5 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 4L, (long)intIntPair.first );
		Assert.assertEquals( 6L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 6 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 3L, (long)intIntPair.first );
		Assert.assertEquals( 15L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 7 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 5L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 8 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 10L, (long)intIntPair.first );
		Assert.assertEquals( 12L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 9 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 8L, (long)intIntPair.first );
		Assert.assertEquals( 1L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 10 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 7L, (long)intIntPair.first );
		Assert.assertEquals( 23L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 11 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 9L, (long)intIntPair.first );
		Assert.assertEquals( -4L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 12 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 12L, (long)intIntPair.first );
		Assert.assertEquals( 24L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 13 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 11L, (long)intIntPair.first );
		Assert.assertEquals( 11L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 14 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.END );		
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
	}

	private void testDelete6( MapStl< Integer, Integer > mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> mapStlNodeList = prefix( mapStl );

		Assert.assertEquals( 12L, mapStl.size() );
		Assert.assertEquals( 14L, mapStlNodeList.size() );

		MapStlNode<Integer, Integer> mapStlNode = mapStlNodeList.get( 0 );		
		Pair<Integer, Integer> intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 5L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 4L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 1 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 2L, (long)intIntPair.first );
		Assert.assertEquals(-1L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 2 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 0L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );
		
		mapStlNode = mapStlNodeList.get( 3 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );		

		mapStlNode = mapStlNodeList.get( 4 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 1L, (long)intIntPair.first );
		Assert.assertEquals( 7L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 5 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 4L, (long)intIntPair.first );
		Assert.assertEquals( 6L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 6 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 3L, (long)intIntPair.first );
		Assert.assertEquals( 15L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 7 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 10L, (long)intIntPair.first );
		Assert.assertEquals( 12L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 8 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 8L, (long)intIntPair.first );
		Assert.assertEquals( 1L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 9 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 7L, (long)intIntPair.first );
		Assert.assertEquals( 23L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 10 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 9L, (long)intIntPair.first );
		Assert.assertEquals( -4L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 11 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 12L, (long)intIntPair.first );
		Assert.assertEquals( 24L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 12 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 11L, (long)intIntPair.first );
		Assert.assertEquals( 11L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 13 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.END );		
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
	}

	private void testDelete0( MapStl< Integer, Integer > mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> mapStlNodeList = prefix( mapStl );

		Assert.assertEquals( 11L, mapStl.size() );
		Assert.assertEquals( 13L, mapStlNodeList.size() );

		MapStlNode<Integer, Integer> mapStlNode = mapStlNodeList.get( 0 );		
		Pair<Integer, Integer> intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 5L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 4L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 1 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 2L, (long)intIntPair.first );
		Assert.assertEquals(-1L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 2 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.BEGIN );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );		

		mapStlNode = mapStlNodeList.get( 3 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 1L, (long)intIntPair.first );
		Assert.assertEquals( 7L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 4 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 4L, (long)intIntPair.first );
		Assert.assertEquals( 6L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 5 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 3L, (long)intIntPair.first );
		Assert.assertEquals( 15L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 6 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 10L, (long)intIntPair.first );
		Assert.assertEquals( 12L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 7 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 8L, (long)intIntPair.first );
		Assert.assertEquals( 1L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 8 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 7L, (long)intIntPair.first );
		Assert.assertEquals( 23L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 9 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 9L, (long)intIntPair.first );
		Assert.assertEquals( -4L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 10 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 12L, (long)intIntPair.first );
		Assert.assertEquals( 24L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 11 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 11L, (long)intIntPair.first );
		Assert.assertEquals( 11L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 12 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.END );		
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
	}

	private void testDelete1_2( MapStl< Integer, Integer > mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> mapStlNodeList = prefix( mapStl );

		Assert.assertEquals( 9L, mapStl.size() );
		Assert.assertEquals( 11L, mapStlNodeList.size() );

		MapStlNode<Integer, Integer> mapStlNode = mapStlNodeList.get( 0 );		
		Pair<Integer, Integer> intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 5L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 4L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 1 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 3L, (long)intIntPair.first );
		Assert.assertEquals( 15L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 2 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );		

		mapStlNode = mapStlNodeList.get( 3 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 4L, (long)intIntPair.first );
		Assert.assertEquals( 6L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 4 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 10L, (long)intIntPair.first );
		Assert.assertEquals( 12L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 5 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 8L, (long)intIntPair.first );
		Assert.assertEquals( 1L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 6 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 7L, (long)intIntPair.first );
		Assert.assertEquals( 23L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 7 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 9L, (long)intIntPair.first );
		Assert.assertEquals( -4L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 8 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 12L, (long)intIntPair.first );
		Assert.assertEquals( 24L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 9 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 11L, (long)intIntPair.first );
		Assert.assertEquals( 11L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 10 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.END );		
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
	}

	private void testDelete3_4( MapStl< Integer, Integer > mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> mapStlNodeList = prefix( mapStl );

		Assert.assertEquals( 7L, mapStl.size() );
		Assert.assertEquals( 9L, mapStlNodeList.size() );

		MapStlNode<Integer, Integer> mapStlNode = mapStlNodeList.get( 0 );		
		Pair<Integer, Integer> intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 10L, (long)intIntPair.first );
		Assert.assertEquals( 12L, (long)intIntPair.second );
		Assert.assertEquals( 4L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 1 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 5L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 2 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );		

		mapStlNode = mapStlNodeList.get( 3 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 8L, (long)intIntPair.first );
		Assert.assertEquals( 1L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 4 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 7L, (long)intIntPair.first );
		Assert.assertEquals( 23L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 5 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 9L, (long)intIntPair.first );
		Assert.assertEquals( -4L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 6 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 12L, (long)intIntPair.first );
		Assert.assertEquals( 24L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 7 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 11L, (long)intIntPair.first );
		Assert.assertEquals( 11L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 8 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.END );		
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
	}

	private void testDelete11_12( MapStl< Integer, Integer > mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> mapStlNodeList = prefix( mapStl );

		Assert.assertEquals( 5L, mapStl.size() );
		Assert.assertEquals( 7L, mapStlNodeList.size() );

		MapStlNode<Integer, Integer> mapStlNode = mapStlNodeList.get( 0 );		
		Pair<Integer, Integer> intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 8L, (long)intIntPair.first );
		Assert.assertEquals( 1L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 1 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 5L, (long)intIntPair.first );
		Assert.assertEquals( 5L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 2 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );		

		mapStlNode = mapStlNodeList.get( 3 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 7L, (long)intIntPair.first );
		Assert.assertEquals( 23L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 4 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 10L, (long)intIntPair.first );
		Assert.assertEquals( 12L, (long)intIntPair.second );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 5 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 9L, (long)intIntPair.first );
		Assert.assertEquals( -4L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 6 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.END );		
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
	}

	private void testDelete8_7_5( MapStl< Integer, Integer > mapStl )
	{
		ArrayList<MapStlNode<Integer,Integer>> mapStlNodeList = prefix( mapStl );

		Assert.assertEquals( 2L, mapStl.size() );
		Assert.assertEquals( 4L, mapStlNodeList.size() );

		MapStlNode<Integer, Integer> mapStlNode = mapStlNodeList.get( 0 );		
		Pair<Integer, Integer> intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 10L, (long)intIntPair.first );
		Assert.assertEquals( 12L, (long)intIntPair.second );
		Assert.assertEquals( 3L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 1 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.BEGIN );
		Assert.assertEquals( 2L, mapStlNode.getHeight() );		

		mapStlNode = mapStlNodeList.get( 2 );		
		intIntPair = mapStlNode.getKeyValuePair();
		Assert.assertEquals( 9L, (long)intIntPair.first );
		Assert.assertEquals( -4L, (long)intIntPair.second );
		Assert.assertEquals( 1L, mapStlNode.getHeight() );

		mapStlNode = mapStlNodeList.get( 3 );		
		Assert.assertTrue( mapStlNode.getNodeType() == MapStlNode.NodeType.END );		
		Assert.assertEquals( 1L, mapStlNode.getHeight() );
	}

	private void checkMap( MapStl<Integer, Integer> mapStl, ArrayList<Integer> integerArrayList )
	{
		int ix = 0;
		MapIterator<Integer, Integer> end = mapStl.end();
		
		MapIterator<Integer, Integer> mapIterator = mapStl.begin();
		
		while(  !mapIterator.equals( end )  )
		{
			Assert.assertEquals(  (long)integerArrayList.get( ix ), (long)mapIterator.dereference().first.longValue()  );
			mapIterator.increment();
			++ix;
		}

	}
	
	@Test
	public void insertTest()
	{
		MapStl< Integer, Integer > mapStl = new MapStl< Integer, Integer >( new IntegerComparator() );

		mapStl.insert(  0,  5 );
		test0( mapStl );
		
		mapStl.insert(  1,  7 );
		test1( mapStl );

		mapStl.insert(  2, -1 );
		test2( mapStl );

		mapStl.insert(  3, 15 );
		test3( mapStl );

		mapStl.insert(  4,  6 );
		test4( mapStl );

		mapStl.insert(  5,  5 );
		mapStl.insert(  6, -6 );
		mapStl.insert(  7, 23 );
		mapStl.insert(  8,  1 );
		mapStl.insert(  9, -4 );
		mapStl.insert( 10, 12 );
		mapStl.insert( 11, 11 );
		mapStl.insert( 12, 24 );
		test12( mapStl );		

		MapIterator< Integer, Integer > mapIterator = mapStl.begin();
		MapIterator< Integer, Integer > end = mapStl.end();
		long ix = 0;
		while(  !mapIterator.equals( end )  )
		{
			Assert.assertEquals( ix, mapIterator.dereference().first.longValue() );
			mapIterator.increment();
			++ix;
		}

		ReverseMapIterator< Integer, Integer > reverseMapIterator = mapStl.rbegin();
		ReverseMapIterator< Integer, Integer > rend = mapStl.rend();
		ix = 12;
		while(  !reverseMapIterator.equals( rend )  )
		{
			Assert.assertEquals( ix, reverseMapIterator.dereference().first.longValue() );
			reverseMapIterator.increment();
			--ix;
		}

		ArrayList< Integer > integerArrayList = new ArrayList<Integer>();
		for( int jx = 0 ; jx < 13 ; ++jx )
		{
			integerArrayList.add(  Integer.valueOf( jx )  );
		}
		
		mapIterator = mapStl.find( 6 );
		Assert.assertEquals( 6L, mapIterator.dereference().first.longValue() );
		Assert.assertEquals( -6L, mapIterator.dereference().second.longValue() );
		mapStl.erase( mapIterator );
		testDelete6( mapStl );

		integerArrayList.remove(  Integer.valueOf( 6 )  );
		checkMap( mapStl, integerArrayList );
		
		mapIterator = mapStl.find( 0 );
		Assert.assertEquals( 0L, mapIterator.dereference().first.longValue() );
		Assert.assertEquals( 5L, mapIterator.dereference().second.longValue() );
		mapStl.erase( mapIterator );
		testDelete0( mapStl );

		integerArrayList.remove(  Integer.valueOf( 0 )  );
		checkMap( mapStl, integerArrayList );

		mapIterator = mapStl.find( 1 );
		Assert.assertEquals( 1L, mapIterator.dereference().first.longValue() );
		Assert.assertEquals( 7L, mapIterator.dereference().second.longValue() );		
		mapStl.erase( mapIterator );

		mapIterator = mapStl.find( 2 );
		Assert.assertEquals( 2L, mapIterator.dereference().first.longValue() );
		Assert.assertEquals(-1L, mapIterator.dereference().second.longValue() );		
		mapStl.erase( mapIterator );
		testDelete1_2( mapStl );

		integerArrayList.remove(  Integer.valueOf( 1 )  );
		integerArrayList.remove(  Integer.valueOf( 2 )  );
		checkMap( mapStl, integerArrayList );

		mapIterator = mapStl.find( 3 );
		Assert.assertEquals( 3L, mapIterator.dereference().first.longValue() );
		Assert.assertEquals(15L, mapIterator.dereference().second.longValue() );
		mapStl.erase( mapIterator );

		mapIterator = mapStl.find( 4 );
		Assert.assertEquals( 4L, mapIterator.dereference().first.longValue() );
		Assert.assertEquals( 6L, mapIterator.dereference().second.longValue() );
		mapStl.erase( mapIterator );
		testDelete3_4( mapStl );

		integerArrayList.remove(  Integer.valueOf( 3 )  );
		integerArrayList.remove(  Integer.valueOf( 4 )  );
		checkMap( mapStl, integerArrayList );

		mapIterator = mapStl.find( 12 );
		Assert.assertEquals(12L, mapIterator.dereference().first.longValue() );
		Assert.assertEquals(24L, mapIterator.dereference().second.longValue() );
		mapStl.erase( mapIterator );

		mapIterator = mapStl.find( 11 );
		Assert.assertEquals(11L, mapIterator.dereference().first.longValue() );
		Assert.assertEquals(11L, mapIterator.dereference().second.longValue() );
		mapStl.erase( mapIterator );
		testDelete11_12( mapStl );

		integerArrayList.remove(  Integer.valueOf( 12 )  );
		integerArrayList.remove(  Integer.valueOf( 11 )  );
		checkMap( mapStl, integerArrayList );

		mapIterator = mapStl.find( 8 );
		Assert.assertEquals(8L, mapIterator.dereference().first.longValue() );
		Assert.assertEquals(1L, mapIterator.dereference().second.longValue() );
		mapStl.erase( mapIterator );

		mapIterator = mapStl.find( 7 );
		Assert.assertEquals( 7L, mapIterator.dereference().first.longValue() );
		Assert.assertEquals(23L, mapIterator.dereference().second.longValue() );
		mapStl.erase( mapIterator );
		
		mapIterator = mapStl.find( 5 );
		Assert.assertEquals( 5L, mapIterator.dereference().first.longValue() );
		Assert.assertEquals( 5L, mapIterator.dereference().second.longValue() );
		mapStl.erase( mapIterator );
		testDelete8_7_5( mapStl );
	}
	
	@Test
	public void iterationTest()
	{
		MapStl<Integer, Integer> mapStl = new MapStl<Integer, Integer>(new IntegerComparator());
		
		Assert.assertTrue(mapStl.empty());
		
		mapStl.insert(2, 1);
		mapStl.insert(8, 4);
		mapStl.insert(4, 2);
		mapStl.insert(0, 0);
		mapStl.insert(6, 3);
		
		Assert.assertEquals(5, mapStl.size());
		
		MapIterator<Integer, Integer> mapItr = mapStl.begin();
		MapIterator<Integer, Integer> mapItr_end = mapStl.end();
		ReverseMapIterator<Integer, Integer> mapRit = mapStl.rend();
		while(!mapItr.equals(mapItr_end))
		{
			mapRit.decrement();
			Assert.assertEquals(mapItr.dereference(), mapRit.dereference());
			mapItr.increment();
		}
		Assert.assertEquals(mapRit, mapStl.rbegin());
		
		mapItr = mapStl.find(2);
		Assert.assertEquals(new Pair<Integer, Integer>(2,1), mapItr.dereference());
		
		mapStl.erase(mapItr);
		Assert.assertEquals(4, mapStl.size());
		
		mapItr = mapStl.find(1);
		Assert.assertEquals(mapItr_end, mapItr);
		
		mapItr = mapStl.lower_bound(6);
		Assert.assertEquals(new Pair<Integer, Integer>(6,3), mapItr.dereference());

		mapItr = mapStl.lower_bound(9);
		Assert.assertEquals(mapItr_end, mapItr);
		
		mapItr = mapStl.lower_bound(-1);
		Assert.assertEquals(new Pair<Integer, Integer>(0,0), mapItr.dereference());
		
		mapItr = mapStl.lower_bound(7);
		Assert.assertEquals(new Pair<Integer, Integer>(8,4), mapItr.dereference());
		
		mapItr = mapStl.upper_bound(0);
		Assert.assertEquals(new Pair<Integer, Integer>(4,2), mapItr.dereference());
		
		mapItr = mapStl.upper_bound(8);
		Assert.assertEquals(mapItr_end, mapItr);

		mapItr = mapStl.upper_bound(7);
		Assert.assertEquals(new Pair<Integer, Integer>(8,4), mapItr.dereference());
	}
	
	// EXPOSES BUG
	@Test
	public void insertEraseTest()
	{
		MapStl<Integer, Integer> mapStl = new MapStl<Integer, Integer>(new IntegerComparator());
		mapStl.insert(10, 10);
		mapStl.insert(5, 5);
		mapStl.insert(2, 2);
		mapStl.erase(5);
		mapStl.erase(2);
//		mapStl.insert(3, 3);
//		mapStl.insert(7, 7);
//		mapStl.erase(3);		
				
		ReverseMapIterator<Integer,Integer> mapItr = mapStl.rend();
		Pair<Integer, Integer> intPair = mapItr.dereference();
		Assert.assertEquals(null, intPair);
	}
}
