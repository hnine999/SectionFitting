package com.geometricmethods.stl.sequence;

import java.util.Comparator;

import com.geometricmethods.stl.iterator.ForwardIterator;
import com.geometricmethods.stl.support.Predicate;

public class LinkedListStlCircular<E> implements ListStl<E> {

	private LinkedListStlBase<E> linkedListStlBase = null;
	
	public LinkedListStlCircular( LinkedListStlBase<E> linkedListStlBase ) {
		this.linkedListStlBase = linkedListStlBase;
	}
	
	@Override
	public int size()
	{
		return linkedListStlBase.sizeCircular();
	}
	
	@Override
	public boolean empty()
	{
		return linkedListStlBase.empty();
	}
	
	@Override
	public ListIterator<E> begin()
	{
		return linkedListStlBase.begin();
	}
	
	@Override
	public ListIterator<E> end()
	{
		return linkedListStlBase.endCircular();
	}
	
	@Override
	public ReverseListIterator<E> rbegin()
	{
		return linkedListStlBase.rbegin();
	}
	
	@Override
	public ReverseListIterator<E> rend()
	{
		return linkedListStlBase.rendCircular();
	}
	
	@Override
	public E front()
	{
		return linkedListStlBase.front();
	}
	
	@Override
	public E back()
	{
		return linkedListStlBase.back();
	}

	@Override
	public void push_front( E value )
	{
		linkedListStlBase.push_frontCircular( value );
	}
	
	@Override
	public void push_back( E value )
	{
		linkedListStlBase.push_backCircular( value );
	}

	@Override
	public void pop_front()
	{
		linkedListStlBase.pop_frontCircular();
	}
	
	@Override
	public void pop_back()
	{
		linkedListStlBase.pop_backCircular();
	}
	
	@Override
	public void clear()
	{
		linkedListStlBase.clear();
	}
	
	@Override
	public ListIterator<E> insert( ListIterator<E> pos, E value )
	{
		return linkedListStlBase.insertCircular( pos, value );
	}

	@Override
	public void insert( ListIterator<E> pos, ForwardIterator<E> first, ForwardIterator<E> last )
	{
		linkedListStlBase.insertCircular( pos, first, last );
	}
	
	@Override
	public ListIterator<E> erase( ListIterator<E> pos )
	{
		return linkedListStlBase.eraseCircular( pos );
	}

	@Override
	public ListIterator<E> erase( ListIterator<E> first, ListIterator<E> last )
	{
		return linkedListStlBase.eraseCircular( first, last );
	}
	
	@Override
	public void remove( E value )
	{
		linkedListStlBase.removeCircular( value );
	}
	
	@Override
	public void remove_if( Predicate<E> p )
	{
		linkedListStlBase.remove_ifCircular( p );
	}

	@Override
	public void splice( ListIterator<E> iterator, LinkedListStl<E> linkedListStl )
	{
		linkedListStlBase.spliceCircular( iterator, linkedListStl );
	}
	
	@Override
	public void splice( ListIterator<E> iterator, LinkedListStl<E> linkedListStl, ListIterator<E> otherIterator )
	{
		linkedListStlBase.spliceCircular( iterator, linkedListStl, otherIterator );
	}

	@Override
	public void splice( ListIterator<E> iterator, LinkedListStl<E> linkedListStl, ListIterator<E> first, ListIterator<E> last )
	{
		linkedListStlBase.spliceCircular( iterator, linkedListStl, first, last );
	}
	
	@Override
	public void merge( LinkedListStl<E> linkedListStl, Comparator<E> comparator )
	{
		linkedListStlBase.mergeCircular( linkedListStl, comparator );
	}
	
	@Override
	public void sort( Comparator<E> comparator )
	{
		linkedListStlBase.sortCircular( comparator );
	}

	@Override
	public void sort( ListIterator<E> first, ListIterator<E> last, Comparator<E> comparator )
	{
		linkedListStlBase.sortCircular( first, last, comparator );
	}

	@Override
	public void swap( LinkedListStl<E> linkedListStl )
	{
		linkedListStlBase.swap( linkedListStl );
	}

	@Override
	public boolean isLinear()
	{
		return linkedListStlBase.isLinear();
	}
	
	@Override
	public void makeLinear()
	{
		linkedListStlBase.makeLinear();
	}

	@Override
	public boolean isCircular()
	{
		return linkedListStlBase.isCircular();
	}

	@Override
	public void makeCircular()
	{
		linkedListStlBase.makeCircular();
	}
	
	@Override
	public void setBegin( ListIterator<E> listIterator )
	{
		linkedListStlBase.setBeginCircular( listIterator );
	}
}
