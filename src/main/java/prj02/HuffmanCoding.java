package prj02;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import HashTable.*;
import List.*;
import SortedList.*;
import Tree.*;


/**
 * The Huffman Encoding Algorithm
 *
 * This is a data compression algorithm designed by David A. Huffman and published in 1952
 *
 * What it does is it takes a string and by constructing a special binary tree with the frequencies of each character.
 * This tree generates special prefix codes that make the size of each string encoded a lot smaller, thus saving space.
 *
 * @author Fernando J. Bermudez Medina (Template)
 * @author A. ElSaid (Review)
 * @author Ián G. Colón Rosado <802-18-0923> (Implementation)
 * @version 2.0
 * @since 10/16/2021
 */
public class HuffmanCoding {

	public static void main(String[] args) {
		HuffmanEncodedResult();
	}

	/* This method just runs all the main methods developed or the algorithm */
	private static void HuffmanEncodedResult() {
		String data = load_data("input1.txt"); //You can create other test input files and add them to the inputData Folder

		/*If input string is not empty we can encode the text using our algorithm*/
		if(!data.isEmpty()) {
			Map<String, Integer> fD = compute_fd(data);
			BTNode<Integer,String> huffmanRoot = huffman_tree(fD);
			Map<String,String> encodedHuffman = huffman_code(huffmanRoot);
			String output = encode(encodedHuffman, data);
			process_results(fD, encodedHuffman,data,output);
		} else {
			System.out.println("Input Data Is Empty! Try Again with a File that has data inside!");
		}

	}

	/**
	 * Receives a file named in parameter inputFile (including its path),
	 * and returns a single string with the contents.
	 *
	 * @param inputFile name of the file to be processed in the path inputData/
	 * @return String with the information to be processed
	 */
	public static String load_data(String inputFile) {
		BufferedReader in = null;
		String line = "";

		try {
			/*We create a new reader that accepts UTF-8 encoding and extract the input string from the file, and we return it*/
			in = new BufferedReader(new InputStreamReader(new FileInputStream("inputData/" + inputFile), "UTF-8"));

			/*If input file is empty just return an empty string, if not just extract the data*/
			String extracted = in.readLine();
			if(extracted != null)
				line = extracted;

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

		}
		return line;
	}

	/**
	 * TODO ADD DESCRIPTION OF WHAT THIS METHOD DOES HERE
	 * 
	 * This method takes a String as a parameter and stores each character
	 * in the map that it returns (called freq). The value for each key is the frequency
	 * of said character in the very string
	 *
	 * @param inputString
	 * @return map with individual characters as keys and their frequency as their values
	 */
	public static Map<String, Integer> compute_fd(String inputString) {
		/* TODO Compute Symbol Frequency Distribution of each character inside input string */

		//Create HashTable to be returned
		SimpleHashFunction<String> freq_hash = new SimpleHashFunction<>();
		HashTableSC<String, Integer> freq = new HashTableSC<>(freq_hash);
		int count = 1;

		//Iterate through inputString
		for(int i = 0; i < inputString.length(); i++){
			
			String character = String.valueOf(inputString.charAt(i));
			//Used substring so that characters would be returned as type String
			//if character is in map get its value and replace with a larger frequency
			if( freq.containsKey(character) ){
				count = freq.get(character);
				count++;
				freq.put(character, count);

			//else simply put in map with a freq of 1
			}else{
				freq.put(character, 1);
			}
		}

		return freq; 
	}


