package se.openflisp.gui.swing;
import se.openflisp.gui.swing.CircuitBord;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CircuitBoardListener extends MouseAdapter{
	@Override
	public void mouseMoved(MouseEvent e) {
		((CircuitBord)e.getSource()).setCords(e.getX(),e.getY());
	}
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
}
