package inf.unibz.data_mining.components;

import java.util.ArrayList;

public class ItemSet {
	
	private ArrayList<Integer> items;
	private int itemSupport;
	int maxOrder;
	int minOrder;
	
	public ItemSet(){
		maxOrder = -1;
	}

	public int getMaxOrder() {
		return maxOrder;
	}

	public void setMaxOrder(int maxOrder) {
		this.maxOrder = maxOrder;
	}

	public ArrayList<Integer> getItems() {
		return items;
	}

	public void setItems(ArrayList<Integer> items) {
		this.items = items;
	}
	
	public void setItem(int...items){
		for(int i : items)
			this.items.add(i);
	}

	public int getItemSupport() {
		return itemSupport;
	}

	public void setItemSupport(int itemSupport) {
		this.itemSupport = itemSupport;
	}
	
	public String toString(){
		String res = "[";
		for(int i : items){
			res += String.valueOf(i) + ", "; 
		}
		res += "]";
		return res;
	}
	
//	public void computeMaxOrder(){
//		for(Item i : items)
//			if(i.getAttributeOrder() > maxOrder)
//				maxOrder = i.getAttributeOrder();
//	}
//	
//	public void computeMinOrder(){
//		for(Item i : items)
//			if(i.getAttributeOrder() < minOrder)
//				minOrder = i.getAttributeOrder();
//	}

}
