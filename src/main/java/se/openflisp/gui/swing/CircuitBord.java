package se.openflisp.gui.swing;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JPanel;

import bibliothek.gui.dock.DefaultDockable;

public class CircuitBord extends JPanel{
	//TODO make the JPanel aware when an object is dropped on the frame.
	Point  Component;
	
	public CircuitBord(){
		setName("test");
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
	}
}
