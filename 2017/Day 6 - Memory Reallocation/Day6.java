import java.nio.file.*;
import java.util.*;

public class Day6 {
	public static void main(String[] args) throws Exception {

		List<Integer> input = Files.readAllLines(Paths.get(args[0])).stream().map(i->i.split("\\t")).flatMap(i->Arrays.stream(i)).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
		HashMap<List<Integer>, Integer> list = new HashMap<>();

		int steps = 0;
		long startTime = System.nanoTime();
		while (true) {
			int maxIndex = input.indexOf(Collections.max(input));
			int max = input.set(maxIndex, 0);

			while (max != 0) {
				maxIndex = (maxIndex + 1) % input.size();
				input.set(maxIndex, input.get(maxIndex) + 1);
				max--;
			}

			List<Integer> current = new LinkedList<>(input);
			if (list.containsKey(current)) {
				System.out.println("Part 1: " + (steps + 1));
				System.out.println("Part 2: " + (steps - list.get(current)));
				break;
			} else {
				list.put(current, steps);
				steps++;
			}
		}
		System.out.println("Took: " + ((System.nanoTime() - startTime) / 1000000) + "ms");
	}
}