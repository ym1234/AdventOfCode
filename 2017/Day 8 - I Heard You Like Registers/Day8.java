import java.util.*;
import java.nio.file.*;
import java.util.stream.*;

public class Day8 {

	public static void main(String[] args) throws Exception {
		List<String[]> input = Files.readAllLines(Paths.get(args[0])).stream().map(i -> i.split("\\s+")).collect(Collectors.toList());
		Map<String, Integer> registers = new HashMap<>();

		int allTimeMax = 0;
		for (String[] array : input) {

			registers.putIfAbsent(array[0], 0);
			int operandA = registers.get(array[0]);

			registers.putIfAbsent(array[4], 0);
			int comA = registers.get(array[4]);
			int newMax = eval(operandA, array[1], Integer.parseInt(array[2]), comA, array[5], Integer.parseInt(array[array.length - 1]));
			registers.put(array[0], newMax);
			if (newMax > allTimeMax) {
				allTimeMax = newMax;
			}
		}
		System.out.println("Part 1: " + Collections.max(registers.values()));
		System.out.println("Part 2: " + allTimeMax);
	}
	public static int eval(int operandA, String op, int operandB, int comA, String com, int comB) {
		boolean comd = false;
		int result = operandA;

		switch (com) {
		case ">": comd = (comA > comB); break;
		case "<": comd = (comA < comB); break;
		case ">=": comd = (comA >= comB); break;
		case "<=": comd = (comA <= comB); break;
		case "==": comd = (comA == comB); break;
		case "!=": comd = (comA != comB); break;
		}

		if (comd) {
			switch (op) {
			case "inc":  result = (operandA + operandB); break;
			case "dec":  result = (operandA - operandB); break;
			}
		}
		return result;
	}
}