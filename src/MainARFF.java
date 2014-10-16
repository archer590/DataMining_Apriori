import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainARFF {
	
	public static void main(String[] args) throws IOException{
		cleanData("./bank.csv", "./bank_marketing.arff");
		cleanData("./bank-full.csv", "./bank_marketing_full.arff");
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
				+ "% Created by: Paulo Cortez (Univ. Minho) and SŽrgio Moro (ISCTE-IUL) @ 2012\n\n"
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
		System.out.println("ARFF parser for data mining course.");
	}
}