	/**
	 * TODO ADD DESCRIPTION OF WHAT THIS METHOD DOES HERE
	 * 
	 * This method receives the map generated in the compute_fd() method
	 * as a parameter and returns the root of a binary tree. Said binary
	 * is constructed in a bottom-up fashion according to the order in 
	 * the created SortedList.
	 * 
	 * The elements of the tree depend of the elements
	 * in the SortedList. Each BTNode holds the frequency of the characters
	 * as a key and the character itself as a value. Each node that is added in
	 * the tree is a combination of the first 2 elements in the list.
	 * 
	 * Constructs the tree until there is only one node left in the list.
	 * 
	 *
	 * @param fD
	 * @return root of a binary tree
	 */
	public static BTNode<Integer, String> huffman_tree(Map<String, Integer> fD) {

		/* TODO Construct Huffman Tree */

		//Initialize the node to be returned as a new node
		BTNode<Integer,String> rootNode = new BTNode<>();
		//Instantiate a new SortedList
		SortedLinkedList<BTNode<Integer, String>> lst = new SortedLinkedList<BTNode<Integer,String>>();

		//Fill the SortedList with the elements in the fD parameter
		for(int i = 0; i < fD.size(); i++){
			BTNode<Integer, String> added = new BTNode<Integer,String>(fD.getValues().get(i), fD.getKeys().get(i));
			lst.add(added);
		}

		//Iterate through SortedList
		while(lst.size() > 1){
			rootNode = new BTNode<>();
			//Set children of rootNode to be the first 2 elements
			rootNode.setLeftChild(lst.get(0));
			rootNode.setRightChild(lst.get(1));
			
			//Set key-value pair of rootNode
			rootNode.setKey(lst.get(0).getKey() + lst.get(1).getKey());
			rootNode.setValue(lst.get(0).getValue() + lst.get(1).getValue());

			//Remove the first 2 elements
			lst.removeIndex(1);
			lst.removeIndex(0);

			//Replace with the newly created node
			lst.add(rootNode);
		}

		return rootNode;
	}


	/**
	 * TODO ADD DESCRIPTION OF WHAT THIS METHOD DOES HERE
	 * 
	 * This method iterates through the tree created in the
	 * huffman_tree() method, assigns a prefix to each leaf node,
	 * and returns a map with the character of the leaf node as
	 * a key and its assigned prefix code as its value.
	 *
	 *
	 * @param huffmanRoot
	 * @return map of characters with their assigned code prefixes
	 */
	public static Map<String, String> huffman_code(BTNode<Integer,String> huffmanRoot) {
		/* TODO Construct Prefix Codes */

		String prefix = "";
		//Initialize HashTable to be returned
		SimpleHashFunction<String> hash = new SimpleHashFunction<>();
		HashTableSC<String, String> huff_code = new HashTableSC<>(hash);
		
		//Call auxiliary method in line 327
		
		Aux_huffcode(huffmanRoot, prefix, huff_code);

		return huff_code;
	}

	/**
	 * TODO ADD DESCRIPTION OF WHAT THIS METHOD DOES HERE
	 * 
	 * This method takes the map created in the huffman_code as a parameter
	 * and returns an encoded version of the second parameter.
	 * 
	 * Firstly, it iterates through the characters of inputString and finds 
	 * them in the encodingMap. Lastly, it appends the prefix codes found 
	 * in the values of each key that are present in the inputString to
	 * the code variable and returns it.
	 *
	 * @param encodingMap
	 * @param inputString
	 * @return A string that represents the encoded version of the inputString
	 */
	public static String encode(Map<String, String> encodingMap, String inputString) {
		/* TODO Encode String */
		//Initialize string to be returned as an empty string
		String code = "";

		//Iterate through the inputString's characters, search for the key's value in
		//the encodedMap and append it to the code variable
		for(int i = 0; i < inputString.length(); i++){
			String character = String.valueOf(inputString.charAt(i));
			code += encodingMap.get(character);
		}

		return code;
	}

