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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class OutputTest extends SignalTest {

	public Output instance;
	public Input input1, input2;
	public Signal signal;
	
	@Override
	protected Signal getInstance(String identifier, Component owner) {
		return new Output(identifier, owner);
	}
	
	@Before
	public void setup() {
		super.setup();
		instance = new Output("identifier", owner);
		input1 = Mockito.mock(Input.class);
		input2 = Mockito.mock(Input.class);
		signal = Mockito.mock(Signal.class);
	}
	
	@Test
	public void testNotNullFromConnection() {
		assertNotNull(instance.getConnections());
	}
	
	@Test
	public void testNoConnectionsAtStart() {
		assertTrue(instance.getConnections().isEmpty());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConnectingInvalidSignalType() {
		instance.connect(signal);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConnectNull() {
		instance.connect(null);
	}

	@Test
	public void testConnect() {
		instance.connect(input1);
		assertThat(instance.getConnections(), hasItem(input1));
	}
	
	@Test
	public void testConnectReturnsTrue() {
		assertTrue(instance.connect(input1));
	}
	
	@Test
	public void testConnectCallsInput() {
		instance.connect(input1);
		verify(input1).connect(instance);
	}
	
	@Test
	public void testConnectDontCallInputWhenConnected() {
		instance.connect(input1);
		instance.connect(input1);
		verify(input1, times(1)).connect(instance);
	}
	
	@Test
	public void testConnectToAlreadyConnected() {
		instance.connect(input1);
		instance.connect(input1);
		assertThat(instance.getConnections(), hasItems(input1));
	}
	
	@Test
	public void testConnectToMultipleInputs() {
		instance.connect(input1);
		instance.connect(input2);
		assertThat(instance.getConnections(), hasItems(input1, input2));
	}
	
	@Test
	public void testConnectToMultipleInputsCallsInputs() {
		instance.connect(input1);
		instance.connect(input2);
		verify(input1).connect(instance);
		verify(input2).connect(instance);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDisconnectingInvalidSignalType() {
		instance.disconnect(signal);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDisconnectNull() {
		instance.disconnect(null);
	}

	@Test
	public void testDisconnect() {
		instance.connect(input1);
		instance.disconnect(input1);
		assertThat(instance.getConnections(), not(hasItem(input1)));
	}
	
	@Test
	public void testDisconnectReturnsTrue() {
		instance.connect(input1);
		assertTrue(instance.disconnect(input1));
	}
	
	@Test
	public void testDisconnectCallsInput() {
		instance.connect(input1);
		instance.disconnect(input1);
		verify(input1).disconnect(instance);
	}
	
	@Test
	public void testDisconnectCallsOutput() {
		instance.connect(input1);
		instance.disconnect(input1);
		verify(input1).disconnect(instance);
	}
	
	public void testDisconnectToNotConnectedOutput() {
		instance.connect(input1);
		assertFalse(instance.disconnect(input2));
	}

	@Test
	public void testIsConnectedInitial() {
		assertFalse(instance.isConnected());
	}
	
	@Test
	public void testIsConnectedAfterConnect() {
		instance.connect(input1);
		assertTrue(instance.isConnected());
	}
	
	@Test
	public void testIsConnectedAfterDisconnect() {
		instance.connect(input1);
		instance.disconnect(input1);
		assertFalse(instance.isConnected());
	}
	
	@Test
	public void testConnectTriggersEvent() {
		instance.connect(input1);
		verify(delegator).onSignalConnection(input1, instance);
	}
	
	@Test
	public void testDisconnectTriggersEvent() {
		instance.connect(input1);
		instance.disconnect(input1);
		verify(delegator).onSignalDisconnection(input1, instance);
	}
}
