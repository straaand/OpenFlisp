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
import org.junit.Test;

import se.openflisp.sls.Signal;
import se.openflisp.sls.component.*;
import se.openflisp.sls.simulation.Circuit;

/**
 * Tests for boolean expressions using gates in a circuit.
 * 
 * @author Pär Svedberg <rockkuf@gmail.com>
 * Version 1.0
 */
public class MultiGateSimTest extends SimulationTest {
	private String andGateID1, andGateID2, orGateID1, orGateID2, xorGateID1,
		nandGateID1, nandGateID2, nandGateID3, nandGateID4, nandGateID5, 
		notGateID1, notGateID2, notGateID3;
	private ConstantGate xGate, yGate, zGate, wGate;
	private boolean x, y, z, w;
	
	@Before
	public void setup() {
		super.setup();
		andGateID1 = "AndGate1";
		andGateID2 = "AndGate2";
		orGateID1 = "OrGate1";
		orGateID2 = "OrGate2";
		notGateID1 = "NotGate1";
		notGateID2 = "NotGate2";
		notGateID3 = "NotGate3";
		nandGateID1 = "NandGate1";
		nandGateID2 = "NandGate2";
		nandGateID3 = "NandGate3";
		nandGateID4 = "NandGate4";
		nandGateID5 = "NandGate5";
		xorGateID1 = "XorGate1";
		
		x = y = z = w = false;
	}
	
	/*		x∙(y+z)		*/
	
	@Test
	public void test01() {
		boolean[][] truthtable = getTruthTable(3);
		
		for (int i = 0; i < 8; i++) {
			Circuit testCircuit = new Circuit();
			testCircuit.getSimulation().start();
			for (int k = 0; k < 3; k++) {
				if (k == 0) {
					x = truthtable[i][k];
					xGate = x ? constantHigh : constantLow;
				}
				else if (k == 1) {
					y = truthtable[i][k];
					yGate = y ? constantHigh : constantLow;
				}
				else if (k == 2) {
					z = truthtable[i][k];
					zGate = z ? constantHigh : constantLow;
				}
			}
			OrGate orGate = new OrGate(orGateID1);
			AndGate andGate = new AndGate(andGateID1);
			connectGates(testCircuit, new Gate[] {yGate, zGate}, orGate);
			connectGates(testCircuit, new Gate[] {xGate, orGate}, andGate);
			
			Signal.State expectedValue = x && (y || z) ? Signal.State.HIGH : Signal.State.LOW;
			waitForSignalChange(andGate);
			assertEquals(expectedValue, andGate.getOutput().getState());
			testCircuit.getSimulation().interrupt();
		}
	}
	
	/*		x∙y + x∙z		*/
	
	@Test
	public void test02() {
		boolean[][] truthtable = getTruthTable(3);
		
		for (int i = 0; i < 8; i++) {
			Circuit testCircuit = new Circuit();
			testCircuit.getSimulation().start();
			for (int k = 0; k < 3; k++) {
				if (k == 0) {
					x = truthtable[i][k];
					xGate = x ? constantHigh : constantLow;
				}
				else if (k == 1) {
					y = truthtable[i][k];
					yGate = y ? constantHigh : constantLow;
				}
				else if (k == 2) {
					z = truthtable[i][k];
					zGate = z ? constantHigh : constantLow;
				}
			}
			OrGate orGate = new OrGate(orGateID1);
			AndGate andGate1 = new AndGate(andGateID1);
			AndGate andGate2 = new AndGate(andGateID2);
			connectGates(testCircuit, new Gate[] {xGate, yGate}, andGate1);
			connectGates(testCircuit, new Gate[] {xGate, zGate}, andGate2);
			connectGates(testCircuit, new Gate[] {andGate1, andGate2}, orGate);
			
			Signal.State expectedValue = (x && y) || (x && z) ? Signal.State.HIGH : Signal.State.LOW;
			waitForSignalChange(orGate);
			assertEquals(expectedValue, orGate.getOutput().getState());
			testCircuit.getSimulation().interrupt();
		}
	}
	
	/*		x+(y∙z)		*/
	
	@Test
	public void test03() {
		boolean[][] truthtable = getTruthTable(3);
		
		for (int i = 0; i < 8; i++) {
			Circuit testCircuit = new Circuit();
			testCircuit.getSimulation().start();
			for (int k = 0; k < 3; k++) {
				if (k == 0) {
					x = truthtable[i][k];
					xGate = x ? constantHigh : constantLow;
				}
				else if (k == 1) {
					y = truthtable[i][k];
					yGate = y ? constantHigh : constantLow;
				}
				else if (k == 2) {
					z = truthtable[i][k];
					zGate = z ? constantHigh : constantLow;
				}
			}
			OrGate orGate = new OrGate(orGateID1);
			AndGate andGate = new AndGate(andGateID1);
			connectGates(testCircuit, new Gate[] {yGate, zGate}, andGate);
			connectGates(testCircuit, new Gate[] {xGate, andGate}, orGate);
			
			Signal.State expectedValue = x || (y && z) ? Signal.State.HIGH : Signal.State.LOW;
			waitForSignalChange(orGate);
			assertEquals(expectedValue, orGate.getOutput().getState());
			testCircuit.getSimulation().interrupt();
		}
	}
	