	/**
	 * Receives the frequency distribution map, the Huffman Prefix Code HashTable, the input string,
	 * and the output string, and prints the results to the screen (per specifications).
	 *
	 * Output Includes: symbol, frequency and code.
	 * Also includes how many bits has the original and encoded string, plus how much space was saved using this encoding algorithm
	 *
	 * @param fD Frequency Distribution of all the characters in input string
	 * @param encodedHuffman Prefix Code Map
	 * @param inputData text string from the input file
	 * @param output processed encoded string
	 */
	public static void process_results(Map<String, Integer> fD, Map<String, String> encodedHuffman, String inputData, String output) {
		/*To get the bytes of the input string, we just get the bytes of the original string with string.getBytes().length*/
		int inputBytes = inputData.getBytes().length;

		/**
		 * For the bytes of the encoded one, it's not so easy.
		 *
		 * Here we have to get the bytes the same way we got the bytes for the original one but we divide it by 8,
		 * because 1 byte = 8 bits and our huffman code is in bits (0,1), not bytes.
		 *
		 * This is because we want to calculate how many bytes we saved by counting how many bits we generated with the encoding
		 */
		DecimalFormat d = new DecimalFormat("##.##");
		double outputBytes = Math.ceil((float) output.getBytes().length / 8);

		/**
		 * to calculate how much space we saved we just take the percentage.
		 * the number of encoded bytes divided by the number of original bytes will give us how much space we "chopped off"
		 *
		 * So we have to subtract that "chopped off" percentage to the total (which is 100%)
		 * and that's the difference in space required
		 */
		String savings =  d.format(100 - (( (float) (outputBytes / (float)inputBytes) ) * 100));


		/**
		 * Finally we just output our results to the console
		 * with a more visual pleasing version of both our Hash Tables in decreasing order by frequency.
		 *
		 * Notice that when the output is shown, the characters with the highest frequency have the lowest amount of bits.
		 *
		 * This means the encoding worked and we saved space!
		 */
		System.out.println("Symbol\t" + "Frequency   " + "Code");
		System.out.println("------\t" + "---------   " + "----");

		SortedList<BTNode<Integer,String>> sortedList = new SortedLinkedList<BTNode<Integer,String>>();

		/* To print the table in decreasing order by frequency, we do the same thing we did when we built the tree
		 * We add each key with it's frequency in a node into a SortedList, this way we get the frequencies in ascending order*/
		for (String key : fD.getKeys()) {
			BTNode<Integer,String> node = new BTNode<Integer,String>(fD.get(key),key);
			sortedList.add(node);
		}

		/**
		 * Since we have the frequencies in ascending order,
		 * we just traverse the list backwards and start printing the nodes key (character) and value (frequency)
		 * and find the same key in our prefix code "Lookup Table" we made earlier on in huffman_code().
		 *
		 * That way we get the table in decreasing order by frequency
		 * */
		for (int i = sortedList.size() - 1; i >= 0; i--) {
			BTNode<Integer,String> node = sortedList.get(i);
			System.out.println(node.getValue() + "\t" + node.getKey() + "\t    " + encodedHuffman.get(node.getValue()));
		}

		System.out.println("\nOriginal String: \n" + inputData);
		System.out.println("Encoded String: \n" + output);
		System.out.println("Decoded String: \n" + decodeHuff(output, encodedHuffman) + "\n");
		System.out.println("The original string requires " + inputBytes + " bytes.");
		System.out.println("The encoded string requires " + (int) outputBytes + " bytes.");
		System.out.println("Difference in space requiered is " + savings + "%.");
	}


	/*************************************************************************************
	 ** ADD ANY AUXILIARY METHOD YOU WISH TO IMPLEMENT TO FACILITATE YOUR SOLUTION HERE 
	 * @param huff_code **
	 *************************************************************************************/
	public static void Aux_huffcode(BTNode<Integer, String> huffRoot, String prefix, HashTableSC<String, String> huff_code) {

		//Assign a 0 to the left child and a 1 to the right child
		//Continue to recursively append a 0 or 1 until the root is null
		if(huffRoot == null) {
			return;
		}
		
		if(huffRoot.getLeftChild() == null && huffRoot.getRightChild() == null) {
			huff_code.put(huffRoot.getValue(), prefix);
			return;
		}
		
		Aux_huffcode(huffRoot.getLeftChild(), prefix + "0", huff_code);
		Aux_huffcode(huffRoot.getRightChild(), prefix + "1", huff_code);
		

	}
	/**
	 * Auxiliary Method that decodes the generated string by the Huffman Coding Algorithm
	 *
	 * Used for output Purposes
	 *
	 * @param output - Encoded String
	 * @param lookupTable
	 * @return The decoded String, this should be the original input string parsed from the input file
	 */
	public static String decodeHuff(String output, Map<String, String> lookupTable) {
		String result = "";
		int start = 0;
		List<String>  prefixCodes = lookupTable.getValues();
		List<String> symbols = lookupTable.getKeys();

		/*looping through output until a prefix code is found on map and
		 * adding the symbol that the code that represents it to result */
		for(int i = 0; i <= output.length();i++){

			String searched = output.substring(start, i);

			int index = prefixCodes.firstIndex(searched);

			if(index >= 0) { //Found it
				result= result + symbols.get(index);
				start = i;
			}
		}
		return result;
	}


}
