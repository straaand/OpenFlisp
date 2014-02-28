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
package se.openflisp.sls.event;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import se.openflisp.sls.Component;
import se.openflisp.sls.Input;
import se.openflisp.sls.Output;
import se.openflisp.sls.Signal;

public abstract class EventDelegatorTest<T> {
	
	public EventDelegator<T> eventDelegator;
	public T listener, listener2;
	public Component component;
	public Signal signal;
	public Input input;
	public Output output;
	private List<T> listeners;
	private static final int NR_OF_DEFAULT_LISTENERS = 9;
	private static final int NR_OF_SWING_LISTENERS = 3;
	private static final int NR_OF_MODEL_LISTENERS = 7;

	@Before
	public void setup() {
		listener = Mockito.mock(getListenerClass());
		listener2 = Mockito.mock(getListenerClass());
		eventDelegator = getDelegatorInstance();
	}

	@Test
	public void testNoListenersAtStart() {
		assertThat(eventDelegator.getListeners(ListenerContext.DEFAULT).size(), is(0));
		assertThat(eventDelegator.getListeners(ListenerContext.SWING).size(), is(0));
		assertThat(eventDelegator.getListeners(ListenerContext.MODEL).size(), is(0));
		assertThat(eventDelegator.getModelListeners().size(), is(0));
		assertThat(eventDelegator.getSwingListeners().size(), is(0));
		assertThat(eventDelegator.getNormalListeners().size(), is(0));
	}

	@Test
	public void testGettingSameListeners_DEFAULT() {
		assertTrue(eventDelegator.addListener(ListenerContext.DEFAULT, listener));
		assertThat(eventDelegator.getListeners(ListenerContext.DEFAULT), hasItem(listener));
	}

	@Test
	public void testGettingSameListenerList_DEFAULT() {
		eventDelegator.addListener(ListenerContext.DEFAULT, listener);
		eventDelegator.addListener(ListenerContext.DEFAULT, listener);
		assertEquals(eventDelegator.getNormalListeners(), eventDelegator.getNormalListeners());
	}

	@Test
	public void testGettingSameListeners_MODEL() {
		assertTrue(eventDelegator.addListener(ListenerContext.MODEL, listener));
		assertThat(eventDelegator.getListeners(ListenerContext.MODEL), hasItem(listener));
	}
	
	@Test
	public void testGettingSameListeners_SWING() {
		assertTrue(eventDelegator.addListener(ListenerContext.SWING, listener));
		assertThat(eventDelegator.getListeners(ListenerContext.SWING), hasItem(listener));
	}

	@Test
	public void testGettingListenersMany() {
		assertTrue(eventDelegator.addListener(listener));
		assertThat(eventDelegator.getNormalListeners().size(), is(1));
		assertTrue(eventDelegator.addListener(listener2));
		assertThat(eventDelegator.getNormalListeners().size(), is(2));
	}

	@Test
	public void testGettingModelListenersBeforeAdding() {
		assertThat(eventDelegator.getModelListeners().size(), is(0));
	}

	@Test
	public void testGettingModelListeners2() {
		assertTrue(eventDelegator.addListener(ListenerContext.MODEL, listener));
		Set<T> listenerList = eventDelegator.getModelListeners();
		assertThat(listenerList.size(), is(1));
		assertThat(listenerList, hasItem(listener));
	}

	@Test
	public void testGettingModelListenersMany() {
		assertTrue(eventDelegator.addListener(ListenerContext.MODEL, listener));
		assertThat(eventDelegator.getModelListeners().size(), is(1));
		assertTrue(eventDelegator.addListener(ListenerContext.MODEL, listener2));
		assertThat(eventDelegator.getModelListeners().size(), is(2));
	}

	@Test
	public void testGettingNormalListenersMany() {
		assertTrue(eventDelegator.addListener(listener));
		assertThat(eventDelegator.getNormalListeners().size(), is(1));
		assertTrue(eventDelegator.addListener(listener2));
		assertThat(eventDelegator.getNormalListeners().size(), is(2));
	}

