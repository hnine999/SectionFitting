package com.geometricmethods.stl.support;

public class ValueUnificationNode<T> extends UnificationNode<T>
{
	public static class Exception extends java.lang.Exception
	{
		private static final long serialVersionUID = 1L;

		public Exception()
		{
			super("Cannot unify a ValueUniificationNode");
		}
	}
	
	private T value;

	public ValueUnificationNode(T value)
	{
		this.value = value;
	}
	
	@Override
	public void unifyWith(UnificationNode<T> unificationNode) throws Exception
	{
		throw new Exception();
	}

	public void nullify()
	{
		value = null;
	}
	
	@Override
	protected Pair<T, UnificationNode<T>> getValueAux()
	{
		return new Pair<T, UnificationNode<T>>(value, null);
	}	
}
