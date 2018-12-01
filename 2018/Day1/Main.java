import java.util.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.function.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		List<Integer> input = Files.readAllLines(Paths.get("input.txt")).stream().map(Integer::parseInt).collect(Collectors.toList());
		System.out.printf("%d, %d, %d\n", part1(input), part2(input), part2_second(input));
	}

	public static int part1(List<Integer> list) throws IOException {
		return list.stream().mapToInt(Integer::intValue).sum();
	}

	public static int part2_second(List<Integer> list) throws IOException {
		IntSupplier supplier = new IntSupplier() {
			int i = 0;
			int currentFreq = 0;
			@Override
			public int getAsInt() {
				currentFreq += list.get(i);
				i = (i + 1) % list.size();
				return currentFreq;
			}
		};
		HashSet<Integer> results = new HashSet<>();
		return IntStream.generate(supplier).filter(i -> {
			if (results.contains(i)) {
				return true;
			} else {
				results.add(i);
				return false;
			}}).findFirst().orElse(-1);
	}

	public static int part2(List<Integer> list) throws IOException {
		HashSet<Integer> results = new HashSet<>();
		int result = 0;
		for(int i = 0; true; i = (i + 1) % list.size()) {
			result += list.get(i);
			if (results.contains(result)) {
				return result;
			}
			results.add(result);
		}
	}
}
