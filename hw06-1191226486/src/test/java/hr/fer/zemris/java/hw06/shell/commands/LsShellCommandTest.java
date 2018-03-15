package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import hr.fer.zemris.java.hw06.shell.MyEnvironment;

public class LsShellCommandTest {

	@Test
	public void output() throws IOException {
		LsShellCommand lsShellCommand = new LsShellCommand();
		File folder = new File("..");
		File[] children = folder.listFiles();

		for (File file : children) {
			System.out.println(lsShellCommand.getFormattedAttributes(file));

		}
		
		TreeShellCommand treeShellCommand = new TreeShellCommand();
		CatShellCommand catShellCommand = new CatShellCommand();
		MyEnvironment env = new MyEnvironment();
		
		treeShellCommand.executeCommand(env, "/home/tina/TestRoot");
		
		//		treeShellCommand.executeCommand(env, "..");
		catShellCommand .executeCommand(env, "/home/tina/TestRoot/3");
		
	}

}
