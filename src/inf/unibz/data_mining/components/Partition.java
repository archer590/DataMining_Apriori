package inf.unibz.data_mining.components;

import java.util.ArrayList;
import java.util.HashMap;

public class Partition {
	HashMap<Integer, Item> mappingTable = null;
	ArrayList<String> transactions = null;
	ArrayList<ItemSet> itemSet = null;
	
	public Partition(HashMap<Integer, Item> mt, ArrayList<String> transactions){
		mappingTable = mt;
		this.transactions = transactions;
	}

	public HashMap<Integer, Item> getMappingTable() {
		return mappingTable;
	}

	public void setMappingTable(HashMap<Integer, Item> mappingTable) {
		this.mappingTable = mappingTable;
	}

	public ArrayList<String> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<String> transactions) {
		this.transactions = transactions;
	}

	public ArrayList<ItemSet> getItemSet() {
		return itemSet;
	}

	public void setItemSet(ArrayList<ItemSet> itemSet) {
		this.itemSet = itemSet;
	}

}
