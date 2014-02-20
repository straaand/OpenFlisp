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
package se.openflisp.sls;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import se.openflisp.sls.event.ComponentEventDelegator;
import org.mockito.Mockito;

public abstract class ComponentTest {

	private Component component;
	public String id;
	public ComponentEventDelegator delegatorMock;
	public Input inputMock1; //All Components have at least 1 input
	public Output outputMock1; //and at least 1 output

	@Before
	public void setup() {
		id = "identifier";
		component = getInstance(id);
		delegatorMock = Mockito.mock(ComponentEventDelegator.class);

		//Mockings for classes extending ComponentTest
		inputMock1 = Mockito.mock(Input.class);
		outputMock1 = Mockito.mock(Output.class);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructingWithNullIdentifier() {
		getInstance(null);
	}

	@Test
	public void gettingEventDelegatorNotNull() {
		assertNotNull(component.getEventDelegator());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructingWithDelegatorWithNullIdentifier() {
		getInstance(null, delegatorMock);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructingWithNullDelegator() {
		getInstance(id, null);
	}

	@Test
	public void testGettingEventDelegator() {
		Component component = getInstance(id, delegatorMock);
		assertSame(delegatorMock, component.getEventDelegator());
	}
	
	@Test
	public void testGettingIdentifier() {
		Component component = getInstance(id, delegatorMock);
		assertEquals(id, component.getIdentifier());
	}

	@Test
	public void testGettingInputNotNull() {
		assertNotNull(component.getInput(id));
	}

	@Test
	public void testGettingInputReturnsSameObject() {
		assertSame(component.getInput(id), component.getInput(id));
	}

	@Test
	public void testGettingInput() {
		Input input = component.getInput(id);
		assertThat(component.getInputs(), hasItem(input));
	}
	
	@Test
	public void testGettingInputs() {
		Input input = component.getInput(id);
		Input input2 = component.getInput("identifier2");
		assertThat(component.getInputs(), hasItems(input, input2));
	}

	@Test
	public void testGettingDifferentInputsForDifferentId() {
		Input tempInput1 = component.getInput(id);
		Input tempInput2 = component.getInput("identifier2");
		assertNotSame(tempInput1, tempInput2);
	}

	@Test
	public void testGettingOutputNotNull() {
		assertNotNull(component.getOutput(id));
	}

	@Test
	public void testGettingOutputReturnSameObject() {
		Output tempOutput1 = component.getOutput(id);
		Output tempOutput2 = component.getOutput(id);
		assertSame(tempOutput1, tempOutput2);
	}

	@Test
	public void testGettingOutput() {
		Output output = component.getOutput(id);
		assertThat(component.getOutputs(), hasItem(output));
	}
	
	@Test
	public void testGettingOutputs() {
		Output output = component.getOutput(id);
		Output output2 = component.getOutput("identifier2");
		assertThat(component.getOutputs(), hasItems(output, output2));
	}

	@Test
	public void testGettingDifferentOutputsForDifferentId() {
		Output tempOutput1 = component.getOutput(id);
		Output tempOutput2 = component.getOutput("identifier2");
		assertNotSame(tempOutput1, tempOutput2);
	}

	@Test
	public void testSettingOutputState() {
		component.setOutputState(id, Signal.State.HIGH);
		assertEquals(Signal.State.HIGH, component.getOutput(id).getState());
	}

	protected abstract Component getInstance(String identifier);

	protected abstract Component getInstance(String identifier, ComponentEventDelegator delegator);
}
