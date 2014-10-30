package inf.unibz.data_mining.components;

import java.util.ArrayList;

public class ItemSet {
	
	private ArrayList<Integer> items;
	private int itemSupport;
	int maxOrder;
	int minOrder;
	
	public ItemSet(){
		maxOrder = -1;
		items = new ArrayList<Integer>();
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
	
	public void setItems(Integer...items){
		for(Integer i : items)
			this.items.add(i);
	}

	public int getItemSupport() {
		return itemSupport;
	}

	public void setItemSupport(int itemSupport) {
		this.itemSupport = itemSupport;
	}
	
	public String toStringSupport(){
		return items.toString() + "->" + itemSupport;
	}
	
	public String toString(){
		return items.toString();
	}

}
