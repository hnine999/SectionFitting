package com.geometricmethods.intersectiondetection;

import java.util.Map;
import java.util.TreeMap;

import com.geometricmethods.geometry.Vertex;
import com.geometricmethods.geometry.VertexCoordinateComparator;

public class EventQueue
{	
	private ScanLine scanLine;
	
	// EVENT MAP -- IN Vertex ORDER
	private TreeMap<Vertex, Event> eventMap = new TreeMap<>(VertexCoordinateComparator.get());

	public EventQueue(ScanLine scanLine)
	{
		this.scanLine = scanLine;
	}

	public void clear()
	{
		eventMap.clear();
	}
	
	public boolean empty()
	{
		return eventMap.isEmpty();
	}
	
	public int size()
	{
		return eventMap.size();
	}

	// FIND THE Event ASSOCIATED WITH Vertex, CREATE IF ONE DOESN'T EXIST
	public Event getEvent(Vertex vertex)
	{
		return eventMap.computeIfAbsent(vertex, x -> new Event(vertex, scanLine, this));
	}

	public void removeEvent(Event event)
	{
		eventMap.remove(event.getEventVertex());
	}

	public Event peekNextEvent()
	{
		Map.Entry<Vertex, Event> entry = eventMap.firstEntry();
		return entry == null ? null : entry.getValue();
	}

	public Event getNextEvent()
	{
		Map.Entry<Vertex, Event> entry = eventMap.pollFirstEntry();
		return entry == null ? null : entry.getValue();
	}
	
	public boolean processEvents()
	{
		Event event;
		while((event = getNextEvent()) != null)
		{
			if (event.process() != null)
			{
				return true;
			}
		}		
		return false;
	}

	// THE VERTEX FOR THIS EVENT SHOULD NOT VARY DUE TO ROUND-OFF ERROR BECAUSE THE REPRESENTATIVE LineSegment'S
	// OF ScanLineGroup'S DO NOT CHANGE UNTIL NEEDED.
	public void addIntersectionEvent(Vertex vertex, ScanLineGroup scanLineGroup1, ScanLineGroup scanLineGroup2)
	{
		Event event = getEvent(vertex);
		event.addScanLineGroup(scanLineGroup1);
		event.addScanLineGroup(scanLineGroup2);
	}
}
