package CapstonePackage;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Donal
 */
public class DrawPane extends javax.swing.JPanel {

    public ArrayList< ArrayList< Pair<Component, Component> > > edges; //Stores all graph edges
    public short greyState; //0 = Normal; 1=Descendent; 2=Adjacent; 3=Shortest
    public ArrayList<Integer> similar; //Stores nodes that have been flagged as similar to another graph
    
    public Node node1;
    
    public DrawPane() {
        edges = new ArrayList();
        greyState = 0;
        node1 = null;
        similar = new ArrayList<>();
        initComponents();
    }
    
    /**
     * Recolours all graph nodes when clearing an analysis display
     * @param g 
     */
    public void colourize(Graph g){
        float colourStep = 1.0f / g.nodes.size();

        for(Component c : this.getComponents()){
            if(c instanceof JPanel){
                for(Component label : ((JPanel)c).getComponents()){
                    if(label instanceof NodeLabel){
                        label.setForeground(Color.getHSBColor(colourStep * Integer.parseInt(((NodeLabel)label).node.ID), 0.8f, 0.7f));
                        ((JLabel)label).setBorder(BorderFactory.createLineBorder(label.getForeground(), 2));
                    }
                }
            }
        }
        
        highlightSimilar();

        greyState = 0;
        
        this.repaint();
    }
    
    /**
     * Highlights all nodes that have been tagged as similar with a black border
     */
    public void highlightSimilar(){
        for(Component c : this.getComponents()){
            if(c instanceof JPanel){
                for(Component label : ((JPanel)c).getComponents()){
                    if(label instanceof NodeLabel && similar.contains(Integer.parseInt(((NodeLabel)label).node.ID))){
                        //label.setForeground(Color.WHITE);
                        ((JLabel)label).setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
                    }
                }
            }
        }
        repaint();
    }
    
