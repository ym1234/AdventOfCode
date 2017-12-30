import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import javafx.util.Pair;
public class Day7 {

	public static class Node {

		private int weight;
		private String name = null;
		private Node parent = null;
		private ArrayList<Node> children = null;

		public Node(String name, int weight) {
			this.weight = weight;
			this.name = name;
		}

		public Node setWeight(int weight) {
			this.weight = weight;
			return this;
		}

		public int getWeight() {
			return weight;
		}

		public Node setParent(Node node) {
			this.parent = node;
			return this;
		}
		public Node setChildren(ArrayList<Node> list) {
			this.children = list;
			return this;
		}
		public String toString() {
			if (parent != null) {
				return "Name: " + name + ", Weight: " + weight;
			} else {
				return "Name: " + name + ", Weight: " + weight;
			}
		}
		public String getName() {
			return name;
		}
		public Node getParent() {
			return parent;
		}
		public ArrayList<Node> getChildren() {
			return children;
		}
	}
	public static void main(String[] args) throws Exception {
		HashMap<String, Pair<Integer, String[]>> nodeProperties = new HashMap<>();
		// This is inefficient, if you know a better way, please tell me.
		Files.readAllLines(Paths.get(args[0])).stream()
			.map(i -> i.replaceAll("[^a-zA-Z0-9]+", " ").split("\\s+"))
			.forEach(i -> nodeProperties.put(i[0], new Pair<Integer, String[]>(Integer.parseInt(i[1]), Arrays.copyOfRange(i, 2, i.length))));
		Node node = buildTree(nodeProperties);

		System.out.println(treeStructure(0, new StringBuilder(), new StringBuilder(), node, 1000, true));
		System.out.println("Needed Weight: " + getNeededWeight(node));
	}
	public static Node buildTree(HashMap<String, Pair<Integer, String[]>> nodeProperties) {
		HashMap<String, Node> nodes = new HashMap<>();

		for (Map.Entry<String, Pair<Integer, String[]>> entry : nodeProperties.entrySet()) {
			String name = entry.getKey();
			Node node = nodes.getOrDefault(name, new Node(name, entry.getValue().getKey()));

			if (node.getChildren() == null) node.setChildren(getChildren(node, nodes, nodeProperties, entry.getValue().getValue()));
			nodes.putIfAbsent(name, node);
		}

		return nodes.entrySet().stream().filter(i -> i.getValue().getParent() == null).collect(Collectors.toList()).get(0).getValue();
	}

	public static ArrayList<Node> getChildren(Node root, HashMap<String, Node> nodes, HashMap<String, Pair<Integer, String[]>> nodeProperties, String[] properties) {
		ArrayList<Node> children = new ArrayList<>();
		for (int i = 0; i < properties.length; i++) {
			String childName = properties[i];
			
			Node child = nodes.getOrDefault(childName, new Node(childName, nodeProperties.get(childName).getKey()));
			nodes.putIfAbsent(childName, child);

			child.setParent(root);
			children.add(child);
		}
		return children;
	}

	public static StringBuilder treeStructure(int depth, StringBuilder indent, StringBuilder dirStructure, Node root, int limit, boolean isTail) {

		dirStructure.append(indent.append((isTail ?  "└── " : "├── " )).append(root).append("\n"));
		indent.setLength(indent.length() - (root.toString().length() + 5));

		if (root.getChildren() == null) return dirStructure;
		if (root.getChildren().size() == 0) return dirStructure;

		if (depth < limit) {
			ArrayList<Node> children = root.getChildren();
			indent.append((isTail ?  "    "  : "│   "));
			for (int i = 0; i < children.size() - 1; i++) {
				treeStructure(depth + 1, indent, dirStructure, children.get(i), limit, false);
			}

			treeStructure(depth + 1, indent, dirStructure, children.get(children.size() - 1), limit, true);
			indent.setLength(indent.length() - 4);
		}
		return dirStructure;
	}

	public static int getNeededWeight(Node root) {

		int i = 0;
		Node faultyNode = getInbalancedNode(root);
		Node rightNode = faultyNode.getParent().getChildren().get(i);
		while (rightNode == faultyNode) {
			rightNode = faultyNode.getParent().getChildren().get(i);
			i++;
		}

		return faultyNode.getWeight() + (sum(rightNode) - sum(faultyNode));
	}

	public static Node getInbalancedNode(Node root) {
		int[] sums = new int[root.getChildren().size()];
		int i = 0;
		for (Node child : root.getChildren()) {
			sums[i++] = sum(child);
		}
		Node faultyNode = null;
		for (int j = 0; j < sums.length; j++) {
			if (sums[j] != sums[(j - 1 + sums.length) % sums.length] && sums[j] != sums[(j + 1) % sums.length]) {
				faultyNode = root.getChildren().get(j);
				break;
			}
		}
		return faultyNode == null ? root : getInbalancedNode(faultyNode);
	}

	public static int sum (Node root) {
		int sum = root.getWeight();
		for (Node child : root.getChildren()) {
			sum += sum(child);
		}
		return sum;
	}
}