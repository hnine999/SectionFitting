package com.geometricmethods.stl.sequence;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

import com.geometricmethods.stl.support.Predicate;

public class LinkedListStlTest {

	private static class IntegerComparator implements Comparator< Integer >
	{
		public int compare( Integer i1, Integer i2 )
		{
			return i1 - i2;
		}
	}
	
	private static IntegerComparator integerComparator = new IntegerComparator();
	
	private void initList( LinkedListStl<Integer> linkedListStl, ListIterator<Integer> iterator)
	{
		linkedListStl.push_back( 5 );
		linkedListStl.push_front( 0 );
		linkedListStl.push_back( 2 );
		linkedListStl.push_front( 4 );
		linkedListStl.push_back( 7 );
		linkedListStl.push_front( 9 );
		if ( iterator != null )
		{
			iterator.set( linkedListStl.begin() );			
		}
		linkedListStl.push_back( 1 );
		linkedListStl.push_front( 3 );
		linkedListStl.push_back( 8 );
		linkedListStl.push_front( 6 );
	}

	private void initList( LinkedListStl<Integer> linkedListStl )
	{
		initList( linkedListStl, null );
	}

	private void initList2( LinkedListStl<Integer> linkedListStl, ListIterator<Integer> iterator )
	{
		linkedListStl.push_back( -4 );
		linkedListStl.push_back( -7 );
		linkedListStl.push_back( -2 );
		if ( iterator != null )
		{
			iterator.set( linkedListStl.end().decrement() );
		}
		linkedListStl.push_back( -5 );
		linkedListStl.push_back( -8 );
		linkedListStl.push_back( -6 );
		linkedListStl.push_back( -9 );
		linkedListStl.push_back( -1 );
		linkedListStl.push_back( -10 );
		linkedListStl.push_back( -3 );
	}

	private boolean checkList( ListIterator<Integer> first, ListIterator<Integer> last, int[] integerArray )
	{
		ListIterator<Integer> traverse = first.clone();
		
		int ix = 0;
		while( !traverse.equals( last ) && ix < integerArray.length )
		{
			if ( traverse.dereference() != integerArray[ix] )
			{
				return false;
			}
			++ix;
			traverse.increment();
		}
		return traverse.equals( last ) && ix == integerArray.length;
	}
	
	private void initList2( LinkedListStl<Integer> linkedListStl )
	{
		initList2( linkedListStl, null );
	}

	@Test
	public void frontBackTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		
		initList( linkedListStl );

