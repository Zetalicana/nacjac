package model;

/**
 * {@code CalculateCost} class contains cost calculator methods for the A* algorithm.
 */
public class CalculateCost {

    /**
     *  Calculate a node heuristic value with the Manhattan distance method.
     * @param actualNode the node where we stand
     * @param endNode the desired end destination which we want to find
     * @return the heuristics value of the node
     */
    public int calculateHeuristic(Node actualNode, Node endNode) {
        return Math.abs(actualNode.getCol() - endNode.getCol()) + Math.abs(actualNode.getRow() - endNode.getRow());
    }

    /**
     * Calculate the cost of step from one node to other.
     * The cost is actually the distance between two nodes.
     * @param ancestorNode parent of the {@code actualNode}
     * @param actualNode node where we stand
     * @return the distance between two nodes
     */
    public int calculateCost(Node ancestorNode, Node actualNode) {
        int a = ancestorNode.getRow() - actualNode.getRow();
        int b = ancestorNode.getCol() - actualNode.getCol();
        return (int) Math.sqrt(a * a + b * b);
    }
}

