import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Arrays;

public class NotQuiteLisp {

	public static void main(String[] args) {
		Character[] input = {' '};
		try {
			input = toCharacterArray(Files.readAllLines(Paths.get(args[0])).get(0).toCharArray());
		} catch (Exception e) {}

		int floor = Arrays.stream(input).mapToInt(i -> i == '(' ? 1 : -1 ).sum();

		/*
		int floor = 0;
		int basementPosition = 0;

		for (int i = 0; i < input.length; i++) {
			if (input[i] == '(') {
				floor++;
			} else if (input[i] == ')') {
				floor--;
				if (basementPosition == 0 && floor == -1) {
					basementPosition = i + 1;
				}
			}
		}
		*/

		System.out.println("First Part: " + floor);
		//System.out.println("Second Part: " + basementPosition);
	}
	public static Character[] toCharacterArray(char[] s) {

		if ( s == null ) {
			return null;
		}

		int len = s.length;
		Character[] array = new Character[len];
		for (int i = 0; i < len ; i++) {
			array[i] = new Character(s[i]);
		}

		return array;
	}

}