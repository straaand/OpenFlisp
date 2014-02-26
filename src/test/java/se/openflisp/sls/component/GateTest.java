package se.openflisp.sls.component;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;

import se.openflisp.sls.Component;
import se.openflisp.sls.ComponentTest;
import se.openflisp.sls.Input;
import se.openflisp.sls.Signal;

public abstract class GateTest extends ComponentTest {
	
	public enum SignalConfiguration {
		NO_SIGNALS(new Signal.State[] {}),
		
		ONE_FLOATING(new Signal.State[] {Signal.State.FLOATING}),
		ONE_HIGH(new Signal.State[] {Signal.State.HIGH}),
		ONE_LOW(new Signal.State[] {Signal.State.LOW}),
		
		TWO_FLOATING(new Signal.State[] {Signal.State.FLOATING, Signal.State.FLOATING}),
		TWO_HIGH(new Signal.State[] {Signal.State.HIGH, Signal.State.HIGH}),
		TWO_LOW(new Signal.State[] {Signal.State.LOW, Signal.State.LOW}),
		
		THREE_FLOATING(new Signal.State[] {Signal.State.FLOATING, Signal.State.FLOATING, Signal.State.FLOATING}),
		THREE_HIGH(new Signal.State[] {Signal.State.HIGH, Signal.State.HIGH, Signal.State.HIGH}),
		THREE_LOW(new Signal.State[] {Signal.State.LOW, Signal.State.LOW, Signal.State.LOW}),
		
		HIGH_AND_LOW(new Signal.State[] {Signal.State.HIGH, Signal.State.LOW}),
		LOW_AND_HIGH(new Signal.State[] {Signal.State.LOW, Signal.State.HIGH}),
		HIGH_AND_FLOATING(new Signal.State[] {Signal.State.HIGH, Signal.State.FLOATING}),
		FLOATING_AND_HIGH(new Signal.State[] {Signal.State.FLOATING, Signal.State.HIGH}),
		LOW_AND_FLOATING(new Signal.State[] {Signal.State.LOW, Signal.State.FLOATING}),
		FLOATING_AND_LOW(new Signal.State[] {Signal.State.FLOATING, Signal.State.LOW}),
		FLOATING_LOW_HIGH(new Signal.State[] {Signal.State.FLOATING, Signal.State.LOW, Signal.State.HIGH}),
		LOW_HIGH_FLOATING(new Signal.State[] {Signal.State.LOW, Signal.State.HIGH, Signal.State.FLOATING}),
		HIGH_FLOATING_LOW(new Signal.State[] {Signal.State.HIGH, Signal.State.FLOATING, Signal.State.LOW});
		
		public final Signal.State[] signals;
		
		private SignalConfiguration(Signal.State[] signals) {
			this.signals = signals;
		}
	}

	public abstract TruthTable generateTruthTable();
	
	@Test
	public void testTruthTable() {
		TruthTable table = this.generateTruthTable();
		for (TruthTableCase ttCase : table.getCases()) {
			Gate gate = (Gate) this.getInstance("gate");
			ttCase.addConnectInputs(gate);
			assertEquals(ttCase.debugMessage, ttCase.output, gate.evaluateOutput());
		}
	}
	
	public class TruthTableCase {
		
		private Map<String, Input> inputs = new HashMap<String, Input>();
		
		public final Signal.State[] signals;
		
		public final Signal.State output;
		
		public final String debugMessage;
		
		public TruthTableCase(Signal.State[] inputs, Signal.State output, String debugMessage) {
			this.signals = inputs;
			this.output = output;
			this.debugMessage = debugMessage;
			for (int inputID = 0; inputID < inputs.length; inputID++) {
				Input input = Mockito.mock(Input.class);
				doReturn(inputs[inputID]).when(input).getState();
				doReturn(Integer.toString(inputID)).when(input).getIdentifier();
				this.inputs.put(
					Integer.toString(inputID), 
					input
				);
			}
		}
		
		public void addConnectInputs(Gate gate) {
			try {
				Field field = Component.class.getDeclaredField("inputs");
				field.setAccessible(true);
				field.set(gate, inputs);
			} catch (Exception e) {}
		}
	}
	
	public class TruthTable  {
		
		private Set<TruthTableCase> cases = new HashSet<TruthTableCase>();
		
		public TruthTable invert() {
			TruthTable invertedTable = new TruthTable();
			for (TruthTableCase ttCase : this.cases) {
				Signal.State invertedOutput;
				if (ttCase.output == Signal.State.LOW) {
					invertedOutput = Signal.State.HIGH;
				} else if (ttCase.output == Signal.State.HIGH) {
					invertedOutput = Signal.State.LOW;
				} else {
					invertedOutput = ttCase.output;
				}
				invertedTable.add(ttCase.signals, invertedOutput, ttCase.debugMessage);
			}
			return invertedTable;
		}
		
		public void add(Signal.State[] inputs, Signal.State output, String debugMessage) {
			this.cases.add(new TruthTableCase(inputs, output, debugMessage));
		}
		
		public void add(SignalConfiguration config, Signal.State output, String debugMessage) {
			this.add(config.signals, output, debugMessage);
		}
		
		public void add(SignalConfiguration config, Signal.State output) {
			this.add(config.signals, output, String.format("%s", config));
		}
		
		public Set<TruthTableCase> getCases() {
			return this.cases;
		}
	}
}
