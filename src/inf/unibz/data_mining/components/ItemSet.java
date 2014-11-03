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
		return "itemset: " + items.toString() + "->" + itemSupport;
	}
	
	public String toString(){
		return items.toString();
	}
	
	public String toItemString(){
		String itemsToString = "";
		for(Integer i : items){
			itemsToString += i;
		}
		return itemsToString;
	}
	
	public boolean contains(ArrayList<String> combinations){
		boolean check = false;
		for(int i = 0; i < items.size(); i++){
			if(String.valueOf(items.get(i)).equals(combinations.get(i)))
				check = true;
			else
				return false;
			
		}
		return check;
	}

}
