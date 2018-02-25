package com.geometricmethods.stl.sequence;

import java.util.Comparator;

import com.geometricmethods.stl.iterator.ForwardIterator;
import com.geometricmethods.stl.support.Predicate;

public class LinkedListStlBase<E> implements ListStl<E>
{
	
	private LinkedListStlNode<E> head;
	private LinkedListStlNode<E> tail;

	private int size = 0;
	private boolean sizeStale = false;

	public LinkedListStlBase()
	{
		head = new LinkedListStlNode<E>();
		tail = new LinkedListStlNode<E>();
		head.setNext( tail );
		tail.setPrevious( head );
	}	


	public LinkedListStlBase( ForwardIterator<E> first, ForwardIterator<E> last )
	{
		this();
		
		@SuppressWarnings("unchecked")
		ForwardIterator<E> traverse = (ForwardIterator<E>)first.clone();
		while(  !traverse.equals( last )  )
		{
			push_back( traverse.dereference() );
			traverse.increment();
		}
	}
	
	@Override
	public int size()
	{
		if ( sizeStale )
		{
			sizeStale = false;
			size = 0;
			
			if ( !empty() )
			{	
				LinkedListStlNode<E> traverse = head.getNext();
				LinkedListStlNode<E> endNode = tail.getPrevious().getNext();
				do
				{
					++size;
					traverse = traverse.getNext();
				}
				while( traverse != endNode );
			}
		}
		
		return size;
	}
	
	public int sizeCircular()
	{
		makeLinear();
		int retval = size();
		makeCircular();
		return retval;
	}

	@Override
	public boolean empty()
	{
		return head.getNext() == tail;		
	}
	
	@Override
	public ListIterator<E> begin()
	{
		return new ListIterator<E>( head.getNext() );
	}

	@Override
	public ListIterator<E> end()
	{
		return new ListIterator<E>( tail );
	}

	public ListIterator<E> endCircular()
	{
		return new ListIterator<E>( head.getNext() );
	}
	
	@Override
	public ReverseListIterator<E> rbegin()
	{
		return new ReverseListIterator<E>( tail.getPrevious() );
	}
	
	@Override
	public ReverseListIterator<E> rend()
	{
		return new ReverseListIterator<E>( head );
	}
	
	public ReverseListIterator<E> rendCircular()
	{
		return new ReverseListIterator<E>( tail.getPrevious() );		
	}

	@Override
	public E front()
	{
		if ( empty() )
		{
			throw new IllegalStateException();
		}
		return head.getNext().getValue();
	}
	
	@Override
	public E back()
	{
		if ( empty() )
		{
			throw new IllegalStateException();
		}
		return tail.getPrevious().getValue();
	}

	@Override
	public void push_front( E value )
	{
		LinkedListStlNode<E> node = new LinkedListStlNode<E>( value, head, head.getNext() );
		head.getNext().setPrevious( node );
		head.setNext( node );
		++size;
	}
	
	public void push_frontCircular( E value )
	{
		makeLinear();
		push_front( value );
		makeCircular();
	}
	
	@Override
	public void push_back( E value )
	{
		LinkedListStlNode<E> node = new LinkedListStlNode<E>( value, tail.getPrevious(), tail );
		tail.getPrevious().setNext( node );
		tail.setPrevious( node );
		++size;
	}

	public void push_backCircular( E value )
	{
		makeLinear();
		push_back( value );
		makeCircular();
	}

	@Override
	public void pop_front()
	{
		if ( empty() )
		{
			throw new IllegalStateException();
		}
		LinkedListStlNode<E> node = head.getNext().getNext();
		node.setPrevious( head );
		head.setNext( node );
		--size;
	}
	
	public void pop_frontCircular()
	{
		makeLinear();
		pop_front();
		makeCircular();
	}
	
	@Override
	public void pop_back()
	{
		if ( empty() )
		{
			throw new IllegalStateException();
		}
		LinkedListStlNode<E> node = tail.getPrevious().getPrevious();
		node.setNext( tail );
		tail.setPrevious( node );
		--size;
	}
	
	public void pop_backCircular()
	{
		makeLinear();
		pop_back();
		makeCircular();
	}
	
