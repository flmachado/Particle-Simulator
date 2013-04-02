package com.me.gravity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Planet {

	Vector Pos;
	Vector Vel;
	Vector Acel;
	Vector Force;
	
	double mass;
	double charge;
	double radii;
	
	double EPS = 0.00001f;
	
	double C = 20f;
	
	Sprite im;
	
	Arrow vel;
	Arrow acel;
	double F = 3;
	
	public Planet(double X, double Y, double Vx, double Vy ,double Charge, double Mass)
	{
			
		Pos = new Vector(X,Y);
		Vel = new Vector(Vx,Vy);
		Acel = new Vector(0,0);
		Force = new Vector(0,0);
		
		vel = new Arrow(Vel.x*F+Pos.x, Vel.y*F+Pos.y, Pos.x,Pos.y,1 );
		acel = new Arrow(0,0,0,0, 2);
		mass = Mass;
		charge = Charge;
		
		radii = (double)Math.sqrt(Math.abs(mass*C));
		
		im = new Sprite( Gravity.planet);
		im.setSize(( float)(2*radii), (float)(2*radii) );
		im.setOrigin(im.getWidth()/2, im.getHeight()/2);
		im.setPosition( (float)(Pos.x- im.getWidth()/2), (float)(Pos.y - im.getHeight()/2));
		
		updateColor();
		
	}
	
	public void update(double dt)
	{
		if( Gravity.bounded)
		{
			if( Pos.x > Gravity.W/2-radii)
			{
				Pos.x = Gravity.W/2 - radii -EPS;
				Vel.x=-Vel.x;
			}
				
			if(Pos.x < -Gravity.W/2 + radii)
			{
				Pos.x = -Gravity.W/2 + radii + EPS;
				Vel.x=-Vel.x;
			}
				
			if( Pos.y > Gravity.H/2-radii)
			{
				Pos.y = Gravity.H/2 - radii -EPS;
				Vel.y=-Vel.y;
			}
				
			if(Pos.y < -Gravity.H/2 + radii)
			{
				Pos.y = -Gravity.H/2 + radii + EPS;
				Vel.y=-Vel.y;
			}
		}
		Acel.x = Force.x/mass;
		Acel.y = Force.y/mass;
		Vel.x += Acel.x*dt;
		Vel.y += Acel.y*dt;
		
		vel.change(Vel.x*F + Pos.x , Vel.y*F + Pos.y , Pos.x , Pos.y);
		acel.change(Acel.x*F + Pos.x , Acel.y*F + Pos.y , Pos.x , Pos.y);
		
		Pos.x += Vel.x*dt;
		Pos.y += Vel.y*dt;
		
		Force.x = 0;
		Force.y = 0;
		
		im.setPosition( (float)(Pos.x - im.getWidth()/2), (float)(Pos.y  - im.getHeight()/2) );
	}
	
	public void addForce (double dx, double dy)
	{
		Force.x += dx;
		Force.y += dy;
	}
	
	public void draw(SpriteBatch b)
	{
		
		im.draw(b);
		
		if( Gravity.forces == true)
		{
			vel.draw(b);
			acel.draw(b);
		}
	}
	
	public void collision(Planet X)
	{
		Vector sep = new Vector( Pos.add( X.Pos.mult(-1) ) );
		if( sep.sizesq() <= (radii+X.radii)*(radii+X.radii) )
		{
			/*double V1 = 0;
			for(Planet p: Gravity.corpos)
			{
				if(p!= this)
				{
					double deltaPos = ( Pos.add(p.Pos.mult(-1))).size(); 
					V1 += Gravity.G * p.charge/ deltaPos;
				}
			}*/
			sep.multS ( (radii + X.radii + EPS)/sep.size() );
			Pos = X.Pos.add( sep );	
			
			/*double V2 = 0;
			for(Planet p: Gravity.corpos)
			{
				if(p!= this)
				{
					double deltaPos = ( Pos.add(p.Pos.mult(-1))).size(); 
					V2 += Gravity.G * p.charge/ deltaPos;
				}
			}*/
						
			
			Vector RelVelX = new Vector( X.Vel.add( Vel.mult(-1) ) );
			//System.out.println("RELVELX: " + RelVelX.x + " " + RelVelX.y + " " + RelVelX.size());
			sep.norm();
			//System.out.println("SEP: " + sep.x + " " + sep.y + " " + sep.size());
			sep.multS( sep.dot( RelVelX ) );
			//System.out.println("SEP: " + sep.x + " " + sep.y + " " + sep.size());
			
			Vector RelVelA = sep.mult( 2*X.mass/(mass+X.mass));
			//System.out.println("RelVelA: " + RelVelA.x + " " + RelVelA.y + " " + RelVelA.size());
			
			Vector RelVelXaf = sep.mult( (X.mass- mass)/(mass+X.mass) );
			//System.out.println("RelVelXaf: " + RelVelXaf.x + " " + RelVelXaf.y + " " + RelVelXaf.size());
			
			Vector K = RelVelXaf.add( sep.mult(-1) );
			K.multS(-1);
			
			//System.out.println("Kf: " + K.x + " " + K.y + " " + K.size());
			//System.out.println("Vel: " + Vel.x + " " + Vel.y + " " + Vel.size());
			//System.out.println("XVel: " + X.Vel.x + " " + X.Vel.y + " " + X.Vel.size());
			
			RelVelX.addS(K.mult(-1));
			RelVelX.addS( this.Vel);
			X.Vel = RelVelX;
			this.Vel.addS(RelVelA);
			
			//System.out.println("qdV "+ this.charge*(V2-V1));
			
			//double V =  Vel.sizesq();
			//Vel.norm();
			//System.out.println(V + (2*this.charge*(V2-V1) * sep.size())/this.mass);
			//Vel.multS(Math.sqrt(V - (2*this.charge*(V2-V1) * sep.size())/this.mass));
			
			//System.out.println("Vel: " + Vel.x + " " + Vel.y + " " + Vel.size());
			//System.out.println("XVel: " + X.Vel.x + " " + X.Vel.y + " " + X.Vel.size());
			

			//return true;
		}
		//return false;
	}
	
	public void addMass( double M)
	{
		mass+=M;
		radii = Math.sqrt( Math.abs(mass*C) );
		im.setSize( (float)(2*radii), (float)(2*radii));
		im.setPosition((float)(Pos.x - im.getWidth()/2), (float)(Pos.y  - im.getHeight()/2));
	}
	
	public void addCharge( double dq)
	{
		this.charge += dq;
		updateColor();
	}
	
	public void changePos(double dx, double dy)
	{
		Pos.x += dx;
		Pos.y += dy;
		im.setPosition((float)(Pos.x - im.getWidth()/2), (float)(Pos.y  - im.getHeight()/2));
	}
	
	private void updateColor()
	{
		float rd = 0;
		float bl = 0;
		if( this.charge > 0)
		{
			rd = (float) (this.charge/(0.5+this.charge));
		}
		else
		{
			bl = (float) (this.charge/(0.5-this.charge));
		}
		
		im.setColor(rd, 0, bl, 1);
	}
}
