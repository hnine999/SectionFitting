package com.geometricmethods.stl.iterator;

public interface BidirectionalIterator<E> extends ForwardIterator<E>
{
	BidirectionalIterator<E> decrement();
	BidirectionalIterator<E> postDecrement();
}
