import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class CorruptionChecksum {
	public static void main(String[] args) {
		try {
			List<String> input = Files.readAllLines(Paths.get(args[0]));

			Supplier<Stream<IntStream>> stream = () -> input.stream().map(i -> i.split("\\s+")).map(i -> Arrays.stream(i).mapToInt(Integer::parseInt).distinct());
			System.out.println(stream.get().mapToInt(i -> {IntSummaryStatistics stats = i.summaryStatistics(); return stats.getMax() - stats.getMin();}).sum());
			System.out.println(stream.get().map(i -> i.boxed().collect(Collectors.toList())).mapToInt(i -> i.stream().mapToInt(k -> i.stream().mapToInt(j -> (k % j == 0 && k != j) ?  k / j :  0 ).sum()).sum()).sum());
			/*outer:
			for (int j = 0; j < i.length; j++) {
				for (int h = 0; h < i.length; h++) {
					if (i[j] % i[h] == 0 && h != j) {
						return i[j] / i[h];
					}
				}
			}
			return 0;
			}.sum();*/
		} catch (Exception e) {}
	}
}