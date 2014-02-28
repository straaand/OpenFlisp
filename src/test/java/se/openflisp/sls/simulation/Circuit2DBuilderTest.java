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

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import se.openflisp.sls.Component;
import se.openflisp.sls.component.NotGate;
import se.openflisp.sls.event.ComponentEventDelegator;

public class Circuit2DBuilderTest {

	public Circuit2DBuilder builder = new Circuit2DBuilder();
	
	public ComponentEventDelegator delegator;
	public Component component1, component2;
	public Point position1, position2;
	public String id1, id2;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		delegator = Mockito.mock(ComponentEventDelegator.class);
		position1 = Mockito.mock(Point.class);
		position2 = Mockito.mock(Point.class);
		id1 = "id1";
		id2 = "id2";
		component1 = mockComponent(id1);
		component2 = mockComponent(id2);
	}
	
	protected Component mockComponent(String identifier) {
		return new NotGate(identifier);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNullComponent() {
		builder.addComponent(null, position1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNullComponentPosition() {
		builder.addComponent(component1, null);
	}
	
	@Test
	public void testAddComponent() {
		builder.addComponent(component1, position1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testReAddComponent() {
		builder.addComponent(component1, position1);
		builder.addComponent(component1, position1);
	}
	
	@Test
	public void testAddConnectionWithAllIdentifiers() {
		builder.addConnection("input", "1", "output", "2");
	}
	
	@Test
	public void testAddConnectionWithCompositeOutput() {
		builder.addConnection("input", "1", "output:2");
	}
	
	@Test
	public void testBuildingOneComponent() {
		builder.addComponent(component1, position1);
		Circuit2D circuit = builder.build();
		assertThat(circuit.getComponents(), hasItem(component1));
	}
	
	@Test
	public void testBuildingOneComponentSetsPosition() {
		builder.addComponent(component1, position1);
		Circuit2D circuit = builder.build();
		assertSame(circuit.getComponentLocation(component1), position1);
	}
	
	@Test
	public void testBuildingTwoComponents() {
		builder.addComponent(component1, position1);
		builder.addComponent(component2, position2);
		Circuit2D circuit = builder.build();
		assertThat(circuit.getComponents(), hasItems(component1, component2));
	}
	
	@Test
	public void testBuildingTwoComponentsSetsPosition() {
		builder.addComponent(component1, position1);
		builder.addComponent(component2, position2);
		Circuit2D circuit = builder.build();
		assertSame(circuit.getComponentLocation(component1), position1);
		assertSame(circuit.getComponentLocation(component2), position2);
	}
	
	@Test
	public void testBuildingTwoConnectedComponents() {
		builder.addComponent(component1, position1);
		builder.addComponent(component2, position2);
		builder.addConnection(id1, "1", id2);
		builder.build();
		assertSame(component1.getInput("1").getConnection().getOwner(), component2);
	}
	
	@Test
	public void testBuildingTwoConnectedComponentsWithCompositeIndentifier() {
		builder.addComponent(component1, position1);
		builder.addComponent(component2, position2);
		builder.addConnection(id1, "1", id2 + ":2");
		builder.build();
		assertSame(component1.getInput("1").getConnection().getOwner(), component2);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testBuildingWithUnknownOutputComponent() {
		builder.addComponent(component1, position1);
		builder.addConnection(id1, "1", "UNKNOWN");
		builder.build();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testBuildingWithUnknownInputComponent() {
		builder.addComponent(component2, position2);
		builder.addConnection("UNKNOWN", "1", id2);
		builder.build();
	}
	
	@Test
	public void testHasComponentsTrue() {
		builder.addComponent(component1, position1);
		assertTrue(builder.hasComponent(component1.getIdentifier()));
	}
	
	@Test
	public void testHasComponentsFalse() {
		builder.addComponent(component1, position1);
		assertFalse(builder.hasComponent(component2.getIdentifier()));
	}
}
