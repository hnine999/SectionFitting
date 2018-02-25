package com.geometricmethods.stl.map;

import com.geometricmethods.stl.support.Pair;


public class MapStlNode<K,V>
{
	public enum NodeType
	{
		BEGIN(1),
		DATA(0),
		END(-1);
		
		private int value;
		
		NodeType( int value )
		{
			this.value = value;
		}
		
		int getValue()
		{
			return value;
		}
	}
	
	private Pair<K,V> keyValuePair = null;
	private MapStlNode<K,V> parent = null;
	private MapStlNode<K,V> leftChild = null;
	private MapStlNode<K,V> rightChild = null;
	
	private NodeType nodeType;
	private int height = 1;
	private boolean isRightChild = false;

	MapStlNode( NodeType nodeType )
	{
		this.nodeType = nodeType;
	}
	
	public MapStlNode()
	{
		this( NodeType.DATA );
	}
	
	MapStlNode( NodeType nodeType, K key, V value )
	{
		this.nodeType = nodeType;
		this.keyValuePair = new Pair<K,V>( key, value );
	}
	
	public MapStlNode( K key, V value )
	{
		this( NodeType.DATA );
	}
	
	MapStlNode( NodeType nodeType, K key, V value, MapStlNode<K,V> parent, boolean isRightChild )
	{
		this( nodeType, key, value );
		
		this.parent = parent;
		this.isRightChild = isRightChild;
		
		if ( isRightChild )
		{
			parent.setRightChild( this );
		}
		else
		{
			parent.setLeftChild( this );
		}
	}

	public MapStlNode( K key, V value, MapStlNode<K,V> parent, boolean isRightChild )
	{
		this( NodeType.DATA, key, value, parent, isRightChild );
	}
	
	public Pair<K,V> getKeyValuePair()
	{
		return keyValuePair;
	}

	private void setKeyValuePair( Pair<K,V> keyValuePair )
	{
		this.keyValuePair = keyValuePair;
	}
	
	public MapStlNode<K,V> getParent()
	{
		return parent;
	}
	public void setParent( MapStlNode<K,V> parent )
	{
		this.parent = parent;
	}
	
	public MapStlNode<K,V> getLeftChild()
	{
		return leftChild;
	}
	public void setLeftChild( MapStlNode<K,V> leftChild )
	{
		this.leftChild = leftChild;
	}

	public MapStlNode<K,V> getRightChild()
	{
		return rightChild;
	}
	public void setRightChild( MapStlNode<K,V> rightChild )
	{
		this.rightChild = rightChild;
	}
	
	public int getHeight()
	{
		return height;
	}
	public int getRightChildHeight()
	{
		return getRightChild() == null ? 0 : rightChild.getHeight();
	}
	public int getLeftChildHeight()
	{
		return getLeftChild() == null ?  0 : leftChild.getHeight();
	}
	
	void setIsRightChild( boolean isRightChild )
	{
		this.isRightChild = isRightChild;
	}
	boolean getIsRightChild()
	{
		return isRightChild;
	}
	
	public int setHeight()
	{
		height = 1;
		if ( leftChild != null )
		{
			height = leftChild.height + 1;
		}
		if ( rightChild != null )
		{
			height = Math.max( height, rightChild.height + 1 );
		}
		return height;
	}
	
	void setHeight( int height )
	{
		this.height = height;
	}
	
	private void setNodeType( NodeType nodeType )
	{
		this.nodeType = nodeType; 
	}
	
	public NodeType getNodeType()
	{
		return this.nodeType;
	}
	
	public void rotateRight()
	{
		MapStlNode<K,V> pivot = leftChild;

		pivot.parent = this.parent;
		pivot.isRightChild = this.isRightChild;
		if ( pivot.parent != null )
		{
			if ( pivot.isRightChild )
			{
				pivot.parent.rightChild = pivot;
			}
			else
			{
				pivot.parent.leftChild = pivot;
			}
		}
		
		this.leftChild = pivot.rightChild;
		if ( this.leftChild != null )
		{
			this.leftChild.isRightChild = false;
			this.leftChild.parent = this;
		}

		this.parent = pivot;
		this.isRightChild = true;
		pivot.rightChild = this;
		
		this.setHeight();
	}
	
