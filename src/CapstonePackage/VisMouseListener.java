/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapstonePackage;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author Donal
 */
public class VisMouseListener extends MouseAdapter{
    public Graph graph;
    
    public VisMouseListener(Graph g){
        super();
        graph = g;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getClickCount() == 1 && e.getComponent() instanceof NodeLabel){ //Click must be on a NodeLabel
            ArrayList<Node> des = graph.getDesNodes(Integer.parseInt( ((NodeLabel)e.getComponent()).node.ID));
            if(e.getComponent().getParent().getParent() instanceof DrawPane){
                DrawPane baseLayer = ((DrawPane)e.getComponent().getParent().getParent());
                
                if(baseLayer.greyState != 0)baseLayer.colourize(graph);
                else{
                    if(e.isShiftDown()){ //While holding shift, display adjacent nodes
                        baseLayer.greyAdj(((NodeLabel)e.getComponent()).node.ID, graph);
                    }else if(e.isControlDown()){ //While holding control, display descendent nodes
                        baseLayer.greyDes(((NodeLabel)e.getComponent()).node.ID, graph);
                    }else{ //Otherwise perform shortest path analysis
                        if(baseLayer.node1 == null){ //If the user is selecting the first node of the path
                            baseLayer.node1 = ((NodeLabel)e.getComponent()).node;
                            ((NodeLabel)e.getComponent()).setBorder(BorderFactory.createLineBorder(e.getComponent().getForeground().brighter()));
                        }else if(baseLayer.node1 == ((NodeLabel)e.getComponent()).node){ //Clear the node if they reselect it
                            baseLayer.node1 = null;
                            baseLayer.colourize(graph);
                        }else{ //Otherwise render the path (undirected if the user is holding Alt)
                            baseLayer.greyPath(((NodeLabel)e.getComponent()).node, graph, e.isAltDown());
                        }
                    }
                    return;
                }
                
                /**
                 * Draw analysis as above, directly out of recolouring from a different analysis.
                 */
                if(e.isShiftDown()){
                    baseLayer.greyAdj(((NodeLabel)e.getComponent()).node.ID, graph);
                }else if(e.isControlDown()){
                    baseLayer.greyDes(((NodeLabel)e.getComponent()).node.ID, graph);
                }
                
            }
            
        }
    }
}
