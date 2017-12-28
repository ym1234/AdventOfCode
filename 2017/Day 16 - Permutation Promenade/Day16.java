import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.nio.file.*;
public class Day16 {

	public static class Move {
		BiFunction <Integer, Integer, Function<List<Character>, Character>> func;
		public final int[] params;
		public Move(BiFunction<Integer, Integer, Function<List<Character>, Character>> func, int... params) {
			this.func = func;
			this.params = params;
		}
	}

	public static void main(String[] args)throws Exception {
		Move[] moves = parse(Files.readAllLines(Paths.get(args[0])));
		while (true) {
			long millis = System.currentTimeMillis();
			System.out.println("Part 1: " + part1(moves));
			System.out.println("Part 2: " + part2(moves));
			System.out.println("Took: " + (System.currentTimeMillis() - millis) + "ms");
		}
	}

	public static Move[] parse(List<String> file) {
		return file.parallelStream().flatMap(i -> Arrays.stream(i.split(","))).map(i -> {
			switch (i.charAt(0)) {
			case 's' : return new Move((x, y) -> list -> { Collections.rotate(list, x); return list.get(x); }, Integer.parseInt(i.substring(1, i.length())), 0);
			case 'x' :
				String[] param = i.substring(1, i.length()).split("/");
				return new Move((x, y) -> list -> list.set(x, list.set(y, list.get(x))), Integer.parseInt(param[0]), Integer.parseInt(param[1]));
			// auto-unboxing hates me.
			case 'p' : return new Move((x, y) -> list -> list.set(list.indexOf((char) x.intValue()), list.set(list.indexOf((char) y.intValue()), (char) x.intValue())), i.charAt(1), i.charAt(3));
			default: return null;
			}
		}).toArray(Move[]::new);
	}

	public static List<Character> part1(Move[] moves) {
		return dance(moves, Arrays.asList(new Character[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'}));
	}

	public static List<Character> part2(Move[] moves) {
		HashMap <List<Character>, Integer> set = new HashMap<>();
		List<Character> input = Arrays.<Character>asList(new Character[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'});
		for (int i = 0; i < 1000000000; i++) {
			dance(moves, input);
			if (set.containsKey(input)) {
				break;
			} else {
				set.put(new ArrayList<>(input), i);
			}
		}
		return set.entrySet().parallelStream().filter(i -> i.getValue() == (1000000000 % set.size() - 1)).map(Map.Entry::getKey).collect(Collectors.toList()).get(0);
	}

	public static List<Character> dance(Move[] moves, List<Character> input) {
		Arrays.stream(moves).forEach(i -> i.func.apply(i.params[0], i.params[1]).apply(input));
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
