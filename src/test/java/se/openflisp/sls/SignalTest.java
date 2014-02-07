package se.openflisp.sls;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public abstract class SignalTest {

	public Component owner;
	
	@Before
	public void setup() {
		owner = Mockito.mock(Component.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructingWithNullIdentifier() {
		getInstance(null, owner);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructingWithNullOwner() {
		getInstance("identifier", null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructingWithEmptyIdentifier() {
		getInstance("", owner);
	}
	
	@Test
	public void testGettingValidIdentifier() {
		Signal signal = getInstance("identifier", null);
		assertEquals("identifier", signal.getIdentifier());
	}
	
	@Test
	public void testGettingValidOwner() {
		Signal signal = getInstance("identifier", owner);
		assertSame(this.owner, signal.getOwner());
	}
	
	@Test
	public void testInitialStateIsFloating() {
		Signal signal = this.getInstance("identifier", owner);
		assertEquals(Signal.State.FLOATING, signal.getState());
	}
	
	@Test
	public void testInitialStateChangeToLow() {
		Signal signal = this.getInstance("identifier", owner);
		signal.setState(Signal.State.LOW);
		assertEquals(Signal.State.LOW, signal.getState());
	}
	
	@Test
	public void testInitialStateChangeToHigh() {
		Signal signal = this.getInstance("identifier", owner);
		signal.setState(Signal.State.HIGH);
		assertEquals(Signal.State.HIGH, signal.getState());
	}
	
	@Test
	public void testStateChangeFromHighToLow() {
		Signal signal = this.getInstance("identifier", owner);
		signal.setState(Signal.State.HIGH);
		signal.setState(Signal.State.LOW);
		assertEquals(Signal.State.LOW, signal.getState());
	}
	
	@Test
	public void testStateChangeFromLowToHigh() {
		Signal signal = this.getInstance("identifier", owner);
		signal.setState(Signal.State.LOW);
		signal.setState(Signal.State.HIGH);
		assertEquals(Signal.State.HIGH, signal.getState());
	}
	
	protected abstract Signal getInstance(String identifier, Component owner);
}
