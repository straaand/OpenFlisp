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
package se.openflisp.sls.simulation;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

import se.openflisp.sls.Component;
import se.openflisp.sls.exception.ComponentEvaluationException;

public class CircuitTest {

	public Circuit circuit;
	
	public Component component1, component2, component3;
	
	@Before
	public void setup() {
		circuit = new Circuit();
		component1 = mockComponent("1");
		component2 = mockComponent("2");
		component3 = mockComponent("3");
	}
	
	protected Component mockComponent(String identifier) {
		Component component = new Component(identifier) {
			public void evaluate() throws ComponentEvaluationException {
				throw new IllegalStateException();
			}
		};
		return component;
	}
	
	@Test
	public void testCircuitIsEmptyAtInit() {
		assertTrue(circuit.getComponents().isEmpty());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddingNullComponent() {
		circuit.addComponent(null);
	}
	
	@Test
	public void testAddingUnconnectedComponent() {
		circuit.addComponent(component1);
		assertThat(circuit.getComponents(), hasItem(component1));
	}

	@Test
	public void testAddingOutputConnectedComponent() {
		component1.getOutput("1").connect(component2.getInput("2"));
		circuit.addComponent(component1);
		assertThat(circuit.getComponents(), hasItems(component1, component2));
	}
	
	@Test
	public void testAddingInputConnectedComponent() {
		component1.getOutput("1").connect(component2.getInput("2"));
		circuit.addComponent(component2);
		assertThat(circuit.getComponents(), hasItems(component1, component2));
	}
	
	@Test
	public void testAddingBothInputAndOutputConnectedComponent() {
		component1.getOutput("1").connect(component2.getInput("2"));
		component3.getOutput("1").connect(component1.getInput("2"));
		circuit.addComponent(component1);
		assertThat(circuit.getComponents(), hasItems(component1, component2, component3));
	}
	
	@Test
	public void testConnectingOutputComponentAfterAdd() {
		circuit.addComponent(component1);
		component1.getOutput("1").connect(component2.getInput("2"));
		assertThat(circuit.getComponents(), hasItems(component1, component2));
	}
	
	@Test
	public void testConnectingInputComponentAfterAdd() {
		circuit.addComponent(component2);
		component1.getOutput("1").connect(component2.getInput("2"));
		assertThat(circuit.getComponents(), hasItems(component1, component2));
	}
	
	@Test
	public void testConnectingInputAndOutputComponentsAfterAdd() {
		circuit.addComponent(component1);
		component1.getOutput("1").connect(component2.getInput("2"));
		component3.getOutput("1").connect(component1.getInput("2"));
		assertThat(circuit.getComponents(), hasItems(component1, component2, component3));
	}
}
