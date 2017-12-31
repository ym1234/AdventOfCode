import java.util.*;
import java.nio.file.*;

public class Day10 {
	public static void main(String[] args) throws Exception {
		int[] input = Files.readAllLines(Paths.get(args[0])).stream().map(i -> i.split(",")).flatMap(Arrays::stream).mapToInt(Integer::parseInt).toArray();
		int[] list = new int[Integer.parseInt(args[1])];
		populate(list);
		System.out.println(solve(input, list));
	}

	public static int solve(int[] input, int[] list) {
		int skip = 0;
		int indexList = 0;
		for (int i = 0; true; i++) {
			if (skip == list.length) {
				break;
			}
			if (input[i % list.length] >= list.length) {
				continue;
			}
			getSection(list, indexList % list.length, input[i % list.length]);
			indexList += (input[i % list.length] + skip);
			skip++;
		}
		return list[0] * list[1];
	}

	public static void getSection(int[] list, int from, int length) {
		int counter = 0;
		for (int i = from; i < (from + length); i++) {
			int temp = list[(i + length - counter) % list.length];
			list[(i + length - counter) % list.length] = list[i % list.length];
			list[i % list.length] = temp;
			counter++;
		}
	}

	public static int[] reverse(int... input) {
		return new int[10];
	}

	public static void populate(int[] list) {
		for (int i = 0; i < list.length; i++) {
			list[i] = i;
		}
	}
}