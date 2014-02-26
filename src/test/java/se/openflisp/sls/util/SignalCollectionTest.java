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
package se.openflisp.sls.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.mockito.Mockito;

import se.openflisp.sls.Signal;

public class SignalCollectionTest {

	@Test(expected=IllegalArgumentException.class)
	public void testContainsStateWithNullCollection() {
		SignalCollection.containsState(null, Signal.State.FLOATING);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testContainsStateWithNullState() {
		SignalCollection.containsState(createCollection(), null);
	}
	
	@Test
	public void testContainsStateWithEmptyCollection() {
		assertFalse(SignalCollection.containsState(createCollection(), Signal.State.FLOATING));
	}
	
	@Test
	public void testContainsStateWithSingleCorrectElement() {
		assertTrue(SignalCollection.containsState(
			createCollection(
				Signal.State.FLOATING
			), 
			Signal.State.FLOATING
		));
	}

	@Test
	public void testContainsStateWithSingleIncorrectElement() {
		assertFalse(SignalCollection.containsState(
			createCollection(
				Signal.State.FLOATING
			), 
			Signal.State.LOW
		));
	}
	
	@Test
	public void testContainsStateWithOneCorrectElement() {
		assertTrue(SignalCollection.containsState(
			createCollection(
				Signal.State.FLOATING, Signal.State.FLOATING, Signal.State.LOW
			), 
			Signal.State.LOW
		));
	}
	
	@Test
	public void testContainsStateWithManyCorrectElements() {
		assertTrue(SignalCollection.containsState(
			createCollection(
				Signal.State.FLOATING, Signal.State.HIGH, Signal.State.HIGH, Signal.State.LOW
			), 
			Signal.State.HIGH
		));
	}
	
	@Test
	public void testContainsStateWithOnlyIncorrectElements() {
		assertFalse(SignalCollection.containsState(
			createCollection(
				Signal.State.FLOATING, Signal.State.HIGH, Signal.State.HIGH
			), 
			Signal.State.LOW
		));
	}
	
	@Test
	public void testContainsStateWithOnlyCorrectElements() {
		assertTrue(SignalCollection.containsState(
			createCollection(
				Signal.State.FLOATING, Signal.State.FLOATING, Signal.State.FLOATING
			), 
			Signal.State.FLOATING
		));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCountNullCollection() {
		SignalCollection.countState(null, Signal.State.FLOATING);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCountNullState() {
		SignalCollection.countState(createCollection(), null);
	}
	
	@Test
	public void testCountAnEmptyCollection() {
		assertEquals(0, SignalCollection.countState(
				createCollection(),
				Signal.State.FLOATING
			));
	}
	
	@Test
	public void testCountStateWithSingleCorrectElement() {
		assertEquals(1, SignalCollection.countState(
			createCollection(
				Signal.State.FLOATING
			),
			Signal.State.FLOATING
		));
	}

	@Test
	public void testCountStateWithSingleIncorrectElement() {
		assertEquals(0, SignalCollection.countState(
			createCollection(
				Signal.State.FLOATING
			), 
			Signal.State.LOW
		));
	}
	
	@Test
	public void testCountStateWithOneCorrectElement() {
		assertEquals(1, SignalCollection.countState(
			createCollection(
				Signal.State.FLOATING, Signal.State.FLOATING, Signal.State.LOW
			), 
			Signal.State.LOW
		));
	}
	
	@Test
	public void testCountStateWithManyCorrectElements() {
		assertEquals(2, SignalCollection.countState(
			createCollection(
				Signal.State.FLOATING, Signal.State.HIGH, Signal.State.HIGH, Signal.State.LOW
			), 
			Signal.State.HIGH
		));
	}
	
	@Test
	public void testCountStateWithOnlyIncorrectElements() {
		assertEquals(0, SignalCollection.countState(
			createCollection(
				Signal.State.FLOATING, Signal.State.HIGH, Signal.State.HIGH
			), 
			Signal.State.LOW
		));
	}
	
	@Test
	public void testCountStateWithOnlyCorrectElements() {
		assertEquals(3, SignalCollection.countState(
			createCollection(
				Signal.State.FLOATING, Signal.State.FLOATING, Signal.State.FLOATING
			), 
			Signal.State.FLOATING
		));
	}
	
	private Collection<Signal> createCollection(Signal.State... states) {
		Collection<Signal> collection = new ArrayList<Signal>();
		for (Signal.State state : states) {
			Signal signal = Mockito.mock(Signal.class);
			doReturn(state).when(signal).getState();
			collection.add(signal);
		}
		return collection;
	}
}