	/*		(x+y)∙(x+z)		*/
	@Test
	public void test04() {
		boolean[][] truthtable = getTruthTable(3);
		
		for (int i = 0; i < 8; i++) {
			Circuit testCircuit = new Circuit();
			testCircuit.getSimulation().start();
			for (int k = 0; k < 3; k++) {
				if (k == 0) {
					x = truthtable[i][k];
					xGate = x ? constantHigh : constantLow;
				}
				else if (k == 1) {
					y = truthtable[i][k];
					yGate = y ? constantHigh : constantLow;
				}
				else if (k == 2) {
					z = truthtable[i][k];
					zGate = z ? constantHigh : constantLow;
				}
			}
			OrGate orGate1 = new OrGate(orGateID1);
			OrGate orGate2 = new OrGate(orGateID2);
			AndGate andGate = new AndGate(andGateID1);
			connectGates(testCircuit, new Gate[] {xGate, yGate}, orGate1);
			connectGates(testCircuit, new Gate[] {xGate, zGate}, orGate2);
			connectGates(testCircuit, new Gate[] {orGate1, orGate2}, andGate);
			
			Signal.State expectedValue = (x || y) && (x || z) ? Signal.State.HIGH : Signal.State.LOW;
			waitForSignalChange(andGate);
			assertEquals(expectedValue, andGate.getOutput().getState());
			testCircuit.getSimulation().interrupt();
		}
	}
	
	/*		5 NAND-gates connected to XOR functionality compared to an XOR-gate		*/
	
	@Test
	public void test05() {
		boolean[][] truthtable = getTruthTable(2);
		
		for (int i = 0; i < 4; i++) {
			Circuit testCircuit = new Circuit();
			testCircuit.getSimulation().start();
			for (int k = 0; k < 2; k++) {
				if (k == 0) {
					x = truthtable[i][k];
					xGate = x ? constantHigh : constantLow;
				}
				else if (k == 1) {
					y = truthtable[i][k];
					yGate = y ? constantHigh : constantLow;
				}
			}
			NandGate nandGate1 = new NandGate(nandGateID1);
			NandGate nandGate2 = new NandGate(nandGateID2);
			NandGate nandGate3 = new NandGate(nandGateID3);
			NandGate nandGate4 = new NandGate(nandGateID4);
			NandGate nandGate5 = new NandGate(nandGateID5);
			XorGate xorGate = new XorGate(xorGateID1);
			connectGates(testCircuit, new Gate[] {xGate, yGate}, xorGate);
			connectGates(testCircuit, new Gate[] {xGate, xGate}, nandGate1);
			connectGates(testCircuit, new Gate[] {yGate, yGate}, nandGate2);
			connectGates(testCircuit, new Gate[] {xGate, nandGate2}, nandGate3);
			connectGates(testCircuit, new Gate[] {nandGate1, yGate}, nandGate4);
			connectGates(testCircuit, new Gate[] {nandGate3, nandGate4}, nandGate5);
			
			waitForSignalChange(nandGate5);
			assertNotEquals(Signal.State.FLOATING, xorGate.getOutput().getState());
			assertEquals(xorGate.getOutput().getState(), nandGate5.getOutput().getState());
			testCircuit.getSimulation().interrupt();
		}
	}
	
	/*		(x+y)⋅(w'+z)		*/
	
	@Test
	public void test06() {
		boolean[][] truthtable = getTruthTable(4);
		
		for (int i = 0; i < 8; i++) {
			Circuit testCircuit = new Circuit();
			testCircuit.getSimulation().start();
			for (int k = 0; k < 4; k++) {
				if (k == 0) {
					x = truthtable[i][k];
					xGate = x ? constantHigh : constantLow;
				}
				else if (k == 1) {
					y = truthtable[i][k];
					yGate = y ? constantHigh : constantLow;
				}
				else if (k == 2) {
					z = truthtable[i][k];
					zGate = z ? constantHigh : constantLow;
				}
				else if (k == 3) {
					w = truthtable[i][k];
					wGate = w ? constantHigh : constantLow;
				}
			}
			OrGate orGate1 = new OrGate(orGateID1);
			OrGate orGate2 = new OrGate(orGateID2);
			AndGate andGate = new AndGate(andGateID1);
			NotGate notGate = new NotGate(notGateID1);
			connectGates(testCircuit, new Gate[] {xGate, yGate}, orGate1);
			connectGates(testCircuit, new Gate[] {wGate}, notGate);
			connectGates(testCircuit, new Gate[] {notGate, zGate}, orGate2);
			connectGates(testCircuit, new Gate[] {orGate1, orGate2}, andGate);
			
			Signal.State expectedValue = (x || y) && (!w || z) ? Signal.State.HIGH : Signal.State.LOW;
			waitForSignalChange(andGate);
			assertEquals(expectedValue, andGate.getOutput().getState());
			testCircuit.getSimulation().interrupt();
		}
	}
	
