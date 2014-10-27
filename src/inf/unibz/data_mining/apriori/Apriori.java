	package inf.unibz.data_mining.apriori;
	
	import inf.unibz.data_mining.components.Item;
import inf.unibz.data_mining.components.ItemSet;

	import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;
	
	public class Apriori {
	
		// public static final String[] ATTRIBUTES_NAME =
	// {"age","job","marital","education","default","balance","housing","loan","contact","day","month","duration","campaign","pdays","previuos","poutcome","y"};
	public static final String[] ATTRIBUTES_NAME = { "first", "second", "third", "fourth", "fifth" };
	public static final int minSup = 2;
	ArrayList<ItemSet> itemsets;
	ArrayList<String> fileLines;
	BufferedReader br;
	HashMap<Integer, Item> mappingTable;
	ArrayList<ItemSet> candidates;
	
	public Apriori() {
		itemsets = new ArrayList<ItemSet>();
		fileLines = new ArrayList<String>();
		mappingTable = new HashMap<Integer, Item>();
		candidates = new ArrayList<ItemSet>();
		FileReader fr = null;
		try {
			fr = new FileReader("./sample_transactions.arff");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(fr);
	}
	
	public void scanData() throws IOException {
		String currentLine = br.readLine();
		while (currentLine != null) {
			if (currentLine.equals("@DATA")) {
				break;
			}
			currentLine = br.readLine();
		}
		currentLine = br.readLine();
	
		while (currentLine != null) {
			fileLines.add(currentLine);
			currentLine = br.readLine();
		}
		br.close();
	}
	
	public Collection<Item> getItems() {
		StringTokenizer st = null;
		Item currentItem = null;
		int keyGenerator = 0;
		for (int j = 0; j < fileLines.size(); j++) {
			st = new StringTokenizer(fileLines.get(j), ",");
			//System.out.println("# of tokens: " + st.countTokens());
			int n = st.countTokens();
			
			for (int i = 0; i < n; i++) {
				String token = st.nextToken();
				//System.out.println("Token: " + token);
				if (!token.equals("?")) {
					currentItem = new Item(ATTRIBUTES_NAME[i], token);
					currentItem.setAttributeOrder(i);
					
					//System.out.println("Current item: " + currentItem.toString());
					if(!contains(mappingTable.values(), currentItem)){
						mappingTable.put(keyGenerator, currentItem);
						System.out.println(mappingTable);
					}
					keyGenerator++;
				}
			}
		}
		return mappingTable.values();
	}
	
	public void generateKItemset() {
//		int initialItemsets = itemsets.size();
//		int updateSize = 0;
		itemsets = populateFirstKItemSets();
		System.out.println(itemsets);
		candidates = itemsets;
		for(int k = 1; candidates.size() != 0; k++){
			candidateGeneration(itemsets, k);
			itemsets.addAll(computeSupport(candidates));
			System.out.println(itemsets);
			pruning();
			itemsets.addAll(candidates);
		}
	}
	
	public void pruning() {
		for (ItemSet is : itemsets.toArray(new ItemSet[] {}))
			if (is.getItemSupport() < minSup)
				itemsets.remove(is); // pruning itemsets with no minimum support
			else
				System.out.println(is.toString());
	}
	
	public void candidateGeneration(ArrayList<ItemSet> kItemSets, int k) {
		if(k != 1){
			ArrayList<Integer> outer = null;
			ArrayList<Integer> inner = null;
			candidates = new ArrayList<ItemSet>();
			for (int i = 0; i < kItemSets.size(); i++) {
				outer = kItemSets.get(i).getItems();
				for (int j = i + 1; j < kItemSets.size(); j++) {
					inner = kItemSets.get(j).getItems();
					ItemSet candidate = null;
					for (int h = 0; h < inner.size(); h++)
					{
						if(mappingTable.get(outer.get(outer.size()-1)).getAttributeOrder() < mappingTable.get(inner.get(h)).getAttributeOrder())
						{
							candidate = new ItemSet();
							candidate.getItems().addAll(outer);
							candidate.getItems().add(inner.get(h));
							if(!contains(candidates, candidate))
								candidates.add(candidate);
						}
					}
				}
			}
		}
	}
	
	
	public ArrayList<ItemSet> computeSupport(ArrayList<ItemSet> itemsets) {
		ArrayList<ItemSet> result = new ArrayList<ItemSet>();
		for (ItemSet is : itemsets) {
			ArrayList<Integer> isItems = is.getItems();
//			System.out.println(isItems);
			for (String s : fileLines) {
				String[] splitted = s.split(",");
				boolean isPresent = false;
//				System.out.println("isItem.size: "+isItems.size());
				for (int i = 0; i < isItems.size(); i++) {
					Item it = mappingTable.get(isItems.get(i));
//					System.out.println("i: "+i+"\nAttribute: "+it.toString()+", AttrOrder: "+it.getAttributeOrder()+"\nStringValue: "+splitted[it.getAttributeOrder()]+"\nMappingTValue: "+it.getAttributeValue());
					if (splitted[it.getAttributeOrder()].equals(it.getAttributeValue()))
						isPresent = true;
					else 
						isPresent = false;
				}
				
				if(isPresent){
//					System.out.println("Item present!");
					is.setItemSupport(is.getItemSupport()+1);
				}
			}
//			System.out.println("Support: "+is.getItemSupport());
			result.add(is);
		}
		return result;
	}
	
	public boolean contains(Collection<Item> items, Item i){
		Comparator<Item> c = new Comparator<Item>() {
			
			@Override
			public int compare(Item o1, Item o2) {
				if(o1.getAttributeName().equals(o2.getAttributeName()) && o1.getAttributeValue().equals(o2.getAttributeValue()))
					return 0;
				return -1;
			}
		};
		for(Item o : items)
			if(c.compare(o, i) == 0)
				return true;
		return false;
	}
	
	public boolean contains(ArrayList<ItemSet> iS1, ItemSet is){
		for(ItemSet i: iS1)
			if(i.toString().equals(is.toString()))
				return true;
		return false;
	}
	
	/**
	 * Populate the itemsets for k = 1.
	 * @return a list with ItemSet for k = 1.
	 */
	public ArrayList<ItemSet> populateFirstKItemSets(){
		Integer[] keys = mappingTable.keySet().toArray(new Integer[] {});
		Arrays.sort(keys);
		ArrayList<ItemSet> iSs = new ArrayList<ItemSet>();
		for(int i : keys){
			ItemSet is = new ItemSet();
			is.setItem(i);
			iSs.add(is);
		}
		return iSs;
	}
}
