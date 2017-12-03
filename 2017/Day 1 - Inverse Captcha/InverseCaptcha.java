import java.nio.file.Paths;
import java.nio.file.Files;

public class InverseCaptcha {
	public static void main(String[] args) {

		// Boring, try using Streams.

		char[] input = {' '};
		try {
			input = Files.readAllLines(Paths.get(args[0])).get(0).toCharArray();
		} catch (Exception e) {}

		int result = 0, otherResult = 0 ;
		int halfLength = input.length/2;

		for (int i = 0; i < input.length; i++) {
			char cache = input[i];
			int j =  input.length / 2;
			if (input[i + halfLength % input.length] == cache) {
				result += cache - '0';
			}

			if (input[i + 1 < input.length ? i + 1 : 0] == cache) {
				otherResult += cache - '0';
			}
		}

		System.out.println("Part 1: " + otherResult);
		System.out.println("Part 2: " + result);
	}
}