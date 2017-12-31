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
				switch (currentChar) {
				case'{':
					stack.push('{');
					score += stack.size();
					cleaned.append(currentChar);
					break;
				case '}':
					cleaned.append(currentChar);
					stack.pop();
					break;
				case '!': currentMode = Mode.IGNOREONE; break;
				case '<': currentMode = Mode.IGNORE; break;
				default: cleaned.append(currentChar);
				} 
				lastMode = Mode.NORMAL;
				break;
			case IGNORE:
				switch (currentChar) {
				case '!': currentMode = Mode.IGNOREONE; break;
				case '>': currentMode = Mode.NORMAL; break;
				default : ignoredChars++;
				}
				lastMode = Mode.IGNORE;
				break;
			case IGNOREONE: currentMode = lastMode; break;
			}
		}
		System.out.println("Part 1: " + score);
		System.out.println("Part 2: " + ignoredChars);
		System.out.println("Cleaned Version: " + cleaned);
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