package com.geometricmethods.stl.iterator;

public interface ForwardIterator<E>
{
	ForwardIterator<E> increment();
	ForwardIterator<E> postIncrement();
	E dereference();
	void setValue( E value );
	ForwardIterator<E> set(ForwardIterator<E> other);
	
	Object clone();
}
