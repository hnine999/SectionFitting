package com.geometricmethods.stl.iterator;

public interface ReverseBidirectionalIterator<E> extends BidirectionalIterator<E>
{
	BidirectionalIterator<E> base();
}
