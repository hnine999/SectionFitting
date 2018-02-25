package com.geometricmethods.stl.map;

import com.geometricmethods.stl.iterator.BidirectionalIterator;
import com.geometricmethods.stl.iterator.ForwardIterator;
import com.geometricmethods.stl.support.Pair;

public class MapIterator<K,V> implements BidirectionalIterator<Pair<K,V>>
{
	private MapStlNode<K,V> node;
	
	public MapIterator()
	{
		this.node = null;
	}

	MapIterator( MapStlNode<K,V> node )
	{
		this.node = node;
	}
	
	public MapIterator( MapIterator<K,V> mapIterator )
	{
		this.node = mapIterator.node;
	}
			
	@Override
	public MapIterator<K,V> clone()
	{
		return new MapIterator<K,V>( this );
	}

	@Override
	public MapIterator<K,V> decrement()
	{
		node = node.getPrevious();
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
		return object instanceof MapIterator && node == ((MapIterator<K,V>)object).node;
	}

	MapStlNode<K,V> getNode()
	{
		return node;
	}
	
	@Override
	public MapIterator<K,V> increment()
	{
		node = node.getNext();
		return this;
	}
	
	@Override
	public MapIterator<K,V> postDecrement()
	{
		MapIterator<K,V> lag = clone();
		decrement();
		return lag;
	}

	@Override
	public MapIterator<K,V> postIncrement()
	{
		MapIterator<K,V> lag = clone();
		increment();
		return lag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MapIterator<K,V> set( ForwardIterator<Pair<K,V>> mapIterator )
	{
		if (!(mapIterator instanceof MapIterator))
		{
			throw new IllegalArgumentException("\"set\" method on a MapIterator takes another MapIterator as its argument");
		}
		this.node = ((MapIterator<K,V>)mapIterator).node;
		return this;
	}
	
	@Override
	public void setValue( Pair<K,V> pair ) {}
	
}
