import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.nio.file.*;

public class Day16 {

	public static class Move<T> {
		public final BiFunction <T, T, Function<List<Character>, T>> func;
		public final T[] params;
		public Move(BiFunction<T, T, Function<List<Character>, T>> func, T... params) {
			this.func = func;
			this.params = params;
		}
	}

	public static void main(String[] args) throws Exception {
		Move[] moves = parse(Files.readAllLines(Paths.get(args[0])));
		Character[] chars = new Character[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'};
		while (true) {
			long millis = System.currentTimeMillis();
			System.out.println("Part 1: " + part1(moves, new ArrayList<>(Arrays.asList(chars))));
			System.out.println("Part 2: " + part2(moves, new ArrayList<>(Arrays.asList(chars))));
			System.out.println("Took: " + (System.currentTimeMillis() - millis) + "ms");
		}
	}

	public static Move[] parse(List<String> file) {
		return file.stream().flatMap(i -> Arrays.stream(i.split(","))).map(i -> {
			switch (i.charAt(0)) {
			case 's' : return new Move<Integer>((x, y) -> list -> { Collections.rotate(list, x); return x; }, Integer.parseInt(i.substring(1, i.length())), 0);
			case 'x' :
				String[] param = i.substring(1, i.length()).split("/");
				return new Move<Integer>((x, y) -> list -> (int)list.set(x, list.set(y, list.get(x))), Integer.parseInt(param[0]), Integer.parseInt(param[1]));
			case 'p' : return new Move<Character>((x, y) -> list -> list.set(list.indexOf(x), list.set(list.indexOf(y), x)), i.charAt(1), i.charAt(3));
			default: return null;
			}
		}).toArray(Move[]::new);
	}

	public static List<Character> part1(Move[] moves, List<Character> chars) {
		return dance(moves, chars);
	}

	public static List<Character> part2(Move[] moves, List<Character> chars) {
		HashMap <List<Character>, Integer> set = new HashMap<>();
		List<Character> input = chars;
		for (int i = 0; i < 1000000000; i++) {
			dance(moves, input);
			if (!set.containsKey(input)) {
				set.put(new ArrayList<>(input), i);
			} else {
				break;
			}
		}
		return set.entrySet().parallelStream().filter(i -> i.getValue() == 1000000000 % set.size() - 1).map(Map.Entry::getKey).collect(Collectors.toList()).get(0);
	}

	public static List<Character> dance(Move[] moves, List<Character> input) {
		Arrays.stream(moves).forEach(i -> ((Function)i.func.apply(i.params[0], i.params[1])).apply(input));
		/*for (Move move : moves) {
			switch (move.type) {
			case 's' : Spin(move.params[0], input); break;
			case 'x' : Exchange(move.params[0], move.params[1], input); break;
			case 'p' : Partner((char)move.params[0], (char)move.params[1], input); break;
			}
		}*/
		return input;
	}
	/*
	public static void Spin(int by, List<Character> list) {
		Collections.rotate(list, by);
	}

	public static void Exchange(int x, int y, List<Character> list) {
		list.set(x, list.set(y, list.get(x)));
	}

	public static void Partner(char x, char y, List<Character> list) {
		list.set(list.indexOf(x), list.set(list.indexOf(y), x));
	}
	*/
}
