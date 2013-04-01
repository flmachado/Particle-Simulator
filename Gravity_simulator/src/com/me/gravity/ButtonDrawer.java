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
		
		
		buttons.add(new Button( new Vector(-Gravity.W/2+20,-Gravity.H/2+20),	new Vector(40,40),Button.ID.BUTUP	, Gravity.barrowup ));
		buttons.add(new Button( new Vector(-Gravity.W/2+20,-Gravity.H/2+20),	new Vector(40,40),Button.ID.BUTDO	, Gravity.barrowdown ));
		buttons.add(new Button( new Vector(20,20-back.getHeight()), 		new Vector(40,40),Button.ID.RED		, Gravity.red)		);
		buttons.add(new Button( new Vector(60,60-back.getHeight()), 		new Vector(40,40),Button.ID.GREEN	, Gravity.green)	);
		buttons.add(new Button( new Vector(20,60-back.getHeight()), 		new Vector(40,40),Button.ID.BLUE	, Gravity.blue)		);
		buttons.add(new Button( new Vector(60,20-back.getHeight()), 		new Vector(40,40),Button.ID.YELLOW	, Gravity.yellow)	);
		
		preview		= new Planet( 100 , 200, 0 ,0 , 0, 100);
		
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
		System.out.println(toact);
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
		case RED:
			System.out.println("RED");
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
			back.setPosition(-Gravity.W/2, -back.getHeight()-Gravity.H/2);
			state = Gravity.STATE.CLOSED;
		}
	}
	
}
