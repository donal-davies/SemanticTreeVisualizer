package CapstonePackage;

import java.util.*;

//This class serves as a placeholder object for the deserializing process run by the Parser class.
public class Data {

	String id;
	String source;
	String input;
	ArrayList<Map<String, String>> tokens;
	ArrayList<Node> nodes;
	ArrayList<Map<String, String>> edges;
	ArrayList<Integer> tops;

	public Data (String id, String source, String input, ArrayList<Map<String, String>> tokens, ArrayList<Node> nodes, ArrayList<Map<String, String>> edges, ArrayList<Integer> tops) {
		this.id = id;
		this.source = source;
		this.input = input;
		this.tokens = tokens;
		this.nodes = nodes;
		this.edges = edges;
		this.tops = tops;
	}

	public Data() {
		
	}

	/**
	 * @return the sentence which is representative of the data
	 */
	public String toString() {
		return input;
	}

	/**
	 * setID required for the Jackson deserializer
	 */
	public void setID(String id) {this.id = id;}
	
	/**
	 * setSource required for the Jackson deserializer
	 */
	public void setSource(String source) {this.source = source;}
	
	/**
	 * setInput required for the Jackson deserializer
	 */
	public void setInput(String input) {this.input = input;}

	/**
	 * setTokens required for the Jackson deserializer
	 */
	public void setTokens(ArrayList<Map<String, String>> tokens) {this.tokens = tokens;}
	
	/**
	 * setNodes required for the Jackson deserializer
	 */
	public void setNodes(ArrayList<Node> nodes) {this.nodes = nodes;}

	/**
	 * setEdges required for the Jackson deserializer
	 */
	public void setEdges(ArrayList<Map<String, String>> edges) {this.edges = edges;}

	/**
	 * setTops required for the Jackson deserializer
	 */
	public void setTops(ArrayList<Integer> tops) {this.tops = tops;}
}