	@Override
	public void clear()
	{
		head.setNext( tail );
		tail.setPrevious( head );
		size = 0;
		sizeStale = false;
	}


	private LinkedListStlNode<E> insert( LinkedListStlNode<E> node, E value )
	{
		LinkedListStlNode<E> newNode = new LinkedListStlNode<E>( value, node.getPrevious(), node );
		node.getPrevious().setNext( newNode );
		node.setPrevious( newNode );
		return newNode;
	}
	
	@Override
	public ListIterator<E> insert( ListIterator<E> pos, E value )
	{	
		LinkedListStlNode<E> posNode = pos.getNode();
		LinkedListStlNode<E> newNode = insert( posNode, value );
		++size;
		
		if ( head.getNext() == posNode )
		{
			head.setNext( newNode );
		}
		return new ListIterator<E>( newNode );
	}

	public ListIterator<E> insertCircular( ListIterator<E> pos, E value )
	{
		makeLinear();
		ListIterator<E> retval = insert( pos, value );
		makeCircular();
		return retval;
	}

	@Override
	public void insert( ListIterator<E> pos, ForwardIterator<E> first, ForwardIterator<E> last )
	{
		LinkedListStlNode<E> posNode = pos.getNode();

		@SuppressWarnings("unchecked")
		ForwardIterator<E> traverse = (ForwardIterator<E>)first.clone();
		LinkedListStlNode<E> newNode = null;
		while( !traverse.equals( last ) )
		{
			newNode = insert( posNode, traverse.dereference() );
			traverse.increment();
			++size;
		}
		if ( posNode == head.getNext() ) {
			head.setNext( newNode );
		}
	}
	
	public void insertCircular( ListIterator<E> pos, ForwardIterator<E> first, ForwardIterator<E> last )
	{
		makeLinear();
		insert( pos, first, last );
		makeCircular();
	}
	
	private void erase(LinkedListStlNode<E> first, LinkedListStlNode<E> last)
	{
		first.getPrevious().setNext( last );
		last.setPrevious( first.getPrevious() );
	}

	@Override
	public ListIterator<E> erase( ListIterator<E> pos )
	{
		ListIterator<E> next = pos.clone().increment();
		erase( pos.getNode(), next.getNode() );
		--size;
		return next;
	}

	public ListIterator<E> eraseCircular( ListIterator<E> pos )
	{
		makeLinear();
		ListIterator<E> retval = erase( pos );
		makeCircular();
		return retval;
	}

	@Override
	public ListIterator<E> erase( ListIterator<E> first, ListIterator<E> last )
	{
		erase( first.getNode(), last.getNode() );
		sizeStale = true;
		
		return last;
	}

	public ListIterator<E> eraseCircular( ListIterator<E> first, ListIterator<E> last )
	{
		setBeginCircular( first.getNode() );		
		makeLinear();
		ListIterator<E> retval = erase( first, last );
		makeCircular();
		return retval;		
	}
	
	@Override
	public void remove( E value )
	{
		LinkedListStlNode<E> traverse = head.getNext();
		while( traverse != tail )
		{
			LinkedListStlNode<E> next = traverse.getNext();
			if (  value == null && traverse.getValue() == null || value != null && value.equals( traverse.getValue() )  )
			{
				traverse.getPrevious().setNext( next );
				next.setPrevious( traverse.getPrevious() );
				--size;
			}
			traverse = next;
		}
	}

	public void removeCircular( E value )
	{
		makeLinear();
		remove( value );
		makeCircular();
	}
	
	@Override
	public void remove_if( Predicate<E> p )
	{
		LinkedListStlNode<E> traverse = this.head.getNext();
		while( traverse != tail )
		{
			LinkedListStlNode<E> next = traverse.getNext();
			if (  p.evaluate( traverse.getValue() )  )
			{
				traverse.getPrevious().setNext( next );
				next.setPrevious( traverse.getPrevious() );
				--size;
			}
			traverse = next;
		}
	}

	public void remove_ifCircular( Predicate<E> p )
	{
		makeLinear();
		remove_if( p );
		makeCircular();
	}
	