	public void rotateLeft()
	{
		MapStlNode<K,V> pivot = rightChild;

		pivot.parent = this.parent;
		pivot.isRightChild = this.isRightChild;
		if ( pivot.parent != null )
		{
			if ( pivot.isRightChild )
			{
				pivot.parent.rightChild = pivot;
			}
			else
			{
				pivot.parent.leftChild = pivot;
			}
		}
		
		this.rightChild = pivot.leftChild;
		if ( this.rightChild != null )
		{
			this.rightChild.isRightChild = true;
			this.rightChild.parent = this;
		}

		this.parent = pivot;
		this.isRightChild = false;
		pivot.leftChild = this;		
		
		this.setHeight();
		pivot.setHeight();
	}

	public void balance()
	{
		int difference = getLeftChildHeight() - getRightChildHeight();
		if ( difference > 1 )
		{
			if ( leftChild.getLeftChildHeight() - leftChild.getRightChildHeight() < 0 )
			{
				leftChild.rotateLeft();
			}
			rotateRight();
		}
		
		if ( difference < -1 )
		{
			if ( rightChild.getLeftChildHeight() - rightChild.getRightChildHeight() > 0 )
			{
				rightChild.rotateRight();
			}
			rotateLeft();
		}
	}

	public void balanceTree()
	{
		MapStlNode<K,V> mapStlNode = this;

		while( mapStlNode != null )
		{
			mapStlNode.balance();
			int oldHeight = mapStlNode.getHeight();
			if ( oldHeight == mapStlNode.setHeight() )
			{
				break;
			}
			mapStlNode = mapStlNode.getParent();
		}
	}
	
	public MapStlNode<K,V> getNext()
	{
		MapStlNode<K,V> node = getRightChild();
		if ( node == null )
		{
			node = this;
			while( node != null && node.isRightChild )
			{
				node = node.getParent();
			}
			if ( node != null )
			{
				node = node.getParent();
			}
		}
		else
		{
			MapStlNode<K,V> nextNode = node.getLeftChild();
			while( nextNode != null )
			{
				node = nextNode;
				nextNode = node.getLeftChild();
			}
		}
		return node;
	}

	public MapStlNode<K,V> getPrevious()
	{
		MapStlNode<K,V> node = getLeftChild();
		if ( node == null )
		{
			node = this;
			while( node != null && !node.isRightChild )
			{
				node = node.getParent();
			}
			node = node.getParent();
		}
		else
		{
			MapStlNode<K,V> nextNode = node.getRightChild();
			while( nextNode != null )
			{
				node = nextNode;
				nextNode = node.getRightChild();
			}
		}
		return node;
	}
	
	private void moveUpSingleChild( MapStlNode<K,V> replacementNode )
	{
		MapStlNode<K,V> deleteNodeParent = getParent();
		if ( deleteNodeParent != null )
		{
			if ( getIsRightChild() )
			{
				deleteNodeParent.setRightChild( replacementNode );
			}
			else
			{
				deleteNodeParent.setLeftChild( replacementNode );			
			}
		}
		
		if ( replacementNode != null )
		{
			replacementNode.setIsRightChild( getIsRightChild() );
			replacementNode.setParent( getParent() );
		}
	}

	public Pair<K,V> delete()
	{
		Pair<K,V> retval = getKeyValuePair();
		
		MapStlNode<K,V> nodeToDelete = null;
		boolean hasLeftChild = getLeftChild() != null;
		if ( !hasLeftChild || getRightChild() == null )
		{
			nodeToDelete = this;
			moveUpSingleChild( hasLeftChild ? getLeftChild() : getRightChild() );
		}
		else
		{
			nodeToDelete = getPrevious();
			setKeyValuePair( nodeToDelete.getKeyValuePair() );
			setNodeType( nodeToDelete.getNodeType() );
			nodeToDelete.moveUpSingleChild( nodeToDelete.getLeftChild() );
		}

		nodeToDelete.getParent().balanceTree();
		
		return retval;
	}

}
