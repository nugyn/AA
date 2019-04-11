import java.io.*;
import java.util.*;


public class DataGenerator{

    public static void main(String args[]){

    Scanner sc = new Scanner(System.in);
    System.out.println("[INPUT FILENAME] Enter in filename of the input dataset: ");
            String inputFilename = sc.nextLine();
    System.out.println("[OUTPUT FILENAME] Enter in filename of the new output dataset: ");
            String outputFilename = sc.nextLine();
	System.out.println("[GENERATE] Choose amount of Edges: " );
			int amtEdges = Integer.parseInt(sc.nextLine());
	System.out.println("Specify the density, L, M or H: ");
			String density = sc.nextLine();
			if(density.equalsIgnoreCase("L")){
				try {
                    BufferedReader reader = new BufferedReader(new FileReader(inputFilename + ".csv"));
                    PrintWriter pw = new PrintWriter(new FileWriter(outputFilename + ".csv"), true);
                    String line;
                    String delimiter = ",";
                    String[] tokens;
                    String srcLabel, tarLabel;
                    int weight;
                    int i = 0;

					while ((line = reader.readLine()) != null && i <= amtEdges/3 ) {
						tokens = line.split(delimiter);
                        srcLabel = tokens[0];
                        tarLabel = tokens[1];

                        weight = Integer.parseInt(tokens[2]);

                        pw.printf("%s,%s,%d\n", srcLabel, tarLabel, weight);
						System.out.println("Creating Graph Dataset..." + i); 
						i++;
                    }
                    pw.close();
                    reader.close();
				}
				catch (FileNotFoundException ex) {
					System.err.println("File " + args[1] + " not found.");
				}
				catch(IOException ex) {
					System.err.println("Cannot open file " + args[1]);
				}
			} //end of LOW DENSITY
		else if(density.equalsIgnoreCase("M")){
			try {
                PrintWriter pw = new PrintWriter(new FileWriter(outputFilename + ".csv"));
				BufferedReader reader = new BufferedReader(new FileReader(inputFilename + ".csv"));
				String line;
				String delimiter = ",";
				String[] tokens;
				String srcLabel, tarLabel;
				int weight;
				int i = 0;
				while ((line = reader.readLine()) != null && i <= amtEdges/2) {
					tokens = line.split(delimiter);
					srcLabel = tokens[0];
					tarLabel = tokens[1];
					weight = Integer.parseInt(tokens[2]);
                    pw.printf("%s,%s,%d\n", srcLabel, tarLabel, weight);
                    pw.printf("");
					System.out.println("Creating Graph dataset..." + i); 
					i++;
                }
                pw.close();
                reader.close();
			}
			catch (FileNotFoundException ex) {
				System.err.println("File " + args[1] + " not found.");
			}
			catch(IOException ex) {
				System.err.println("Cannot open file " + args[1]);
			}
		} //end of LOW DENSITY
		else if(density.equalsIgnoreCase("H")){
			try {
                PrintWriter pw = new PrintWriter(new FileWriter(outputFilename + ".csv"));
				BufferedReader reader = new BufferedReader(new FileReader(inputFilename + ".csv"));
				String line;
				String delimiter = ",";
				String[] tokens;
				String srcLabel, tarLabel;
				int weight;
				int i = 0;
				while ((line = reader.readLine()) != null && i <= amtEdges) {
					tokens = line.split(delimiter);
					srcLabel = tokens[0];
					tarLabel = tokens[1];
					weight = Integer.parseInt(tokens[2]);
                    pw.printf("%s,%s,%d\n", srcLabel, tarLabel, weight);
                    pw.printf("");
					System.out.println("Creating Graph dataset..." + i); 
					i++;
                }
                pw.close();
                reader.close();
			}
		
			catch (FileNotFoundException ex) {
				System.err.println("File " + args[1] + " not found.");
			}
			catch(IOException ex) {
				System.err.println("Cannot open file " + args[1]);
			}
		} //end of LOW DENSITY


    }
}


