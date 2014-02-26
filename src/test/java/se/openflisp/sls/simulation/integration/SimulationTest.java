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

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;

import se.openflisp.sls.Signal;
import se.openflisp.sls.component.*;
import se.openflisp.sls.simulation.Circuit;

/**
 * Abstract class for simulation test classes to extend. Creates a circuit, starts and stop the simulation.
 * 
 * @author Pär Svedberg <rockkuf@gmail.com>
 * @version 1.0
 */
public abstract class SimulationTest {
	public Circuit circuit;
	public ConstantGate constantHigh, constantLow, constantFloating;
	private String constantHighID, constantLowID, constantFloatingID;
	
	@Before
	public void setup() {
		constantHighID = "ConstantHigh";
		constantLowID = "ConstantLow";
		constantFloatingID = "ConstantFloating";
		
		constantHigh = new ConstantGate(constantHighID, Signal.State.HIGH);
		constantLow = new ConstantGate(constantLowID, Signal.State.LOW);
		constantFloating = new ConstantGate(constantFloatingID, Signal.State.FLOATING);
		
		circuit = new Circuit();
		circuit.getSimulation().start();
	}
	
	@After
	public void stopSim() {
		circuit.getSimulation().interrupt();
	}
	
	/**
	 * Connects the outputs of the gates in fromOutputs to inputs of gate toInput.
	 * Also verifies that connections were successful.
	 * Adds the toInput gate to the given simCircuit.
	 * 
	 * @param simCircuit	The circuit that gate toInput is added to.
	 * @param fromOutputs	An array of gates which outputs is to be connected
	 * 						to the inputs of toInput.
	 * @param toInput		A single gate which will have 1+ gates connected to its input(s).
	 */
	public void connectGates(Circuit simCircuit, Gate[] fromOutputs, Gate toInput) {
		if (!simCircuit.contains(toInput)) { 
			simCircuit.addComponent(toInput);
		}
		if (fromOutputs.length > 0) {
			int i = 0;
			for (Gate output : fromOutputs) {
				String inputID = Integer.toString(i);
				toInput.getInput(inputID).connect(output.getOutput());
				assertTrue(toInput.getInput(inputID).isConnected());
				assertTrue(output.getOutput().isConnected());
				i++;
			}
		}		
	}
	
	public void waitForSignalChange(Gate gateToTest) {
		boolean waitedEnough = false;
		long timeout = System.currentTimeMillis() + 1200;
		
		while (!waitedEnough) {
			if (!gateToTest.getOutput().getState().equals(Signal.State.FLOATING)
				&& (System.currentTimeMillis() + 900) > timeout ) {
				waitedEnough = true;
				System.out.println("Exit by output signal change.");
			} else if (System.currentTimeMillis() > timeout) {
				waitedEnough = true;
				System.out.println("Exit by timeout.");
			}
		}
	}
}
