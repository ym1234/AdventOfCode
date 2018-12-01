import java.util.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.function.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		part1();
		System.out.println(part2());
		System.out.println(part2_second());
	}

	public static void part1() throws IOException {
		System.out.println(
				Files.readAllLines(Paths.get("input.txt")).stream().mapToInt(Integer::parseInt).sum()
				);
	}

	public static int part2_second() throws IOException {
		List<Integer> list = Files.readAllLines(Paths.get("input.txt")).stream().map(Integer::parseInt).collect(Collectors.toList());
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
				results.put(i, true);
				return false;
			}}).findFirst().orElse(-1);
	}

	public static int part2()throws IOException  {
		List<Integer> list = Files.readAllLines(Paths.get("input.txt")).stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
		HashMap<Integer, Boolean> results = new HashMap<>();
		int result = 0;
		int i = 0;
		while(true) {
			result += list.get(i);
			if (results.get(result) != null) {
				return result;
			}
			results.put(result, true);
			i = (i + 1) % list.size();
		}
	}
}
