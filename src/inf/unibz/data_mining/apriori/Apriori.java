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
import java.util.Scanner;
import java.util.StringTokenizer;
	
	public class Apriori {
	
		 public static final String[] ATTRIBUTES_NAME =
	 {"age","job","marital","education","default","balance","housing","loan","contact","day","month","duration","campaign","pdays","previuos","poutcome","y"};
//	public static final String[] ATTRIBUTES_NAME = { "first", "second", "third", "fourth", "fifth" };
	private int minSup;
	ArrayList<ItemSet> itemsets;
	ArrayList<String> fileLines;
	ArrayList<ArrayList<String>> partitions;
	BufferedReader br;
	HashMap<Integer, Item> mappingTable;
	
	
	public Apriori(String file, int minSup) {
		itemsets = new ArrayList<ItemSet>();
		fileLines = new ArrayList<String>();
		mappingTable = new HashMap<Integer, Item>();
		FileReader fr = null;
		this.minSup = minSup;
		try {
			fr = new FileReader(file);
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
	
	public Collection<Item> getItems(ArrayList<String> currentFileLines) {
		StringTokenizer st = null;
		Item currentItem = null;
		int keyGenerator = 0;
		for (int j = 0; j < currentFileLines.size(); j++) {
			st = new StringTokenizer(currentFileLines.get(j), ",");
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
						keyGenerator++;
					}
				}
			}
		}
		return mappingTable.values();
	}
	
	public Collection<Item> getItems(ArrayList<String> currentFileLines, HashMap<Integer, Item> mappingTable) {
		StringTokenizer st = null;
		Item currentItem = null;
		int keyGenerator = 0;
		for (int j = 0; j < currentFileLines.size(); j++) {
			st = new StringTokenizer(currentFileLines.get(j), ",");
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
						if(!contains(this.mappingTable.values(), currentItem))
							this.mappingTable.put(keyGenerator, currentItem);
						keyGenerator++;
					}
				}
			}
		}
		return mappingTable.values();
	}
	
	public ArrayList<ItemSet> generateKItemset(HashMap<Integer, Item> mappingTable) {
		ArrayList<ItemSet> candidates = new ArrayList<ItemSet>();
		ArrayList<ItemSet> itemsets = new ArrayList<ItemSet>();
		System.out.println("GENERATING ITEMSETS OF SIZE 1...");
		candidates = populateFirstKItemSets(mappingTable);
		System.out.println("Performing pruning: NOT NECCESSARY FOR k = 1");
		itemsets.addAll(computeSupport(candidates));
		System.out.println("Itemsets for k = 1: "+ itemsets);
		System.out.println("Support:");
		itemsets = checkCandidateSupport(itemsets);
		System.out.println("**************************************************************************************************");
		for(int k = 2; candidates.size() != 0; k++){
			System.out.println("GENERATING ITEMSETS OF SIZE " + k + "...");
			candidates = candidateGeneration(itemsets, k); 
			if(candidates.size() != 0){
				ArrayList<ItemSet> candGen = new ArrayList<ItemSet>();
				System.out.println("Candidates generated (not pruned):  " + candidates);
				System.out.println("Perorming pruning:");
				candGen = pruning(k, candidates, itemsets);
				System.out.println("Pruned: " + candGen);
				candGen = computeSupport(candGen);
				System.out.println("Computed support: " + candGen);
				System.out.println("Support:");
				candGen = checkCandidateSupport(candGen);
				System.out.println("Checked support: " + candGen);
				itemsets.addAll(candGen);
				System.out.println("Itemsets for k = " + k + ": " + itemsets);
				System.out.println("**************************************************************************************************");
			}
			else
				System.out.println("NO ITEMSETS WITH k = " + k + ". EXIT.");
		}
		return itemsets;
	}
	
	public ArrayList<ItemSet> candidateGeneration(ArrayList<ItemSet> kItemSets, int k) {
		ArrayList<ItemSet> candidates = new ArrayList<ItemSet>();
		if(k != 1){
			ArrayList<Integer> outer = null;
			ArrayList<Integer> inner = null;
			for (int i = 0; i < kItemSets.size(); i++) {
				outer = kItemSets.get(i).getItems();
				if(outer.size() == k-1){
					for (int j = i + 1; j < kItemSets.size(); j++) {
						inner = kItemSets.get(j).getItems();
						ItemSet candidate = null;
						for (int h = 0; h < inner.size(); h++)
						{
							Integer key = outer.get(outer.size()-1); //get the item mapped to the integer at the last position of the outer object
							if(mappingTable.get(key).getAttributeOrder() < mappingTable.get(inner.get(h)).getAttributeOrder())
							{
								candidate = new ItemSet();
								candidate.getItems().addAll(outer);
								candidate.getItems().add(inner.get(h));
								if(!contains(candidates, candidate)){
									candidates.add(candidate);
								}
							}
						}
					}
				}
			}
		}
		return candidates;
	}
	
	
	public ArrayList<ItemSet> computeSupport(ArrayList<ItemSet> itemsets) {
		ArrayList<ItemSet> result = new ArrayList<ItemSet>();
		for (ItemSet is : itemsets) {
			ArrayList<Integer> isItems = is.getItems();
			for (String s : fileLines) {
				String[] splitted = s.split(",");
				boolean isPresent = false;
				for (int i = 0; i < isItems.size(); i++) {
					Item it = mappingTable.get(isItems.get(i));
					if (splitted[it.getAttributeOrder()].equals((String) it.getAttributeValue()))
						isPresent = true;
					else {
						isPresent = false;
						break;
					}
				}
				
				if(isPresent){
					is.setItemSupport(is.getItemSupport()+1);
				}
			}
			result.add(is);
		}
		return result;
	}
	
	public ArrayList<ItemSet> checkCandidateSupport(ArrayList<ItemSet> toCheck) {
		ArrayList<ItemSet> aux = toCheck;
		for (ItemSet is : aux.toArray(new ItemSet[] {}))
			if (is.getItemSupport() < minSup){
//				System.out.println("\t" + is.toStringSupport() + " not enough");
				aux.remove(is); //eliminate candidate with no minimum support
			}
			else{
				System.out.println("\t" + is.toStringSupport() + " SUPPORT OK");
			}
		System.out.println();
		return aux;
	}
	
	public ArrayList<ItemSet> pruning(int k, ArrayList<ItemSet> candidates, ArrayList<ItemSet> itemsets){			
			ArrayList<ItemSet> toReturn = new ArrayList<ItemSet>();
			ArrayList<String> combinations = null;
			for(ItemSet is : candidates){
				System.out.print("\n\tcandidate: " + is.toString());
				boolean check = false;
				combinations = comb1(is.toItemString(), k-1);
				System.out.print(", possible subset: ");
				printCombinations(combinations);
				for(String s : combinations){
					ArrayList<String> currentCombination = toArrayList(s);
					for(int i = 0; i < itemsets.size(); i++){
						ItemSet isK = itemsets.get(i);
						if(isK.getItems().size() == k-1){
							check = isK.contains(currentCombination);
							if(check)
								break;
						}
					}
					if(!check)
						break;
				}
				if(check){
					toReturn.add(is);
				}
			}

			return toReturn;
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
	
	public ArrayList<String> toArrayList(String s){
		ArrayList<String> finalArray = new ArrayList<String>();
		String[] tmp = s.split("");
		for (int i=0;i<tmp.length;i++){
			finalArray.add(tmp[i]);
		}
		return finalArray;
	}

	
	/**
	 * Populate the itemsets for k = 1.
	 * @return a list with ItemSet for k = 1.
	 */
	public ArrayList<ItemSet> populateFirstKItemSets(HashMap<Integer, Item> mappingTable){
		Integer[] keys = mappingTable.keySet().toArray(new Integer[] {});
		Arrays.sort(keys);
		ArrayList<ItemSet> iSs = new ArrayList<ItemSet>();
		for(int i : keys){
			ItemSet is = new ItemSet();
			is.setItems(i);
			iSs.add(is);
		}
		System.out.println("Candidate generated (not pruned): " + iSs);
		return iSs;
	}

	public boolean containSubItemSet(ItemSet is1, ItemSet is2){
		if(is1.toString().equals(is2.toString()))
			return true;
		return false;
	}
	
	public ArrayList<String> comb1(String s, int k) { 
		ArrayList<String> combinations = new ArrayList<String>();
		comb1(s, "", k, combinations);
		return combinations;
	}
	
    private void comb1(String s, String prefix, int k, ArrayList<String> combinations) {
        if (s.length() < k) return;
        else if (k == 0){ 
//        	System.out.println(prefix); 
        	combinations.add(prefix);
        	
        }
        else {
            comb1(s.substring(1), prefix + s.charAt(0), k-1, combinations);
            comb1(s.substring(1), prefix, k, combinations);
        }
    }  
    
    public void printCombinations(ArrayList<String> combinations){
    	System.out.print("[");
    	for(int i = 0; i < combinations.size(); i++){
    		System.out.print("[" + combinations.get(i) + "]");
    		if(i < combinations.size()-1)
    			System.out.print(", ");
    	}    	
    	System.out.println("]");
    }
    
    public HashMap<Integer, Item> getMappingTable(){
    	return this.mappingTable;
    }
    
    public ArrayList<String> reverseMappingTable(ArrayList<ItemSet> itemsets){
    	ArrayList<String> reversedTable = new ArrayList<String>();
    	for(ItemSet is : itemsets){
    		String itemset = "<";
    		ArrayList<Integer> items = is.getItems();
    		for(int i = 0; i < is.getItems().size(); i++){
    			itemset += items.get(i) + "=" + mappingTable.get(items.get(i)).toString();
    			if(i < is.getItems().size()-1)
    				itemset += ", ";
    		}
    		itemset += ">";
    		reversedTable.add(itemset);
    	}
    	return reversedTable;
    }
    
    @SuppressWarnings("resource")
	public void partitioningDB() throws IOException{
    	System.out.print("\nHow many transactions per partitions?   ");
    	Scanner s = new Scanner(System.in);
    	int linesPerPartition = s.nextInt();
    	int numberOfTransitions = 0;
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
		
		numberOfTransitions = fileLines.size();
		partitions = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp = null;
		for(double j = 0.0; j < numberOfTransitions; j++){		
			if(j+1d % linesPerPartition == 0.0 || j == 0.0)
				tmp = new ArrayList<String>();	
			tmp.add(fileLines.get(( (int) j)));
			partitions.add(tmp);
		}
		br.close();
		
		ArrayList<ItemSet> partitionItemsSets = new ArrayList<ItemSet>();		
		
//		getItems(fileLines, mappingTable);
		
		for(int h=0; h<partitions.size();h++){
			ArrayList<String> currentPartition = partitions.get(h);
			
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					HashMap<Integer, Item> currentMappingTable = new HashMap<Integer, Item>();
					getItems(currentPartition, currentMappingTable);					
					partitionItemsSets.addAll(generateKItemset(currentMappingTable));
				}
			});
			t.start();
		}
		ArrayList<ItemSet> globalCandidates = new ArrayList<ItemSet>();
		globalCandidates.addAll(cleanGlobalCandidates(partitionItemsSets));
		computeSupport(globalCandidates);
    }

	public ArrayList<String> getFileLines() {
		return fileLines;
	}

	public void setFileLines(ArrayList<String> fileLines) {
		this.fileLines = fileLines;
	}
    
    public void mergePartitions(ArrayList<ArrayList<ItemSet>> partitionItemsSets){
    	ArrayList<ItemSet> result = new ArrayList<ItemSet>();
    		for(int j = 0; j < partitionItemsSets.size(); j++){
    			ArrayList<ItemSet> currentPartition = partitionItemsSets.get(j);
    		}
    }
    
    public ArrayList<ItemSet> cleanGlobalCandidates(ArrayList<ItemSet> itemsets){
    	for(int i = 0; i < itemsets.size(); i++){
    		ItemSet is = itemsets.get(i);    		
    		for(int j = i+1; j < itemsets.size(); j++){
    			ItemSet is1 = itemsets.get(j);
    			if(is.toString().equals(is1.toString()))
    				itemsets.remove(j);
    		}
    	}
    	return itemsets;
    }
    
}
