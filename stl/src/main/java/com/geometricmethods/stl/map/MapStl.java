package com.geometricmethods.stl.map;

import java.util.Comparator;

import com.geometricmethods.stl.iterator.ForwardIterator;
import com.geometricmethods.stl.map.MapStlNode.NodeType;
import com.geometricmethods.stl.support.Pair;

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
				return comparator.compare( p1.first, p2.first );
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
		if (  comparator.compare( key, node.getKeyValuePair().first ) == 0  )
		{
			return new Pair< MapStlNode<K,V>, Integer >( node, 0 );
		}
		
		MapStlNode<K,V> parent = node.getParent();
		while( parent != null )
		{
			int comp = comparator.compare( key, parent.getKeyValuePair().first );
			if ( comp == 0 )
			{
				return new Pair< MapStlNode<K,V>, Integer >( parent, 0 );
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
			comp = node.getNodeType() == MapStlNode.NodeType.DATA ? comparator.compare( key , node.getKeyValuePair().first ) : node.getNodeType().getValue();
			if ( comp == 0 )
			{
				return new Pair< MapStlNode<K,V>, Integer >( node, 0 );
			}

			parent = node;
			node = comp < 0 ? node.getLeftChild() : node.getRightChild();
		}
		return new Pair< MapStlNode<K,V>, Integer >( parent, comp );		
	}
		
	private Pair< MapStlNode<K,V>, Integer > search( K key )
	{
		return search_down( key, root );
	}
	
	private Pair< MapIterator<K,V>,Boolean > insert( K key, V value, Pair< MapStlNode<K,V>, Integer > nodeIntegerPair )
	{
		MapStlNode<K,V> mapStlNode = nodeIntegerPair.first;
		
		if ( nodeIntegerPair.second == 0 )
		{
			return new Pair< MapIterator<K,V>, Boolean >(  new MapIterator<K,V>( mapStlNode ), false  );
		}
		
		Pair< MapIterator<K,V>, Boolean > retval =
				new Pair< MapIterator<K,V>, Boolean >(   new MapIterator<K,V>(  new MapStlNode<K,V>( key, value, mapStlNode, nodeIntegerPair.second > 0 )  ), true   );

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
		return insert( pair.first, pair.second );
	}

	public MapIterator<K,V> insert( MapIterator<K,V> mapIterator, K key, V value )
	{
		return insert(  key, value, search_up( key, mapIterator.getNode() )  ).first;
	}
	
	public MapIterator<K,V> insert( MapIterator<K,V> mapIterator, Pair<K,V> pair )
	{
		return insert(  pair.first, pair.second, search_up( pair.first, mapIterator.getNode() )  ).first;
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
		if ( nodeIntegerPair.second == 0 )
		{
			erase( nodeIntegerPair.first );
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
		return nodeIntegerPair.second == 0 ? new MapIterator<K,V>( nodeIntegerPair.first) : end();
	}
	
	public int count( K key )
	{
		Pair< MapStlNode<K,V>, Integer > nodeIntegerPair = search( key );
		return nodeIntegerPair.second == 0 ? 1 : 0;
	}
	
	public MapIterator<K,V> lower_bound( K key )
	{
		Pair< MapStlNode<K,V>, Integer > nodeIntegerPair = search( key );
		return new MapIterator<K,V>( nodeIntegerPair.second <= 0 ? nodeIntegerPair.first : nodeIntegerPair.first.getNext() );
	}

	public MapIterator<K,V> upper_bound( K key )
	{
		Pair< MapStlNode<K,V>, Integer > nodeIntegerPair = search( key );
		return new MapIterator<K,V>( nodeIntegerPair.second >= 0 ? nodeIntegerPair.first.getNext() : nodeIntegerPair.first );
	}

	public Pair< MapIterator<K,V>, MapIterator<K,V> > equal_range( K key )
	{
		Pair< MapStlNode<K,V>, Integer > nodeIntegerPair = search( key );
		return nodeIntegerPair.second == 0 ?
				new Pair< MapIterator<K,V>, MapIterator<K,V> >(  new MapIterator<K,V>( nodeIntegerPair.first ), new MapIterator<K,V>( nodeIntegerPair.first.getNext() )  ) :
				new Pair< MapIterator<K,V>, MapIterator<K,V> >( end(), end() );
	}
}
