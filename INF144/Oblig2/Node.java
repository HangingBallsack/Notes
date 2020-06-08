class Node {
	char c;
	int occurance;
	Node left, right = null;

	Node(char c, int occurance){
		this.c = c;
		this.occurance = occurance;
	}

	public Node(char c, int occurance, Node left, Node right) {
		this.c = c;
		this.occurance = occurance;
		this.left = left;
		this.right = right;
	}
};