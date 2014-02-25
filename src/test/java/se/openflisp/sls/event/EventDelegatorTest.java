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
import static org.mockito.Mockito.*;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public abstract class EventDelegatorTest<T> {
	
	public EventDelegator<T> eventDelegator;
	public T listener;

	@Before
	public void setup() {
		listener = Mockito.mock(getListenerClass());
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
		assertSame(listener, eventDelegator.getListeners(ListenerContext.DEFAULT).get(0));
		assertSame(eventDelegator.getListeners(ListenerContext.DEFAULT).get(0),
			eventDelegator.getNormalListeners().get(0));
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
		assertSame(listener, eventDelegator.getModelListeners().get(0));
		assertSame(eventDelegator.getListeners(ListenerContext.MODEL).get(0),
			eventDelegator.getModelListeners().get(0));
	}
	
	@Test
	public void testGettingSameListeners_SWING() {
		assertTrue(eventDelegator.addListener(ListenerContext.SWING, listener));
		assertSame(listener, eventDelegator.getSwingListeners().get(0));
		assertSame(eventDelegator.getListeners(ListenerContext.SWING).get(0),
			eventDelegator.getSwingListeners().get(0));
	}

	@Test
	public void testGettingListenersMany() {
		assertTrue(eventDelegator.addListener(listener));
		assertThat(eventDelegator.getNormalListeners().size(), is(1));

		assertThat(eventDelegator.getNormalListeners().size(), is(1));

		assertTrue(eventDelegator.addListener(listener));
		assertThat(eventDelegator.getNormalListeners().size(), is(2));
	}

	@Test
	public void testGettingModelListenersBeforeAdding() {
		assertThat(eventDelegator.getModelListeners().size(), is(0));
	}

	@Test
	public void testGettingModelListeners2() {
		assertTrue(eventDelegator.addListener(ListenerContext.MODEL, listener));
		List<T> listenerList = eventDelegator.getModelListeners();
		assertThat(listenerList.size(), is(1));
		assertSame(listenerList.get(0), listener);
	}

	@Test
	public void testGettingModelListenersMany() {
		assertTrue(eventDelegator.addListener(ListenerContext.MODEL, listener));
		assertThat(eventDelegator.getModelListeners().size(), is(1));

		assertThat(eventDelegator.getModelListeners().size(), is(1));

		assertTrue(eventDelegator.addListener(ListenerContext.MODEL, listener));
		assertThat(eventDelegator.getModelListeners().size(), is(2));
	}

	@Test
	public void testGettingNormalListenersMany() {
		assertTrue(eventDelegator.addListener(listener));
		assertThat(eventDelegator.getNormalListeners().size(), is(1));

		assertThat(eventDelegator.getNormalListeners().size(), is(1));

		assertTrue(eventDelegator.addListener(listener));
		assertThat(eventDelegator.getNormalListeners().size(), is(2));
	}

	@Test
	public void testGettingSwingListenersMany() {
		assertTrue(eventDelegator.addListener(ListenerContext.SWING, listener));
		assertThat(eventDelegator.getSwingListeners().size(), is(1));

		assertThat(eventDelegator.getSwingListeners().size(), is(1));

		assertTrue(eventDelegator.addListener(ListenerContext.SWING, listener));
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

		assertTrue(eventDelegator.addListener(ListenerContext.DEFAULT, listener));
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

	public abstract EventDelegator<T> getDelegatorInstance();
	public abstract Class<T> getListenerClass();
}
