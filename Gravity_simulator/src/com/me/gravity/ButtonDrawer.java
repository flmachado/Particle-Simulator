package com.me.gravity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class ButtonDrawer {
	
	Sprite back;
	Sprite arrowup;
	Sprite arrowdown;
	Planet preview;
	Gravity.STATE state;

	
	ArrayList <Button> buttons = new ArrayList<Button>(6);
	
	public ButtonDrawer ()
	{
		back 		= new Sprite(Gravity.bback);
		back.setSize( Gravity.W, Gravity.W);
		back.setOrigin( back.getWidth()/2, back.getHeight()/2);
		back.setPosition(-back.getWidth()/2, (float) (-back.getHeight() - Gravity.H/2));
		
		
		buttons.add(new Button( new Vector(-Gravity.W/2 +20,-Gravity.H/2 +20),	new Vector(40,40),Button.ID.BUTUP	, Gravity.barrowup ));
		buttons.add(new Button( new Vector(-Gravity.W/2 +20,-Gravity.H/2 +20),	new Vector(40,40),Button.ID.BUTDO	, Gravity.barrowdown ));
		
		
		buttons.add(new Button( new Vector(50,-60-back.getHeight()), 		new Vector(60,60),Button.ID.MASSUP		, Gravity.plus)		);
		buttons.add(new Button( new Vector(130,-60-back.getHeight()), 		new Vector(60,60),Button.ID.MASSDOWN	, Gravity.minus)	);
		
		buttons.add(new Button( new Vector(50,20-back.getHeight()), 		new Vector(60,60),Button.ID.CHARGEUP		, Gravity.plus)		);
		buttons.add(new Button( new Vector(130,20-back.getHeight()), 		new Vector(60,60),Button.ID.CHARGEDOWN	, Gravity.minus)	);
		
		
		buttons.add(new Button( new Vector(-back.getWidth()/2 + 60 ,-back.getHeight() + 80 - Gravity.H/2 ),
				new Vector(60,60),Button.ID.CLEAR	, Gravity.green)	);
		buttons.add(new Button( new Vector(-back.getWidth()/2 + 140 ,-back.getHeight() + 80 - Gravity.H/2 ),
				new Vector(60,60),Button.ID.FORCE	, Gravity.blue)	);
		//buttons.add(new Button( new Vector(60,20-back.getHeight()), 		new Vector(40,40),Button.ID.YELLOW	, Gravity.yellow)	);
		
		preview	= new Planet( -back.getWidth()/4, -20 - back.getHeight(), 0 ,0 , 0, 10);
		
		state = Gravity.STATE.CLOSED;
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
		back.draw(batch);
		for(Button b:buttons)
		{
			if( state == Gravity.STATE.CLOSED && b.id == Button.ID.BUTDO) continue;
			if( state == Gravity.STATE.OPEN	  && b.id == Button.ID.BUTUP) continue;
			b.draw(batch);
			preview.draw(batch);
		}
		
		preview.draw(batch);
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
