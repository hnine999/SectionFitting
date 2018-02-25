package com.geometricmethods.stl.set;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

public class SetStlTest {

	private static class IntegerComparator implements Comparator< Integer >
	{
		public int compare( Integer i1, Integer i2 )
		{
			return i1 - i2;
		}
	}
	
	private void prefix( SetStlNode< Integer > setStlNode, ArrayList<SetStlNode< Integer >> retval )
	{
		if ( setStlNode == null )
		{
			return;
		}
		retval.add(setStlNode);
		prefix(setStlNode.getLeftChild(),retval);
		prefix(setStlNode.getRightChild(),retval);
	}

	private ArrayList<SetStlNode< Integer >> prefix( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> retval = new ArrayList<>();
		prefix( mapStl.getRoot(), retval );
		return retval;
	}
	
	private void test0( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> setStlNodeList = prefix( mapStl );

		Assert.assertEquals( 1L, mapStl.size() );
		Assert.assertEquals( 3L, setStlNodeList.size() );

		SetStlNode< Integer > setStlNode = setStlNodeList.get( 0 );		
		Assert.assertEquals( 0L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );
		
		setStlNode = setStlNodeList.get( 1 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, setStlNode.getHeight() );
		
		setStlNode = setStlNodeList.get( 2 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.END );		
		Assert.assertEquals( 1L, setStlNode.getHeight() );
	}
	
