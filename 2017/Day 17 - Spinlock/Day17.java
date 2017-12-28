import java.util.*;
public class Day17 {
	public static void main(String[] args) {
		int stepForward = 343;
		//int stepForward = Integer.parseInt(args[0]);

		System.out.println(part1(stepForward));
		System.out.println(part2(stepForward));
	}

	public static int part1(int stepForward) {
		int currentPos = 0;
		List<Integer> spinlock = new LinkedList<>();
		spinlock.add(0);
		for (int i = 1; i < 2018; i++) {
			currentPos = (currentPos + stepForward) % spinlock.size();
			spinlock.add(currentPos++, i);
		}
		return spinlock.get((spinlock.indexOf(2017) + 1) % spinlock.size());
	}

	public static int part2(int stepForward) {
		int currentPos2 = 0;
		int req = 0;
		for (int i = 1; i < 50000001; i++) {
			currentPos2 = (currentPos2 + stepForward + 1) % i;
			if (currentPos2 == 0) {
				req = i;
			}
		}
		return req;
	}
	public static int part2v2(int input) {
		int currPos = 0;
		int result = 0;
		int limit = 50000000;
		int n = 0;
		while (n < limit) {
			if (currPos == 1)
				result = n;
			int fits = (n - currPos) / input;
			n += (fits + 1);
			currPos =  (currPos + (fits + 1) * (input + 1) - 1) % n + 1;
		}
		return result;
	}

}