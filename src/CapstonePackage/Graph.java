package CapstonePackage;
import javafx.util.Pair;
import java.util.*;
/**
    <H>Graph data structure to store parsed data from json files.</H>

    <p>Information is held in a Graph object for visualisation. A graph object is also able to perform various analysis
    tasks.
    </p>



    @author Jared Miles
    @author Nick Beningfield
    @author Donal Davies



**/
public class Graph {
    public String key; // Sentence associated with the graph.
    public int tops;
    public ArrayList<Node> nodes; // Stores the full graph. Indices denote the index associated with each node.
    public ArrayList<Map<String, String>> tokens;
    public ArrayList<ArrayList<Map<String, String> > > edges;
    public  int[][] edgeList;
    public int noEdges;


    public Graph(String key, int tops, ArrayList<Node> nodes, ArrayList<ArrayList<Map<String, String> > > edges, ArrayList<Map<String, String>> tokens){
        this.key = key;
        this.tops = tops;
        this.nodes = nodes;
        this.edges = edges;
        this.tokens = tokens;
    }


    public Graph(String key) {
        this.key = key;
        tops = 0;
        this.nodes = new ArrayList ();
        this.edges = new ArrayList ();
        edgeList = new int[1][1];
    }


    public Graph(Data d) {
        this.key = d.input;
        this.tops = d.tops.get(0);
        this.nodes = d.nodes;
        this.tokens = d.tokens;
        this.noEdges = d.edges.size();
        this.edgeList = getEdgesToList(d.edges);


        this.edges = new ArrayList<>(this.nodes.size());
        for (int i = 0; i < this.nodes.size(); ++i)this.edges.add(new ArrayList<>());
        for (Map<String, String> edge : d.edges) {
            int index = Integer.parseInt(edge.get("source"));
            this.edges.get(index).add(edge);
        }
    }
    

    public ArrayList<Pair<Integer, Integer>> getAnchors() {
        ArrayList<Pair<Integer, Integer>> anch = new ArrayList();
        for (Node n : nodes) {
            for (Map<String, Integer> m : n.anchors) {
                anch.add(new Pair(m.get("from"), m.get("end")));
            }
        }
        return anch;
    }


    private int[][] getEdgesToList(ArrayList<Map<String, String>> edges){
        int[][] edgeList = new int[noEdges][];

        for(int i=0; i<noEdges; i++){ //Takes list of edge maps and extracts the source and target index to be placed in an integer list
            Map<String, String> edge = edges.get(i);
            int[] pair = new int[]{Integer.parseInt(edge.get("source")),Integer.parseInt(edge.get("target"))};
            edgeList[i] = pair;
        }
        return edgeList;
    }


    public Boolean isPlanar(){

        //edges are crossing if min(x,y)<=min(p,t)<max(x,y)<max(p,t) and therefore the graph is not planar
        if(this.nodes.isEmpty()){
            return false;
        }
        for(int i = 0; i<noEdges; i++){ //Outer loop gets min and max of xy edge
            int x = edgeList[i][0];
            int y = edgeList[i][1];
            int xymin = Math.min(x,y);
            int xymax = Math.max(x,y);
            for(int z = 0; z<noEdges; z++){ //inner loop get min and max of pt edge
                int p = edgeList[z][0];
                int t = edgeList[z][1];
                int ptmin = Math.min(p,t);
                int ptmax = Math.max(p,t);
                if(i!=z && ((xymin<=ptmin)&&(ptmin<xymax)&&(xymax<ptmax))){ //check for crossing edges done here
                    return false;
                }
            }
        }
        return true; //planar if no edges are crossing
    }


    private LinkedList<Integer>[] getAdjacentNodeIndexes(Boolean undirected){
        int noNodes = nodes.size();
        LinkedList<Integer>[] adj = new LinkedList[noNodes];

        for(int i = 0; i<noNodes; i++){ //initializing linked lists
            adj[i] = new LinkedList();
        }

        for(int j = 0; j<noEdges; j++){ //adding adjacent nodes to linkedlist. All nodes indexes in linkedlist are adjacent to node of the outer list index
            adj[edgeList[j][0]].add(edgeList[j][1]);
            if(undirected){
                adj[edgeList[j][1]].add(edgeList[j][0]);
            }
        }
        return adj;
    }


