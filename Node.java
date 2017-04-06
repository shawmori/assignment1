import java.util.*;

public class Node {
	private Boolean leaf;
	private String outcome;
	private String attribute;
	private Node leftChild;
	private Node rightChild;
	private double probability;
	private int total;
	
	public Node(String att, Node left, Node right, Boolean leaf, String outcome, double probability, int total){
		attribute = att;
		leftChild = left;
		rightChild = right;
		this.leaf = leaf;
		this.outcome = outcome;
		this.probability = probability * 100;
		this.total = total;
	}
	
	public String getAttribute(){
		return attribute;
	}
	
	public Node getLeft(){
		return leftChild;
	}
	
	public Node getRight(){
		return rightChild;
	}
	
	public Boolean isLeaf(){
		return leaf;
	}
	
	public String getOutcome(){
		return outcome;
	}

	public void display(String i) {
		if(leaf){
				System.out.format("%sCategory %s, prob = %4.0f%% : /%d\n",
				i, outcome, probability, total);
				return;

		}
		System.out.format("%s%s = True:\n",
				i, attribute);
				leftChild.display(i+"    ");
				System.out.format("%s%s = False:\n",
				i, attribute);
				rightChild.display(i+"    ");

	}
}
