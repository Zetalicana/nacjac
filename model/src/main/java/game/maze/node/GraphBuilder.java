package game.maze.node;

import game.Maze;
import matrix.Matrix;
import model.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Build a graph from the {@code Maze}.
 */
public class GraphBuilder {
    private static Logger logger = LoggerFactory.getLogger(GraphBuilder.class);

    /**
     * Get the nodes of crossroads.
     * For the pathfinder we need only the nodes where we can change direction.
     * @param map {@code Maze}
     * @return {@code Matrix} that hold nodes for the {@code AStarPathAlgorithm}
     */
    public Matrix buildCrossroads(Maze map) {
        Matrix<Node> graph = new Matrix<>();
        graph.setDimension(map.getRow(), map.getCol());

        int rows = graph.getRows();
        int cols = graph.getColumns();

        for (int row = 1; row < rows - 1; row++) {
            for (int col = 4; col < cols - 4; col++) {
                if (map.getMaze()[row][col] == 0) {
                    if (map.getMaze()[row - 1][col] == 0 || map.getMaze()[row + 1][col] == 0) {
                        if (map.getMaze()[row][col - 1] == 0 || map.getMaze()[row][col + 1] == 0) {
                            Node node = new Node();
                            node.setRow(row);
                            node.setCol(col);
                            graph.setValue(row, col, node);
                            //logger.debug("Set the value to {}", graph.getValue(row, col).getRow());
                            //logger.debug("Rows {} Cols{}", node.getRow(), node.getCol());
                        }
                    }
                }
                else if (map.getMaze()[row][col] == 1) {
                    Node node = new Node();
                    node.setRow(row);
                    node.setCol(col);
                    node.setWall(true);
                    graph.setValue(row, col, node);
                }
            }
        }

        return graph;
    }

    /**
     * Constructs the edges of the nodes.
     * @param graph that hold the unconnected nodes.
     */
    public void buildEdges(Matrix<Node> graph) {
        int rows = graph.getRows();
        int cols = graph.getColumns();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (graph.getValue(row, col) == null) {
                    continue;
                }
                else if (!graph.getValue(row, col).isWall()){
                    Node ancestorNode = graph.getValue(row, col);
                    List<Node> edges = new ArrayList<>();
                    int i = col;
                    while (i != cols - 1) {
                        i++;
                        if (graph.getValue(row, i) == null) {
                            continue;
                        }
                        else if (graph.getValue(row, i).isWall()) {
                            break;
                        }
                        else {
                            Node edgeNode = graph.getValue(row, i);
                            edges.add(edgeNode);
                            break;
                        }
                    }
                    i = row;
                    while (i != 0) {
                        i--;
                        if (graph.getValue(i, col) == null) {
                            continue;
                        }
                        else if (graph.getValue(i, col).isWall()) {
                            break;
                        }
                        else {
                            Node edgeNode = graph.getValue(i, col);
                            edges.add(edgeNode);
                            break;
                        }
                    }
                    i = row;
                    while (i != graph.getRows() - 1) {
                        i++;
                        if (graph.getValue(i, col) == null) {
                            continue;
                        }
                        else if (graph.getValue(i, col).isWall()) {
                            break;
                        }
                        else {
                            Node edgeNode = graph.getValue(i, col);
                            edges.add(edgeNode);
                            break;
                        }
                    }
                    i = col;
                    while (i != 0) {
                        i--;
                        if (graph.getValue(row, i) == null) {
                            continue;
                        }
                        else if (graph.getValue(row, i).isWall()) {
                            break;
                        }
                        else {
                            Node edgeNode = graph.getValue(row, i);
                            edges.add(edgeNode);
                            break;
                        }
                    }
                    ancestorNode.setEdges(edges);
                }
            }
        }

    }
}
