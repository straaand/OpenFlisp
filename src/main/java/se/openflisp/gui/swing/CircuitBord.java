package se.openflisp.gui.swing;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.geom.Line2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import se.openflisp.sls.Component;
import se.openflisp.sls.simulation.Circuit;
import bibliothek.gui.dock.DefaultDockable;

public class CircuitBord extends JPanel implements DropTargetListener{
	Circuit circuit;
	private JLabel coordinates;
	private CircuitBoardListener MouseListener = new CircuitBoardListener();
	
	//Oskar testar
	private DropTarget dropTarget;
	
	public CircuitBord(){
		this.coordinates = new JLabel("0:0");
		coordinates.setForeground(Color.BLUE);
		add(coordinates);
		setName("test");
		addMouseListener(MouseListener);
		addMouseMotionListener(MouseListener);
		
		this.setTransferHandler( new CircuitBordTransferHandler() );
		//Oskar Testar
		dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE,
		        this, true, null);
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		Graphics2D  g2 = (Graphics2D) g;
		g2.clearRect(0, 0, 70, 20);
		g2.setColor(new Color(0xCC, 0xCC, 0xCC));
		g2.drawRect(0, 0, getWidth()-1, getHeight());
		g2.setColor(new Color(0xCD, 0xCD, 0xCD));
		paintGrid(g2,this.getWidth(),this.getHeight());
		paintComponents(g2);
		g2.dispose();	
	}
	public void setCords(int x,int y) {
		coordinates.setText(x+":"+y);
		repaint();
	}
	
	public void paintComponents(Graphics g) {
		coordinates.paint(g);
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

	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
		System.out.println("DU HAR DARGIT IN NÅGOT TILL DROPWINDOW");
		//nothing to do here.
		// TODO Auto-generated method stub
	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
		System.out.println("DU HAR DRAGIT UT NÅGOT UR DROPWINDOW");
		//Nothing to do here
		// TODO Auto-generated method stub
	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		System.out.println("DU DRAR NÅGOT ÖVER DROPWINDOW");
		//nothing to do here
		// TODO Auto-generated method stub
	}

	@Override
	public void drop(DropTargetDropEvent arg0) {
		System.out.println("DU HAR SLÄPPT NÅGOT I DROPWINDOW");
		//create a _new_ gate at the dropped location
		// TODO make the JPanel add the dropped item (if it is a componentView)
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		System.out.println("VA FAN ÄR DETTA?");
		// TODO Auto-generated method stub
	}
}