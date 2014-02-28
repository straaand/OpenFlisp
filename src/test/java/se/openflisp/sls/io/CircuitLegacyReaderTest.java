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
package se.openflisp.sls.io;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import se.openflisp.sls.Component;
import se.openflisp.sls.component.Gate;
import se.openflisp.sls.component.NotGate;
import se.openflisp.sls.simulation.Circuit2D;

public class CircuitLegacyReaderTest {
	
	public CircuitLegacyReader reader;
	
	@Test(expected=ParseException.class)
	public void testComponentDefinitionWithNonNumericType() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:FAIL,TYPE:1,X=0,Y=0"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testComponentDefinitionWithUnknownType() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:99,X=0,Y=0"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testComponentDefinitionWithNonNumericIdentifier() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:x,X=0,Y=0"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testComponentDefinitionWithInvalidXPosition() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=-A,Y=0"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testComponentDefinitionWithInvalidYPosition() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0,Y=A"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testComponentDefinitionWithMissingIdentifier() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">TYPE:x,X=0,Y=0"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testComponentDefinitionWithMissingType() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,X=0,Y=0"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testComponentDefinitionWithMissingXPosition() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,Y=0"
		));
		reader.readCircuit();
	}

	@Test(expected=ParseException.class)
	public void testComponentDefinitionWithMissingYPosition() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testConnectionDefinitionWithNonNumericInput() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0,Y=0\n" +
			"IV[x]1\n" +
			"<"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testConnectionDefinitionWithInvalidInput() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0,Y=0\n" +
			"IV[A]1\n" +
			"<"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testConnectionDefinitionWithMissingInput() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0,Y=0\n" +
			"IV1\n" +
			"<"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testConnectionDefinitionWithMissingOutput() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0,Y=0\n" +
			"IV[0]\n" +
			"<"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testConnectionDefinitionWithInvalid() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0,Y=0\n" +
			"IV[0].+$%!\n" +
			"<"
		));
		reader.readCircuit();
	}
	
	@Test(expected=ParseException.class)
	public void testConnectionDefinitionWithoutComponent() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			"IV[0]1\n"
		));
		reader.readCircuit();
	}
	
	@Test
	public void testCircuitWithOneUnconnectedComponent() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0,Y=0\n"
		));
		Circuit2D circuit = reader.readCircuit();
		assertThat(circuit.getComponents(), hasItem(ComponentMatcher.component(NotGate.class, "u0")));
	}
	
	@Test
	public void testCircuitWithOneComponentConnectedToHigh() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0,Y=0\n" + 
			"IV[0]1\n" + 
			"<"
		));
		Circuit2D circuit = reader.readCircuit();
		assertThat(circuit.getComponents(), hasItem(InputConnectionMatcher.withConnectedInput("1", "0")));
	}
	
	@Test
	public void testCircuitWithOneComponentConnectedToLow() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0,Y=0\n" + 
			"IV[0]0\n" + 
			"<"
		));
		Circuit2D circuit = reader.readCircuit();
		assertThat(circuit.getComponents(), hasItem(InputConnectionMatcher.withConnectedInput("0", "0")));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCircuitWithMultipleUnconnectedComponents() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0,Y=0\n" + 
			"<\n" + 
			">NUM:1,TYPE:1,X=1,Y=1\n" + 
			"<"
		));
		Circuit2D circuit = reader.readCircuit();
		assertThat(circuit.getComponents(), hasItems(
			ComponentMatcher.component(NotGate.class, "u0"), 
			ComponentMatcher.component(NotGate.class, "u1"))
		);
	}
	
	@Test
	public void testCircuitWithConnectedComponents() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0,Y=0\n" + 
			"IV[0]0\n" + 
			"<\n" + 
			">NUM:1,TYPE:1,X=1,Y=1\n" + 
			"IV[0]u1\n" + 
			"<"
		));
		Circuit2D circuit = reader.readCircuit();
		assertThat(circuit.getComponents(), hasItem(InputConnectionMatcher.withConnectedInput("0", "0")));
		assertThat(circuit.getComponents(), hasItem(OutputConnectionMatcher.withConnectedOutput("u0", Gate.OUTPUT)));
		assertThat(circuit.getComponents(), hasItem(InputConnectionMatcher.withConnectedInput("u1", "0")));
		assertThat(circuit.getComponents(), hasItem(OutputConnectionMatcher.withConnectedOutput("u1", Gate.OUTPUT)));
	}
	
	@Test(expected=ParseException.class)
	public void testDuplicateComponentIdentifier() throws IOException, ParseException {
		reader = new CircuitLegacyReader(new StringReader(
			">NUM:0,TYPE:1,X=0,Y=0\n" + 
			"IV[0]0\n" + 
			"<\n" + 
			">NUM:0,TYPE:1,X=1,Y=1\n" + 
			"IV[0]u1\n" + 
			"<"
		));
		reader.readCircuit();
	}
	
	public static class ComponentMatcher extends TypeSafeMatcher<Component> {

		private final Class<? extends Component> component;
		private final String identifier;
		
		public ComponentMatcher(Class<? extends Component> component, String identifier) {
			this.component  = component;
			this.identifier = identifier;
		}
		
		@Override
		public void describeTo(Description description) {
			description.appendText("not a " + this.component.getSimpleName() + " with " + this.identifier + "as identifier");
		}

		@Override
		protected boolean matchesSafely(Component component) {
			return component.getClass().equals(this.component) && 
				   component.getIdentifier().equals(this.identifier);
		}
		
		@Factory
		public static <T> Matcher<Component> component(Class<? extends Component> component, String identifier) {
			return new ComponentMatcher(component, identifier);
		}
	}
	
	public static class OutputConnectionMatcher extends TypeSafeMatcher<Component> {

		private final String componentIdentifier;
		private final String outputIdentifier;
		
		public OutputConnectionMatcher(String componentIdentifier, String outputIdentifier) {
			this.componentIdentifier = componentIdentifier;
			this.outputIdentifier = outputIdentifier;
		}
		
		@Override
		public void describeTo(Description description) {
			description.appendText("missing output");
		}

		@Override
		protected boolean matchesSafely(Component component) {
			return component.getOutput(this.outputIdentifier)
							.getOwner()
							.getIdentifier()
							.equals(this.componentIdentifier);
		}
		
		@Factory
		public static <T> Matcher<Component> withConnectedOutput(String component, String output) {
			return new OutputConnectionMatcher(component, output);
		}
	}
	
	public static class InputConnectionMatcher extends TypeSafeMatcher<Component> {

		private final String componentIdentifier;
		private final String inputIdentifier;
		
		public InputConnectionMatcher(String componentIdentifier, String inputIdentifier) {
			this.componentIdentifier = componentIdentifier;
			this.inputIdentifier = inputIdentifier;
		}
		
		@Override
		public void describeTo(Description description) {
			description.appendText("missing output");
		}

		@Override
		protected boolean matchesSafely(Component component) {
			return component.getInput(this.inputIdentifier)
							.getOwner()
							.getIdentifier()
							.equals(this.componentIdentifier);
		}
		
		@Factory
		public static <T> Matcher<Component> withConnectedInput(String component, String output) {
			return new InputConnectionMatcher(component, output);
		}
	}
}
