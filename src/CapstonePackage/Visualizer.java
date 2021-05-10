package CapstonePackage;

import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Donal
 */
public class Visualizer extends javax.swing.JPanel {

    /**
     * Creates new form Visualizer
     */
    
    private UIManager manager;
    private final ArrayList< ArrayList<Component> > elements; //Stores all rendered graph Components.
    private final ArrayList< String > rendering; //Stores the list of currently rendered keys.
    public final ArrayList< Graph > renderedGraphs; //Stores a rendered graph in Component form. Indices match Node indices.
    
    public Visualizer(UIManager core) {
        manager = core;
        elements = new ArrayList();
        rendering = new ArrayList();
        renderedGraphs = new ArrayList();
        initComponents();
    }

    public Visualizer(){
        elements = new ArrayList();
        rendering = new ArrayList();
        renderedGraphs = new ArrayList();
        initComponents();
    }
    
    /**
     * Adds graph to be rendered, alongside its associated text.
     * @param g The graph to be rendered.
     * @param s The associated string of text.
     */
    public boolean setRenderGraph(Graph g, String s){
        /**
         * Remove the given graph if it has already been listed for rendering.
         */
        
        if(rendering.contains(s)){
            int index = rendering.indexOf(s);
            
            JPanel canvas = (JPanel)elements.get(index).remove(0);
            JPanel curTarget = null;
            for(Component c : elements.get(index)){
                if(c instanceof JPanel){
                    if(curTarget != null)canvas.remove(curTarget);
                    curTarget = (JPanel)c;
                }else{
                    curTarget.remove(c);
                }
            }
            baseLayer.remove(canvas);
            baseLayer.remove(elements.get(index).get(elements.get(index).size()-1));
            rendering.remove(s);
            elements.remove(index);
            renderedGraphs.remove(g);
            
            /**
             * Remove any similarity rendering
             */
            for(int i = 0;i < elements.size(); ++i){
                for(Component c : elements.get(i)){
                    if(c instanceof DrawPane){
                        ((DrawPane)c).similar.clear();
                        ((DrawPane)c).colourize(renderedGraphs.get(i));
                    }
                }
            }
            
            baseLayer.repaint();
            this.repaint();
            return false;
        }
        
        /**
         * Otherwise render the graph.
         */
        
        //Mark the graph key as currently rendered.
        rendering.add(s);

        //Create arraylist to store first set of rendered elements.
        ArrayList<Component> components = new ArrayList();
        ArrayList<Component> nodes = new ArrayList();
        for(int i = 0; i < g.nodes.size(); ++i){
            nodes.add(null);
        }
        
        //Create new JPanel to hold text line.
        DrawPane textCanvas = new DrawPane();
        textCanvas.setLayout(new BoxLayout(textCanvas, BoxLayout.LINE_AXIS)); //Set layout to create a line of elements along x-axis
        textCanvas.setAlignmentX(Component.LEFT_ALIGNMENT); //Left justify elements
        textCanvas.setBorder(new BevelBorder(1));
        
        //Include the text panel in the components array
        components.add(textCanvas);
        
        /**Accumulate each tier of information by token id.**/
        ArrayList<Node> tier0 = new ArrayList();
        
        for(int i = 0; i <= g.tokens.size(); ++i){
            tier0.add(null);
        }
        
        ArrayList<ArrayList<Node> > tiers = new ArrayList<>();
        tiers.add(null);
        tiers.add(tier0);
       
        /**
         * Fill tiers
         */
        for(Node n : g.nodes){
            int targetIndex = n.anchors.get(0).get("end");
            boolean flag = false;
            for(ArrayList<Node> tier : tiers){
                if(tier == null || tier.get(targetIndex)!=null)continue;
                tier.set(targetIndex, n);
                flag = true;
                break;
            }
            if(!flag){
                ArrayList<Node> newTier = new ArrayList<>();
                for(int i = 0;i < g.tokens.size(); ++i) newTier.add(null);
                newTier.set(targetIndex, n);
                tiers.add(newTier);
            }
        }
        
        /**
         * Generate elements for each column of the graph.
         */
        
        float colourStep = 1.0f / g.nodes.size(); //Generate uniform colour distribution.
        
        for(int i = 0; i < g.tokens.size(); ++i){
            JPanel column = new JPanel();
            column.setLayout(new BoxLayout(column, BoxLayout.PAGE_AXIS));
            column.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            components.add(column);
            
            Component[] tags = new Component[tiers.size()];
            
            tags[0] = new JLabel();
            ((JLabel)tags[0]).setText(g.tokens.get(i).get("form"));
            
            Component topBuffer = Box.createRigidArea(new Dimension(40, 80)); //Create buffer space above the graph
            
            column.add(topBuffer);
            components.add(topBuffer);
            
            for(int x = 1; x < tiers.size(); ++x){
                if(tiers.get(x).get(i)==null){
                    tags[x] = Box.createRigidArea(new Dimension(40, 15)); //If the node slot is empty, fill the space with a rigid box.
                }else{ //Otherwise generate the NodeLabel.
                    
                    tags[x] = new NodeLabel(tiers.get(x).get(i));
                    VisMouseListener listener = new VisMouseListener(g); //Create new mouse listener for graph nodes
                    ((JLabel)tags[x]).addMouseListener(listener);
                    tags[x].setForeground(Color.getHSBColor(colourStep * Integer.parseInt(tiers.get(x).get(i).ID), 0.8f, 0.7f));
                    ((JLabel)tags[x]).setBorder(BorderFactory.createLineBorder(tags[x].getForeground(), 2));
                    
                    ((JLabel)tags[x]).setText(tiers.get(x).get(i).label);
                    nodes.set(Integer.parseInt(tiers.get(x).get(i).ID), tags[x]);
                }
            
                Component box = Box.createRigidArea(new Dimension(40, 80)); //Create buffer space below the node
            
                column.add(tags[x]);
                column.add(box);
            
                components.add(tags[x]);
                components.add(box);
            }
            
            column.add(tags[0]);
            components.add(tags[0]);
            
            textCanvas.add(column);
            
            JPanel bufferColumn = new JPanel();
            bufferColumn.setLayout(new BoxLayout(bufferColumn, BoxLayout.PAGE_AXIS));
            bufferColumn.add(Box.createRigidArea(new Dimension(80, 200)));
            
            components.add(bufferColumn);
            textCanvas.add(bufferColumn);
        }
        
        /**
         * Pass edges to the DrawPane for rendering
         */
        
        ArrayList<Pair<Component, Component>> edges = new ArrayList();
        for(int i = 0; i < g.edges.size(); ++i){
            for(Map<String, String> edge : g.edges.get(i)){
                edges.add(new Pair(nodes.get(i), nodes.get(Integer.parseInt(edge.get("target")))));
            }
        }
        textCanvas.addEdges(edges);
        
        //Add hover-over text to display graph properties.
        boolean isPlanar = g.isPlanar();
        boolean isConnected = g.isConnected();
        
        textCanvas.setToolTipText((isPlanar?"Graph is planar; ":"") + (isConnected?"Graph is connected":""));
        
         //Create buffer line between rendered graphs.
        JPanel buffer = new JPanel();
        buffer.setLayout(new BoxLayout(buffer, BoxLayout.LINE_AXIS));
        buffer.add(Box.createRigidArea(new Dimension(50, 100)));
        
        components.add(buffer);
        
        //Render generated components.
        baseLayer.add(textCanvas);
        baseLayer.add(buffer);
        
        //Store generated components.
        elements.add(components);
        renderedGraphs.add(g);
        
        /**
         * If 2 graphs are loaded, perform a similarity test and render the result.
         */
        if(renderedGraphs.size()==2){
            try {
                Map<Integer, Integer> similar = renderedGraphs.get(0).getSimilarities(renderedGraphs.get(1));
                ((DrawPane)elements.get(0).get(0)).similar.addAll(similar.keySet());
                ((DrawPane)elements.get(1).get(0)).similar.addAll(similar.values());
                
                System.out.println(similar.keySet());
                System.out.println(similar.values());
                
                ((DrawPane)elements.get(0).get(0)).colourize(renderedGraphs.get(0));
                ((DrawPane)elements.get(1).get(0)).colourize(renderedGraphs.get(1));
            } catch (Exception ex) {
                //Logger.getLogger(Visualizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Repaint the Visualization panel.
        baseLayer.revalidate();
        baseLayer.repaint();

        return true;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        baseLayer = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(100, 100));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        scrollPane.setMinimumSize(new java.awt.Dimension(100, 100));

        baseLayer.setLayout(new javax.swing.BoxLayout(baseLayer, javax.swing.BoxLayout.PAGE_AXIS));
        scrollPane.setViewportView(baseLayer);

        add(scrollPane);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel baseLayer;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables

    class Edge {
        public int x1;
        public int x2;
        public int y1;
        public int y2;
        public String label;
        
        public Edge(int x1, int x2, int y1, int y2, String label){
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.label = label;
        }
        
        public void draw(Graphics g){
            g.drawLine(x1, y1, x2, y2);
            g.drawString(label, (x1+x2)/2, (y1+y2)/2);
        }
    }
}
