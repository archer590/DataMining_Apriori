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
		FileReader fr = new FileReader("./bank_marketing.arff");
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		while (line != "@DATA" && line != null)
		{
			br.readLine();
		}
		line = br.readLine();
		StringTokenizer st = null;
		while (line != null){
			st = new  StringTokenizer(line, ",");
			for(int i = 0; i < st.countTokens() - 1; i++){
				//TODO read the line and fill the list of the k-itemset
			}
		}
	}

}
