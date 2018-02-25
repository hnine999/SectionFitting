package com.geometricmethods.stl.map;

import com.geometricmethods.stl.iterator.ReverseBidirectionalIterator;
import com.geometricmethods.stl.iterator.ForwardIterator;
import com.geometricmethods.stl.support.Pair;

public class ReverseMapIterator<K,V> implements ReverseBidirectionalIterator<Pair<K,V>>
{
	private MapStlNode<K,V> node;

	ReverseMapIterator( MapStlNode<K,V> node )
	{
		this.node = node;
	}
	
	public ReverseMapIterator( ReverseMapIterator<K,V> reverseIterator )
	{
		this.node = reverseIterator.node;
	}
	
	public ReverseMapIterator( MapIterator<K,V> mapIterator )
	{
		this.node = mapIterator.getNode().getPrevious();
	}
			
	@Override
	public MapIterator<K,V> base()
	{
		return new MapIterator<K,V>( node.getNext() );
	}
	
	@Override
	public ReverseMapIterator<K,V> clone()
	{
		return new ReverseMapIterator<K,V>( this );
	}

	@Override
	public ReverseMapIterator<K,V> decrement()
	{
		node = node.getNext();
		return this;
	}

	@Override
	public Pair<K,V> dereference()
	{
		return node.getKeyValuePair();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals( Object object ) {
		return object instanceof ReverseMapIterator && node == ((ReverseMapIterator<K,V>)object).node;
	}

	MapStlNode<K,V> getNode()
	{
		return node;
	}
	
	@Override
	public ReverseMapIterator<K,V> increment()
	{
		node = node.getPrevious();
		return this;
	}
	
	@Override
	public ReverseMapIterator<K,V> postDecrement()
	{
		ReverseMapIterator<K,V> lag = clone();
		decrement();
		return lag;
	}

	@Override
	public ReverseMapIterator<K,V> postIncrement()
	{
		ReverseMapIterator<K,V> lag = clone();
		increment();
		return lag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ReverseMapIterator<K,V> set( ForwardIterator<Pair<K,V>> reverseMapIterator )
	{
		if (!(reverseMapIterator instanceof ReverseMapIterator))
		{
			throw new IllegalArgumentException("\"set\" method on a ReverseMapIterator takes another ReverseMapIterator as its argument");
		}
		this.node = ((ReverseMapIterator<K,V>)reverseMapIterator).node;
		return (ReverseMapIterator<K,V>)reverseMapIterator;
	}
	
	public void set( MapIterator<K,V> mapIterator )
	{
		this.node = mapIterator.getNode();
	}
	
	@Override
	public void setValue( Pair<K,V> value )
	{}
}
