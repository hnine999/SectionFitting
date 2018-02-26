package com.geometricmethods.intersectiondetection;

import java.util.ArrayList;
import com.geometricmethods.geometry.Vertex;
import com.geometricmethods.geometry.VertexCoordinateComparator;
import com.geometricmethods.stl.sequence.ListIterator;


public class Section extends ArrayList<Polygon>
{	
	public static final long serialVersionUID = 0;

	private ScanLine scanLine = new ScanLine();
	EventQueue eventQueue = new EventQueue(scanLine);
	
	public boolean hasIntersection()
	{
		removeRedundantVertexes();
		primeEventQueue();
		
		return eventQueue.processEvents();
	}
	
	private void removeRedundantVertexes()
	{
		for( Polygon polygon : this )
		{
			polygon.removeRedundantVertexes();
		}
	}

	void primeEventQueue()
	{
		eventQueue.clear();
		
		VertexCoordinateComparator vertexCoordinateComparator = VertexCoordinateComparator.get();
		for( Polygon polygon : this )
		{
			
			if ( polygon.size() == 0 )
			{
				continue;
			}

			ListIterator<Vertex> polygon_begin = polygon.begin();
					
			ListIterator<Vertex> currentIterator = polygon.begin();
			Vertex previousVertex = currentIterator.decrement().dereference();
			Vertex currentVertex = currentIterator.increment().dereference();
			ListIterator<Vertex> nextIterator = new ListIterator<>(currentIterator).increment();

			do
			{
				Vertex nextVertex = nextIterator.dereference();
				
				if (  vertexCoordinateComparator.compare( currentVertex, previousVertex ) < 0 &&  vertexCoordinateComparator.compare( currentVertex, nextVertex ) < 0  )
				{
					Event event = eventQueue.getEvent(currentVertex);
					
					event.addStartVertexIterator(currentIterator);
				}

				previousVertex = currentVertex;
				currentVertex = nextVertex;
				currentIterator.set(nextIterator);
				nextIterator.increment();
			}
			while(  !currentIterator.equals( polygon_begin )  );
		}		
	}
}
