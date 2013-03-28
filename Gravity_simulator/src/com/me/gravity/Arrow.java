package com.me.gravity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Arrow {

	Sprite A;
	double F = 1;
	public double startX;
	public double startY;
	public double endX;
	public double endY;
	double angle;
	
	public Arrow( double startx, double starty, double endx, double endy, int color)
	{
		startX = startx;
		startY = starty;
		endX = endx;
		endY = endy;

		double dx = endx - startx;
		double dy = endy - starty;
		double size = (double) Math.sqrt( dx*dx + dy*dy);
		double sizey = (double) (Math.sqrt(size) * F);
		switch( color)
		{
		case 1:
			A = new Sprite( Gravity.T1);
			break;
		case 2:
			A = new Sprite ( Gravity.T2);
			break;
		case 3:
			A = new Sprite( Gravity.T3);
			break;
		}
		
		A.setSize((float)size, (float)sizey);
		A.setOrigin((float)0, (float)sizey/2);
		A.setPosition( (float)startx, (float)(starty - sizey/2) );
		angle =  (Math.atan2(dy, dx) * 180/Math.PI);
		A.rotate((float) angle);
	}
	
	public void draw( SpriteBatch S)
	{
		A.draw(S);
	}
	
	public void change( double startx, double starty, double endx, double endy )
	{
		startX = startx;
		startY = starty;
		endX = endx;
		endY = endy;

		double dx = endx - startx;
		double dy = endy - starty;
		double size = Math.sqrt( dx*dx + dy*dy);
		double sizey =  (Math.sqrt(size) * F);
		
		A.setSize((float)size, (float)sizey);
		A.setOrigin((float)0, (float)sizey/2);
		A.setPosition( (float)startx, (float)(starty - sizey/2) );
		double nangle = Math.atan2(dy, dx) * 180/Math.PI;
		A.rotate((float) (nangle-angle) );
		angle = nangle;
	}
	
}
