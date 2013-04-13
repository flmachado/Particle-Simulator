package com.me.gravity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.gravity.Button.ID;


public class ButtonDrawer {
	
	Sprite back;
	Planet preview;
	Gravity.STATE state;

	
	ArrayList <Button> buttons = new ArrayList<Button>(6);
	
	public ButtonDrawer ()
	{
		back 		= new Sprite(Gravity.bback);
		back.setSize( Gravity.W, Gravity.W);
		back.setOrigin( back.getWidth()/2, back.getHeight()/2);
		back.setPosition(-back.getWidth()/2, (float) (-back.getHeight() - Gravity.H/2));
		
		
		buttons.add(new Button( new Vector(-Gravity.W/2 + Gravity.H/16,-Gravity.H/2 +Gravity.H/16),	
				new Vector(Gravity.H/8,Gravity.H/8),Button.ID.BUTUP	, Gravity.barrowup ));
		buttons.add(new Button( new Vector(-Gravity.W/2 + Gravity.H/16,-Gravity.H/2 +Gravity.H/16),	
				new Vector(Gravity.H/8,Gravity.H/8),Button.ID.BUTDO	, Gravity.barrowdown ));
		
		
		buttons.add(new Button( new Vector(Gravity.W/8, -Gravity.H/2 - back.getHeight()*((float)1/(float)8)),
				new Vector(Gravity.H/10,Gravity.H/10),Button.ID.MASSUP		, Gravity.plus)		);
		buttons.add(new Button( new Vector(Gravity.W*3/8, -Gravity.H/2 - back.getHeight()*((float)1/(float)8)),
				new Vector(Gravity.H/10,Gravity.H/10),Button.ID.MASSDOWN	, Gravity.minus)	);
		
		buttons.add(new Button( new Vector(Gravity.W/8, -Gravity.H/2 - back.getHeight()*((float)3/(float)8)),
				new Vector(Gravity.H/10,Gravity.H/10),Button.ID.CHARGEUP		, Gravity.plus)		);
		buttons.add(new Button( new Vector(Gravity.W*3/8, -Gravity.H/2 - back.getHeight()*((float)3/(float)8)),
				new Vector(Gravity.H/10,Gravity.H/10),Button.ID.CHARGEDOWN	, Gravity.minus)	);
		
		
		buttons.add(new Button( new Vector(-back.getWidth()/2 + Gravity.H/10 * 1 ,Gravity.H/10 - Gravity.H ),
				new Vector(Gravity.H/10,Gravity.H/10),Button.ID.CLEAR	, Gravity.green)	);
		buttons.add(new Button( new Vector(-back.getWidth()/2 + Gravity.H/10*2.5 ,Gravity.H/10  - Gravity.H ),
				new Vector(Gravity.H/10,Gravity.H/10),Button.ID.FORCE	, Gravity.blue)	);
		buttons.add(new Button( new Vector(-back.getWidth()/2 + Gravity.H/10*4 ,Gravity.H/10  - Gravity.H ),
				new Vector(Gravity.H/10,Gravity.H/10),Button.ID.WALLS	, Gravity.yellow)	);
		
		preview	= new Planet( -back.getWidth()/4, -20 - back.getHeight(), 0 ,0 , 0, 10);
		
		state = Gravity.STATE.CLOSED;
		this.changestate();
		this.changestate();
	}
	
	public boolean checkPress( Vector touch)
	{
		Button.ID toact = Button.ID.ZERO;
	
		for(Button b:buttons)
		{
			if( state == Gravity.STATE.CLOSED && b.id == Button.ID.BUTDO) continue;
			if( state == Gravity.STATE.OPEN	  && b.id == Button.ID.BUTUP) continue;
			
			toact = b.isTouched(touch);
			if( toact != Button.ID.ZERO){
				break;
			}
		}
		//System.out.println(toact);
		switch(toact)
		{
		case BUTUP:
			this.changestate();
			System.out.println("UP");
			break;
		case BUTDO:
			this.changestate();
			System.out.println("DOWN");
			break;
		case MASSUP:
			preview.addMass(1);
			break;
			
		case MASSDOWN:
			preview.addMass(-1);
			break;
		
		case CHARGEUP:
			preview.addCharge(0.1);
			break;
			
		case CHARGEDOWN:
			preview.addCharge(-0.1);
			break;
			
		case CLEAR:
			Gravity.ClearCorpos();
			break;
			
		case FORCE:
			Gravity.ToogleForces();
			break;
		
		case WALLS:
			Gravity.ToogleWalls();
			break;
			
		case GREEN:
			System.out.println("GREEN");
			break;
			
		case BLUE:
			System.out.println("BLUE");
			break;
			
		case YELLOW:
			System.out.println("YELLOW");
			break;
		
		case ZERO:
			return false;
		}
		return true;
	}
	
	public void draw(SpriteBatch batch)
	{
		if(state == Gravity.STATE.CLOSED)
		{
			for(Button b: buttons)
				if(b.id == Button.ID.BUTUP)
				{
					System.out.println(b.Pos.x +" "+b.Pos.y);
					b.draw(batch);
				}
		}
		
		else{
			back.draw(batch);
			for(Button b:buttons)
			{
				if( b.id == Button.ID.BUTUP) continue;
				b.draw(batch);
				preview.draw(batch);
			}
			
			preview.draw(batch);
		}
	}
	
	private void changestate()
	{
		if(state == Gravity.STATE.CLOSED)
		{
			Gravity.running = false;
			
			for (Button b:buttons)
			{
				b.move(0, back.getHeight());
			}
			preview.changePos(0,back.getHeight());
			
			back.setPosition(-Gravity.W/2, -Gravity.H/2);
			state = Gravity.STATE.OPEN;
		}
		else
		{
			Gravity.running = true;
			for (Button b:buttons)
			{
				b.move(0, -back.getHeight());
			}
			preview.changePos(0,-back.getHeight());
			back.setPosition(-Gravity.W/2, -back.getHeight()-Gravity.H/2);
			state = Gravity.STATE.CLOSED;
		}
	}
	
	public Planet getPlanet()
	{
		return preview;
	}
	
}
