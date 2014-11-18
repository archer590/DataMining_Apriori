package inf.unibz.data_mining.main;
import inf.unibz.data_mining.apriori.Apriori;
import inf.unibz.data_mining.components.AssociationRule;
import inf.unibz.data_mining.components.ItemSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException{
		int choice = 0;
		long startTime = 0, endTime = 0, duration = 0;

		System.out.println("********* APRIORI ALGORITHM *********");
		System.out.println("Choose wihch algorithm do you want to run:");
		System.out.println("1. Apriori (pure version);");
		System.out.println("2. Apriori with database partitioning (optimized).");
		System.out.print("Choice: ");
		Scanner s = new Scanner(System.in);
		choice = s.nextInt();		
		ArrayList<ItemSet> frequentPatterns = new ArrayList<ItemSet>();
		Apriori ap = null;
		if(choice == 1){
			
			System.out.println("###############################################");
			System.out.println("######### FREQUENT PATTERN GENERATION #########");
			System.out.println("###############################################\n");
			System.out.print("Cleaning data... ");
			cleanData("./bank.csv", "./bank_marketing.arff");
			cleanData("./bank-full.csv", "./bank_marketing_full.arff");
			System.out.println("Done.");
			ap = new Apriori(args[0], Integer.parseInt(args[1]));
//			System.out.print("Scanning data file \"bank_marketing_full.arff\"... ");
			ap.scanData();
			System.out.println("Done.");
			System.out.print("Generating items... ");
			ap.getItems(ap.getFileLines());
			System.out.println("Done.");
			System.out.print("Generated items: ");
			System.out.println(ap.getMappingTable());
			System.out.println();
			System.out.println("**************************************************************************************************");
			System.out.println("##### K-ITEMSETS GENERATION #####\n");
			startTime = System.currentTimeMillis();
			frequentPatterns.addAll(ap.generateKItemset(ap.getMappingTable(), ap.getFileLines(), Integer.parseInt(args[1])));
			endTime = System.currentTimeMillis();
			
		} else if (choice == 2) {
			
			System.out.println("###############################################");
			System.out.println("######### FREQUENT PATTERN GENERATION #########");
			System.out.println("##### PARTITIONING DATABASE OPTIMIZATION ######");
			System.out.println("###############################################\n");
			System.out.print("Cleaning data... ");
			cleanData("./bank.csv", "./bank_marketing.arff");
			cleanData("./bank-full.csv", "./bank_marketing_full.arff");
			System.out.println("Done.");
			ap = new Apriori(args[0], Integer.parseInt(args[1]));
//			System.out.print("Scanning data file \"bank_marketing.arff\"... ");
			System.out.print("Database to partition: "+args[0]+"\nMinimum support: "+args[1]);
			System.out.println();
			System.out.println();
			startTime = System.currentTimeMillis();
			frequentPatterns.addAll(ap.partitioningDB(Integer.parseInt(args[2]), Integer.parseInt(args[1])));
			endTime = System.currentTimeMillis();
		}
		
		//reversing the mapping table to get the initial items
		System.out.println();
		System.out.println("FINAL ITEMSETS: " + ap.reverseMappingTable(frequentPatterns));
		System.out.println();

		duration = endTime - startTime;
		System.out.println();
		System.out.println("\nELAPSED TIME: " + new SimpleDateFormat("mm:ss").format(new Date(duration)));
		System.out.println();
		System.out.println("GENERATING ASSOCIATION RULES");
		System.out.println();
		ArrayList<AssociationRule> arules = ap.generateAssociationRules(frequentPatterns, 90);
		for(AssociationRule ar  : arules){
			System.out.println(ar.toString() + ", Confidence: " + ar.getConfidence() + "%");
		}
		System.out.println();
		System.out.println("Done.");
	}
	
	public static void cleanData(String input, String output) throws IOException{
		String newLine = null, currentLine;
		FileReader fr = new FileReader(input);
		BufferedReader br = new BufferedReader(fr);
		File f = new File(output);
		FileWriter fw = new FileWriter(f.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		newLine = "% 1. Title: Bank Marketing\n"
				+ "%\n"
				+ "% 2. Sources:\n"
				+ "% Created by: Paulo Cortez (Univ. Minho) and S�rgio Moro (ISCTE-IUL) @ 2012\n\n"
				+ "@RELATION\tbank_marketing\n\n"
				+ "@ATTRIBUTE\tage\t\t\tNUMERIC\n"
				+ "@ATTRIBUTE\tjob\t\t\tNOMINAL\n"
				+ "@ATTRIBUTE\tmarital\t\tNOMINAL\n"
				+ "@ATTRIBUTE\teducation\tNOMINAL\n"
				+ "@ATTRIBUTE\tdefault\t\tNOMINAL\n"
				+ "@ATTRIBUTE\tbalance\t\tNUMERIC\n"
				+ "@ATTRIBUTE\thousing\t\tNOMINAL\n"
				+ "@ATTRIBUTE\tloan\t\tNOMINAL\n"
				+ "@ATTRIBUTE\tcontact\t\tNOMINAL\n"
				+ "@ATTRIBUTE\tday\t\t\tNUMERIC\n"
				+ "@ATTRIBUTE\tmonth\t\tNOMINAL\n"
				+ "@ATTRIBUTE\tduration\tNUMERIC\n"
				+ "@ATTRIBUTE\tcampaign\tNUMERIC\n"
				+ "@ATTRIBUTE\tpdays\t\tNUMERIC\n"
				+ "@ATTRIBUTE\tprevious\tNUMERIC\n"
				+ "@ATTRIBUTE\tpoutcome\tNOMINAL\n"
				+ "@ATTRIBUTE\ty\t\t\tNOMINAL\n\n"
				+ "@DATA\n";
		currentLine = br.readLine();
		currentLine = br.readLine();
		bw.write(newLine);
		while(currentLine != null){
			newLine = currentLine.replaceAll("\"", "");
			newLine = newLine.replaceAll(";", ",");
			bw.write(newLine+"\n");
			currentLine = br.readLine();
		}
		bw.close();
		br.close();
		
	}
	
}