		Assert.assertEquals( "front incorrect, " + linkedListStl.front() + " should be 6", linkedListStl.front(), Integer.valueOf(6) );
		Assert.assertEquals( "back incorrect, " + linkedListStl.back() + " should be 8", linkedListStl.back(), Integer.valueOf(8) );
	}
	
	@Test
	public void frontBackCircularTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		linkedListStl.makeCircular();
		
		initList( linkedListStl );

		Assert.assertEquals( "front incorrect, " + linkedListStl.front() + " should be 6", linkedListStl.front(), Integer.valueOf(6) );
		Assert.assertEquals( "back incorrect, " + linkedListStl.back() + " should be 8", linkedListStl.back(), Integer.valueOf(8) );
	}

	@Test
	public void pushTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		
		initList( linkedListStl );

		int[] intArray = new int[] { 6, 3, 9, 4, 0, 5, 2, 7, 1, 8 };
		Assert.assertTrue(  checkList( linkedListStl.begin(), linkedListStl.end(), intArray )  );
	}
	
	@Test
	public void pushCircularTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		linkedListStl.makeCircular();
		
		initList( linkedListStl );

		ListIterator< Integer > start = linkedListStl.begin();
		start.increment();
		start.increment();
		start.increment();
		
		ListIterator< Integer > save = start.clone();
		start.increment();
		
		int[] intArray = new int[] { 0, 5, 2, 7, 1, 8, 6, 3, 9 };
		Assert.assertTrue( save.dereference() == 4 );
		Assert.assertTrue(  checkList( start, save, intArray )  );
	}
	
	@Test
	public void popTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		
		initList( linkedListStl );

		linkedListStl.pop_back();
		linkedListStl.pop_front();
		
		Assert.assertEquals( "front incorrect, " + linkedListStl.front() + " should be 3", linkedListStl.front(), Integer.valueOf(3) );
		Assert.assertEquals( "back incorrect, " + linkedListStl.back() + " should be 1", linkedListStl.back(), Integer.valueOf(1) );
	}
	
	@Test
	public void popCircularTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		linkedListStl.makeCircular();
		
		initList( linkedListStl );

		linkedListStl.pop_back();
		linkedListStl.pop_front();
		
		Assert.assertEquals( "front incorrect, " + linkedListStl.front() + " should be 3", linkedListStl.front(), Integer.valueOf(3) );
		Assert.assertEquals( "back incorrect, " + linkedListStl.back() + " should be 1", linkedListStl.back(), Integer.valueOf(1) );
		
		linkedListStl.pop_front();
		for(int ix = 0 ; ix < 6 ; ++ix )
		{
			linkedListStl.pop_back();
		}

		Assert.assertEquals( "front incorrect, " + linkedListStl.front() + " should be 9", linkedListStl.front(), Integer.valueOf(9) );
		Assert.assertEquals( "back incorrect, " + linkedListStl.back() + " should be 9", linkedListStl.back(), Integer.valueOf(9) );

		linkedListStl.pop_front();
		
		Assert.assertTrue( linkedListStl.empty() );
	}
	
	@Test
	public void valueInsertTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		ListIterator<Integer> listIterator = new ListIterator<Integer>();

		initList(linkedListStl, listIterator);
		ListIterator<Integer> newListIterator = linkedListStl.insert( listIterator, -1 );
		
		Assert.assertEquals("Incorrect iterator from insert( listIterator, value ): " + newListIterator.dereference() + " should be -1",
				(long)newListIterator.dereference(), (long)-1);
		
		int value = newListIterator.decrement().dereference();
		Assert.assertEquals("Insertion at wrong point, point before is " + value + " should be 3", (long)value, (long)3);
		
		value = newListIterator.increment().increment().dereference();
		Assert.assertEquals("Insertion at wrong point, point after is " + value + " should be 9", (long)value, (long)9);
	}
	
	@Test
	public void valueInsertCircularTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		linkedListStl.makeCircular();
		
		ListIterator<Integer> listIterator = new ListIterator<Integer>();

		initList(linkedListStl, listIterator);
		ListIterator<Integer> newListIterator = linkedListStl.insert( listIterator, -1 );
		
		Assert.assertEquals("Incorrect iterator from insert( listIterator, value ): " + newListIterator.dereference() + " should be -1",
				(long)newListIterator.dereference(), (long)-1);
		
		int value = newListIterator.decrement().dereference();
		Assert.assertEquals("Insertion at wrong point, point before is " + value + " should be 3", (long)value, (long)3);
		
		value = newListIterator.increment().increment().dereference();
		Assert.assertEquals("Insertion at wrong point, point after is " + value + " should be 9", (long)value, (long)9);
		
		listIterator = linkedListStl.end();
		newListIterator = linkedListStl.insert( listIterator, -10 );

		Assert.assertEquals("Incorrect iterator from insert( listIterator, value ): " + newListIterator.dereference() + " should be -10",
				(long)newListIterator.dereference(), (long)-10);

		value = linkedListStl.front();
		Assert.assertEquals("Insertion at wrong point, point after is " + value + " should be -10", (long)value, (long)-10);

	}
	
	@Test
	public void sequenceInsertTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		ListIterator<Integer> listIterator = new ListIterator<Integer>();

		initList(linkedListStl, listIterator);
		
		LinkedListStl< Integer > newLinkedListStl = new LinkedListStl<Integer>();
		newLinkedListStl.push_back( -1 );
		newLinkedListStl.push_back( -5 );
		newLinkedListStl.push_back( -2 );
		
		ListIterator<Integer> newListIterator = listIterator.clone();
		newListIterator.decrement();
		
		linkedListStl.insert( listIterator, newLinkedListStl.begin(), newLinkedListStl.end() );
		listIterator.increment();
		
		Assert.assertTrue(  checkList( newListIterator, listIterator, new int[] { 3, -1, -5, -2, 9 } )  );
	}

	@Test
	public void sequenceInsertCircularTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		linkedListStl.makeCircular();
		
		initList(linkedListStl);
		
		LinkedListStl< Integer > newLinkedListStl = new LinkedListStl<Integer>();
		newLinkedListStl.push_back( -1 );
		newLinkedListStl.push_back( -5 );
		newLinkedListStl.push_back( -2 );
		
		ListIterator<Integer> listIterator = linkedListStl.begin();

		ListIterator<Integer> newListIterator = listIterator.clone();
		newListIterator.decrement();
		
		linkedListStl.insert( listIterator, newLinkedListStl.begin(), newLinkedListStl.end() );
		listIterator.increment();
		
		Assert.assertTrue(  checkList( newListIterator, listIterator, new int[] { 8, -1, -5, -2, 6 } )  );
	}

	@Test
	public void eraseTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		ListIterator<Integer> listIterator = new ListIterator<Integer>();

		initList(linkedListStl, listIterator);
		
		ListIterator<Integer> nextListIterator = linkedListStl.erase( listIterator );
		
		Assert.assertEquals( "eraseTest failed for value 9", (long)listIterator.dereference(), (long)9 );		
		Assert.assertEquals( "eraseTest failed for value 4", (long)nextListIterator.dereference(), (long)4 );		
	}
	
	@Test
	public void eraseCircularTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();		
		linkedListStl.makeCircular();
		
		initList(linkedListStl);
		
		ListIterator<Integer> listIterator = linkedListStl.begin();
		listIterator.decrement();
		listIterator = linkedListStl.erase( listIterator );
		listIterator.decrement();
		
		Assert.assertEquals( "eraseTest failed for value 8", (long)listIterator.dereference(), (long)1 );		
	}
	
	@Test
	public void sequenceEraseTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		ListIterator<Integer> listIterator = new ListIterator<Integer>();

		initList(linkedListStl, listIterator);

		ListIterator<Integer> newListIterator = listIterator.clone();
		listIterator.increment();
		listIterator.increment();
		
		linkedListStl.erase(newListIterator, listIterator);
		listIterator.decrement();
		
		Assert.assertEquals( "sequenceEraseTest failed", (long)listIterator.dereference(), (long)3 );		
	}
	
	@Test
	public void sequenceEraseCircularTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();
		linkedListStl.makeCircular();
		
		ListIterator<Integer> listIterator = new ListIterator<Integer>();

		initList(linkedListStl, listIterator);

		ListIterator<Integer> newListIterator = listIterator.clone();
		newListIterator.increment();
		
		linkedListStl.erase(newListIterator, listIterator);
		
		Assert.assertTrue( linkedListStl.size() == 1 );
		Assert.assertEquals( "sequenceEraseTest failed", (long)linkedListStl.front(), (long)9 );		
	}
	
	@Test
	public void removeTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl< Integer >();
		ListIterator< Integer > iterator = new ListIterator< Integer >();
		
		initList( linkedListStl, iterator );
		iterator.setValue( 2 );
		
		linkedListStl.remove( 2 );
		
		Assert.assertTrue( linkedListStl.size() == 8 );
		
		int[] intArray = new int[] { 6, 3, 4, 0, 5, 7, 1, 8 };
		Assert.assertTrue(  checkList( linkedListStl.begin(), linkedListStl.end(), intArray )  );		
	}
	
	@Test
	public void removeCircularTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl< Integer >();
		linkedListStl.makeCircular();
		
		ListIterator< Integer > iterator = new ListIterator< Integer >();
		
		initList( linkedListStl, iterator );
		iterator.setValue( 8 );
		
		linkedListStl.remove( 8 );
		
		Assert.assertTrue( linkedListStl.size() == 8 );
		
		iterator = linkedListStl.begin();
		iterator.increment();
		Assert.assertTrue( iterator.dereference() == 3 );
		iterator.increment();
		
		ListIterator<Integer> otherIterator = linkedListStl.begin();
		otherIterator.increment();
		
		int[] intArray = new int[] { 4, 0, 5, 2, 7, 1, 6 };
		Assert.assertTrue(  checkList( iterator, otherIterator, intArray )  );		
	}
	
	@Test
	public void removeIfTest()
	{
		LinkedListStl< Integer > linkedListStl1 = new LinkedListStl< Integer >();
		
		initList( linkedListStl1 );
		
		Predicate<Integer> predicate = new Predicate<Integer>()
		{
			public boolean evaluate( Integer value ) {
				return value > 3 && value < 7;
			}
		};
		
		linkedListStl1.remove_if( predicate );
		
		Assert.assertTrue( linkedListStl1.size() == 7 );
		
		int[] intArray = new int[] { 3, 9, 0, 2, 7, 1, 8 };
		Assert.assertTrue(  checkList( linkedListStl1.begin(), linkedListStl1.end(), intArray )  );		
	}
	
	@Test
	public void removeIfCircularTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl< Integer >();
		linkedListStl.makeCircular();
		
		initList( linkedListStl );
		
		Predicate<Integer> predicate = new Predicate<Integer>()
		{
			public boolean evaluate( Integer value ) {
				return value > 2 && value < 8;
			}
		};
		
		linkedListStl.remove_if( predicate );
		
		Assert.assertTrue( linkedListStl.size() == 5 );
		
		ListIterator<Integer> listIterator1 = linkedListStl.end();
		listIterator1.decrement();
		ListIterator<Integer> listIterator2 = listIterator1.clone();
		listIterator2.decrement();
		
		Assert.assertTrue( listIterator2.dereference() == 1 );
		
		int[] intArray = new int[] { 8, 9, 0, 2 };
		Assert.assertTrue(  checkList( listIterator1, listIterator2, intArray )  );		
	}
	
	@Test
	public void spliceListTest()
	{
		LinkedListStl< Integer > linkedListStl1 = new LinkedListStl< Integer >();
		ListIterator< Integer > iterator1 = new ListIterator< Integer >();
		
		LinkedListStl< Integer > linkedListStl2 = new LinkedListStl< Integer >();

		initList( linkedListStl1, iterator1 );
		ListIterator< Integer > newIterator1 = iterator1.clone();
		newIterator1.decrement();
		
		initList2( linkedListStl2);

		linkedListStl1.splice( iterator1, linkedListStl2 );
		iterator1.increment();

		Assert.assertEquals( linkedListStl1.size(), 20 );
		Assert.assertTrue( linkedListStl2.empty() );
		
		int[] intArray = new int[] { 3, -4, -7, -2, -5, -8, -6, -9, -1, -10, -3, 9 };
		Assert.assertTrue(  checkList( newIterator1, iterator1, intArray )  );
	}
	
	@Test
	public void spliceListCircularTest()
	{
		LinkedListStl< Integer > linkedListStl1 = new LinkedListStl< Integer >();
		linkedListStl1.makeCircular();
		
		LinkedListStl< Integer > linkedListStl2 = new LinkedListStl< Integer >();
		linkedListStl2.makeCircular();
		
		initList2( linkedListStl2);

		linkedListStl1.splice( linkedListStl1.end(), linkedListStl2 );

		Assert.assertEquals( linkedListStl1.size(), 10 );
		Assert.assertTrue( linkedListStl2.empty() );
		
		ListIterator<Integer> listIterator2 = linkedListStl1.end();
		listIterator2.increment();
		ListIterator<Integer> listIterator1 = listIterator2.clone();
		listIterator1.increment();
		
		Assert.assertTrue( listIterator2.dereference() == -7);
		int[] intArray = new int[] { -2, -5, -8, -6, -9, -1, -10, -3, -4 };
		Assert.assertTrue(  checkList( listIterator1, listIterator2, intArray )  );
	}
	
	@Test
	public void spliceTest()
	{
		LinkedListStl< Integer > linkedListStl1 = new LinkedListStl< Integer >();
		ListIterator< Integer > iterator1 = new ListIterator< Integer >();
		
		LinkedListStl< Integer > linkedListStl2 = new LinkedListStl< Integer >();
		ListIterator< Integer > iterator2 = new ListIterator< Integer >();
		
		initList( linkedListStl1, iterator1 );
		ListIterator< Integer > newIterator1 = iterator1.clone();
		newIterator1.decrement();
		
		initList2( linkedListStl2, iterator2 );
		ListIterator< Integer > newIterator2 = iterator2.clone();
		newIterator2.decrement();

		linkedListStl1.splice( iterator1, linkedListStl2, iterator2 );
		
		Assert.assertEquals( linkedListStl1.size(), 11 );
		Assert.assertEquals( linkedListStl2.size(), 9 );
		
		{
			int[] intArray = new int[] { 3, -2, 9 };
			iterator1.increment();
			Assert.assertTrue(  checkList( newIterator1, iterator1, intArray )  );
		}

		{
			int[] intArray = new int[] { -7, -5 };
			iterator2 = newIterator2.clone();
			iterator2.increment().increment();
			Assert.assertTrue(  checkList( newIterator2, iterator2, intArray )  );
		}
		
	}
	
	@Test
	public void spliceCircularTest()
	{
		LinkedListStl< Integer > linkedListStl1 = new LinkedListStl< Integer >();
		linkedListStl1.makeCircular();
		ListIterator< Integer > iterator1 = new ListIterator< Integer >();
		
		LinkedListStl< Integer > linkedListStl2 = new LinkedListStl< Integer >();
		linkedListStl2.makeCircular();
		ListIterator< Integer > iterator2 = new ListIterator< Integer >();
		
		initList( linkedListStl1, iterator1 );		
		initList2( linkedListStl2, iterator2 );

		ListIterator<Integer> newIterator2 = iterator2.clone();
		newIterator2.increment();
		
		linkedListStl1.splice( iterator1, linkedListStl2, iterator2 );
		
		Assert.assertEquals( linkedListStl1.size(), 11 );
		Assert.assertEquals( linkedListStl2.size(), 9 );
		
		{
			ListIterator<Integer> newIterator1 = iterator1.clone();
			
			Assert.assertTrue( iterator1.dereference() == 9 );
			
			int[] intArray = new int[] { 4, 0, 5, 2, 7, 1, 8, 6, 3, -2 };
			iterator1.increment();
			Assert.assertTrue(  checkList( iterator1, newIterator1, intArray )  );
		}

		{
			iterator2 = newIterator2.clone();
			Assert.assertTrue( iterator2.dereference() == -5 );
			iterator2.increment();

			int[] intArray = new int[] { -8, -6, -9, -1, -10, -3, -4, -7 };
			Assert.assertTrue(  checkList( iterator2, newIterator2, intArray )  );
		}
		
	}
	
	@Test
	public void spliceSubListTest()
	{
		LinkedListStl< Integer > linkedListStl1 = new LinkedListStl< Integer >();
		ListIterator< Integer > iterator1 = new ListIterator< Integer >();
		
		LinkedListStl< Integer > linkedListStl2 = new LinkedListStl< Integer >();
		ListIterator< Integer > iterator2 = new ListIterator< Integer >();
		
		initList( linkedListStl1, iterator1 );
		ListIterator< Integer > newIterator1 = iterator1.clone();
		newIterator1.decrement();
		
		initList2( linkedListStl2, iterator2 );
		ListIterator< Integer > newIterator2 = iterator2.clone();
		newIterator2.decrement();
		ListIterator< Integer > newIterator3 = newIterator2.clone();
		newIterator3.decrement();
		iterator2.increment().increment();

		linkedListStl1.splice( iterator1, linkedListStl2, newIterator2, iterator2 );
		
		Assert.assertEquals( linkedListStl1.size(), 13 );
		Assert.assertEquals( linkedListStl2.size(), 7 );
		
		{
			int[] intArray = new int[] { 3, -7, -2, -5, 9 };
			iterator1.increment();
			Assert.assertTrue(  checkList( newIterator1, iterator1, intArray )  );
		}

		{
			int[] intArray = new int[] { -4, -8 };
			iterator2.increment();
			Assert.assertTrue(  checkList( newIterator3, iterator2, intArray )  );
		}
		
	}

	@Test
	public void spliceSubListCircularTest()
	{
		LinkedListStl< Integer > linkedListStl1 = new LinkedListStl< Integer >();
		linkedListStl1.makeCircular();
		
		LinkedListStl< Integer > linkedListStl2 = new LinkedListStl< Integer >();
		linkedListStl2.makeCircular();
		
		initList( linkedListStl1 );		
		initList2( linkedListStl2 );

		ListIterator<Integer> iterator1 = linkedListStl1.begin();
		iterator1.increment();
		iterator1.increment();
		
		ListIterator<Integer> iterator2 = linkedListStl2.begin();
		iterator2.decrement();
		iterator2.decrement();
		iterator2.decrement();
		ListIterator<Integer> newIterator2 = linkedListStl2.begin();
		newIterator2.increment();
		newIterator2.increment();
		
		linkedListStl1.splice( iterator1, linkedListStl2, iterator2, newIterator2 );
		
		Assert.assertEquals( linkedListStl1.size(), 15 );
		Assert.assertEquals( linkedListStl2.size(), 5 );
		
		{
			iterator1 = linkedListStl1.begin();
			ListIterator<Integer> newIterator1 = linkedListStl1.begin();
			Assert.assertTrue( iterator1.dereference() == 6 );
			iterator1.increment();

			int[] intArray = new int[] { 3, -1, -10, -3, -4, -7, 9, 4, 0, 5, 2, 7, 1, 8 };
			Assert.assertTrue(  checkList( iterator1, newIterator1, intArray )  );
		}

		{
			iterator2 = linkedListStl2.begin();
			newIterator2 = linkedListStl2.begin();
			Assert.assertTrue( iterator2.dereference() == -9 );
			iterator2.increment();
			
			int[] intArray = new int[] { -2, -5, -8, -6 };
			Assert.assertTrue(  checkList( iterator2, newIterator2, intArray )  );
		}
		
	}

	@Test
	public void reverseTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();

		initList( linkedListStl );

		ReverseListIterator< Integer > reverseListIterator = linkedListStl.rbegin();
		ListIterator< Integer > listIterator = reverseListIterator.base();
		
		while(  !reverseListIterator.equals( linkedListStl.rend() )  )
		{
			listIterator.decrement();
			Assert.assertEquals( "reverseTest failed for value " + listIterator.dereference(), (long)listIterator.dereference(), (long)reverseListIterator.dereference() );
			reverseListIterator.increment();
		}
	}

	@Test
	public void sortTest()
	{
		LinkedListStl< Integer > linkedListStl = new LinkedListStl<Integer>();

		initList( linkedListStl );
		
		linkedListStl.sort( integerComparator );
		
		int ix = 0;
		for( ListIterator< Integer > listIterator = linkedListStl.begin() ; !listIterator.equals( linkedListStl.end() ) ; listIterator.increment() )
		{
			Assert.assertEquals( "sortTest failed for value " + ix, (long)ix,  (long)listIterator.dereference() );
			++ix;
		}
	}
	
}
