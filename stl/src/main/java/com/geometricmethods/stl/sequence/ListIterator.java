package com.geometricmethods.stl.sequence;

import com.geometricmethods.stl.iterator.BidirectionalIterator;
import com.geometricmethods.stl.iterator.ForwardIterator;

public class ListIterator<E> implements BidirectionalIterator<E>
{
	private LinkedListStlNode<E> node;
	
	public ListIterator()
	{
		this.node = null;
	}

	ListIterator( LinkedListStlNode<E> node )
	{
		this.node = node;
	}
	
	public ListIterator( ListIterator<E> listIterator )
	{
		this.node = listIterator.node;
	}
			
	@Override
	public ListIterator<E> clone()
	{
		return new ListIterator<E>( this );
	}

	@Override
	public ListIterator<E> decrement()
	{
		node = node.getPrevious();
		return this;
	}

	@Override
	public E dereference()
	{
		return node.getValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals( Object object ) {
		return object instanceof ListIterator && node == ((ListIterator<E>)object).node;
	}

	LinkedListStlNode<E> getNode()
	{
		return node;
	}
	
	@Override
	public ListIterator<E> increment()
	{
		node = node.getNext();
		return this;
	}
	
	@Override
	public ListIterator<E> postDecrement()
	{
		ListIterator<E> lag = clone();
		decrement();
		return lag;
	}

	@Override
	public ListIterator<E> postIncrement()
	{
		ListIterator<E> lag = clone();
		increment();
		return lag;
	}

	@Override
	public ListIterator<E> set( ForwardIterator<E> listIterator )
	{
		if (!(listIterator instanceof ListIterator))
		{
			throw new IllegalArgumentException("\"set\" method on a ListIterator takes another ListIterator as its argument");
		}
		this.node = ((ListIterator<E>)listIterator).node;
		return this;
	}
	
	@Override
	public void setValue( E value )
	{
		node.setValue(value );
	}	
	
}
