package com.geometricmethods.stl.set;

import java.util.Comparator;

import com.geometricmethods.stl.iterator.ForwardIterator;
import com.geometricmethods.stl.set.SetStlNode.NodeType;

import org.apache.commons.lang3.tuple.Pair;

public class SetStl<K> {

	private SetStlNode<K> root = null;
	private SetStlNode<K> head = null;
	private SetStlNode<K> tail = null;
	private int size = 0;
	private Comparator<K> comparator;
	
	public SetStl( Comparator<K> comparator )
	{
		this.comparator = comparator;
		root = head = new SetStlNode<>( SetStlNode.NodeType.BEGIN );
		root.setHeight( 2 );
		root.setRightChild(  tail = new SetStlNode<>( SetStlNode.NodeType.END, null, root, true )  );
	}

	public SetStl( ForwardIterator<K> first, ForwardIterator<K> last, Comparator<K> comparator )
	{
		this( comparator );
		
		@SuppressWarnings("unchecked")
		ForwardIterator<K> traverse = (ForwardIterator<K>)first.clone();
		while( !traverse.equals(last) )
		{
			insert( traverse.dereference() );
			traverse.increment();
		}
		
	}
	
	public SetStl(SetStl<K> other)
	{
		this(other.begin(), other.end(), other.key_comp());
	}
	
	SetStlNode<K> getRoot()
	{
		return root;
	}
	
	public SetIterator<K> begin()
	{
		return new SetIterator<>( head.getNext() );
	}
	
	public SetIterator<K> end()
	{
		return new SetIterator<>( tail );
	}
	
	public ReverseSetIterator<K> rbegin()
	{
		return new ReverseSetIterator<>( tail.getPrevious() );
	}

	public ReverseSetIterator<K> rend()
	{
		return new ReverseSetIterator<>( head );
	}

	public int size()
	{
		return size;
	}
	
	public boolean empty()
	{
		return root == head || root == tail;
	}
	
	public Comparator<K> key_comp()
	{
		return comparator;
	}

	public Comparator<K> value_comp()
	{
		return comparator;
	}

	public void swap( SetStl<K> other )
	{
		SetStl<K> otherNew = new SetStl<>( key_comp() );
		otherNew.insert( begin(), end() );
		
		clear();
		comparator = other.comparator;
		insert( other.begin(), other.end() );

		other.root = otherNew.root;
		other.head = otherNew.head;
		other.tail = otherNew.tail;
		other.size = otherNew.size;
	}
	
	private Pair< SetStlNode<K>, Integer > search_up(K key, SetStlNode<K> node )
	{
		if ( node == null )
		{
			return null;
		}
		if (  comparator.compare( key, node.getValue() ) == 0  )
		{
			return Pair.of( node, 0 );
		}
		
		SetStlNode<K> parent = node.getParent();
		while( parent != null )
		{
			int comp = comparator.compare( key, parent.getValue() );
			if ( comp == 0 )
			{
				return Pair.of( parent, 0 );
			}
			
			if ( node.getIsRightChild() && comp > 0 || !node.getIsRightChild() && comp < 0 )
			{
				break;
			}
			node = parent;
			parent = node.getParent();
		}
		return search_down( key, node );
	}
	
	private Pair< SetStlNode<K>, Integer > search_down( K key, SetStlNode<K> node )
	{
		SetStlNode<K> parent = null;
		int comp = 0;
		while( node != null )
		{
			comp = node.getNodeType() == SetStlNode.NodeType.DATA ? comparator.compare( key , node.getValue() ) : node.getNodeType().getValue();
			if ( comp == 0 )
			{
				return Pair.of( node, 0 );
			}

			parent = node;
			node = comp < 0 ? node.getLeftChild() : node.getRightChild();
		}
		return Pair.of( parent, comp );
	}
		
	private Pair< SetStlNode<K>, Integer > search( K key )
	{
		return search_down( key, root );
	}
	
