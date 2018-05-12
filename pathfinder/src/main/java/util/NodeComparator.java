package util;

import model.Node;

import java.util.Comparator;

/**
 * {@code NodeComparator} is a utility class for {@link util.BinaryHeap}.
 */
public class NodeComparator implements Comparator<Node> {
    /**
     * Compare two {@link model.Node} element.
     * @param o1 the first node
     * @param o2 the second node
     * @return the result of subtraction
     */
    @Override
    public int compare(Node o1, Node o2) {
        return (int) (o1.getCost() + o1.getHeuristic() - (o2.getCost() + o2.getHeuristic()));
    }

}
