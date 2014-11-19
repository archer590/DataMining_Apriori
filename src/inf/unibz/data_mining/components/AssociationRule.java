package inf.unibz.data_mining.components;

import java.util.HashMap;

public class AssociationRule {
	
	ItemSet left;
	ItemSet right;
	double support;
	double subsetSupport;
	int confidence;
	
	public AssociationRule(){}

	public AssociationRule(ItemSet left, ItemSet right, double support, double subsetSupport) {
		super();
		this.left = left;
		this.right = right;
		this.support = support;
		this.subsetSupport = subsetSupport;
		this.confidence =  (int) ((subsetSupport/support) * 100);
	}

	public ItemSet getLeft() {
		return left;
	}

	public void setLeft(ItemSet left) {
		this.left = left;
	}

	public ItemSet getRight() {
		return right;
	}

	public void setRight(ItemSet right) {
		this.right = right;
	}

	public double getSupport() {
		return support;
	}

	public void setSupport(double support) {
		this.support = support;
	}

	public double getSubsetSupport() {
		return subsetSupport;
	}

	public void setSubsetSupport(double subsetSupport) {
		this.subsetSupport = subsetSupport;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}	
	
	public String toString(){
		return left.toString() + " ---> " + right.toString();
	}
	
	public String reversedToString(HashMap<Integer, Item> mappingTable){
		String result = "[";
		for(Integer i : left.getItems()){
			Item it = mappingTable.get(i);
			result += it.toString();
		}
		result += " ---> ";
		for(Integer i : right.getItems()){
			Item it = mappingTable.get(i);
			result += it.toString();
		}
		return result;
	}

}
