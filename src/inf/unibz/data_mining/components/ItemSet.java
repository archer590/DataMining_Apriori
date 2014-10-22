package inf.unibz.data_mining.components;

import java.util.ArrayList;

public class ItemSet {
	
	private ArrayList<Item> items;
	private int itemSupport;
	int maxOrder;
	
	public ItemSet(){
		maxOrder = -1;
		computeMaxOrder();
	}

	public int getMaxOrder() {
		return maxOrder;
	}

	public void setMaxOrder(int maxOrder) {
		this.maxOrder = maxOrder;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	public void setItem(Item...items){
		for(Item i : items)
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
		for(Item i : items){
			res += i.toString() + ", "; 
		}
		res += "]";
		return res;
	}
	
	public void computeMaxOrder(){
		for(Item i : items)
			if(i.getAttributeOrder() > maxOrder)
				maxOrder = i.getAttributeOrder();
	}

}
