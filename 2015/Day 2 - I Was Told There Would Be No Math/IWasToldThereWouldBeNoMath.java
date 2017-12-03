import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Stream;

public class IWasToldThereWouldBeNoMath {
	public static void main(String[] args) {
		List<String> lines = new ArrayList<String>();
		try {
			lines = Files.readAllLines(Paths.get(args[0]));
		} catch (Exception e) {}

		int wrappingPaper = 0;
		int ribbonLength = 0;

		for (String line : lines) {
			wrappingPaper += Stream.of(line.split("x")).mapToInt(Integer::parseInt).reduce(0,(i, j) -> 2 * i * j);

			String[] inputs = line.split("x");
			int length = Integer.parseInt(inputs[0]);
			int width = Integer.parseInt(inputs[1]);
			int height = Integer.parseInt(inputs[2]);
			//wrappingPaper += (2 * length * width) + (2 * width * height) + (2 * length * height);
			wrappingPaper += Math.min(Math.min(length * width, width * height), length * height);
			ribbonLength += Math.min(length * 2 + width * 2,
			                         Math.min(length * 2 + height * 2, width * 2 + height * 2));
			ribbonLength += length * width * height;
		}
		System.out.println("Part 1: " + wrappingPaper);
		System.out.println("Part 2: " + ribbonLength);
	}
}