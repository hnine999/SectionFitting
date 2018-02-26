package com.geometricmethods.stl.map;

import java.util.Comparator;

import com.geometricmethods.stl.iterator.ForwardIterator;
import com.geometricmethods.stl.map.MapStlNode.NodeType;

import org.apache.commons.lang3.tuple.Pair;


public class MapStl<K,V> {

	private MapStlNode<K,V> root = null;
	private MapStlNode<K,V> head = null;
	private MapStlNode<K,V> tail = null;
	private int size = 0;
	private Comparator<K> comparator;

	public MapStl( Comparator<K> comparator )
	{
		this.comparator = comparator;
		root = head = new MapStlNode<K,V>( MapStlNode.NodeType.BEGIN );
		root.setHeight( 1 );
		root.setRightChild(  tail = new MapStlNode<K,V>( MapStlNode.NodeType.END, null, null, root, true )  );
	}

	public MapStl( ForwardIterator<Pair<K,V>> first, ForwardIterator<Pair<K,V>> last, Comparator<K> comparator )
	{
		this( comparator );
		
		@SuppressWarnings("unchecked")
		ForwardIterator<Pair<K,V>> traverse = (ForwardIterator<Pair<K,V>>)first.clone();
		while( traverse != last )
		{
			insert( traverse.dereference() );
			traverse.increment();
		}
		
	}
	
	MapStlNode<K,V> getRoot()
	{
		return root;
	}
	
	public MapIterator<K,V> begin()
	{
		return new MapIterator<K,V>( head.getNext() );
	}
	
	public MapIterator<K,V> end()
	{
		return new MapIterator<K,V>( tail );
	}
	
	public ReverseMapIterator<K,V> rbegin()
	{
		return new ReverseMapIterator<K,V>( tail.getPrevious() );
	}

