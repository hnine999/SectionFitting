package com.geometricmethods.stl.sequence;

import java.util.Comparator;

import com.geometricmethods.stl.iterator.ForwardIterator;
import com.geometricmethods.stl.support.Predicate;

public class LinkedListStl<E> implements ListStl<E>
{
	
	LinkedListStlBase<E> linkedListStlBase = null;
	private LinkedListStlCircular<E> linkedListStlCircular = null;
	private ListStl<E> listStl = null;

	public LinkedListStl()
	{
		linkedListStlBase = new LinkedListStlBase<E>();
		linkedListStlCircular = new LinkedListStlCircular<E>( linkedListStlBase );
		listStl = linkedListStlBase;
	}	


	public LinkedListStl( ForwardIterator<E> first, ForwardIterator<E> last )
	{
		linkedListStlBase = new LinkedListStlBase<E>( first, last );
		linkedListStlCircular = new LinkedListStlCircular<E>( linkedListStlBase );
		listStl = linkedListStlBase;
	}
	
	public int size()
	{
		return listStl.size();
	}
	
	
	public boolean empty()
	{
		return listStl.empty();		
	}
		
	public ListIterator<E> begin()
	{
		return listStl.begin();
	}
	
	public ListIterator<E> end()
	{
		return listStl.end();
	}
	
	public ReverseListIterator<E> rbegin()
	{
		return listStl.rbegin();
	}
	
	public ReverseListIterator<E> rend()
	{
		return listStl.rend();
	}

	public E front()
	{
		return listStl.front();
	}
	
	public E back()
	{
		return listStl.back();
	}

	public void push_front( E value )
	{
		listStl.push_front( value );
	}
	
	public void push_back( E value )
	{
		listStl.push_back( value );
	}

	public void pop_front()
	{
		listStl.pop_front();
	}
	
	public void pop_back()
	{
		listStl.pop_back();
	}
	
	public void clear()
	{
		listStl.clear();
	}

	public ListIterator<E> insert( ListIterator<E> pos, E value )
	{	
		return listStl.insert( pos, value );
	}

	public void insert( ListIterator<E> pos, ForwardIterator<E> first, ForwardIterator<E> last )
	{
		listStl.insert( pos, first, last );
	}

	public ListIterator<E> erase( ListIterator<E> pos )
	{
		return listStl.erase( pos );
	}
	
	public ListIterator<E> erase( ListIterator<E> first, ListIterator<E> last )
	{
		return listStl.erase( first, last );
	}

	public void remove( E value )
	{
		listStl.remove( value );
	}

	public void remove_if( Predicate<E> p )
	{
		listStl.remove_if( p );
	}
	
	public void splice( ListIterator<E> iterator, LinkedListStl<E> linkedListStl )
	{
		listStl.splice( iterator, linkedListStl );
	}
	
	public void splice( ListIterator<E> iterator, LinkedListStl<E> linkedListStl, ListIterator<E> otherIterator )
	{
		listStl.splice( iterator,  linkedListStl, otherIterator );
	}
	
	public void splice( ListIterator<E> iterator, LinkedListStl<E> linkedListStl, ListIterator<E> first, ListIterator<E> last )
	{
		listStl.splice( iterator,  linkedListStl, first, last );
	}

	
	public void merge( LinkedListStl<E> linkedListStl, Comparator<E> comparator )
	{
		listStl.merge( linkedListStl,  comparator );
	}
	
	
//	private void printSequence( Node<E> first, Node<E> last )
//	{
//		boolean start = true;
//		while( first != last )
//		{
//			if ( start )
//			{
//				start = false;
//			}
//			else
//			{
//				System.out.print( ", " );
//			}
//			System.out.print( first.getValue() );
//			first = first.getNext();
//		}
//	}
	
	public void sort( Comparator<E> comparator )
	{
		listStl.sort( comparator );
	}
			
	public void sort( ListIterator<E> first, ListIterator<E> last, Comparator<E> comparator )
	{
		listStl.sort( first, last, comparator );
	}
	
	public void swap( LinkedListStl<E> linkedListStl )
	{
		{
			LinkedListStlBase<E> temp = linkedListStlBase;
			linkedListStlBase = linkedListStl.linkedListStlBase;
			linkedListStl.linkedListStlBase = temp;
		}
		
		{
			LinkedListStlCircular<E> temp = linkedListStlCircular;
			linkedListStlCircular = linkedListStl.linkedListStlCircular;
			linkedListStl.linkedListStlCircular = temp;
		}
		
		{
			ListStl<E> temp = listStl;
			listStl = linkedListStl.listStl;
			linkedListStl.listStl = temp;
		}
	}
	
	public boolean isLinear() {
		return linkedListStlBase.isLinear();
	}
	
	public void makeLinear()
	{
		linkedListStlBase.makeLinear();
		listStl = linkedListStlBase;
	}

	public boolean isCircular() {
		return linkedListStlBase.isCircular();
	}

	public void makeCircular()
	{
		linkedListStlBase.makeCircular();
		listStl = linkedListStlCircular;
	}
	
	public void setBegin( ListIterator<E> listIterator )
	{
		listStl.setBegin( listIterator );
	}
}
