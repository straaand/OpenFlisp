package se.openflisp.gui.swing;

import java.awt.Component;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ComponentFactory {
	/**
	private Image notGateImg = new Image ("BILDEN");
	private Image constantGateImg = new Image ("BILDEN");
	private Image andGateImg = new Image ("BILDEN");
	private Image xorGateImg = new Image ("BILDEN");
	private Image nandGateImg = new Image ("BILDEN");
	private Image NorGateImg = new Image ("BILDEN");
	*/
	public static ComponentView createViewFromComponent(Component component) {

		try {
			/**if (component instanceof NotGate) {
				//TODO add the NotGate components image to the component
				//make sure the gate choose the right number of inputs
				return ComponentView(component, ImageIO.read("file");
			}
			else if (component instanceof ConstantGate {
				//TODO add the ConstantGate components image to the component
				//make sure the gate choose the right number of inputs
				 return ComponentView(component, ImageIO.read("file");
			}
			else if (component instanceof AndGate){
				//TODO add the AndGate components image to the component
				//make sure the gate choose the right number of inputs
				return ComponentView(component, ImageIO.read("file");	
						}
			else if (component instanceof XorGate) {
				//TODO add the XorGate components image to the component
				//make sure the gate choose the right number of inputs
				return ComponentView(component, ImageIO.read("file");
			}
			else if (component instanceof NandGate) {
				//TODO add the NandGate components image to the component
				//make sure the gate choose the right number of inputs
				return ComponentView(component, ImageIO.read("file");
			}
			else if (component instanceof NorGate) {
				//TODO add the NorGate components image to the component
				//make sure the gate choose the right number of inputs
				return ComponentView(component, ImageIO.read("file");
			}*/


			//TODO Change this so we don't always return the same thing.
			return new ComponentView(component, ImageIO.read(new URL("http://www.bbc.co.uk/schools/gcsebitesize/design/images/el_not_gate.gif")) );
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("oskars mamma:)");
			return null;
		}
	}
}
