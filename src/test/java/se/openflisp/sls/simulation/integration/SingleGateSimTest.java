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
package se.openflisp.sls.simulation.integration;

import static org.junit.Assert.*;

import org.junit.Before;

import se.openflisp.sls.Signal;
import se.openflisp.sls.component.*;

/**
 * Abstract class for test classes simulating single gates to extend.
 *  
 * @author Pär Svedberg <rockkuf@gmail.com>
 * @version 1.0
 */
public abstract class SingleGateSimTest extends SimulationTest{
	public ConstantGate constantHigh, constantLow, constantFloating;
	private String constantHighID, constantLowID, constantFloatingID;
	
	@Before
	public void setup() {
		super.setup();
		constantHighID = "ConstantHigh";
		constantLowID = "ConstantLow";
		constantFloatingID = "ConstantFloating";
		
		constantHigh = new ConstantGate(constantHighID, Signal.State.HIGH);
		constantLow = new ConstantGate(constantLowID, Signal.State.LOW);
		constantFloating = new ConstantGate(constantFloatingID, Signal.State.FLOATING);

	}
	
	/**
	 * Help method for simulation of a single Gates' output. Every Gate passed to
	 * the method is added to a Circuit, connected and then simulated. 
	 * Checks after simulation in a Circuit if the Output Signal.State of the given 
	 * gateToSimulate is equal to the given value of expectedOutput.
	 * 
	 * @param inputGates		Each Output from a Gate[] is connected to 
	 * 							an Input of the gateToSimulate.  
	 * @param expectedOutput	A Signal.State value to compare for equality with 
	 * 							the value of gateToSimulates Output.
	 * @param gateToSimulate	The Gate which Output is to be tested
	 */
	public void helpSimulate(Gate[] inputGates, Signal.State expectedOutput, Gate gateToSimulate) {
		circuit.addComponent(gateToSimulate);
		
		if (inputGates.length > 0) {
			int i = 0;
			for (Gate inputGate : inputGates) {
				String inputID = Integer.toString(i);
				circuit.addComponent(inputGate);
				gateToSimulate.getInput(inputID).connect(inputGate.getOutput());
				assertTrue(gateToSimulate.getInput(inputID).isConnected());
				assertTrue(inputGate.getOutput().isConnected());
				i++;
			}
		}
		try {
			Thread.sleep(1300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expectedOutput, gateToSimulate.getOutput().getState());
	}

}
