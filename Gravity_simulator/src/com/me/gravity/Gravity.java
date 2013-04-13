package com.me.gravity;

import java.util.ArrayList;
import java.math.*;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Gravity implements ApplicationListener {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	public static Texture planet;
	public static Texture Arrow1;
	public static Texture Arrow2;
	public static Texture Arrow3;
	public static Texture red;
	public static Texture blue;
	public static Texture yellow;
	public static Texture green;
	public static Texture barrowup;
	public static Texture barrowdown;
	public static Texture bback;
	public static Texture plus;
	public static Texture minus;
	
	public static TextureRegion T1;
	public static TextureRegion T2;
	public static TextureRegion T3;
	
	public float startpointx;
	public float startpointy;
	public static boolean pressed;
	public static boolean forces = true;
	boolean Spacep = false;
	boolean Cp = false;
	boolean Vp = false;
	boolean Fp = false;
	boolean Rp = false;
	boolean Np = false;
	boolean negativeMass = false;
	boolean RandomPlanets = false;
	
	
	public static float Wreg;
	public static float Hreg;
	public static float W;
	public static float H;
	public static boolean bounded = false;
	
	public static boolean running = false;
	
	public enum STATE {OPEN, CLOSED};
	
	public static float G = 1000000;
	float DT = 0.1f;
	float total_time = 0f;
	float CV = 0.1f;
	float LIMGEN = 10;
	float generate = 0;
	Random Rand = new Random();
	
	ButtonDrawer butdr;
	
	public static List<Planet> corpos = new ArrayList<Planet>(10);
	
	@Override
	public void create() {		
		
		//Gdx.input.setInputProcessor(new GestureDetector(new GestureEngine() ) );
		
		W = Gdx.graphics.getWidth();
		H = Gdx.graphics.getHeight();
		Wreg = W;
		Hreg = H;
		
		camera = new OrthographicCamera(W, H);
		batch = new SpriteBatch();
		
		planet= new Texture(Gdx.files.internal("data/planet.png"));
		planet.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		Arrow1= new Texture(Gdx.files.internal("data/seta1.png"));
		Arrow1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		Arrow2= new Texture(Gdx.files.internal("data/seta2.png"));
		Arrow2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		Arrow3= new Texture(Gdx.files.internal("data/seta3.png"));
		Arrow3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		red = new Texture( Gdx.files.internal("data/red.png"));
		red.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		blue = new Texture( Gdx.files.internal("data/blue.png"));
		blue.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		yellow = new Texture( Gdx.files.internal("data/yellow.png"));
		yellow.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		green = new Texture( Gdx.files.internal("data/green.png"));
		green.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		barrowup = new Texture( Gdx.files.internal("data/arrowup.png"));
		barrowup.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		barrowdown = new Texture( Gdx.files.internal("data/arrowdown.png"));
		barrowdown.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bback = new Texture( Gdx.files.internal("data/bback.png"));
		bback.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		plus = new Texture( Gdx.files.internal("data/plus.png"));
		plus.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		minus = new Texture( Gdx.files.internal("data/minus.png"));
		minus.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		T1 = new TextureRegion( Gravity.Arrow1, 0, 0, 1024, 256);
		T2 = new TextureRegion( Gravity.Arrow2, 0, 0, 1024, 256);
		T3 = new TextureRegion( Gravity.Arrow3, 0, 0, 1024, 256);
		
		butdr = new ButtonDrawer();
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		planet.dispose();
	}

	@Override
	public void render() {		
		
		Input();
		if( RandomPlanets)
		{
			generate++;
			GeneratePlanets();
		}
		
		if(running)
		{
			Update();
			total_time+=DT;
		}
		
		// System.out.println(corpos.size());
		
		double EK = 0;
		double Ep = 0;
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			
		for( Planet p: corpos)
		{
			for(Planet q:corpos)
			{
				if(q!=p)
				{
					Ep+=Gravity.G*p.charge*q.charge/q.Pos.add(p.Pos.mult(-1)).size();
				}
			}
			EK += 0.5 * p.mass* p.Vel.sizesq();
			p.draw(batch);
		}
		Ep/=2;
		//System.out.println("E: " + (EK+Ep));
		
		butdr.draw(batch);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		W = width;
		H = height;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	private void Input()
	{
		if(Gdx.input.isKeyPressed(Keys.SPACE) && Spacep == false)
		{
			bounded = !bounded;
			Spacep = true;
		}
		else if(!Gdx.input.isKeyPressed(Keys.SPACE) && Spacep)
		{
			Spacep = false;
		}
		
		if(Gdx.input.isKeyPressed(
				Keys.C) && Cp == false)
		{
			corpos.clear();
			Cp = true;
		}
		else if(!Gdx.input.isKeyPressed(Keys.C) && Cp)
		{
			Cp = false;
		}
		
		if(Gdx.input.isKeyPressed(
				Keys.V) && Vp == false)
		{
			Vp = true;
		}
		else if(!Gdx.input.isKeyPressed(Keys.V) && Vp)
		{
			running = !running;
			Vp = false;
		}
		
		if(Gdx.input.isKeyPressed(
				Keys.R) && Rp == false)
		{
			Rp = true;
		}
		else if(!Gdx.input.isKeyPressed(Keys.R) && Rp)
		{
			RandomPlanets = !RandomPlanets;
			Rp = false;
		}
		
		if(Gdx.input.isKeyPressed(
				Keys.F) && Fp == false)
		{
			Fp = true;
		}
		else if(!Gdx.input.isKeyPressed(Keys.F) && Fp)
		{
			forces = !forces;
			Fp = false;
		}
		
		if(Gdx.input.isKeyPressed(
				Keys.N) && Np == false)
		{
			Np = true;
		}
		else if(!Gdx.input.isKeyPressed(Keys.N) && Np)
		{
			negativeMass = !negativeMass;
			Np = false;
		}
		
		
		if(butdr.state == STATE.CLOSED)
		{
			if( pressed )
			{
				if( !Gdx.input.isTouched() )
				{
					pressed = false;
					float endpointx = Gdx.input.getX();
					float endpointy = Gdx.input.getY();
					
					float dx = startpointx - endpointx;
					float dy = startpointy - endpointy;
					int k = 1;
					if( negativeMass) k = -1;
					Planet T = butdr.getPlanet();
					corpos.add( new Planet( endpointx-W/2, -endpointy + H/2, dx*CV, -dy*CV, T.charge ,T.mass));
				}
			}
			
			if(!pressed)
			{
				if( Gdx.input.isTouched())
				{
					startpointx =  Gdx.input.getX();
					startpointy = Gdx.input.getY();
					Vector touch = new Vector(startpointx-Gravity.W/2, -startpointy + Gravity.H/2);
					//System.out.println(startpointx+" "+startpointy);
					if( !butdr.checkPress(touch))
						pressed = true;
				}
			}
		}
		
		if(butdr.state == STATE.OPEN)
		{
			if( !pressed && Gdx.input.isTouched())
			{
				pressed = true;
				startpointx =  Gdx.input.getX();
				startpointy = Gdx.input.getY();
			}
			
			if(pressed && !Gdx.input.isTouched())
			{
				float tx =  Gdx.input.getX();
				float ty = Gdx.input.getY();
				if(Math.abs(startpointx-tx)< 10 && Math.abs(startpointy - ty)<10)
				{
					Vector touch = new Vector(tx-Gravity.W/2, -ty + Gravity.H/2);
					//System.out.println(startpointx+" "+startpointy);
					butdr.checkPress(touch);
				}
				pressed = false;
			}
			
		}
	}
	
	public void Update()
	{
		List<Planet> toRemove = new ArrayList<Planet>(10);
		for(Planet p: corpos)
		{
			if( !(toRemove.contains(p)) )
			{
				for( Planet l: corpos)
				{
					if( p!= l && !(toRemove.contains(l)) )
					{
						p.collision(l);
					}
				}
			}
		}
		
		corpos.removeAll( toRemove);
		
		for(Planet p: corpos)
		{
			for( Planet l: corpos)
			{
				if( corpos.indexOf(l) > corpos.indexOf(p))
				{
					Vector sep = new Vector( p.Pos.x - l.Pos.x, p.Pos.y - l.Pos.y);
					double dist = sep.size();
					double force =   -(G*p.charge*l.charge/(dist*dist));

					l.addForce( force * (sep.x)/dist , force * (sep.y)/dist );
					p.addForce( -force * (sep.x)/dist ,  -force * (sep.y)/dist );
				}
			}
		}
		
		double Xmax = 0;
		double Ymax = 0;
		for ( Planet p : corpos)
		{
			p.update(DT);
			Xmax = Math.max(Xmax, Math.abs(p.Pos.x));
			Ymax = Math.max(Ymax, Math.abs(p.Pos.y));
			//System.out.println(p.Pos.x + " " + p.Pos.y);
		}		
		System.out.println("END SIM");
		/*W = (float)Math.max((Xmax*2), Wreg );
		H = (float)Math.max((Ymax*2), Hreg );
		
		if( W * Hreg/Wreg > H)
		{
			H = W * Hreg/Wreg;
		}
		else
		{
			W = H * Wreg/Hreg;
		}*/

		
	}
	
	
	void GeneratePlanets()
	{
		if( generate >= LIMGEN)
		{
			generate = 0;
			corpos.add( new Planet(Rand.nextInt((int)W) - W/2  ,Rand.nextInt((int)H) - H/2 , Rand.nextFloat()*100 - 50 ,Rand.nextFloat()*100 - 50, Rand.nextFloat()*2 - 1, 1000 ));
		}
	}
	
	public static void ClearCorpos()
	{
		corpos.clear();
	}
	
	public static void ToogleForces()
	{
		forces = !forces;
	}
	
	public static void ToogleWalls()
	{
		bounded = !bounded;
	}
}
