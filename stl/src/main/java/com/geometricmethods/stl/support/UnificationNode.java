package com.geometricmethods.stl.support;

import com.geometricmethods.stl.support.ValueUnificationNode.Exception;

public abstract class UnificationNode<T>
{
	public abstract void unifyWith(UnificationNode<T> unificationNode) throws Exception;
	
	public final T getValue()
	{
		return getValueAux().first;
	}
	
	protected abstract Pair<T, UnificationNode<T>> getValueAux();	
}
