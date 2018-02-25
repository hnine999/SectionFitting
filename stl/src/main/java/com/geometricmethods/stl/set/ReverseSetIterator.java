package com.geometricmethods.stl.set;

import com.geometricmethods.stl.iterator.ReverseBidirectionalIterator;
import com.geometricmethods.stl.iterator.ForwardIterator;

public class ReverseSetIterator<V> implements ReverseBidirectionalIterator<V>
{
	private SetStlNode<V> node;

	ReverseSetIterator( SetStlNode<V> node )
	{
		this.node = node;
	}
	
	public ReverseSetIterator( ReverseSetIterator<V> reverseIterator )
	{
		this.node = reverseIterator.node;
	}
	
	public ReverseSetIterator( SetIterator<V> setIterator )
	{
		this.node = setIterator.getNode().getPrevious();
	}
			
	@Override
	public SetIterator<V> base()
	{
		return new SetIterator<V>( node.getNext() );
	}
	
	@Override
	public ReverseSetIterator<V> clone()
	{
		return new ReverseSetIterator<V>( this );
	}

	@Override
	public ReverseSetIterator<V> decrement()
	{
		node = node.getNext();
		return this;
	}

	@Override
	public V dereference()
	{
		return node.getValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals( Object object ) {
		return object instanceof ReverseSetIterator && node == ((ReverseSetIterator<V>)object).node;
	}

	SetStlNode<V> getNode()
	{
		return node;
	}
	
	@Override
	public ReverseSetIterator<V> increment()
	{
		node = node.getPrevious();
		return this;
	}
	
	@Override
	public ReverseSetIterator<V> postDecrement()
	{
		ReverseSetIterator<V> lag = clone();
		decrement();
		return lag;
	}

	@Override
	public ReverseSetIterator<V> postIncrement()
	{
		ReverseSetIterator<V> lag = clone();
		increment();
		return lag;
	}

	@Override
	public ReverseSetIterator<V> set( ForwardIterator<V> reverseSetIterator )
	{
		if (!(reverseSetIterator instanceof ReverseSetIterator))
		{
			throw new IllegalArgumentException("\"set\" method on a ReverseSetIterator takes another ReverseSetIterator as its argument");
		}
		this.node = ((ReverseSetIterator<V>)reverseSetIterator).node;
		return (ReverseSetIterator<V>)reverseSetIterator;
	}
	
	public void set( SetIterator<V> setIterator )
	{
		this.node = setIterator.getNode();
	}
	
	@Override
	public void setValue( V value )
	{}
}
