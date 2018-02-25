package com.geometricmethods.stl.set;

import com.geometricmethods.stl.iterator.BidirectionalIterator;
import com.geometricmethods.stl.iterator.ForwardIterator;

public class SetIterator<V> implements BidirectionalIterator<V>
{
	private SetStlNode<V> node;
	
	public SetIterator()
	{
		this.node = null;
	}

	SetIterator( SetStlNode<V> node )
	{
		this.node = node;
	}
	
	public SetIterator( SetIterator<V> mapIterator )
	{
		this.node = mapIterator.node;
	}
			
	@Override
	public SetIterator<V> clone()
	{
		return new SetIterator<V>( this );
	}

	@Override
	public SetIterator<V> decrement()
	{
		node = node.getPrevious();
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
		return object instanceof SetIterator && node == ((SetIterator<V>)object).node;
	}

	SetStlNode<V> getNode()
	{
		return node;
	}
	
	@Override
	public SetIterator<V> increment()
	{
		node = node.getNext();
		return this;
	}
	
	@Override
	public SetIterator<V> postDecrement()
	{
		SetIterator<V> lag = clone();
		decrement();
		return lag;
	}

	@Override
	public SetIterator<V> postIncrement()
	{
		SetIterator<V> lag = clone();
		increment();
		return lag;
	}

	@Override
	public SetIterator<V> set( ForwardIterator<V> setIterator )
	{
		if (!(setIterator instanceof SetIterator))
		{
			throw new IllegalArgumentException("\"set\" method on a SetIterator takes another SetIterator as its argument");
		}
		this.node = ((SetIterator<V>)setIterator).node;
		return this;
	}
	
	@Override
	public void setValue( V value ) {}
	
}
