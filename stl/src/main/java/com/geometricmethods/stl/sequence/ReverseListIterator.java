package com.geometricmethods.stl.sequence;

import com.geometricmethods.stl.iterator.ReverseBidirectionalIterator;
import com.geometricmethods.stl.iterator.ForwardIterator;

public class ReverseListIterator<E> implements ReverseBidirectionalIterator<E>
{
	private LinkedListStlNode<E> node;

	ReverseListIterator( LinkedListStlNode<E> node )
	{
		this.node = node;
	}
	
	public ReverseListIterator( ReverseListIterator<E> reverseIterator )
	{
		this.node = reverseIterator.node;
	}
	
	public ReverseListIterator( ListIterator<E> listIterator )
	{
		this.node = listIterator.getNode().getPrevious();
	}
			
	@Override
	public ListIterator<E> base()
	{
		return new ListIterator<E>( node.getNext() );
	}
	
	@Override
	public ReverseListIterator<E> clone()
	{
		return new ReverseListIterator<E>( this );
	}

	@Override
	public ReverseListIterator<E> decrement()
	{
		node = node.getNext();
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
		return object instanceof ReverseListIterator && node == ((ReverseListIterator<E>)object).node;
	}

	LinkedListStlNode<E> getNode()
	{
		return node;
	}
	
	@Override
	public ReverseListIterator<E> increment()
	{
		node = node.getPrevious();
		return this;
	}
	
	@Override
	public ReverseListIterator<E> postDecrement()
	{
		ReverseListIterator<E> lag = clone();
		decrement();
		return lag;
	}

	@Override
	public ReverseListIterator<E> postIncrement()
	{
		ReverseListIterator<E> lag = clone();
		increment();
		return lag;
	}

	@Override
	public ReverseListIterator<E> set( ForwardIterator<E> reverseListIterator )
	{
		if (!(reverseListIterator instanceof ReverseListIterator))
		{
			throw new IllegalArgumentException("\"set\" method on a ReverseListIterator takes another ReverseListIterator as its argument");
		}
		this.node = ((ReverseListIterator<E>)reverseListIterator).node;
		return (ReverseListIterator<E>)reverseListIterator;
	}
	
	public void set( ListIterator<E> listIterator )
	{
		this.node = listIterator.getNode();
	}
	
	@Override
	public void setValue( E value )
	{
		node.setValue( value );
	}
}