	private void splice( LinkedListStlNode<E> insert, LinkedListStlNode<E> first, LinkedListStlNode<E> last )
	{
		LinkedListStlNode<E> lastPrevious = last.getPrevious();
		
		first.getPrevious().setNext( last );
		last.setPrevious( first.getPrevious() );
		
		insert.getPrevious().setNext( first );
		first.setPrevious( insert.getPrevious() );
		
		lastPrevious.setNext( insert );
		insert.setPrevious( lastPrevious );
	}

	@Override
	public void splice( ListIterator<E> iterator, LinkedListStl<E> linkedListStl )
	{
		linkedListStl.makeLinear();
		LinkedListStlBase<E> linkedListStlBase = linkedListStl.linkedListStlBase;
		splice( iterator.getNode(), linkedListStlBase.head.getNext(), linkedListStlBase.tail );

		linkedListStlBase.size = 0;
		linkedListStlBase.sizeStale = false;
		
		sizeStale = true;
	}
	
	public void spliceCircular( ListIterator<E> iterator, LinkedListStl<E> linkedListStl )
	{
		makeLinear();
		splice( iterator, linkedListStl );
		makeCircular();
	}
	
	public void splice( ListIterator<E> iterator, LinkedListStl<E> linkedListStl, ListIterator<E> otherIterator )
	{
		if ( otherIterator.equals( iterator ) || otherIterator.getNode().getPrevious() == iterator.getNode() )
		{
			return;
		}

		boolean isOtherCirc = linkedListStl.isCircular();
		linkedListStl.makeLinear();
		splice( iterator.getNode(), otherIterator.getNode(), otherIterator.getNode().getNext() );
		++size;
		--linkedListStl.linkedListStlBase.size;
		if ( isOtherCirc )
		{
			linkedListStl.makeCircular();
		}
	}
	
	public void spliceCircular( ListIterator<E> iterator, LinkedListStl<E> linkedListStl, ListIterator<E> otherIterator )
	{
		makeLinear();
		splice( iterator, linkedListStl, otherIterator );
		makeCircular();
	}
	
	public void splice( ListIterator<E> iterator, LinkedListStl<E> linkedListStl, ListIterator<E> first, ListIterator<E> last )
	{
		LinkedListStlBase<E> linkedListStlBase = linkedListStl.linkedListStlBase;
		
		boolean isOtherCirc = linkedListStl.isCircular();
		if ( isOtherCirc )
		{
			linkedListStlBase.setBeginCircular( first.getNode().getPrevious() );
			linkedListStl.makeLinear();
		}

		splice( iterator.getNode(), first.getNode(), last.getNode() );
		sizeStale = true;

		linkedListStlBase.sizeStale = true;
		if ( isOtherCirc )
		{
			linkedListStl.makeCircular();
		}
	}

	public void spliceCircular( ListIterator<E> iterator, LinkedListStl<E> linkedListStl, ListIterator<E> first, ListIterator<E> last )
	{
		makeLinear();
		splice( iterator, linkedListStl, first, last );
		makeCircular();
	}

	private void merge( LinkedListStlNode<E> first1, LinkedListStlNode<E> last1, LinkedListStlNode<E> first2, LinkedListStlNode<E> last2, Comparator<E> comparator )
	{

		while(  !first2.equals( last2 )  )
		{
			while (  !first1.equals( last1 ) && comparator.compare( first1.getValue(), first2.getValue() ) <= 0  )
			{
				first1 = first1.getNext();
			}

			if (  first1.equals( last1 )  )
			{
				splice( last1, first2, last2 );
				break;
			}
			
			LinkedListStlNode<E> saveFirst2 = first2;
			while (  !first2.equals( last2 ) && comparator.compare( first1.getValue(), first2.getValue() ) > 0  )
			{
				first2 = first2.getNext();
			}
			
			splice( first1, saveFirst2, first2 );
		}
		
	}
	
	public void merge( LinkedListStl<E> linkedListStl, Comparator<E> comparator )
	{
		linkedListStl.makeLinear();
		merge( head.getNext(), tail, linkedListStl.linkedListStlBase.head.getNext(), linkedListStl.linkedListStlBase.tail, comparator );
	}
	
