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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D.Float;

import se.openflisp.sls.Signal;

/**	
 * InputSignalView for painting input signals
 * 
 * @author Daniel Svensson <daniel@dsit.se>
 * @version 1.0
 */
@SuppressWarnings("serial")
public class InputSignal extends SignalView {
	//Since this is a JButton we have to explain what shape it is
	Shape shape;
	
	InputSignal(Signal signal) {
		super(signal);
	}

	/**
	 * Custom paint method so our button looks like a signal
	 */
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (getModel().isArmed() || signal.getState() == Signal.State.HIGH ) {
			g2.setColor(Color.BLUE);
		} else {
			g2.setColor(getBackground());
		}
		g2.fillOval(0,0, arcLength-1, arcLength-1);
	}
	
	/**
	 * Paints the border around the signal
	 */
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getForeground());
		g2.setStroke(new BasicStroke(4));
		g2.drawLine(arcLength, btnSize.height/2, btnSize.width, btnSize.height/2);
		g2.setStroke(new BasicStroke(1));
		g2.drawOval(0,0,arcLength-1,arcLength-1);
	}
	
	/**
	 * Will decide if the given x and y - values are within our button
	 * @param	x	the x value
	 * @param	y	the y value
	 */
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Float(0,0,arcLength-1,arcLength-1);
		}
		return shape.contains(x, y);
	}
}
