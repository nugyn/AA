import java.io.*;
import java.util.*;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import java.lang.String;

/**
 * Framework to test the association graph implementations.
 *
 * There should be no need to change this for task A.  If you need to make changes for task B, please make a copy, then modify the copy for task B.
 *
 * @author Jeffrey Chan, 2019.
 */
public class GraphEvalB
{
	/** Name of class, used in error messages. */
	protected static final String progName = "GraphEval";

	/** Standard outstream. */
	protected static final PrintStream outStream = System.out;

	/**
	 * Print help/usage message.
	 */
	public static void usage(String progName) {
		System.err.println(progName + ": <implementation> [-f <filename to load graph>] [filename to print vertices] [filename to print edges] [filename to print neighbours]");
		System.err.println("<implementation> = <adjlist | incmat>");
		System.err.println("If all three optional output filenames are specified, then non-interative mode will be used and respective output is written to those files.  Otherwise interative mode is assumed and output is written to System.out.");
		System.exit(1);
	} // end of usage


	/**
	 * Process the operation commands coming from inReader, and updates the graph according to the operations.
	 *
	 * @param inReader Input reader where the operation commands are coming from.
	 * @param graph The graph which the operations are executed on.
	 * @param verticesOutWriter Where to output the results of printing the set of vertices.
	 * @param edgesOutWriter Where to output the results of printing the set of edges.
	 * @param neighbourOutWriter Where to output the results of finding the set of neighbours.
	 * @param miscOutWriter Where to output the results of other methods that has output and not covered by the other three output files.
	 *
	 * @throws IOException If there is an exception to do with I/O.
	 */
	public static void processOperations(BufferedReader inReader, AssociationGraph graph,
			PrintWriter verticesOutWriter, PrintWriter edgesOutWriter, PrintWriter neighbourOutWriter, PrintWriter miscOutWriter, String inputFilename, String implementationType)
		throws IOException
	{
		String line;
		int lineNum = 1;
		boolean bQuit = false;

		// continue reading in commands until we either receive the quit signal or there are no more input commands
		while (!bQuit && (line = inReader.readLine()) != null) {
			String[] tokens = line.split(" ");

			// check if there is at least an operation command
			if (tokens.length < 1) {
				System.err.println(lineNum + ": not enough tokens.");
				lineNum++;
				continue;
			}

			String command = tokens[0];

			try {
				// determine which operation to execute
				switch (command.toUpperCase()) {
					// add vertex
					case "AV":
						if (tokens.length == 2) {
							graph.addVertex(tokens[1]);
						}
						else {
							System.err.println(lineNum + ": incorrect number of tokens.");
						}
						break;
	                // add edge
					case "AE":
						if (tokens.length == 4) {
							int weight = Integer.parseInt(tokens[3]);
							if (weight < 0) {
								System.err.println(lineNum + ": edge weight must be non-negative.");
							}
							else {
								graph.addEdge(tokens[1], tokens[2], weight);
							}
						}
						else {
							System.err.println(lineNum + ": incorrect number of tokens.");
						}
						break;
					// get edge weight
					case "W":
						if (tokens.length == 3) {
							int answer = graph.getEdgeWeight(tokens[1], tokens[2]);
							miscOutWriter.println(answer);
						}
						else {
							System.err.println(lineNum + ": incorrect number of tokens.");
						}
						break;
					// update weight of edge
					case "U":
						
						BufferedReader edgeReader = new BufferedReader(new FileReader(inputFilename));
						String edgeLine;
						String delimi = ",";
						String edgeTokens[];

						long edgeTime = 0;
						long edgeTime2 = 0;
						long updateEdgeTime = 0;

						BufferedWriter edgeWriter = new BufferedWriter(new FileWriter("UPDATE_" + inputFilename + "_" + implementationType + "_.txt"));
						while((edgeLine = edgeReader.readLine()) != null){
							edgeTokens = edgeLine.split(delimi);

							edgeTime = System.nanoTime();
							graph.updateWeightEdge(edgeTokens[0], edgeTokens[1], 1);
							edgeTime2 = System.nanoTime();
							updateEdgeTime = edgeTime2 - edgeTime;
							System.out.println(updateEdgeTime);
							edgeWriter.write(updateEdgeTime + " ");
						}
						edgeWriter.close();

						break;
					// remove vertex
					case "RV":
							
							Scanner sc = new Scanner(System.in);
							// System.out.println("Graph: "); 
							// String graphName = sc.nextLine();

							BufferedReader br = new BufferedReader(new FileReader(inputFilename));
							BufferedWriter writer = new BufferedWriter(new FileWriter("REMOVE_" +inputFilename + "_" + implementationType + "_.txt", true));

							String lineRV = "";
							String delimiter = ",";
							String[] tokensRV = {};
							String vertA, vertB;
							int i = 0;
							long time1RV = 0;
							long time2RV = 0;
							long time3RV = 0;
							long time4RV = 0;
							long timeTakenRV = 0;
							long timeTaken2RV = 0;

							while((line = br.readLine()) != null){
								tokensRV = line.split(delimiter);
								vertA = tokensRV[0];
								vertB = tokensRV[1];

								time1RV = System.nanoTime();
								graph.removeVertex(vertA);
								time2RV = System.nanoTime();
								timeTakenRV = time2RV - time1RV;

								time3RV = System.nanoTime();
								graph.removeVertex(vertB);
								time4RV = System.nanoTime();
								timeTaken2RV = time4RV - time3RV;

								writer.write(timeTakenRV + " " + timeTaken2RV + " ");
								time1RV = 0;
								time2RV = 0;
								timeTakenRV = 0;
								time3RV = 0;
								time4RV = 0;
								timeTaken2RV = 0;
								System.out.println("Removing " + i);
								i++;
							}
						br.close();
						writer.close();
						break;
					// k-nearest out-neighbourhood
					case "ON":
					
						BufferedReader brON = new BufferedReader(new FileReader(inputFilename));
						BufferedWriter writerAON = new BufferedWriter(new FileWriter("ON_" + inputFilename + "_" + implementationType + "_.txt", true));
						BufferedWriter writerBON = new BufferedWriter(new FileWriter("ON_" + inputFilename + "_" + implementationType + "_.txt", true));
						String lineON = "";
						String delimiterON = ",";
						String[] tokensON = {};
						int k = -1 ;
						String vertAON = "";
						String vertBON = "";
						int sizeAON = 0;
						int sizeBON = 0;
						long time1ON = 0;
						long time2ON = 0;
						long time3ON = 0; 
						long time4ON = 0;
						long timeTakenAON = 0; 
						long timeTakenBON = 0;
						while((lineON = brON.readLine()) != null) {
						tokensON = lineON.split(delimiterON);
						
							
						// To get IN neighbours, you will need to define k.
						// K cannot be more than amount of vertices of graph. 
						// We need to find the nearest neighbours, for all K, including -1. k = n - 1

						/*
							Obtain -1 all possible pairs
							Define the next interation, to be from k - n, where n++;
							possible pairs decrease as k decreases.
						*/

							vertAON = tokensON[0];
							vertBON = tokensON[1];

						//K needs to increment until it reaches n - 1 (not including the Vertice being ON'ed);
							if(sizeAON < 1){
							time1ON = System.nanoTime();
							List<MyPair> neighboursA = graph.outNearestNeighbours(k, vertAON);
							time2ON = System.nanoTime();
							timeTakenAON = time2ON - time1ON;
							sizeAON = neighboursA.size();
							writerAON.write(timeTakenAON + " ");
							time1ON = 0;
							time2ON = 0;
							timeTakenAON = 0;
							}							
							System.out.println("===================" + sizeAON);
							if(sizeAON >=1){
								for(int y = 0; y < sizeAON; y++){
									time1ON = System.nanoTime();
									List<MyPair> neighboursA = graph.outNearestNeighbours(y, vertAON);
									time2ON = System.nanoTime();
									timeTakenAON = time2ON - time1ON;
									writerAON.write(timeTakenAON + " ");
									time1ON = 0;
									time2ON = 0;
									timeTakenAON = 0;
								}
							}

							if(sizeBON < 1){
								time3ON = System.nanoTime();
								List<MyPair> neighboursB = graph.outNearestNeighbours(k, vertBON);
								time4ON = System.nanoTime();
								timeTakenBON = time4ON - time3ON;
								sizeBON = neighboursB.size();
								writerBON.write(timeTakenBON + " ");
								time3ON = 0;
								time4ON = 0;
								timeTakenBON = 0;
								}
								System.out.println("===================" + sizeBON);
								if(sizeBON >=1){
									for(int y = 0; y < sizeBON; y++){
										time3ON = System.nanoTime();
										List<MyPair> neighboursA = graph.outNearestNeighbours(y, vertBON);
										time4ON = System.nanoTime();
										timeTakenBON = time2ON - time1ON;
										writerBON.write(timeTakenBON + " ");
										time3ON = 0;
										time4ON = 0;
										timeTakenBON = 0;
									}
								}
			
							sizeAON = 0;
							sizeBON = 0;
						}
						writerAON.close();	
						writerBON.close();			
						brON.close();	
						break;
					// k-nearest in-neighbourhood
					case "IN":
					BufferedReader brIN = new BufferedReader(new FileReader(inputFilename));
					BufferedWriter writerAIN = new BufferedWriter(new FileWriter("IN_" + inputFilename + "_" + implementationType + "_.txt", true));
					BufferedWriter writerBIN = new BufferedWriter(new FileWriter("IN_" + inputFilename + "_" + implementationType + "_.txt", true));
					String lineIN = "";
					String delimiterIN = ",";
					String[] tokensIN = {};
					int kIN = -1 ;
					String vertAIN = "";
					String vertBIN = "";
					int sizeINA = 0;
					int sizeINB = 0;
					long time1IN = 0;
					long time2IN = 0;
					long time3IN = 0;
					long time4IN = 0;
					long timeTakenAIN = 0; 
					long timeTakenBIN = 0;
					while((lineIN = brIN.readLine()) != null) {
					tokensIN = lineIN.split(delimiterIN);
					
						
					// To get IN neighbours, you will need to define k.
					// K cannot be more than amount of vertices of graph. 
					// We need to find the nearest neighbours, for all K, including -1. k = n - 1

					/*
						Obtain -1 all possible pairs
						Define the next interation, to be from k - n, where n++;
						possible pairs decrease as k decreases.
					*/

						vertAIN = tokensIN[0];
						vertBIN = tokensIN[1];

					//K needs to increment until it reaches n - 1 (not including the Vertice being ON'ed);
						if(sizeINA < 1){
						time1IN = System.nanoTime();
						List<MyPair> neighboursA = graph.inNearestNeighbours(kIN, vertAIN);
						time2IN = System.nanoTime();
						timeTakenAIN = time2IN - time1IN;
						sizeINA = neighboursA.size();
						writerAIN.write(timeTakenAIN + " ");
						}
						System.out.println("===================" + sizeINA);
						if(sizeINA >=1){
							for(int y = 0; y < sizeINA; y++){
								time1IN = System.nanoTime();
								List<MyPair> neighboursA = graph.inNearestNeighbours(y, vertAIN);
								time2IN = System.nanoTime();
								timeTakenAIN = time2IN - time1IN;
								writerAIN.write(timeTakenAIN + " ");

								time1IN = 0;
								time2IN = 0;
								timeTakenAIN = 0;
							}
						}
						
						if(sizeINB < 1){
							time3IN = System.nanoTime();
							List<MyPair> neighboursB = graph.inNearestNeighbours(kIN, vertBIN);
							time4IN = System.nanoTime();
							timeTakenBIN = time4IN - time3IN;
							sizeINB = neighboursB.size();
							writerBIN.write(timeTakenBIN + " ");
							}
							System.out.println("===================" + sizeINB);
							if(sizeINB >=1){
								for(int y = 0; y < sizeINB; y++){
									time3IN = System.nanoTime();
									List<MyPair> neighboursA = graph.inNearestNeighbours(y, vertBIN);
									time4IN = System.nanoTime();
									timeTakenBIN = time4IN - time3IN;
									writerBIN.write(timeTakenBIN + " ");
									time3IN = 0;
									time4IN = 0;
									timeTakenBIN = 0;
								}
							}

						sizeINA = 0;
						sizeINB = 0;
					}
					writerAIN.close();	
					writerBIN.close();
					brIN.close();
					break;// print vertices


					case "PV":
						graph.printVertices(verticesOutWriter);
						break;
	                // print edges
					case "PE":
						graph.printEdges(edgesOutWriter);
						break;
					// quit
					case "Q":
						bQuit = true;
						break;
					default:
						System.err.println(lineNum + ": Unknown command.");
				} // end of switch()
			}
			catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
			}

			lineNum++;
		}

	} // end of processOperations()



	/**
	 * Main method.  Determines which implementation to test and processes command line arguments.
	 */
	public static void main(String[] args) {

		// parse command line options
		OptionParser parser = new OptionParser("f:");
		OptionSet options = parser.parse(args);

		String inputFilename = null;
		// -f <inputFilename> specifies the file that contains edge list information to construct the initial graph with.
		if (options.has("f")) {
			if (options.hasArgument("f")) {
				inputFilename = (String) options.valueOf("f");
			}
			else {
				System.err.println("Missing filename argument for -f option.");
				usage(progName);
			}
		}

		// non option arguments
		List<?> tempArgs = options.nonOptionArguments();
		List<String> remainArgs = new ArrayList<String>();
		for (Object object : tempArgs) {
			remainArgs.add((String) object);
		}

		// check number of non-option command line arguments
		if (remainArgs.size() > 5 || remainArgs.size() < 1) {
			System.err.println("Incorrect number of arguments.");
			usage(progName);
		}

		// parse non-option arguments
		String implementationType = remainArgs.get(0);

		String verticesOutFilename = null;
		String edgesOutFilename = null;
		String neighbourOutFilename = null;
		String miscOutFilename = null;

		// output files
		if (remainArgs.size() == 5) {
			verticesOutFilename = remainArgs.get(1);
			edgesOutFilename = remainArgs.get(2);
			neighbourOutFilename = remainArgs.get(3);
			miscOutFilename = remainArgs.get(4);
		}
		else {
			System.out.println("Interative mode.");
		}


		// determine which implementation to test
		AssociationGraph graph = null;
		switch(implementationType) {
			case "adjlist":
				graph = new AdjList();
				break;
			case "incmat":
				graph = new IncidenceMatrix();
				break;
			default:
				System.err.println("Unknown implmementation type.");
				usage(progName);
		}


		// if file specified, then load file
		if (inputFilename != null) {
				try {
					BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
					String line;
					String delimiterON = ",";
					String[] tokens;
					String srcLabel, tarLabel;
					int weight;
					int i = 0;
					while ((line = reader.readLine()) != null ) {
						tokens = line.split(delimiterON);
						srcLabel = tokens[0];
						tarLabel = tokens[1];
						weight = Integer.parseInt(tokens[2]);
						graph.addVertex(srcLabel);
						graph.addVertex(tarLabel);
						graph.addEdge(srcLabel, tarLabel, weight);
						graph.addEdge(tarLabel, srcLabel, weight);
						System.out.println("Creating Graph..." + i); 
						i++;
					}
				}
				catch (FileNotFoundException ex) {
					System.err.println("File " + args[1] + " not found.");
				}
				catch(IOException ex) {
					System.err.println("Cannot open file " + args[1]);
				}
			} //end of LOW DENSITY
		

		
		// construct in and output streams/writers/readers, then process each operation.
		try {
			BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));

			// vertices output location
			PrintWriter verticesOutWriter = new PrintWriter(System.out, true);
			if (verticesOutFilename != null) {
				verticesOutWriter = new PrintWriter(new FileWriter(verticesOutFilename), true);
			}

			// edgs output location
			PrintWriter edgesOutWriter = new PrintWriter(System.out, true);
			if (edgesOutFilename != null) {
				edgesOutWriter = new PrintWriter(new FileWriter(edgesOutFilename), true);

			}

			// neighbourhood output location
			PrintWriter neighbourOutWriter = new PrintWriter(System.out, true);
			if (neighbourOutFilename != null) {
				neighbourOutWriter = new PrintWriter(new FileWriter(neighbourOutFilename), true);
			}

			// miscellaneous output location
			PrintWriter miscOutWriter = new PrintWriter(System.out, true);
			if (miscOutFilename != null) {
				miscOutWriter = new PrintWriter(new FileWriter(miscOutFilename), true);
			}


			// process the operations
			processOperations(inReader, graph, verticesOutWriter, edgesOutWriter, neighbourOutWriter, miscOutWriter, inputFilename, implementationType);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		

	} // end of main()

} // end of class GraphEval