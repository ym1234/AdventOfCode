import java.nio.file.*;
import java.util.*;
public class Day18 {
	public static void main(String[] args) throws Exception {
		List<String> instructions = Files.readAllLines(Paths.get(args[0]));
		System.out.println("Recovered: " + Part1(new ArrayList<>(instructions)));
		System.out.println("Messages sent: " + Part2(new ArrayList<>(instructions)));
	}

	public static int Part2(List<String> instructions) throws Exception {
		Program zero = new Program(instructions, 0);
		Program one = new Program(instructions, 1);
		zero.pair(one);
		one.pair(zero);
		while (!zero.isWaiting() || !one.isWaiting()) {
			zero.processInst();
			one.processInst();
		}
		return one.messagesSent();
	}

	public static class Program {

		private Queue<Long> queue;
		private List<String> instructions;
		private HashMap<String, Long> registers;

		private boolean isWaiting = false;

		private int messagesSent = 0;
		private int counter = 0;

		private Program otherProgram;

		public Program(List<String> instructions, long programID) {
			queue = new ArrayDeque<>();
			registers = new HashMap<>();

			this.instructions = instructions;
			registers.put("p", programID);
		}

		public boolean isWaiting() {
			return isWaiting;
		}

		public void send(long value) {
			queue.add(value);
			isWaiting = false;
		}

		public Program pair(Program program) {
			this.otherProgram = program;
			return this;
		}

		public int messagesSent() {
			return messagesSent;
		}

		public void processInst() {
			if (isWaiting) {
				return;
			}
			outer:
			while (counter < instructions.size()) {
				String[] instruction = instructions.get(counter).split("\\s+");
				if (instruction.length == 2) {
					switch (instruction[0]) {
					case "snd" :
						otherProgram.send(registers.get(instruction[1]));
						messagesSent++;
						break;
					case "rcv" :
						if (queue.isEmpty()) {
							isWaiting = true;
							break outer;
						} else {
							registers.put(instruction[1], queue.poll());
						}
						break;
					}
				} else {
					// The only instructions you need to eval are 'snd', 'rcv' and 'jnz'.
					counter += eval(instruction, registers) - 1;
				}
				counter++;
			}
		}
	}
	public static Long Part1(List<String> instructions) {
		HashMap <String, Long> registers = new HashMap<>();
		Long lastFreq = 0L;

		outer: for (int i = 0; i < instructions.size(); i++) {
			String[] instruction = instructions.get(i).split("\\s+");

			if (instruction.length == 2) {
				long currentValue = registers.get(instruction[1]);;
				switch (instruction[0]) {
				case "snd" :
					lastFreq = currentValue;
					break;
				case "rcv" :
					if (currentValue != 0) {
						break outer;
					}
				}
			} else {
				i += eval(instruction, registers) - 1;
			}
		}
		return lastFreq;
	}
	public static int eval(String[] instruction, HashMap<String, Long> registers) {
		int offset = 1;
		long firstParam = Character.isLetter(instruction[1].charAt(0)) ? registers.getOrDefault(instruction[1], 0L) : Integer.parseInt(instruction[1]);
		long secondParam = Character.isLetter(instruction[2].charAt(0)) ? registers.getOrDefault(instruction[2], 0L) : Integer.parseInt(instruction[2]);
		switch (instruction[0]) {
		case "set" :
			registers.put(instruction[1], secondParam);
			break;
		case "add" :
			registers.put(instruction[1], firstParam + secondParam);
			break;
		case "mul" :
			registers.put(instruction[1], firstParam * secondParam);
			break;
		case "mod" :
			registers.put(instruction[1], firstParam % secondParam);
			break;
		case "jgz" :
			if (firstParam > 0) {
				offset = (int) secondParam;
			}
			break;
		}
		return offset;
	}
}