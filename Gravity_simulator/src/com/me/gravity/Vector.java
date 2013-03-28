package com.me.gravity;

public class Vector {

	double x;
	double y;
	
	public Vector( double _x, double _y)
	{
		x = _x;
		y = _y;
	}
	
	
	public Vector( Vector A)
	{
		x = A.x;
		y = A.y;
	}
			
			
	public static Vector add( Vector a, Vector b)
	{
		Vector K = new Vector( a.x + b.x, a.y+b.y);
		return K;
	}
	
	public void addS( Vector a)
	{
		x+=a.x;
		y+=a.y;
	}
	
	public Vector add(Vector a)
	{
		Vector K = new Vector(x+a.x, y+a.y);
		return K;
	}
	
	public double size()
	{
		return(double) Math.sqrt( x*x + y*y);
	}
	
	public double sizesq()
	{
		return x*x + y*y;
	}
	
	public double cos( Vector K)
	{
		return (x*K.x + y*K.y)/(size()*K.size());
	}
	
	public double  dot(Vector K)
	{
		return x*K.x + y*K.y ;
	}
	
	
	public Vector mult( double t)
	{
		Vector M = new Vector( t*x,t*y);
		return M;
	}
	
	public void multS( double t)
	{
		x*=t;
		y*=t;
	}
	
	public void norm()
	{
		double dist = size();
		x /= dist;
		y /= dist;
	}
	
}
