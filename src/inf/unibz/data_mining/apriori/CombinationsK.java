package inf.unibz.data_mining.apriori;

import java.util.ArrayList;

public class CombinationsK {

	public static String endString = null;
	public static ArrayList<String> combinations = new ArrayList<String>();
    // print all subsets that take k of the remaining elements, with given prefix 
    public  static void comb1(String s, int k) { comb1(s, "", k);   }
    private static void comb1(String s, String prefix, int k) {
        if (s.length() < k) return;
        else if (k == 0){ 
        	//System.out.println(prefix); 
        	endString = prefix;
        	combinations.add(endString);
        	
        }
        else {
            comb1(s.substring(1), prefix + s.charAt(0), k-1);
            comb1(s.substring(1), prefix, k);
        }
    }  


    // print all subsets that take k of the remaining elements, with given prefix 
    public  static void comb2(String s, int k) { comb2(s, "", k); }
    private static void comb2(String s, String prefix, int k) {
        if (k == 0) System.out.println(prefix);
        else {
            for (int i = 0; i < s.length(); i++)
                comb2(s.substring(i + 1), prefix + s.charAt(i), k-1);
        }
    }  

    // read in N and k from command line, and print all subsets of size k from N elements
    public static void main(String[] args) {
       int N = 3;
       int k = 2;
       String alphabet = "068";
       String elements = alphabet.substring(0, N);

       comb1(elements, k);
       System.out.println("Final combinations: "+combinations);
    }

}