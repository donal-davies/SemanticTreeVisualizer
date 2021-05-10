package CapstonePackage;

import java.util.ArrayList;
import java.util.Map;

public class Node {

    public String ID;
    public String label;
    public ArrayList < Map < String, Integer >> anchors;

    /**
     * @return a formatted string which represents the node
     */
    public String toString() {
        return "{ID: "+ID+", label: "+label+", anchors: "+anchors+"}";
    }

    /**
     * Necessary for the Jackson deserializer
     * @param id refers to the Node's ID
     */
    public void setID(String id) {
        ID = id;
    }

    /**
     * Necessary for the Jackson deserializer
     * @param label refers to the Node's label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Necessary for the Jackson deserializer
     * @param anchors refers to the Node's anchors. Note that in the data it is represented as an array, however we find it only holds one index
     */
    public void setAnchors(ArrayList < Map < String, Integer >> anchors) {
        this.anchors = anchors;
    }

}