	@Test
	public void testGettingSwingListenersMany() {
		assertTrue(eventDelegator.addListener(ListenerContext.SWING, listener));
		assertThat(eventDelegator.getSwingListeners().size(), is(1));
		assertTrue(eventDelegator.addListener(ListenerContext.SWING, listener2));
		assertThat(eventDelegator.getSwingListeners().size(), is(2));
	}

	@Test
	public void testAddingSeveralListeners() {
		assertTrue(eventDelegator.addListener(listener));
		assertThat(eventDelegator.getModelListeners().size(), is(0));
		assertThat(eventDelegator.getModelListeners().size(), is(0));
		assertThat(eventDelegator.getNormalListeners().size(), is(1));
		assertTrue(eventDelegator.addListener(ListenerContext.MODEL, listener));
		assertThat(eventDelegator.getModelListeners().size(), is(1));
		assertThat(eventDelegator.getModelListeners().size(), is(1));

		assertTrue(eventDelegator.addListener(ListenerContext.DEFAULT, listener2));
		assertThat(eventDelegator.getModelListeners().size(), is(1));
		assertThat(eventDelegator.getNormalListeners().size(), is(2));
		assertThat(eventDelegator.getModelListeners().size(), is(1));

		assertTrue(eventDelegator.addListener(ListenerContext.SWING, listener));
		assertThat(eventDelegator.getSwingListeners().size(), is(1));
		assertThat(eventDelegator.getNormalListeners().size(), is(2));
		assertThat(eventDelegator.getModelListeners().size(), is(1));
	}

	@Test
	public void testAddingNullListener() {
		assertFalse(eventDelegator.addListener(null));
		assertThat(eventDelegator.getNormalListeners().size(), is(0));
		assertThat(eventDelegator.getModelListeners().size(), is(0));
		assertThat(eventDelegator.getSwingListeners().size(), is(0));
		assertFalse(eventDelegator.addListener(null, listener));
		assertThat(eventDelegator.getNormalListeners().size(), is(0));
		assertThat(eventDelegator.getModelListeners().size(), is(0));
		assertThat(eventDelegator.getSwingListeners().size(), is(0));
	}

	@Test
	public void testAddListeners() {
		EventDelegator<T> delegator = getDelegatorInstance();
		addListeners(delegator, getListenerClass());
		assertThat(delegator.getModelListeners().size(), is(NR_OF_MODEL_LISTENERS));
		assertThat(delegator.getSwingListeners().size(), is(NR_OF_SWING_LISTENERS));
		assertThat(delegator.getNormalListeners().size(), is(NR_OF_DEFAULT_LISTENERS));
	}

	public abstract EventDelegator<T> getDelegatorInstance();
	public abstract Class<T> getListenerClass();

	/**
	 * Creates an anonymous class.
	 * Used for testing that listener methods
	 * are run in correct thread. Swing listener's methods should
	 * run in Event Dispatch Thread and the other should not.
	 *
	 * @param isSwingListener boolean to indicate that the listener
	 * should be a swing listener.
	 */
	 public abstract T createListener(boolean isSwingListener);

	/**
	 * Adds listeners to a delegator.
	 * Uses Constants NR_OF_DEFAULT_LISTENERS,
	 * NR_OF_SWING_LISTENERS and NR_OF_MODEL_LISTENERS
	 */
	public void addListeners(EventDelegator<T> delegator, Class<T> listenerClass) {
		listeners = new ArrayList<T>();
		for (int i = 0; i < NR_OF_DEFAULT_LISTENERS; i++) {
			T listener = Mockito.mock(listenerClass);
			delegator.addListener(ListenerContext.DEFAULT, listener);
			listeners.add(listener);
		}
		for (int i = 0; i < NR_OF_SWING_LISTENERS; i++) {
			T listener = Mockito.mock(listenerClass);
			delegator.addListener(ListenerContext.SWING, listener);
			listeners.add(listener);
		}for (int i = 0; i < NR_OF_MODEL_LISTENERS; i++) {
			T listener = Mockito.mock(listenerClass);
			delegator.addListener(ListenerContext.MODEL, listener);
			listeners.add(listener);
		}
	}

}
