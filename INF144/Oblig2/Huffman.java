import java.io.*;
import java.util.*;

class Huffman{
	static StringBuilder builder, debuilder;
	String input, output;

	public Huffman(String input) throws IOException {
		this.input = input;
		output = huffmanTree(input);
	} 

	public static void encode(Node root, String string, Map<Character, String> code){
		if (root == null)
			return;

		if (root.left == null && root.right == null) {
			code.put(root.c, string);
		}
		encode(root.left, string + "0", code);
		encode(root.right, string + "1", code);
	}

	public static String huffmanTree(String input) {
		Map<Character, Integer> occurance = new HashMap<>();
		PriorityQueue<Node> pq = new PriorityQueue<>((l, r) -> l.occurance - r.occurance);

		for (int i = 0 ; i < input.length(); i++) {
			if (!occurance.containsKey(input.charAt(i))) {
				occurance.put(input.charAt(i), 0);
			}
			occurance.put(input.charAt(i), occurance.get(input.charAt(i)) + 1);
		}


		for (Map.Entry<Character, Integer> entry : occurance.entrySet()) {
			pq.add(new Node(entry.getKey(), entry.getValue()));
		}

		while (pq.size() != 1) {
			Node left = pq.poll();
			Node right = pq.poll();

			int sum = left.occurance + right.occurance;
			pq.add(new Node('\0', sum, left, right));
		}

		Node root = pq.peek();

		Map<Character, String> huffmanCode = new HashMap<>();
		encode(root, "", huffmanCode);

		builder = new StringBuilder();
		for (int i = 0 ; i < input.length(); i++) {
			builder.append(huffmanCode.get(input.charAt(i)));
		}
		
		debuilder = new StringBuilder();
		int index = -1;
		while (index < builder.length() - 2) {
			index = decode(root, index, builder);
		}
		return builder.toString();
	}

	public String getDecoded() {
		return debuilder.toString();
	}

	public static int decode(Node root, int idx, StringBuilder builder){
		if (root == null)
			return idx;

		if (root.left == null && root.right == null){
			debuilder.append(root.c);
			return idx;
		}

		idx++;

		if (builder.charAt(idx) == '0') {
			idx = decode(root.left, idx, builder);
		} else {
			idx = decode(root.right, idx, builder);
		}
		return idx;
	}
}
