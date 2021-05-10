package CapstonePackage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

public class RepGraphCore {
    private final UIManager manager;
    private final Map<String, Graph> storedGraphs;
    
    private final Parser parser;
    
    public RepGraphCore(){
        storedGraphs = new HashMap();
        
        parser = new Parser();
        
        
        //parseGraph(new File("wsj00a.dmrs"));
        manager = new UIManager(this);        
    }
    
    public static void main(String[] args){
        RepGraphCore core = new RepGraphCore();
        
        core.manager.updateUI();
    }
    
    /**
     * Returns a graph associated with a given sentence.
     * @param s The sentence to match to a stored graph.
     * @return The Graph object corresponding to the given key.
     */
    public Graph retrieveGraph(String s){
        return storedGraphs.get(s);
    }
    
    /**
     * @return The key sentences associated with every stored graph.
     */
    public Pair<ArrayList<Graph>, ArrayList<String>> retrieveGraphs(){
        ArrayList<Graph> output = new ArrayList();
        ArrayList<String> outputKeys = new ArrayList();
        
        storedGraphs.keySet().forEach((s) ->{
           outputKeys.add(s);
        });
        
        storedGraphs.values().forEach((s) -> {
            output.add(s);
        });
        
        return new Pair<>(output, outputKeys);
    }
    
    /**
     * @param toLoad Target file to be parsed into a graph
     * @return The loaded graphs
     */
    public ArrayList<Graph> parseGraph(File toLoad){
        
        Map<String, Graph> loaded = parser.parseFile(toLoad);
        
        for(String s : loaded.keySet()){
            if(storedGraphs.keySet().contains(s)){
                continue;
            }
            storedGraphs.put(s, loaded.get(s));
        }
        
        return new ArrayList<>(loaded.values());
    }
    
    /**
     * Remove the target graph from storage.
     * @param graphName The sentence associated with the graph to be removed.
     */
    public void removeGraph(String graphName){
        storedGraphs.remove(graphName);
    }
}
