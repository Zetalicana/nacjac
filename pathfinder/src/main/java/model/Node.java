package model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represent a matrix node.
 */
@Getter
@Setter
public class Node {

    private boolean open = true;
    private boolean visited;
    private boolean wall;

    private float cost = 0;
    private float heuristic;
    private Node ancestor;
    private List<Node> edges;

    private int row;
    private int col;




    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(heuristic + cost);
        sb.append("(");
        sb.append(cost);
        sb.append(",");
        sb.append(heuristic);
        sb.append(")");
        return sb.toString();
    }


}