	/*		(x+y)⋅(w⋅z)'		*/
	
	@Test
	public void test07() {
		boolean[][] truthtable = getTruthTable(4);
		
		for (int i = 0; i < 8; i++) {
			Circuit testCircuit = new Circuit();
			testCircuit.getSimulation().start();
			for (int k = 0; k < 4; k++) {
				if (k == 0) {
					x = truthtable[i][k];
					xGate = x ? constantHigh : constantLow;
				}
				else if (k == 1) {
					y = truthtable[i][k];
					yGate = y ? constantHigh : constantLow;
				}
				else if (k == 2) {
					z = truthtable[i][k];
					zGate = z ? constantHigh : constantLow;
				}
				else if (k == 3) {
					w = truthtable[i][k];
					wGate = w ? constantHigh : constantLow;
				}
			}
			OrGate orGate = new OrGate(orGateID1);
			AndGate andGate = new AndGate(andGateID1);
			NandGate nandGate = new NandGate(nandGateID1);
			connectGates(testCircuit, new Gate[] {xGate, yGate}, orGate);
			connectGates(testCircuit, new Gate[] {wGate, zGate}, nandGate);
			connectGates(testCircuit, new Gate[] {orGate, nandGate}, andGate);
			
			Signal.State expectedValue = (x || y) && !(w && z) ? Signal.State.HIGH : Signal.State.LOW;
			waitForSignalChange(andGate);
			assertEquals(expectedValue, andGate.getOutput().getState());
			testCircuit.getSimulation().interrupt();
		}
	}
	
	/*		(x + y)’ = x’ ∙ y’		*/
	
	@Test
	public void test08() {
		boolean[][] truthtable = getTruthTable(2);
		
		for (int i = 0; i < 4; i++) {
			Circuit testCircuit = new Circuit();
			testCircuit.getSimulation().start();
			for (int k = 0; k < 2; k++) {
				if (k == 0) {
					x = truthtable[i][k];
					xGate = x ? constantHigh : constantLow;
				}
				else if (k == 1) {
					y = truthtable[i][k];
					yGate = y ? constantHigh : constantLow;
				}
			}
			OrGate orGate = new OrGate(orGateID1);
			AndGate andGate = new AndGate(andGateID1);
			NotGate notGate1 = new NotGate(notGateID1);
			NotGate notGate2 = new NotGate(notGateID2);
			NotGate notGate3 = new NotGate(notGateID3);
			connectGates(testCircuit, new Gate[] {xGate, yGate}, orGate);
			connectGates(testCircuit, new Gate[] {orGate}, notGate1);
			connectGates(testCircuit, new Gate[] {xGate}, notGate2);
			connectGates(testCircuit, new Gate[] {yGate}, notGate3);
			connectGates(testCircuit, new Gate[] {notGate2, notGate3}, andGate);
			
			waitForSignalChange(andGate);
			assertNotEquals(Signal.State.FLOATING, notGate1.getOutput().getState());
			assertEquals(andGate.getOutput().getState(), notGate1.getOutput().getState());
			testCircuit.getSimulation().interrupt();
		}
	}
	
	/**
	 * Returns a truth table of 1-4 variables.
	 * 
	 * @param variables Determines how many variables the returned truth table
	 * 					should have. Currently 1-4 is supported.
	 * 					If variables > 4 or < 2, a truth table for 1 is returned. 
	 * @return
	 */
	public boolean[][] getTruthTable(int variables) {
		if (variables == 2) { 
			return new boolean[][]{
				{false, false},
				{false, true},
				{true, false},
				{true, true}
			}; 
		} else if (variables == 3) {
			return new boolean[][]{
				{false, false, false},
				{false, false, true},
				{false, true, false},
				{false, true, true},
				{true, false, false},
				{true, false, true},
				{true, true, false},
				{true, true, true}
			};
		} else if (variables == 4) {
			return new boolean[][]{
				{false, false, false, false},
				{false, false, false, true},
				{false, false, true, false},
				{false, false, true, true},
				{false, true, false, false},
				{false, true, false, true},
				{false, true, true, false},
				{false, true, true, true},
				{true, false, false, false},
				{true, false, false, true},
				{true, false, true, false},
				{true, false, true, true},
				{true, true, false, false},
				{true, true, false, true},
				{true, true, true, false},
				{true, true, true, true}
			};
		} else {
			return new boolean[][]{ {false}, {true} };	
		}
	}
}
