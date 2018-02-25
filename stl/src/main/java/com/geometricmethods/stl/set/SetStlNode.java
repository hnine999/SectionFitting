package com.geometricmethods.stl.set;


public class SetStlNode<V>
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
	
	private V value = null;
	private SetStlNode<V> parent = null;
	private SetStlNode<V> leftChild = null;
	private SetStlNode<V> rightChild = null;
	
	private NodeType nodeType;
	private int height = 1;
	private boolean isRightChild = false;

	SetStlNode( NodeType nodeType )
	{
		this.nodeType = nodeType;
	}
	
	public SetStlNode()
	{
		this( NodeType.DATA );
	}
	
	SetStlNode( NodeType nodeType, V value )
	{
		this.nodeType = nodeType;
		this.value = value;
	}
	
	public SetStlNode( V value )
	{
		this( NodeType.DATA );
	}
	
	SetStlNode( NodeType nodeType, V value, SetStlNode<V> parent, boolean isRightChild )
	{
		this( nodeType, value );
		
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

	public SetStlNode( V value, SetStlNode<V> parent, boolean isRightChild )
	{
		this( NodeType.DATA, value, parent, isRightChild );
	}
	
	public V getValue()
	{
		return value;
	}

	private void setValue( V value )
	{
		this.value = value;
	}
	
	public SetStlNode<V> getParent()
	{
		return parent;
	}
	public void setParent( SetStlNode<V> parent )
	{
		this.parent = parent;
	}
	
	public SetStlNode<V> getLeftChild()
	{
		return leftChild;
	}
	public void setLeftChild( SetStlNode<V> leftChild )
	{
		this.leftChild = leftChild;
	}

	public SetStlNode<V> getRightChild()
	{
		return rightChild;
	}
	public void setRightChild( SetStlNode<V> rightChild )
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
		SetStlNode<V> pivot = leftChild;

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
		SetStlNode<V> pivot = rightChild;

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
		SetStlNode<V> mapStlNode = this;

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
	
	public SetStlNode<V> getNext()
	{
		SetStlNode<V> node = getRightChild();
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
			SetStlNode<V> nextNode = node.getLeftChild();
			while( nextNode != null )
			{
				node = nextNode;
				nextNode = node.getLeftChild();
			}
		}
		return node;
	}

	public SetStlNode<V> getPrevious()
	{
		SetStlNode<V> node = getLeftChild();
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
			SetStlNode<V> nextNode = node.getRightChild();
			while( nextNode != null )
			{
				node = nextNode;
				nextNode = node.getRightChild();
			}
		}
		return node;
	}
	
	private void moveUpSingleChild( SetStlNode<V> replacementNode )
	{
		SetStlNode<V> deleteNodeParent = getParent();
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

	public V delete()
	{
		V retval = getValue();
		
		SetStlNode<V> nodeToDelete = null;
		boolean hasLeftChild = getLeftChild() != null;
		if ( !hasLeftChild || getRightChild() == null )
		{
			nodeToDelete = this;
			moveUpSingleChild( hasLeftChild ? getLeftChild() : getRightChild() );
		}
		else
		{
			nodeToDelete = getPrevious();
			setValue( nodeToDelete.getValue() );
			setNodeType( nodeToDelete.getNodeType() );
			nodeToDelete.moveUpSingleChild( nodeToDelete.getLeftChild() );
		}

		nodeToDelete.getParent().balanceTree();
		
		return retval;
	}

}