	private Pair< SetIterator<K>,Boolean > insert( K key, Pair< SetStlNode<K>, Integer > nodeIntegerPair )
	{
		SetStlNode<K> mapStlNode = nodeIntegerPair.getLeft();
		
		if ( nodeIntegerPair.getRight() == 0 )
		{
			return Pair.of(  new SetIterator<>( mapStlNode ), false  );
		}
		
		Pair< SetIterator<K>, Boolean > retval =
				Pair.of(   new SetIterator<>(  new SetStlNode<>( key, mapStlNode, nodeIntegerPair.getRight() > 0 )  ), true   );

		mapStlNode.balanceTree();
		if ( root.getParent() != null )
		{
			root = root.getParent();
		}

		++size;
		return retval;		
	}

	public Pair< SetIterator<K>, Boolean > insert( K key )
	{
		return insert(  key, search( key )  );
	}

	public SetIterator<K> insert( SetIterator<K> mapIterator, K key )
	{
		return insert(  key, search_up( key, mapIterator.getNode() )  ).getLeft();
	}
	
	public void insert( ForwardIterator<K> first, ForwardIterator<K> last )
	{
		@SuppressWarnings("unchecked")
		ForwardIterator<K> traverse = (ForwardIterator<K>)first.clone();
		while( traverse != last )
		{
			insert( traverse.dereference() );
			traverse.increment();
		}		
	}

	private void erase( SetStlNode<K> setStlNode )
	{
		setStlNode.delete();
		if (setStlNode.getNodeType() == NodeType.BEGIN)
		{
			head = setStlNode;
		}
		if ( root.getParent() != null )
		{
			root = root.getParent();
		}

		--size;		
	}
	
	public void erase( SetIterator<K> setIterator )
	{
		erase( setIterator.getNode() );
	}
	
	public int erase( K key ) {
		Pair< SetStlNode<K>, Integer > nodeIntegerPair = search( key );
		if ( nodeIntegerPair.getRight() == 0 )
		{
			erase( nodeIntegerPair.getLeft() );
			return 1;
		}
		return 0;
	}

	public void erase( SetIterator<K> start, SetIterator<K> finish ) {
		
		SetStlNode<K> startNode = start.getNode();
		SetStlNode<K> endNode = finish.getNode();
		while( startNode != endNode )
		{
			SetStlNode<K> nextNode = startNode.getNext();
			erase( startNode );
			startNode = nextNode;
		}
	}
	
	public void clear()
	{
		root = head;
		head.setParent(null);
		head.setRightChild( tail );
		head.setHeight(2);

		tail.setParent(head);
		tail.setLeftChild( null );
		tail.setHeight(1);

		size = 0;
	}

	public SetIterator<K> find( K key )
	{
		Pair< SetStlNode<K>, Integer > nodeIntegerPair = search( key );
		return nodeIntegerPair.getRight() == 0 ? new SetIterator<>( nodeIntegerPair.getLeft()) : end();
	}
	
	public int count( K key )
	{
		Pair< SetStlNode<K>, Integer > nodeIntegerPair = search( key );
		return nodeIntegerPair.getRight() == 0 ? 1 : 0;
	}
	
	public SetIterator<K> lower_bound( K key )
	{
		Pair< SetStlNode<K>, Integer > nodeIntegerPair = search( key );
		return new SetIterator<K>( nodeIntegerPair.getRight() >= 0 ? nodeIntegerPair.getLeft() : nodeIntegerPair.getLeft().getNext() );
	}

	public SetIterator<K> upper_bound( K key )
	{
		Pair< SetStlNode<K>, Integer > nodeIntegerPair = search( key );
		return new SetIterator<K>( nodeIntegerPair.getRight() <= 0 ? nodeIntegerPair.getLeft().getNext() : nodeIntegerPair.getLeft() );
	}

	public Pair< SetIterator<K>, SetIterator<K> > equal_range( K key )
	{
		Pair< SetStlNode<K>, Integer > nodeIntegerPair = search( key );
		return nodeIntegerPair.getRight() == 0 ?
		       Pair.of(  new SetIterator<K>( nodeIntegerPair.getLeft() ), new SetIterator<K>( nodeIntegerPair.getLeft().getNext() )  ) :
		       Pair.of( end(), end() );
	}
}
