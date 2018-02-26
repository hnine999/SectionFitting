package com.geometricmethods.geometry;

import java.io.Serializable;
import java.text.DecimalFormat;


public class Vertex implements Serializable, Cloneable
{
	private static final long serialVersionUID = 1L;
	private static final DecimalFormat decimalFormat = new DecimalFormat("#.#######");
	
	private static int uniqueInt = 0;
	
	private static int getUniqueId()
	{
		return uniqueInt++;
	}
	
	private float x = 0;
	private float y = 0;
	private int identifier = getUniqueId();
	
//	private Label label = null;

	public Vertex() {}
	public Vertex( float x, float y )
	{
		this.x = x;
		this.y = y;
	}
	
	public Vertex( Vertex vertex )
	{
		this.x = vertex.x;
		this.y = vertex.y;
	}
	
	public int getIdentifier()
	{
		return identifier;
	}

	private void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public void setX( float x )
	{
		this.x = x;
	}
	
	public void setY( float y )
	{
		this.y = y;
	}
	
	public void set( float x, float y )
	{
		this.x = x;
		this.y = y;
	}
	public void set( Vertex other )
	{
		this.x = other.x;
		this.y = other.y;
	}

	public Vertex negative()
	{
		return new Vertex(-getX(), -getY());
	}
	
	public Vertex negativeAssign()
	{
		setX(-getX());
		setY(-getY());
		return this;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public Vertex plus( Vertex other )
	{
		return new Vertex( x + other.x, y + other.y );
	}
	
	public Vertex plusAssign( Vertex other )
	{
		x += other.x;
		y += other.y;
		return this;
	}
	
	public Vertex minus( Vertex other )
	{
		return new Vertex( x - other.x, y - other.y );
	}
	
	public Vertex minusAssign( Vertex other )
	{
		x -= other.x;
		y -= other.y;
		return this;
	}
	
	public Vertex multiply( long value )
	{
		return new Vertex( x * value, y * value );
	}
	
	public Vertex multiplyAssign( long value )
	{
		x *= value;
		y *= value;
		return this;
	}
	
	public float dotProduct( Vertex other )
	{
		return x * other.y + other.x * y;
	}
	
	public float crossProductZ( Vertex other )
	{
		return x * other.y - other.x * y;
	}

	public float magnitudeSquared()
	{
		return getX() * getX() + getY() * getY();
	}
	
	public float magnitude()
	{
		return (float)Math.sqrt( magnitudeSquared() );
	}
	
	public float distance( Vertex other )
	{
		return this.minus( other ).magnitude();
	}
	
	public boolean parallelTo( Vertex other )
	{
		return crossProductZ( other ) == 0;
	}
	
	public boolean sameDirection(Vertex other)
	{
		return parallelTo( other ) && dotProduct( other ) > 0;
	}
	
	public boolean oppositeDirection(Vertex other)
	{
		return parallelTo( other ) && dotProduct( other ) < 0;
	}
	
	public float cosine()
	{
		return getX() / magnitude();
	}
	
	public float sine()
	{
		return getY() / magnitude();
	}
	
	public float cosine(Vertex other)
	{
		return dotProduct(other) / (magnitude() * other.magnitude());
	}
	
	public float sine(Vertex other)
	{
		return crossProductZ(other) / (magnitude() * other.magnitude());
	}
	
	public float angle()
	{
		float ang = (float)Math.acos(cosine());
		if ( getY() < 0 ) ang = 2 * (float)Math.PI - ang;
		return ang;
	}
	
	public float angle(Vertex other)
	{
		float ang = (float)Math.acos(cosine(other));
		if (crossProductZ(other) > 0) ang = 2 * (float)Math.PI - ang;
		return ang;
	}
	
	public float angle( float lessAngle )
	{
		float ang = angle();
		int rotations = (int)Math.ceil( (lessAngle - ang) / ( 2 * (float)Math.PI ));
		return ang + rotations * 2 * (float)Math.PI;
	}
	
	public float angle( Vertex other, float lessAngle )
	{
		float ang = angle(other);
		int rotations = (int)Math.ceil( (lessAngle - ang) / ( 2 * (float)Math.PI ));
		return ang + rotations * 2 * (float)Math.PI;
	}
	
	@Override
	public boolean equals( Object other )
	{
		if ( !( other instanceof Vertex) )
		{
			return false;
		}
		Vertex vertex = (Vertex)other;
		return this.x == vertex.x && this.y == vertex.y;
	}
	
	public boolean identicalTo( Vertex other )
	{
		return this.identifier == other.identifier;
	}

	public String toString()
	{
		return "(" + decimalFormat.format(getX()) + "," + decimalFormat.format(getY()) + ")";
	}
	
	@Override
	public Vertex clone()
	{
		return new Vertex(this);
	}

	public Vertex duplicate() {
		Vertex vertex = clone();
		vertex.setIdentifier(getIdentifier());
		return vertex;
	}

	public int getQuadrant()
	{
		if (getX() == 0 && getY() == 0)
		{
			return -1;
		}
		
		if (getX() >= 0 && getY() > 0)
		{
			return 0;
		}
		
		if (getX() < 0 && getY() >= 0)
		{
			return 1;
		}
		
		if (getX() <= 0 && getY() < 0)
		{
			return 2;
		}
		
		// getX() > 0 && getY() <= 0
		return 3;
	}
}
