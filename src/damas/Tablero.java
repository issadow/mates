package damas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public abstract class Tablero extends Canvas
{


	public void paint(Graphics g,boolean turno)
	{
		g.setColor(new Color(10,100,200,100));
		g.fillRect(0, 0, 1500, 810);
			
		if(!turno)
			{g.setColor(Color.red);
			g.fillRect(10, 400, 100, 100);
			}
		else 
			{g.setColor(Color.blue);
			g.fillRect(10, 400, 100, 100);
			}
	}

	public abstract void dibujarExtra(Graphics g);	
}
