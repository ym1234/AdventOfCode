import java.util.*;
import java.io.*;

public class Day9 {

	public static enum Mode {
		IGNORE, IGNOREONE, NORMAL
	}

	public static void parse(char[] input) {

		Stack<Character> stack = new Stack<>();
		StringBuilder cleaned = new StringBuilder();

		Mode currentMode = Mode.NORMAL;
		Mode lastMode = Mode.NORMAL;

		int score = 0;
		int ignoredChars = 0;

		for (int i = 0; i < input.length; i++) {
			char currentChar = input[i];
			switch (currentMode) {
			case NORMAL:
				if (currentChar == '{') {
					stack.push('{');
					score += stack.size();
					cleaned.append(currentChar);
				} else if (currentChar == '}') {
					cleaned.append(currentChar);
					stack.pop();
				} else if (currentChar == '!') {
					currentMode = Mode.IGNOREONE;
					lastMode = Mode.NORMAL;
				} else if (currentChar == '<') {
					currentMode = Mode.IGNORE;
					lastMode = Mode.NORMAL;
				} else {
					cleaned.append(currentChar);
				}
				lastMode = currentMode;
				break;
			case IGNOREONE:
				currentMode = lastMode;
				lastMode = Mode.IGNOREONE;
				break;
			case IGNORE:
				if (currentChar == '!') {
					currentMode = Mode.IGNOREONE;
					lastMode = Mode.IGNORE;
				} else if (currentChar == '>') {
					currentMode = Mode.NORMAL;
					lastMode = Mode.IGNORE;
				} else {
					ignoredChars++;
				}
				break;
			}
		}
		System.out.println("Part 1: " + score);
		System.out.println("Part 2: " + ignoredChars);
	}

	public static char[] getInput(String name) throws Exception {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(name));
		while (reader.ready()) {
			builder.append(reader.readLine());
		}
		char[] output = new char[builder.length()];
		builder.getChars(0, builder.length(), output, 0);
		return output;
	}

	public static void main(String[] args) throws Exception {
		parse(getInput(args[0]));
	}
}