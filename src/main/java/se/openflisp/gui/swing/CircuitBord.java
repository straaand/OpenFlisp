package se.openflisp.gui.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import se.openflisp.sls.Component;
import se.openflisp.sls.simulation.Circuit;

import bibliothek.gui.dock.DefaultDockable;

public class CircuitBord extends JPanel{
	Circuit circuit;
	
	public CircuitBord(){
		setName("test");
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		Graphics2D  g2 = (Graphics2D) g;
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(0,0,this.getWidth(),this.getHeight());
		g2.setColor(Color.GRAY);
		paintGrid(g2,this.getWidth(),this.getHeight());
		g2.setColor(Color.red);
		Line2D line = new Line2D.Float(20, 50, 250, 260);               
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//g2.draw(line); //pending  
		g2.dispose();
	}
	
	public void paintGrid(Graphics g, int gridWidth, int gridHeight) {
		  for(int i=1; i<gridWidth; i=i+10)
		  {
			  g.drawLine(i,	0,	i,	gridHeight);          
		  }      

		  for(int i=1; i<gridHeight; i=i+10)
		  {      
		      g.drawLine(0, i, gridWidth, i);          
		  } 
	}
}