	public ReverseMapIterator<K,V> rend()
	{
		return new ReverseMapIterator<K,V>( head );
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

	public Comparator<Pair<K,V>> value_comp()
	{
		return new Comparator<Pair<K,V>>()
		{
			public int compare( Pair<K,V> p1, Pair<K,V> p2 )
			{
				return comparator.compare( p1.getLeft(), p2.getLeft() );
			}
		};
	}

	public void swap( MapStl<K,V> other )
	{
		MapStl<K,V> otherNew = new MapStl<K,V>( key_comp() );
		otherNew.insert( begin(), end() );
		
		clear();
		comparator = other.comparator;
		insert( other.begin(), other.end() );

		other.root = otherNew.root;
		other.head = otherNew.head;
		other.tail = otherNew.tail;
		other.size = otherNew.size;
	}
	
	private Pair< MapStlNode<K,V>, Integer > search_up( K key, MapStlNode<K,V> node )
	{
		if ( node == null )
		{
			return null;
		}
		if (  comparator.compare( key, node.getKeyValuePair().getLeft() ) == 0  )
		{
			return Pair.of( node, 0 );
		}
		
		MapStlNode<K,V> parent = node.getParent();
		while( parent != null )
		{
			int comp = comparator.compare( key, parent.getKeyValuePair().getLeft() );
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
	
	private Pair< MapStlNode<K,V>, Integer > search_down( K key, MapStlNode<K,V> node )
	{
		MapStlNode<K,V> parent = null;
		int comp = 0;
		while( node != null )
		{
			comp = node.getNodeType() == MapStlNode.NodeType.DATA ? comparator.compare( key , node.getKeyValuePair().getLeft() ) : node.getNodeType().getValue();
			if ( comp == 0 )
			{
				return Pair.of( node, 0 );
			}

			parent = node;
			node = comp < 0 ? node.getLeftChild() : node.getRightChild();
		}
		return Pair.of( parent, comp );
	}
		
	private Pair< MapStlNode<K,V>, Integer > search( K key )
	{
		return search_down( key, root );
	}
	
	private Pair< MapIterator<K,V>,Boolean > insert( K key, V value, Pair< MapStlNode<K,V>, Integer > nodeIntegerPair )
	{
		MapStlNode<K,V> mapStlNode = nodeIntegerPair.getLeft();
		
		if ( nodeIntegerPair.getRight() == 0 )
		{
			return Pair.of(  new MapIterator<K,V>( mapStlNode ), false  );
		}
		
		Pair< MapIterator<K,V>, Boolean > retval =
				Pair.of(   new MapIterator<K,V>(  new MapStlNode<K,V>( key, value, mapStlNode, nodeIntegerPair.getRight() > 0 )  ), true   );

		mapStlNode.balanceTree();
		if ( root.getParent() != null )
		{
			root = root.getParent();
		}

		++size;
		return retval;		
	}

	public Pair< MapIterator<K,V>, Boolean > insert( K key, V value )
	{
		return insert(  key, value, search( key )  );
	}

	public Pair< MapIterator<K,V>, Boolean > insert( Pair<K,V> pair )
	{
		return insert( pair.getLeft(), pair.getRight() );
	}

	public MapIterator<K,V> insert( MapIterator<K,V> mapIterator, K key, V value )
	{
		return insert(  key, value, search_up( key, mapIterator.getNode() )  ).getLeft();
	}
	
	public MapIterator<K,V> insert( MapIterator<K,V> mapIterator, Pair<K,V> pair )
	{
		return insert(  pair.getLeft(), pair.getRight(), search_up( pair.getLeft(), mapIterator.getNode() )  ).getLeft();
	}

	public void insert( ForwardIterator<Pair<K,V>> first, ForwardIterator<Pair<K,V>> last )
	{
		@SuppressWarnings("unchecked")
		ForwardIterator<Pair<K,V>> traverse = (ForwardIterator<Pair<K,V>>)first.clone();
		while( traverse != last )
		{
			insert( traverse.dereference() );
			traverse.increment();
		}		
	}

	private void erase( MapStlNode<K,V> mapStlNode )
	{
		mapStlNode.delete();
		if (mapStlNode.getNodeType() == NodeType.BEGIN)
		{
			head = mapStlNode;
		}
		if ( root.getParent() != null )
		{
			root = root.getParent();
		}

		--size;		
	}
	
	public void erase( MapIterator<K,V> mapIterator )
	{
		erase( mapIterator.getNode() );
	}
	
	public int erase( K key ) {
		Pair< MapStlNode<K,V>, Integer > nodeIntegerPair = search( key );
		if ( nodeIntegerPair.getRight() == 0 )
		{
			erase( nodeIntegerPair.getLeft() );
			return 1;
		}
		return 0;
	}

	public void erase( MapIterator<K,V> start, MapIterator<K,V> finish ) {
		
		MapStlNode<K,V> startNode = start.getNode();
		MapStlNode<K,V> endNode = finish.getNode();
		while( startNode != endNode )
		{
			MapStlNode<K,V> nextNode = startNode.getNext();
			erase( startNode );
			startNode = nextNode;
		}
	}
	
	public void clear()
	{
		root = head;
		head.setParent(null);
		head.setRightChild( tail );
		tail.setLeftChild( null );
		tail.setParent(head);
		size = 0;
	}

	public MapIterator<K,V> find( K key )
	{
		Pair< MapStlNode<K,V>, Integer > nodeIntegerPair = search( key );
		return nodeIntegerPair.getRight() == 0 ? new MapIterator<K,V>( nodeIntegerPair.getLeft()) : end();
	}
	
	public int count( K key )
	{
		Pair< MapStlNode<K,V>, Integer > nodeIntegerPair = search( key );
		return nodeIntegerPair.getRight() == 0 ? 1 : 0;
	}
	
	public MapIterator<K,V> lower_bound( K key )
	{
		Pair< MapStlNode<K,V>, Integer > nodeIntegerPair = search( key );
		return new MapIterator<K,V>( nodeIntegerPair.getRight() <= 0 ? nodeIntegerPair.getLeft() : nodeIntegerPair.getLeft().getNext() );
	}

	public MapIterator<K,V> upper_bound( K key )
	{
		Pair< MapStlNode<K,V>, Integer > nodeIntegerPair = search( key );
		return new MapIterator<K,V>( nodeIntegerPair.getRight() >= 0 ? nodeIntegerPair.getLeft().getNext() : nodeIntegerPair.getLeft() );
	}

	public Pair< MapIterator<K,V>, MapIterator<K,V> > equal_range( K key )
	{
		Pair< MapStlNode<K,V>, Integer > nodeIntegerPair = search( key );
		return nodeIntegerPair.getRight() == 0 ?
				Pair.of(  new MapIterator<K,V>( nodeIntegerPair.getLeft() ), new MapIterator<K,V>( nodeIntegerPair.getLeft().getNext() )  ) :
				Pair.of( end(), end() );
	}
}
