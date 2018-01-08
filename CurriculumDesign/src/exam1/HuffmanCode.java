package exam1;

import java.io.Serializable;

public class HuffmanCode implements Serializable{
	private int[] counts;	// 字符串中的字符对应出现次数
	private Tree tree;	// 生成的树

	/**
	 * 用特定的字符串初始化这个类，并创建一个这个对象
	 * @param text
	 */
	public HuffmanCode(String text) {
		counts = getCharacterFrequency(text);
		tree = getHuffmanTree(counts);;
	}

	/**
	 * 返回对应的字符出现的次数
	 * @return
	 */
	public int[] getCounts() {
		return counts;
	}

	/**
	 * 返回Huffman树
	 * @return
	 */
	public Tree getTree() {
		return tree;
	}

	public static String[] getCode(Tree.Node root) {
		if (root == null)
			return null;
		String[] codes = new String[2 * 128];
		assignCode(root, codes);
		return codes;
	}

	/**
	 * 拼编码
	 * @param root
	 * @param codes
	 */
	private static void assignCode(Tree.Node root, String[] codes) {
		if (root.left != null) {
			root.left.code = root.code + "0";
			assignCode(root.left, codes);
			
			root.right.code = root.code + "1";
			assignCode(root.right, codes);
		}
		else {
			codes[(int)root.element] = root.code;
		}
	}

	/**
	 * 采用堆排序获取到对应的HuffmanTree
	 * @param counts
	 * @return
	 */
	public static Tree getHuffmanTree(int[] counts) {
		Heap<Tree> heap = new Heap<Tree>();
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > 0)
				heap.add(new Tree(counts[i], (char)i));
		}
		
		while (heap.getSize() > 1) {
			Tree t1 = heap.remove();
			Tree t2 = heap.remove();
			heap.add(new Tree(t1, t2));
		}
		
		return heap.remove();
	}

	/**
	 * 获取到字符出现的次数
	 * @param text
	 * @return
	 */
	public static int[] getCharacterFrequency(String text) {
		int[] counts = new int[256];
		
		for (int i = 0; i < text.length(); i++) {
			counts[(int)text.charAt(i)]++;
		}
		
		return counts;
	}

	/**
	 * 构建HuffmanTree
	 */
	public static class Tree implements Comparable<Tree>, Serializable {
		Node root;
		
		public Tree(Tree t1, Tree t2) {
			root = new Node();
			root.left = t1.root;
			root.right = t2.root;
			root.weight = t1.root.weight + t2.root.weight;
		}

		public Tree(int weight, char element) {
			root = new Node(weight, element);
		}
		
		public int compareTo(Tree o) {
			if (root.weight < o.root.weight)
				return 1;
			else if (root.weight == o.root.weight)
				return 0;
			else 
				return -1;
		}
		
		public class Node implements Serializable{
			char element;	// 对应的字符
			int weight;	// 权重，就是字符出现的次数
			Node left;		// 左孩子
			Node right;		// 右孩子
			String code = "";	// 对应编码

			/**
			 * 默认的初始化方式
			 */
			public Node() {
			}

			/**
			 * 以特定的权重还有字符构建一个新的对象
			 * @param weight
			 * @param element
			 */
			public Node(int weight, char element) {
				this.weight = weight;
				this.element = element;
			}
		}
	}
}
