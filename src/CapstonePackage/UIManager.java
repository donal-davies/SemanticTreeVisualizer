package CapstonePackage;

import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;


public class UIManager {

    private final UICore ui;
    
    private final RepGraphCore core;
    public ArrayList<File> filesToLoad;
    
    public UIManager(RepGraphCore parent){
        core = parent;
        filesToLoad = new ArrayList<>();
        
        ui = new UICore(this);
        ui.setVisible(true);
    }
    
    /**
     * Retrieves all sentences associated with stored graphs
     * @return An ArrayList containing the sentences
     */
    public Pair<ArrayList<Graph>, ArrayList<String>> getKeys(){
        return core.retrieveGraphs();
    }
    
    /**
     * Forces the UI to update with newly acquired graph data.
     */
    public void updateUI(){
        ui.updateComponents();
    }
    
    /**
     * Loads and removes graphs from storage, depending on user interaction with the ListView interface.
     */
    public void addFiles(){
        ArrayList<File> toLoad = (ArrayList<File>)filesToLoad.clone();
        filesToLoad.clear();
        
        if(toLoad.size() > 0){
            ArrayList<Graph> loadedGraphs = new ArrayList();
            toLoad.forEach((f) -> {
                for(Graph g : core.parseGraph(f)){
                    loadedGraphs.add(g);
                }
            });
            ui.addGraphs(loadedGraphs);
        }
    }
    
    /**
     * Retrieves the graph object associated with a given sentence.
     * @param s Target sentence for retrieval.
     * @return The graph associated with the given sentence.
     */
    public Graph retrieveGraph(String s){
        return core.retrieveGraph(s);
    }
    
    /**
     * Call to the Visualizer to add the target graph to rendering.
     * @param g Object of the target graph.
     * @param s Key sentence of the target graph.
     */
    public boolean renderGraph(Graph g, String s){
        return ui.updateVis(g, s);
    }
}
