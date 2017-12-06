import java.nio.file.*;
import java.util.*;

public class Day5 {
	public static void main(String[] args) throws Exception {
		int[] result = Files.readAllLines(Paths.get(args[0])).stream().mapToInt(Integer::parseInt).toArray();
		int steps = 0;
		for (int i = 0; i < result.length;) {
			i += result[i] >= 3 ? result[i]-- : result[i]++;
			steps++;
		}
		System.out.println(steps);
	}
}