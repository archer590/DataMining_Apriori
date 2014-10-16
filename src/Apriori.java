import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Apriori {
	
	public static final String[] ATTRIBUTES_NAME = {"age","job","marital","education","default","balance","housing","loan","contact","day","month","duration","campaign","pdays","previuos","poutcome","y"};
		
	public static void main(String args[]) throws IOException
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
		System.out.println(currentLine);
		while (currentLine != null){
			st = new  StringTokenizer(currentLine, ",");
			Item currentItem = null;
			for(int i = 0; i < st.countTokens() - 1; i++){
				currentItem = new Item(ATTRIBUTES_NAME[i], st.nextToken());
				transactions.add(currentItem);
				//TODO read the line and fill the list of the k-itemset
			}
		}
		br.close();
	}

}
