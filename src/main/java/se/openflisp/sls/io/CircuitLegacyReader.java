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

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.openflisp.sls.Signal;
import se.openflisp.sls.component.ConstantGate;
import se.openflisp.sls.simulation.Circuit2D;
import se.openflisp.sls.simulation.Circuit2DBuilder;

/**
 * Reader class for importing legacy file format containing information about
 * a Circuit2D object.
 * 
 * @author Anton Ekberg <anton.ekberg@gmail.com>
 * @version 1.0
 */
public class CircuitLegacyReader extends BufferedReader {

	/**
	 * Reference identifier to the last seen component definition.
	 */
	protected String currentComponent;
	
	/**
	 * Current line number that is being parsed.
	 */
	protected int currentLineNumber;
	
	/**
	 * Regular expression to match and extract a component definition.
	 */
	public static final Pattern COMPONENT_DEF = Pattern.compile(">NUM:(\\d+),TYPE:(\\d+),X=(-?\\d+),Y=(-?\\d+)");
	
	/**
	 * Regular expression to match and extract a component connection definition.
	 */
	public static final Pattern INPUT_VECTOR = Pattern.compile("IV\\[(\\d+)\\]([A-Za-z0-9:]+)");
	
	/**
	 * Creates a new reader for importing a Circuit2D object from a legacy file format.
	 * 
	 * @param in	reader where the circuit definitions should be read.
	 */
	public CircuitLegacyReader(Reader in) {
		super(in);
		this.currentLineNumber = 0;
	}

	/**
	 * Reads a Circuit2D from the attached reader. 
	 * 
	 * Expects format from the legacy application where three line types can be processed; a line that starts with ">"
	 * is considered a component definition matching {@link #parseComponentDefinition(Circuit2DBuilder, String)}, 
	 * a line that starts with "<" is considered the end of a component definition and a line that starts with "IV" 
	 * is considered a input vector definition matching {@link #parseConnectionDefinition(Circuit2DBuilder, String)}.
	 * 
	 * @return a new Circuit2D containing components specified by the reader
	 * @throws IOException if a line could not be read from the reader
	 * @throws ParseException if a line could not be parsed or errors in the definitions
	 */
	public Circuit2D readCircuit() throws IOException, ParseException {
		Circuit2DBuilder builder = new Circuit2DBuilder();
		this.currentComponent = null;
		String line;
		while ((line = this.readLine()) != null) {
			try {
				this.currentLineNumber++;
				if (line.startsWith(">")) {
					this.parseComponentDefinition(builder, line);
				} else if (line.startsWith("<")) {
					this.currentComponent = null;
				} else if (line.startsWith("IV")) {
					this.parseConnectionDefinition(builder, line);
				}
			} catch (IllegalArgumentException e) {
				throw new ParseException(e.getMessage(), this.currentLineNumber);
			} catch (IllegalStateException e) {
				throw new ParseException(e.getMessage(), this.currentLineNumber);
			}
		}
		return builder.build();
	}
	
	/**
	 * Parsing a component definition and add it to the builder.
	 * 
	 * @param builder	builder where the component should be added
	 * @param line		line containing the component definition
	 * @throws ParseException if the line doesn't contain valid information or unknown parts
	 */
	protected void parseComponentDefinition(Circuit2DBuilder builder, String line) throws ParseException {
		Matcher m = COMPONENT_DEF.matcher(line);
		if (!m.matches() || m.groupCount() != 4) {
			throw new ParseException("Illegal component definition", this.currentLineNumber);
		}
		LegacyComponent component = LegacyComponent.fromTypeIdentifer(
			Integer.parseInt(m.group(2))
		);
		if (component == null) {
			throw new ParseException("Unknown component type",  this.currentLineNumber);
		}
		this.currentComponent = component.identifierPrefix + m.group(1);
		Point position = new Point(
			Integer.parseInt(m.group(3)),
			Integer.parseInt(m.group(4))
		);
		builder.addComponent(component.toComponent(this.currentComponent), position);
	}
	
	/**
	 * Parsing a component connection and add it to the builder.
	 * 
	 * If the parser finds a constant in the output part of the connection it will
	 * try to add a constant at a undefined point in the Circuit.
	 * 
	 * @param builder	builder where the connection should be added
	 * @param line		line containing the component connection definition
	 * @throws ParseException if the line doesn't contain valid information or unknown parts
	 */
	protected void parseConnectionDefinition(Circuit2DBuilder builder, String line) throws ParseException {
		Matcher m = INPUT_VECTOR.matcher(line);
		if (!m.matches() || m.groupCount() != 2) {
			throw new ParseException("Illegal input vector definition", this.currentLineNumber);
		}
		if (currentComponent == null) {
			throw new ParseException("Input vector outside component definition", this.currentLineNumber);
		}
		String outputIdentifier = m.group(2);
		if (!builder.hasComponent(outputIdentifier)) {
			if (outputIdentifier.equals("1")) {
				builder.addComponent(
					new ConstantGate("1", Signal.State.HIGH), 
					new Point(0, 0)
				);
			} else if (outputIdentifier.equals("0")) {
				builder.addComponent(
					new ConstantGate("0", Signal.State.LOW), 
					new Point(0, 0)
				);
			}
		}
		builder.addConnection(
			currentComponent, 
			m.group(1), 
			outputIdentifier
		);
	}
}