    /**
     * Renders the shortest path between two nodes by greying out all other nodes and edges.
     * @param node2 destination node
     * @param g
     * @param directed if true draw a directed shortest path, otherwise draw undirected
     */
    public void greyPath(Node node2, Graph g, boolean directed){
        if(greyState == 0){
            
            try {
                ArrayList<Node> path = directed?g.getShortestPath(Integer.parseInt(node1.ID), Integer.parseInt(node2.ID), false)
                        :g.getShortestPath(Integer.parseInt(node1.ID), Integer.parseInt(node2.ID), true);
                
                for(Component c : this.getComponents()){
                if(c instanceof JPanel){
                    for(Component label : ((JPanel)c).getComponents()){
                        
                        if(label instanceof NodeLabel){
                            if(((NodeLabel)label).node.ID.equals(node1.ID) || ((NodeLabel)label).node.ID.equals(node2.ID)){
                                ((JLabel)label).setBorder(BorderFactory.createCompoundBorder(
                                                    BorderFactory.createLineBorder(label.getForeground().brighter().brighter(),2),
                                                    BorderFactory.createLineBorder(label.getForeground(),1)));
                            }
                            if(!path.contains( ((NodeLabel)label).node)){
                                label.setForeground(Color.getHSBColor(0f, 0f, 0.3f));
                                ((JLabel)label).setBorder(BorderFactory.createLineBorder(label.getForeground(), 2));
                            }
                        }
                    }
                }
            }
            
            greyState = 3;
            node1 = null;
            
            this.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "No Path Found", 0, null);
                node1 = null;
                this.colourize(g);
            }
        }
    }
    
    /**
     * Greys out all nodes that are not adjacent to the target node
     * @param s
     * @param g 
     */
    public void greyAdj(String s, Graph g){
        int ID = Integer.parseInt(s);
        
        if(greyState == 0){
            ArrayList<Node> nodes = g.getAdjNodes(ID);
            
            for(Component c : this.getComponents()){
                if(c instanceof JPanel){
                    for(Component label : ((JPanel)c).getComponents()){
                        if(label instanceof NodeLabel && !nodes.contains( ((NodeLabel)label).node)){
                            label.setForeground(Color.getHSBColor(0f, 0f, 0.3f));
                            ((JLabel)label).setBorder(BorderFactory.createLineBorder(label.getForeground(), 2));
                        }
                    }
                }
            }
            
            greyState = 1;
        }
        
        this.repaint();
    }
    
    /**
     * Greys out all nodes that are not descendents of the target node
     * @param s
     * @param g 
     */
    public void greyDes(String s, Graph g){
        int ID = Integer.parseInt(s);
        
        if(greyState == 0){
            ArrayList<Node> nodes = g.getDesNodes(ID);
            
            for(Component c : this.getComponents()){
                if(c instanceof JPanel){
                    for(Component label : ((JPanel)c).getComponents()){
                        if(label instanceof NodeLabel){
                            if(Integer.parseInt(((NodeLabel)label).node.ID)==ID){
                                ((JLabel)label).setBorder(BorderFactory.createCompoundBorder(
                                                    BorderFactory.createLineBorder(label.getForeground().brighter().brighter(),2),
                                                    BorderFactory.createLineBorder(label.getForeground(),1)));
                            }
                            if(!nodes.contains( ((NodeLabel)label).node)){
                                label.setForeground(Color.getHSBColor(0f, 0f, 0.3f));
                                ((JLabel)label).setBorder(BorderFactory.createLineBorder(label.getForeground(), 2));
                            }
                        }
                    }
                }
            }
            
            greyState = 2;
        }
        
        this.repaint();
    }

    /**
     * Draw all graph edges
     * @param g 
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        /**
         * TODO: Implement edge rendering.
         */
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if(edges.size() > 0){
            for(Pair<Component, Component> pcc : edges.get(0)){
                
                Component first = pcc.getKey();
                Component second = pcc.getValue();
                
                if(second.getForeground().getRed() == second.getForeground().getBlue() 
                        && second.getForeground().getBlue() == second.getForeground().getGreen()){
                    g2.setColor(Color.getHSBColor(0f, 0f, 0.3f));
                }else{
                    g2.setColor(first.getForeground());
                }
                
                int xFirst = first.getLocationOnScreen().x - this.getLocationOnScreen().x;
                int yFirst = first.getLocationOnScreen().y - this.getLocationOnScreen().y;
                
                int xSecond = second.getLocationOnScreen().x - this.getLocationOnScreen().x;
                int ySecond = second.getLocationOnScreen().y - this.getLocationOnScreen().y;
                
                if(Math.abs(xFirst - xSecond) < 10){
                    
                    xFirst -= 10;
                    xSecond -= 10;
                    
                    if(yFirst < ySecond){
                        g2.drawArc(xFirst, yFirst+first.getHeight(), 20, Math.abs(yFirst - ySecond)-second.getHeight(), 90, 180);
                    }else{
                        g2.drawArc(xFirst, ySecond+second.getHeight(), 20, Math.abs(yFirst - ySecond)-first.getHeight(), 90, 180);
                    }
                }else if(Math.abs(yFirst-ySecond) < 10){
                    if(xFirst < xSecond){
                        buildCurveHorizontal(xFirst+first.getWidth()/2, xSecond, yFirst+first.getHeight(), g2, 1);
                    }else{
                        buildCurveHorizontal(xSecond+second.getWidth()/2, xFirst, yFirst, g2, -1);
                    }
                }else{
                    if(xFirst < xSecond){
                        if(yFirst < ySecond){
                            g2.drawLine(xFirst + first.getWidth()/2, yFirst+first.getHeight(), xSecond, ySecond+second.getHeight()/2);
                        }else{
                            g2.drawLine(xFirst + first.getWidth()/2, yFirst, xSecond, ySecond+second.getHeight()/2);
                        }
                    }else if(xFirst > xSecond){
                        if(yFirst < ySecond){
                            g2.drawLine(xFirst + first.getWidth()/2, yFirst+first.getHeight(), xSecond+second.getWidth(), ySecond+second.getHeight()/2);
                        }else{
                            g2.drawLine(xFirst + first.getWidth()/2, yFirst, xSecond+second.getWidth(), ySecond+second.getHeight()/2);
                        }
                    }else{
                        g2.drawLine(xFirst, yFirst, xSecond, ySecond);
                    }
                }
                
                g2.setColor(Color.BLACK);
            }
        }
    }
    
    /**
     * Trigonometric curve drawing for smoother curved horizontal edges
     * @param xFirst
     * @param xSecond
     * @param height
     * @param g
     * @param sign 
     */
    private void buildCurveHorizontal(int xFirst, int xSecond, int height, Graphics g, int sign){
        int[] x = new int[180];
        int[] y = new int[180];
        
        int amp = sign * (int)Math.round(15 * Math.log((Math.abs(xFirst-xSecond)/20)));
        
        for(int i = 0; i < 180; ++i){
            x[i] = xFirst + (int)Math.round((i / 1.0)*(Math.abs(xFirst - xSecond)/180.0));
            y[i] = height + (int)Math.round(amp * Math.sin( (i / 1.0) * Math.PI / 180.0));
        }
        
        g.drawPolyline(x, y, 180);
    }
    
    public void addEdges(ArrayList<Pair<Component, Component>> newEdges){
        edges.add(newEdges);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