    private void DFSHelper(int v, boolean visited[], ArrayList<Integer> DFS) {

        visited[v] = true;
        DFS.add(v);

        LinkedList<Integer> adj[] = getAdjacentNodeIndexes(true);
        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext())
        {
            int n = i.next();
            if (!visited[n])
                DFSHelper(n, visited, DFS);
        }
    }


    private ArrayList<Integer> DFS() {

        ArrayList<Integer> DFS = new ArrayList<Integer>();

        boolean visited[] = new boolean[nodes.size()];

        DFSHelper(0, visited, DFS);

        return DFS;
    }


    public Boolean isConnected(){
        if(this.nodes.isEmpty()){
            return false;
        }
        ArrayList<Integer> DFS = DFS();
        return (DFS.size()==nodes.size()); //if all nodes are contained in the path then the list is connected(weakly)
    }


    public ArrayList<Node> getDesNodes(int nodeIndex){
        if(this.nodes.isEmpty()){
            return new ArrayList<Node>();
        }

        int newTop = nodeIndex;
        ArrayDeque<Map<String,String> > toTraverse = new ArrayDeque<>();

        toTraverse.addAll(edges.get(nodeIndex));
        ArrayList<Node> newNodes = new  ArrayList<Node>();
        ArrayList<String> contained = new ArrayList<String>();

        newNodes.add(this.nodes.get(nodeIndex));
        contained.add(""+nodeIndex);

        while(!toTraverse.isEmpty()){
            Map<String,String> next = toTraverse.pop();
            if(!contained.contains(next.get("target"))){
                newNodes.add(this.nodes.get(Integer.parseInt(next.get("target"))));
                toTraverse.addAll(this.edges.get(Integer.parseInt(next.get("target"))));
            }
        }

        return newNodes;
    }

    public ArrayList<Node> getAdjNodes(int nodeIndex){
        if(this.nodes.isEmpty()){
            return new ArrayList<Node>();
        }
        ArrayList<Node> output = new ArrayList<>();
        output.add(this.nodes.get(nodeIndex));

        for(int i=0;i<this.nodes.size();++i){
            for(Map<String,String> edge : this.edges.get(i)){
                if(Integer.parseInt(edge.get("target"))== nodeIndex ){
                    if(!output.contains(this.nodes.get(Integer.parseInt(edge.get("source")))))
                        output.add(this.nodes.get(Integer.parseInt(edge.get("source"))));
                }else if(Integer.parseInt(edge.get("source"))==nodeIndex){
                    if(!output.contains(this.nodes.get(Integer.parseInt(edge.get("target")))))
                        output.add(this.nodes.get(Integer.parseInt(edge.get("target"))));
                }
            }
        }

        return output;
    }


    public boolean labelInGraph(String label){

        for(Node node:nodes){
            if(node.label.equals(label)){
                return true;
            }
        }
        return false;
    }


    public Map<Integer,Integer> getSimilarities(Graph graph) throws Exception{
        if(key.equals(graph.key)) {

            Map<Integer, Integer> similarNodes = new HashMap<>();

            for (Node node1 : nodes) {
                for (Node node2 : graph.nodes) {
                    if (node1.label.equals(node2.label) && !similarNodes.values().contains(Integer.parseInt(node2.ID))) {
                        similarNodes.put(Integer.parseInt(node1.ID), Integer.parseInt(node2.ID));
                    }
                }
            }
            return similarNodes;
        }
        else{
            throw new Exception("Sentences are not the same");
        }
    }


    private void addEdgeDirected(ArrayList<ArrayList<Integer>> adj, int i, int j) {
        adj.get(i).add(j);
    }


    private static void addEdgeUndirected(ArrayList<ArrayList<Integer>> adj, int i, int j) {
        adj.get(i).add(j);
        adj.get(j).add(i);
    }


    private boolean BFS( ArrayList<ArrayList<Integer>> adj, int src, int dest, int noNodes, int pred[], int dist[]){

        LinkedList<Integer> queue = new LinkedList<Integer>();

        boolean visited[] = new boolean[noNodes];

        for(int i=0; i<noNodes; i++){
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        visited[src] = true;
        dist[src] = 0;
        queue.add(src);

        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));

                    if (adj.get(u).get(i) == dest)
                        return true;
                }
            }
        }

        return false;
    }


    private ArrayList<Map<String, String> > getPathEdgesDirected(LinkedList<Integer> path){
        ArrayList<Map<String, String> > pathEdges = new ArrayList<Map<String, String> >();

        for(int i = path.size()-1; i>0; i--){
            int source = path.get(i);
            int target = path.get(i-1);
            ArrayList<Map<String, String> > curEdges = edges.get(source);
            for(Map<String, String> curEdge: curEdges){
                if(Integer.parseInt(curEdge.get("target")) == target){

                    pathEdges.add(curEdge);
                }
            }
        }

        return pathEdges;
    }


    private ArrayList<Map<String, String> > getPathEdgesUndirected(LinkedList<Integer> path){
        ArrayList<Map<String, String> > pathEdges = new ArrayList<Map<String, String> >();

        int oldTarget = -1;

        for(int i = path.size()-1; i>=0; i--){
            int source = path.get(i);
            int target = path.get(1);
            if(i!=0) {
                target = path.get(i - 1);
            }
            ArrayList<Map<String, String> > curEdgesSrc = edges.get(source);
            for(Map<String, String> curEdge: curEdgesSrc){
                if(Integer.parseInt(curEdge.get("target")) == target  || (Integer.parseInt(curEdge.get("source")) == source && Integer.parseInt(curEdge.get("target")) == oldTarget)){
                    pathEdges.add(curEdge);
                }
            }

            oldTarget = source;

        }

        return pathEdges;
    }


    public ArrayList<Node> getShortestPath(int src, int dest, boolean directed) throws Exception {

        if(this.nodes.isEmpty()){
            throw new Exception("Graph not populated");
        }

        int noNodes = nodes.size();

        ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(noNodes);
        for (int i = 0; i < noNodes; i++) {
            adj.add(new ArrayList<Integer>());
        }

        for (ArrayList<Map<String, String>> list : edges) {
            for (Map<String, String> edge : list) {
                if (directed) {
                    addEdgeDirected(adj, Integer.parseInt(edge.get("source")), Integer.parseInt(edge.get("target")));
                } else {
                    addEdgeUndirected(adj, Integer.parseInt(edge.get("source")), Integer.parseInt(edge.get("target")));
                }
            }
        }

        int pred[] = new int[noNodes];
        int dist[] = new int[noNodes];

        if (BFS(adj, src, dest, noNodes, pred, dist) == false) {
            throw new Exception("Source and destination nodes are not connected");
        }

        ArrayList<Integer> path = new ArrayList<Integer>();
        int end = dest;
        path.add(end);
        while (pred[end] != -1) {
            path.add(pred[end]);
            end = pred[end];
        }

        Collections.sort(path);
        ArrayList<Node> outputPath = new ArrayList<>();

        for(Integer index : path){
            outputPath.add(this.nodes.get(index));
        }

        return outputPath;

    }


    @Override
    public String toString(){
        String string = "Key: "+key +"\n" + "Top: "+ String.valueOf(tops) +"\n"+ "Nodes: " + nodes + "\n" + "Edges: " + edges + "\n" + "Tokens: "+ tokens + "\n";
        return string;
    }


    @Override
    public boolean equals(Object o) {
        Graph graph1 = (Graph)o;
        return this.toString().equals(graph1.toString());
    }

}
