package com.geometricmethods.stl.sequence;

import java.util.Comparator;

import com.geometricmethods.stl.iterator.ForwardIterator;
import com.geometricmethods.stl.support.Predicate;

public interface ListStl<E> {

	int size();
	
	boolean empty();
		
	ListIterator<E> begin();
	ListIterator<E> end();
	
	ReverseListIterator<E> rbegin();
	ReverseListIterator<E> rend();

	E front();	
	E back();
	
	void push_front( E value );	
	void push_back( E value );

	void pop_front();
	void pop_back();
	
	void clear();

	ListIterator<E> insert( ListIterator<E> pos, E value );
	void insert( ListIterator<E> pos, ForwardIterator<E> first, ForwardIterator<E> last );

	ListIterator<E> erase( ListIterator<E> pos );	
	ListIterator<E> erase( ListIterator<E> first, ListIterator<E> last );

	void remove( E value );
	void remove_if( Predicate<E> p );
	
	void splice( ListIterator<E> iterator, LinkedListStl<E> linkedListStl );
	void splice( ListIterator<E> iterator, LinkedListStl<E> linkedListStl, ListIterator<E> otherIterator );
	void splice( ListIterator<E> iterator, LinkedListStl<E> linkedListStl, ListIterator<E> first, ListIterator<E> last );
	
	void merge( LinkedListStl<E> linkedListStl, Comparator<E> comparator );
	
	void sort( Comparator<E> comparator );
	void sort( ListIterator<E> first, ListIterator<E> last, Comparator<E> comparator );
	
	void swap( LinkedListStl<E> linkedListStl );
	
	boolean isLinear();
	void makeLinear();
	
	boolean isCircular();
	void makeCircular();
	
	void setBegin( ListIterator<E> begin );
	
}
