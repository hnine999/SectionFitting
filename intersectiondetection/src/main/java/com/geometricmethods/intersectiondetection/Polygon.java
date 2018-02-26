package com.geometricmethods.intersectiondetection;

import java.util.HashMap;
import java.util.Map;

import com.geometricmethods.geometry.Line;
import com.geometricmethods.geometry.LineSegment;
import com.geometricmethods.geometry.Vertex;
import com.geometricmethods.stl.iterator.ForwardIterator;
import com.geometricmethods.stl.sequence.LinkedListStl;
import com.geometricmethods.stl.sequence.ListIterator;

import org.apache.commons.lang3.tuple.Pair;

public class Polygon extends LinkedListStl< Vertex >
{
	public enum PRL
	{
		INSIDE,
		ON,
		OUTSIDE
	}

//	public static final long serialVersionUID = 0;
	
	public Polygon()
	{
		super();
		makeCircular();
	}
	
	public Polygon(  ForwardIterator<Vertex> first, ForwardIterator<Vertex> last )
	{
		super( first, last );
		makeCircular();
	}

	public void removeRedundantVertexes()
	{
		if ( empty() )
		{
			return;
		}

		boolean isCircular = isCircular();
		makeLinear();

		ListIterator< Vertex > currentIterator = begin();
		Vertex vertex = currentIterator.dereference();
		ListIterator< Vertex > terminalIterator = end();
		ListIterator< Vertex > nextIterator = new ListIterator<>( currentIterator ).increment();
		ListIterator< Vertex > thirdIterator = new ListIterator<>();
		while(!nextIterator.equals(terminalIterator))
		{
			if (vertex.equals(nextIterator.dereference()))
			{
				thirdIterator.set(nextIterator).increment();
				erase(nextIterator);
				nextIterator.set(thirdIterator);
			}
			else
			{
				vertex = currentIterator.set(nextIterator).dereference();
				nextIterator.increment();
			}
		}

		if (begin().dereference().equals(vertex))
		{
			pop_back();
		}
		
		if ( isCircular )
		{
			makeCircular();
		}
	}

	public Polygon translate(Vertex vertex)
	{
		boolean isCircular = isCircular();
		if (isCircular)
		{
			makeLinear();
		}
		
		ListIterator<Vertex> end = end();
		for(ListIterator<Vertex> iterator = begin() ; !iterator.equals(end) ; iterator.increment())
		{
			iterator.dereference().plusAssign(vertex);
		}

		if (isCircular)
		{
			makeCircular();
		}

		return this;
	}
	
	public Polygon copy()
	{
		Polygon polygonCopy = new Polygon();
		
		boolean isCircular = isCircular();
		if (isCircular)
		{
			makeLinear();
		}
		
		ListIterator<Vertex> end = end();
		for(ListIterator<Vertex> iterator = begin() ; !iterator.equals(end) ; iterator.increment())
		{
			polygonCopy.push_back(iterator.dereference().clone());
		}
		
		if (isCircular)
		{
			makeCircular();
		}
		
		return polygonCopy;
	}

	private static int rotate4(int value)
	{
		return ++value % 4;
	}	

	private static Map<Pair<Integer, Integer>, Integer> wrapNumberMap = new HashMap<>();
	static
	{
		for(int ix = -1 ; ix < 4 ; ++ix)
		{
			wrapNumberMap.put(Pair.of(-1, ix), -1);
		}
		
		int baseValue = 0;
		for(int ix = 0 ; ix < 4 ; ++ix)
		{
			// 0 -> -1 => -1
			wrapNumberMap.put(Pair.of(baseValue, -1), -1);
						
			int nextValue = baseValue;      // 0

			// 0 -> 0 => 0
			wrapNumberMap.put(Pair.of(baseValue, nextValue), 0);
						
			nextValue = rotate4(nextValue); // 1
			
			// 0 -> 1 => 1
			wrapNumberMap.put(Pair.of(baseValue, nextValue), 1);

			nextValue = rotate4(nextValue); // 2

			// 0 -> 2 => 2 (or -2)
			wrapNumberMap.put(Pair.of(baseValue, nextValue), 2);

			nextValue = rotate4(nextValue); // 3
			
			// 0 -> 3 => -1
			wrapNumberMap.put(Pair.of(baseValue, nextValue), -1);
			
			baseValue = rotate4(baseValue);
		}
	}
		
	
	public PRL hasOnInside(Vertex vertex)
	{
		if (size() == 0)
		{
			return PRL.OUTSIDE;
		}
		
		if (size() == 1)
		{
			return begin().dereference().equals(vertex) ? PRL.ON : PRL.OUTSIDE;
		}
		
		if (size() == 2)
		{
			return new LineSegment(begin().dereference(), begin().increment().dereference()).contains(vertex) ? PRL.ON : PRL.OUTSIDE;
		}
		
		Polygon polygonCopy = copy();
		
		polygonCopy.translate(vertex.negative());


		polygonCopy.makeCircular();
		
		ListIterator<Vertex> begin = polygonCopy.begin();
		ListIterator<Vertex> iterator = polygonCopy.begin();
		Vertex currentVertex = iterator.dereference();
				
		int sum = 0;
		do
		{
			iterator.increment();
			Vertex nextVertex = iterator.dereference();
			
			int currentQuadrant = currentVertex.getQuadrant();
			
			if (currentQuadrant < 0)				
			{
				return PRL.ON;
			}
						
			int addend = wrapNumberMap.get(Pair.of(currentQuadrant, nextVertex.getQuadrant()));

			if (addend == 2)
			{
				if (currentVertex.getX() == 0 && nextVertex.getX() == 0)
				{
					return PRL.ON;
				}
				
				Line line = new Line(currentVertex, nextVertex);
				float xIntercept = line.getXgivenY(0);
				if (xIntercept == 0)
				{
					return PRL.ON;
				}
				
				if (xIntercept < 0 && currentQuadrant > 1 || xIntercept > 0 && currentQuadrant < 2)
				{
					addend = -2;
				}
			}
			
			sum += addend;
			currentVertex = nextVertex;
			
		} while(!iterator.equals(begin));
		
		
		return sum == 0 ? PRL.OUTSIDE : PRL.INSIDE;
	}
	
	// A PRE-CONDITION FOR THIS METHOD IS THAT THE "polygon" ARGUMENT
	// HAS NO INTERSECTION (EXTENDED OR OTHERWISE) WITH THIS POLYGON
	public PRL hasOnInside(Polygon polygon)
	{
		ListIterator<Vertex> end = polygon.end();
		for(ListIterator<Vertex> iterator = polygon.begin() ; iterator.equals(end) ; iterator.increment())
		{
			PRL vertexPRL = hasOnInside(iterator.dereference());
			if (vertexPRL != PRL.ON)
			{
				return vertexPRL;
			}
		}
		
		return PRL.ON;
	}
	
	public boolean isDisjunct(Polygon polygon)
	{
		return hasOnInside(polygon) == PRL.OUTSIDE && polygon.hasOnInside(this) == PRL.OUTSIDE;
	}
}
