package inf.unibz.data_mining.apriori;
import inf.unibz.data_mining.components.Item;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;


public class Apriori {
	
//	public static final String[] ATTRIBUTES_NAME = {"age","job","marital","education","default","balance","housing","loan","contact","day","month","duration","campaign","pdays","previuos","poutcome","y"};
	public static final String[] ATTRIBUTES_NAME = {"first","second","third","fourth","fifth"};
	private ArrayList<Item> items;
	private Hashtable<String, Integer> itemSupport;
	
	public Apriori(){}
		
	public List<Item> extractData() throws IOException
	{
		items = new ArrayList<Item>();
		FileReader fr = new FileReader("./sample_transactions.arff");
		BufferedReader br = new BufferedReader(fr);
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
		StringTokenizer st = null;
		Item currentItem = null;
		itemSupport = new Hashtable<String, Integer>();
		while (currentLine != null){
			st = new  StringTokenizer(currentLine, ",");
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
			currentLine = br.readLine();
		}
		br.close();
		return items;
	}
	
	public void generateKItemset(){
		for(Item i : items){
			Integer support = itemSupport.get(i.toString());
			if(support != null)
				itemSupport.replace(i.toString(), ++support);
			else
				itemSupport.put(i.toString(), 1);
		}
		for(String i : itemSupport.keySet())
			System.out.println(i + ": " + itemSupport.get(i));
	}
}
