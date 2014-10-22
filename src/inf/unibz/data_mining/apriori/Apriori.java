package inf.unibz.data_mining.apriori;
import inf.unibz.data_mining.components.Item;
import inf.unibz.data_mining.components.ItemSet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;


public class Apriori {
	
//	public static final String[] ATTRIBUTES_NAME = {"age","job","marital","education","default","balance","housing","loan","contact","day","month","duration","campaign","pdays","previuos","poutcome","y"};
	public static final String[] ATTRIBUTES_NAME = {"first","second","third","fourth","fifth"};
	public static final int minSup = 2;
	ArrayList<ItemSet> itemsets;
	ArrayList<String> fileLines;
	BufferedReader br;
	
	public Apriori(){
		itemsets = new ArrayList<ItemSet>();
		fileLines = new ArrayList<String>();
		FileReader fr = null;
		try {
			fr = new FileReader("./sample_transactions.arff");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(fr);
	}
		
	public void scanData() throws IOException
	{
		String currentLine = br.readLine();
		while (currentLine != null)
		{	
			if (currentLine.equals("@DATA"))
			{
				break;
			}
			currentLine = br.readLine();
		}
		currentLine = br.readLine();
		
		while (currentLine != null){
			fileLines.add(currentLine);
			currentLine = br.readLine();
		}
		br.close();
	}
	
	public List<Item> getItems(){
		StringTokenizer st = null;
		Item currentItem = null;
		ArrayList<Item> items = new ArrayList<Item>();
		for(int j = 0; j < fileLines.size(); j++){
			st = new  StringTokenizer(fileLines.get(j), ",");
			System.out.println("# of tokens: " + st.countTokens());
			int n = st.countTokens();
			for(int i = 0; i < n; i++){
				String token = st.nextToken();
				System.out.println("Token: " + token);
				if(!token.equals("?")){
					currentItem = new Item(ATTRIBUTES_NAME[i], token);
					System.out.println("Current item: " + currentItem.toString());
					items.add(currentItem);
				}
			}
		}
		return items;
	}
	
	public void generateKItemset(){
	
//		for(int k = 1; k < ; k++){
//			
//			itemsets = candidateGeneration(itemsets, k);
//			computeSupport(itemsets);
//			pruning();
//		}
	}
	
	public void pruning(){
		for(ItemSet is : itemsets.toArray(new ItemSet[] {}))
			if(is.getItemSupport() < minSup)
				itemsets.remove(is); //pruning itemsets with no minimum support
			else
				System.out.println(is.toString() + ": " + is.getItemSupport());
	}
	
	public ArrayList<ItemSet> candidateGeneration(ArrayList<ItemSet> kItemSets, int k){
		if(k == 1)
			return kItemSets;
		else{
			ArrayList<ItemSet> result = new ArrayList<ItemSet>();
			Comparator c = new Comparator<Item>() {

				@Override
				public int compare(Item o1, Item o2) {
					if(o1.getAttributeOrder() < o2.getAttributeOrder())
						return -1;
					else if (o1.getAttributeOrder() == o2.getAttributeOrder())
						return 0;
					else if (o1.getAttributeOrder() > o2.getAttributeOrder())
						return 1;
					return k;
				}
			};
			ItemSet is = null;
			ArrayList<Item> outer = null;
			ArrayList<Item> inner = null;
			for(int i = 0; i < kItemSets.size(); i++){
				outer = kItemSets.get(i).getItems();
				for(int j = i+1; j < kItemSets.size(); j++ ){
					inner = kItemSets.get(j).getItems();
					for(int y = 0; y < inner.size(); y++){
						if(kItemSets.get(i).getMaxOrder() < inner.get(y).getAttributeOrder()){
							is = new ItemSet();
							is.setItems(outer);
							is.setItem(inner.get(y));
							result.add(is);
						}
					}
				}
			}
			return result;
		}
	}
	
	public void computeSupport(ArrayList<ItemSet> itemsets){
		for(ItemSet is : itemsets){
			ArrayList<Item> isItems = is.getItems();
			for(String s : fileLines){
				String[] splitted = s.split(",");
			}
		}
	}
}
