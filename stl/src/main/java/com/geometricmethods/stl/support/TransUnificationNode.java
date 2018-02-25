package com.geometricmethods.stl.support;

public class TransUnificationNode<T> extends UnificationNode<T>
{
	private UnificationNode<T> nextNode;

	public TransUnificationNode(UnificationNode<T> unificationNode)
	{
		this.nextNode = unificationNode;
	}
	
	@Override
	public void unifyWith(UnificationNode<T> unificationNode)
	{
		nextNode = unificationNode.getValueAux().second;
	}

	@Override
	protected Pair<T, UnificationNode<T>> getValueAux()
	{
		Pair<T, UnificationNode<T>> value = nextNode.getValueAux();
		if (value.second == null)
		{
			value.second = this;
		}
		return value;
	}	
}
