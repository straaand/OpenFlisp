/*
 * Copyright (C) 2014- See AUTHORS file.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.openflisp.gui.swing.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D.Float;

import javax.swing.JButton;

import se.openflisp.sls.Input;
import se.openflisp.sls.Signal;

/**	
 * A basic model for viewing signals
 * 
 * @author Daniel Svensson <daniel@dsit.se>
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SignalView extends JButton {
	
	//The signal we make a view for
	protected Signal signal;
	
	//The size of the signal-circle
	protected int arcLength = ComponentView.componentSize/5;
	
	//The size of the whole signal object
	public Dimension btnSize = new Dimension(ComponentView.componentSize/2,arcLength); 
	
	//We need this to determine if a x.y coordinate is whithin this button
	private Shape shape;
	
	SignalView(Signal signal){
		setPreferredSize(btnSize);
		this.signal = signal;
		setContentAreaFilled(false);
		
		//this.Signal
	}
	/**
	 * Custom paint method so our button looks like a signal
	 */
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed() || signal.getState() == Signal.State.HIGH) {
			g.setColor(Color.BLUE);
		} else {
			g.setColor(getBackground());
		}
		if (signal instanceof Input) {
			g.fillOval(0,0, arcLength-1, arcLength-1);
		} else {
			g.fillOval((btnSize.width - (arcLength+1)),0, arcLength-1, arcLength-1);
		}
	}
	
	/**
	 * Paints the border around the signal
	 */
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getForeground());
		g2.setColor(getForeground());
		g2.setStroke(new BasicStroke(4));
		
		if (signal instanceof Input) {
			g2.drawLine(arcLength, btnSize.height/2, btnSize.width, btnSize.height/2);
			g2.setStroke(new BasicStroke(1));
			g2.drawOval(0,0,arcLength-1,arcLength-1);
		}	else {
			g2.drawLine(0, btnSize.height/2, btnSize.width - (arcLength+1), btnSize.height/2);
			g2.setStroke(new BasicStroke(1));
			g2.drawOval(btnSize.width - (arcLength+1),0,arcLength-1,arcLength-1);
		}
	}
	
	/**
	 * Will decide if the given x and y - values are within our button
	 * @param	x	the x value
	 * @param	y	the y value
	 */
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			if (signal instanceof Input)
				shape = new Float(0,0,arcLength-1,arcLength-1);
			else
				shape = new Float(btnSize.width - (arcLength+1),0, arcLength-1, arcLength-1);
		}
		return shape.contains(x, y);
	}
}
