package com.me.gravity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {
	
	Vector Pos;
	Vector Size;
	Sprite T;
	
	public enum ID {ZERO, RED, GREEN, BLUE, YELLOW, BUTUP, BUTDO};
	ID id;
	
	public Button(Vector Pos, Vector Size, ID id, Texture T)
	{
		this.Pos = Pos;
		this.Size = Size;
		this.id = id;
		this.T = new Sprite(T);
		this.T.setSize( (float)Size.x, (float)Size.y);
		this.T.setOrigin((float)Size.x/2, (float)Size.y/2);
		this.T.setPosition((float)Pos.x - T.getWidth()/2 , (float)Pos.y - T.getHeight()/2);
	}
	
	public void draw(SpriteBatch b)
	{
		T.draw(b);
	}
	
	public ID isTouched( Vector touch)
	{
		if(touch.x > Pos.x - Size.x/2 && touch.x < Pos.x + Size.x/2 && 
				touch.y > Pos.y - Size.y/2 && touch.y < Pos.y + Size.y/2){
			return id;}
		
		return ID.ZERO;
	}
	
	public void move(float dx, float dy)
	{
		this.Pos.x += dx;
		this.Pos.y += dy;
		this.T.setPosition((float)Pos.x - T.getWidth()/2 , (float)Pos.y - T.getHeight()/2);
	}
	
}
