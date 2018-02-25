package com.geometricmethods.stl.sequence;


public class LinkedListStlNode<E>
{
	private E value = null;
	private LinkedListStlNode<E> next = null;
	private LinkedListStlNode<E> previous = null;
	
	public LinkedListStlNode()
	{}
	
	public LinkedListStlNode( E value )
	{
		this.value = value;
	}
	
	public LinkedListStlNode( E value, LinkedListStlNode<E> previous, LinkedListStlNode<E> next)
	{
		this.value = value;
		this.next = next;
		this.previous = previous;
	}
	
	public E getValue()
	{
		return value;
	}
	public void setValue( E value )
	{
		this.value = value;
	}
	
	public LinkedListStlNode<E> getNext()
	{
		return next;
	}
	public void setNext( LinkedListStlNode<E> next )
	{
		this.next = next;
	}
	
	public LinkedListStlNode<E> getPrevious()
	{
		return previous;
	}
	public void setPrevious( LinkedListStlNode<E> previous )
	{
		this.previous = previous;
	}
}