	public void mergeCircular( LinkedListStl<E> linkedListStl, Comparator<E> comparator )
	{
		makeLinear();
		merge( linkedListStl, comparator );
		makeCircular();
	}
	
	
//	private void printSequence( Node<E> first, Node<E> last )
//	{
//		boolean start = true;
//		while( first != last )
//		{
//			if ( start )
//			{
//				start = false;
//			}
//			else
//			{
//				System.out.print( ", " );
//			}
//			System.out.print( first.getValue() );
//			first = first.getNext();
//		}
//	}
	
	public void sort( Comparator<E> comparator )
	{
		sort( head.getNext(), size(), comparator );
	}

	public void sortCircular( Comparator<E> comparator )
	{
		makeLinear();
		sort( head.getNext(), size(), comparator );
		makeCircular();
	}
	
	private LinkedListStlNode<E> sort( LinkedListStlNode<E> start, int number, Comparator<E> comparator )
	{
		if ( number == 1 )
		{
			return start.getNext();
		}
		
		int number1 = number/2;
		int number2 = number - number1;
		
		LinkedListStlNode<E> temp = start.getPrevious();
		LinkedListStlNode<E> nextStart = sort( start, number1, comparator );
		start = temp.getNext();

		temp = insert( nextStart, null );
		LinkedListStlNode<E> followingStart = sort( nextStart, number2, comparator );
		nextStart = temp.getNext();

//		System.out.println(); System.out.println();
		merge( start, temp, nextStart, followingStart, comparator );
		
		temp.getPrevious().setNext( temp.getNext() );
		temp.getNext().setPrevious( temp.getPrevious() );

		return followingStart;
	}
		
	public void sort( ListIterator<E> first, ListIterator<E> last, Comparator<E> comparator )
	{
		int size = 0;
		LinkedListStlNode<E> traverseNode = first.getNode();
		LinkedListStlNode<E> lastNode = last.getNode();

		while( traverseNode != lastNode )
		{
			traverseNode = traverseNode.getNext();
			++size;
		}
		
		sort( first.getNode(), size, comparator );
	}
	
	public void sortCircular( ListIterator<E> first, ListIterator<E> last, Comparator<E> comparator )
	{
		int size = 0;
		LinkedListStlNode<E> beginNode = head.getNext();
		LinkedListStlNode<E> traverseNode = first.getNode();
		LinkedListStlNode<E> lastNode = last.getNode();

		while( traverseNode != lastNode )
		{
			if ( traverseNode == beginNode )
			{
				setBeginCircular( first.getNode() );
				break;
			}
			traverseNode = traverseNode.getNext();
			++size;
		}
		while( traverseNode != lastNode )
		{
			traverseNode = traverseNode.getNext();
			++size;
		}
		
		makeLinear();
		sort( first.getNode(), size, comparator );
		makeCircular();
		setBeginCircular( beginNode );
	}

	public void swap( LinkedListStl<E> linkedListStl )
	{
		LinkedListStlBase<E> linkedListStlBase = linkedListStl.linkedListStlBase;
		{
			LinkedListStlNode<E> temp = head;
			head = linkedListStlBase.head;
			linkedListStlBase.head = temp;
		
			temp = tail;
			tail = linkedListStlBase.tail;
			linkedListStlBase.tail = temp;
		}

		{
			int temp = size;
			size = linkedListStlBase.size;
			linkedListStlBase.size = temp;
		}
		
		{
			boolean temp = sizeStale;
			sizeStale = linkedListStlBase.sizeStale;
			linkedListStlBase.sizeStale = temp;
		}
	}
	
	public void makeCircular()
	{
		tail.getPrevious().setNext( head.getNext() );
		head.getNext().setPrevious( tail.getPrevious() );
	}
	
	public void makeLinear()
	{
		tail.getPrevious().setNext( tail );
		head.getNext().setPrevious( head );
	}

	private void setBeginCircular( LinkedListStlNode<E> node )
	{
		head.setNext( node );
		tail.setPrevious( node.getPrevious() );
	}

	public void setBegin( ListIterator<E> listIterator )
	{
		makeCircular();
		setBeginCircular( listIterator.getNode() );
		makeLinear();
	}
	
	public void setBeginCircular( ListIterator<E> listIterator )
	{
		setBeginCircular( listIterator.getNode() );
	}

	public boolean isLinear() {
		return tail.getPrevious().getNext() == tail;
	}
	
	public boolean isCircular() {
		return tail.getPrevious().getNext() == head.getNext();
	}
}