	private void test1( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> setStlNodeList = prefix( mapStl );

		Assert.assertEquals( 2L, mapStl.size() );
		Assert.assertEquals( 4L, setStlNodeList.size() );

		SetStlNode< Integer > setStlNode = setStlNodeList.get( 0 );		
		Assert.assertEquals( 0L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );
		
		setStlNode = setStlNodeList.get( 1 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, setStlNode.getHeight() );
		
		setStlNode = setStlNodeList.get( 2 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.END );		
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 3 );		
		Assert.assertEquals( 1L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );
	}
	
	private void test2( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> setStlNodeList = prefix( mapStl );

		Assert.assertEquals( 3L, mapStl.size() );
		Assert.assertEquals( 5L, setStlNodeList.size() );

		SetStlNode< Integer > setStlNode = setStlNodeList.get( 0 );		
		Assert.assertEquals( 0L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );
		
		setStlNode = setStlNodeList.get( 1 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, setStlNode.getHeight() );
		
		setStlNode = setStlNodeList.get( 2 );		
		Assert.assertEquals( 2L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 3 );		
		Assert.assertEquals( 1L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 4 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.END );		
		Assert.assertEquals( 1L, setStlNode.getHeight() );
	}
	
	private void test3( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> setStlNodeList = prefix( mapStl );

		Assert.assertEquals( 4L, mapStl.size() );
		Assert.assertEquals( 6L, setStlNodeList.size() );

		SetStlNode< Integer > setStlNode = setStlNodeList.get( 0 );		
		Assert.assertEquals( 2L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 1 );		
		Assert.assertEquals( 0L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );
		
		setStlNode = setStlNodeList.get( 2 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, setStlNode.getHeight() );		

		setStlNode = setStlNodeList.get( 3 );		
		Assert.assertEquals( 1L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 4 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.END );		
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 5 );		
		Assert.assertEquals( 3L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );
	}
	
	private void test4( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> setStlNodeList = prefix( mapStl );

		Assert.assertEquals( 5L, mapStl.size() );
		Assert.assertEquals( 7L, setStlNodeList.size() );

		SetStlNode< Integer > setStlNode = setStlNodeList.get( 0 );		
		Assert.assertEquals( 2L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 1 );		
		Assert.assertEquals( 0L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );
		
		setStlNode = setStlNodeList.get( 2 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, setStlNode.getHeight() );		

		setStlNode = setStlNodeList.get( 3 );		
		Assert.assertEquals( 1L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 4 );		
		Assert.assertEquals( 4L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 5 );		
		Assert.assertEquals( 3L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 6 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.END );		
		Assert.assertEquals( 1L, setStlNode.getHeight() );
	}

	private void test12( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> setStlNodeList = prefix( mapStl );

		Assert.assertEquals( 13L, mapStl.size() );
		Assert.assertEquals( 15L, setStlNodeList.size() );

		SetStlNode< Integer > setStlNode = setStlNodeList.get( 0 );		
		Assert.assertEquals( 6L, (long)setStlNode.getValue() );
		Assert.assertEquals( 4L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 1 );		
		Assert.assertEquals( 2L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 2 );		
		Assert.assertEquals( 0L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );
		
		setStlNode = setStlNodeList.get( 3 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, setStlNode.getHeight() );		

		setStlNode = setStlNodeList.get( 4 );		
		Assert.assertEquals( 1L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 5 );		
		Assert.assertEquals( 4L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 6 );		
		Assert.assertEquals( 3L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 7 );		
		Assert.assertEquals( 5L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 8 );		
		Assert.assertEquals( 10L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 9 );		
		Assert.assertEquals( 8L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 10 );		
		Assert.assertEquals( 7L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 11 );		
		Assert.assertEquals( 9L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 12 );		
		Assert.assertEquals( 12L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 13 );		
		Assert.assertEquals( 11L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 14 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.END );		
		Assert.assertEquals( 1L, setStlNode.getHeight() );
	}

	private void testDelete6( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> setStlNodeList = prefix( mapStl );

		Assert.assertEquals( 12L, mapStl.size() );
		Assert.assertEquals( 14L, setStlNodeList.size() );

		SetStlNode< Integer > setStlNode = setStlNodeList.get( 0 );		
		Assert.assertEquals( 5L, (long)setStlNode.getValue() );
		Assert.assertEquals( 4L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 1 );		
		Assert.assertEquals( 2L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 2 );		
		Assert.assertEquals( 0L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );
		
		setStlNode = setStlNodeList.get( 3 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, setStlNode.getHeight() );		

		setStlNode = setStlNodeList.get( 4 );		
		Assert.assertEquals( 1L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 5 );		
		Assert.assertEquals( 4L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 6 );		
		Assert.assertEquals( 3L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 7 );		
		Assert.assertEquals( 10L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 8 );		
		Assert.assertEquals( 8L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 9 );		
		Assert.assertEquals( 7L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 10 );		
		Assert.assertEquals( 9L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 11 );		
		Assert.assertEquals( 12L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 12 );		
		Assert.assertEquals( 11L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 13 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.END );		
		Assert.assertEquals( 1L, setStlNode.getHeight() );
	}

	private void testDelete0( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> setStlNodeList = prefix( mapStl );

		Assert.assertEquals( 11L, mapStl.size() );
		Assert.assertEquals( 13L, setStlNodeList.size() );

		SetStlNode< Integer > setStlNode = setStlNodeList.get( 0 );		
		Assert.assertEquals( 5L, (long)setStlNode.getValue() );
		Assert.assertEquals( 4L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 1 );		
		Assert.assertEquals( 2L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 2 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.BEGIN );
		Assert.assertEquals( 2L, setStlNode.getHeight() );		

		setStlNode = setStlNodeList.get( 3 );		
		Assert.assertEquals( 1L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 4 );		
		Assert.assertEquals( 4L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 5 );		
		Assert.assertEquals( 3L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 6 );		
		Assert.assertEquals( 10L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 7 );		
		Assert.assertEquals( 8L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 8 );		
		Assert.assertEquals( 7L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 9 );		
		Assert.assertEquals( 9L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 10 );		
		Assert.assertEquals( 12L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 11 );		
		Assert.assertEquals( 11L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 12 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.END );		
		Assert.assertEquals( 1L, setStlNode.getHeight() );
	}

	private void testDelete1_2( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> setStlNodeList = prefix( mapStl );

		Assert.assertEquals( 9L, mapStl.size() );
		Assert.assertEquals( 11L, setStlNodeList.size() );

		SetStlNode< Integer > setStlNode = setStlNodeList.get( 0 );		
		Assert.assertEquals( 5L, (long)setStlNode.getValue() );
		Assert.assertEquals( 4L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 1 );		
		Assert.assertEquals( 3L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 2 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, setStlNode.getHeight() );		

		setStlNode = setStlNodeList.get( 3 );		
		Assert.assertEquals( 4L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 4 );		
		Assert.assertEquals( 10L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 5 );		
		Assert.assertEquals( 8L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 6 );		
		Assert.assertEquals( 7L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 7 );		
		Assert.assertEquals( 9L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 8 );		
		Assert.assertEquals( 12L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 9 );		
		Assert.assertEquals( 11L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 10 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.END );		
		Assert.assertEquals( 1L, setStlNode.getHeight() );
	}

	private void testDelete3_4( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> setStlNodeList = prefix( mapStl );

		Assert.assertEquals( 7L, mapStl.size() );
		Assert.assertEquals( 9L, setStlNodeList.size() );

		SetStlNode< Integer > setStlNode = setStlNodeList.get( 0 );		
		Assert.assertEquals( 10L, (long)setStlNode.getValue() );
		Assert.assertEquals( 4L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 1 );		
		Assert.assertEquals( 5L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 2 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, setStlNode.getHeight() );		

		setStlNode = setStlNodeList.get( 3 );		
		Assert.assertEquals( 8L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 4 );		
		Assert.assertEquals( 7L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 5 );		
		Assert.assertEquals( 9L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 6 );		
		Assert.assertEquals( 12L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 7 );		
		Assert.assertEquals( 11L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 8 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.END );		
		Assert.assertEquals( 1L, setStlNode.getHeight() );
	}

	private void testDelete11_12( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> setStlNodeList = prefix( mapStl );

		Assert.assertEquals( 5L, mapStl.size() );
		Assert.assertEquals( 7L, setStlNodeList.size() );

		SetStlNode< Integer > setStlNode = setStlNodeList.get( 0 );		
		Assert.assertEquals( 8L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 1 );		
		Assert.assertEquals( 5L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 2 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.BEGIN );
		Assert.assertEquals( 1L, setStlNode.getHeight() );		

		setStlNode = setStlNodeList.get( 3 );		
		Assert.assertEquals( 7L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 4 );		
		Assert.assertEquals( 10L, (long)setStlNode.getValue() );
		Assert.assertEquals( 2L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 5 );		
		Assert.assertEquals( 9L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 6 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.END );		
		Assert.assertEquals( 1L, setStlNode.getHeight() );
	}

	private void testDelete8_7_5( SetStl< Integer > mapStl )
	{
		ArrayList<SetStlNode< Integer >> setStlNodeList = prefix( mapStl );

		Assert.assertEquals( 2L, mapStl.size() );
		Assert.assertEquals( 4L, setStlNodeList.size() );

		SetStlNode< Integer > setStlNode = setStlNodeList.get( 0 );		
		Assert.assertEquals( 10L, (long)setStlNode.getValue() );
		Assert.assertEquals( 3L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 1 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.BEGIN );
		Assert.assertEquals( 2L, setStlNode.getHeight() );		

		setStlNode = setStlNodeList.get( 2 );		
		Assert.assertEquals( 9L, (long)setStlNode.getValue() );
		Assert.assertEquals( 1L, setStlNode.getHeight() );

		setStlNode = setStlNodeList.get( 3 );		
		Assert.assertTrue( setStlNode.getNodeType() == SetStlNode.NodeType.END );		
		Assert.assertEquals( 1L, setStlNode.getHeight() );
	}

	private void checkMap( SetStl< Integer > mapStl, ArrayList<Integer> integerArrayList )
	{
		int ix = 0;
		SetIterator< Integer > end = mapStl.end();
		
		SetIterator< Integer > mapIterator = mapStl.begin();
		
		while(  !mapIterator.equals( end )  )
		{
			Assert.assertEquals(  (long)integerArrayList.get( ix ), mapIterator.dereference().longValue()  );
			mapIterator.increment();
			++ix;
		}

	}
	
	@Test
	public void insertTest()
	{
		SetStl< Integer > setStl = new SetStl<>( new IntegerComparator() );

		setStl.insert(  0 );
		test0( setStl );
		
		setStl.insert(  1 );
		test1( setStl );

		setStl.insert(  2 );
		test2( setStl );

		setStl.insert(  3 );
		test3( setStl );

		setStl.insert(  4 );
		test4( setStl );

		setStl.insert(  5 );
		setStl.insert(  6 );
		setStl.insert(  7 );
		setStl.insert(  8 );
		setStl.insert(  9 );
		setStl.insert( 10 );
		setStl.insert( 11 );
		setStl.insert( 12 );
		test12( setStl );		

		SetIterator< Integer > mapIterator = setStl.begin();
		SetIterator< Integer > end = setStl.end();
		long ix = 0;
		while(  !mapIterator.equals( end )  )
		{
			Assert.assertEquals( ix, mapIterator.dereference().longValue() );
			mapIterator.increment();
			++ix;
		}

		ReverseSetIterator< Integer > reverseSetIterator = setStl.rbegin();
		ReverseSetIterator< Integer > rend = setStl.rend();
		ix = 12;
		while(  !reverseSetIterator.equals( rend )  )
		{
			Assert.assertEquals( ix, reverseSetIterator.dereference().longValue() );
			reverseSetIterator.increment();
			--ix;
		}

		ArrayList< Integer > integerArrayList = new ArrayList<>();
		for( int jx = 0 ; jx < 13 ; ++jx )
		{
			integerArrayList.add(jx);
		}
		
		mapIterator = setStl.find( 6 );
		Assert.assertEquals( 6L, mapIterator.dereference().longValue() );
		setStl.erase( mapIterator );
		testDelete6( setStl );

		integerArrayList.remove(  Integer.valueOf( 6 )  );
		checkMap( setStl, integerArrayList );
		
		mapIterator = setStl.find( 0 );
		Assert.assertEquals( 0L, mapIterator.dereference().longValue() );
		setStl.erase( mapIterator );
		testDelete0( setStl );

		integerArrayList.remove(  Integer.valueOf( 0 )  );
		checkMap( setStl, integerArrayList );

		mapIterator = setStl.find( 1 );
		Assert.assertEquals( 1L, mapIterator.dereference().longValue() );
		setStl.erase( mapIterator );

		mapIterator = setStl.find( 2 );
		Assert.assertEquals( 2L, mapIterator.dereference().longValue() );
		setStl.erase( mapIterator );
		testDelete1_2( setStl );

		integerArrayList.remove(  Integer.valueOf( 1 )  );
		integerArrayList.remove(  Integer.valueOf( 2 )  );
		checkMap( setStl, integerArrayList );

		mapIterator = setStl.find( 3 );
		Assert.assertEquals( 3L, mapIterator.dereference().longValue() );
		setStl.erase( mapIterator );

		mapIterator = setStl.find( 4 );
		Assert.assertEquals( 4L, mapIterator.dereference().longValue() );
		setStl.erase( mapIterator );
		testDelete3_4( setStl );

		integerArrayList.remove(  Integer.valueOf( 3 )  );
		integerArrayList.remove(  Integer.valueOf( 4 )  );
		checkMap( setStl, integerArrayList );

		mapIterator = setStl.find( 12 );
		Assert.assertEquals(12L, mapIterator.dereference().longValue() );
		setStl.erase( mapIterator );

		mapIterator = setStl.find( 11 );
		Assert.assertEquals(11L, mapIterator.dereference().longValue() );
		setStl.erase( mapIterator );
		testDelete11_12( setStl );

		integerArrayList.remove(  Integer.valueOf( 12 )  );
		integerArrayList.remove(  Integer.valueOf( 11 )  );
		checkMap( setStl, integerArrayList );

		mapIterator = setStl.find( 8 );
		Assert.assertEquals(8L, mapIterator.dereference().longValue() );
		setStl.erase( mapIterator );

		mapIterator = setStl.find( 7 );
		Assert.assertEquals( 7L, mapIterator.dereference().longValue() );
		setStl.erase( mapIterator );
		
		mapIterator = setStl.find( 5 );
		Assert.assertEquals( 5L, mapIterator.dereference().longValue() );
		setStl.erase( mapIterator );
		testDelete8_7_5( setStl );
	}
}
