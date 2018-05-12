package model;


import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.BinaryHeap;


import java.util.ArrayList;
import java.util.List;

/**
 * {@code AStarPathAlgorithm} class implements the A* path finder algorithm.
 */
@Getter
public class AStarPathAlgorithm {
    private static Logger logger = LoggerFactory.getLogger(AStarPathAlgorithm.class);
    private CalculateCost calculateCost;
    private BinaryHeap binaryHeap;
    private List<Node> path;

    public AStarPathAlgorithm() {
        binaryHeap = new BinaryHeap();
        calculateCost = new CalculateCost();
    }

    /**
     * Generate the shortest path between the nodes.
     * @param start node from which we want to find the {@code end}
     * @param end node what we want to find from the {@code start}
     * @return a list with the shortest path between the start and end node
     */
    public List<Node> generatePath(Node start, Node end){
        findPath(start, end);
        return this.path;
    }

    /**
     * Find the shortest path between the start and end nodes.
     * If path was found then reconstruct the path from the end node to the start node.
     * @param start the starting node for the search
     * @param end the end node of desired destination
     * @return if path was found {@code true} otherwise {@code false}
     */
    public boolean findPath(Node start, Node end) {
        Node current = start;
        current.setVisited(true);
        current.setCost(0);
        current.setHeuristic(calculateCost.calculateHeuristic(current, end));
        binaryHeap.getBinaryHeap().offer(current);
        while (binaryHeap.getBinaryHeap().size() > 0) {
            current = binaryHeap.getBinaryHeap().poll();
            //current.setHeuristic(calculateCost.calculateHeuristic1(current, end));

            current.setOpen(false);
            List<Node> edges = current.getEdges();
            for (Node neighbour : edges) {
                if (!neighbour.isOpen()) {
                    continue;
                }
                int cost = (int) (calculateCost.calculateCost(current, neighbour) + current.getCost());
                if (!neighbour.isVisited()) {
                    neighbour.setVisited(true);
                    neighbour.setCost(cost);
                    neighbour.setAncestor(current);
                    neighbour.setHeuristic(calculateCost.calculateHeuristic(neighbour, end));
                    binaryHeap.getBinaryHeap().offer(neighbour);
                }
                else if (cost < neighbour.getCost()) {
                    neighbour.setCost(cost);
                    neighbour.setAncestor(current);
                    binaryHeap.getBinaryHeap().remove(neighbour);
                    binaryHeap.getBinaryHeap().offer(neighbour);
                }

            }
            if (binaryHeap.getBinaryHeap().contains(end)) {
                reconstructPath(start, end);
                //logger.info("Found the path");
                binaryHeap.getBinaryHeap().clear();
                return true;
            }

        }
        //logger.info("Path not found");
        binaryHeap.getBinaryHeap().clear();
        return false;
    }

    private void reconstructPath(Node start, Node end) {
        Node node = end;
        path = new ArrayList<>();
        //logger.info("Start node position row={} col={}", start.getRow(), start.getCol());
        //logger.info("End node position row={} col={}", end.getRow(), end.getCol());
        while (node != start) {
            path.add(node);
            node = node.getAncestor();
            //logger.debug("Node row {} col {} {}", node.getRow(),node.getCol(),node.toString());

        }
        path.add(node);
    }

}

