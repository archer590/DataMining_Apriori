package inf.unibz.data_mining.apriori;
import inf.unibz.data_mining.components.Item;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;


public class Apriori {
	
	public static final String[] ATTRIBUTES_NAME = {"age","job","marital","education","default","balance","housing","loan","contact","day","month","duration","campaign","pdays","previuos","poutcome","y"};
	private Hashtable<List<Item>, Integer> itemSupport;
	
	public Apriori(){}
		
	public List<Item> extractData() throws IOException
	{
		ArrayList<Item> transactions = new ArrayList<Item>();
		FileReader fr = new FileReader("./bank_marketing.arff");
		BufferedReader br = new BufferedReader(fr);
		String currentLine = br.readLine();
		System.out.println("Start\n"+currentLine);
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
		System.out.println(currentLine);
		while (currentLine != null){
			st = new  StringTokenizer(currentLine, ",");
			for(int i = 0; i < st.countTokens() - 1; i++){
				currentItem = new Item(ATTRIBUTES_NAME[i], st.nextToken());
				transactions.add(currentItem);
			}
		}
		br.close();
		return transactions;
	}
	
	public void generateKItemset(){
		//TODO
	}

}
