package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.SortedMap;

/**
 * Implements environment interface that represents environment for MyShell.
 * 
 * @author tina
 *
 */
public class MyEnvironment implements Environment {
	/** Prompt symbol of the shell. */
	private char PROMPT = '>';
	/** Symbol indicating that more lines are coming. */
	private char MORELINES = '\\';
	/** Symbol written on the beginning of each line in multi-line command. */
	private char MULTILINE = '|';
	/** Available commands */
	SortedMap<String, ShellCommand> avaliableCommands;


	/**
	 * Constructor with one arguments.
	 * 
	 * 
	 */
	public MyEnvironment() {

	}

	/**
	 * Constructor with one arguments.
	 * 
	 * @param commands
	 *            shell commands
	 */
	public MyEnvironment(SortedMap<String, ShellCommand> commands) {
		avaliableCommands = commands;
	}

	@Override
	public String readLine() throws ShellIOException {
		BufferedReader brIn = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			String input = brIn.readLine();
			return input;
		} catch (IOException e) {
			throw new ShellIOException();
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		BufferedWriter brOut = new BufferedWriter(new OutputStreamWriter(System.out));
		
		try {
			brOut.write(text);
			brOut.flush();
		} catch (IOException e) {
			throw new ShellIOException();
		}		

	}

	@Override
	public void writeln(String text) throws ShellIOException {
		write(text + "\n");
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(avaliableCommands);
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTILINE;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINE = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPT;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPT = symbol;

	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINES;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINES = symbol;
	}

}
