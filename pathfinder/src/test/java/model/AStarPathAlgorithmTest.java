package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AStarPathAlgorithmTest {
    private AStarPathAlgorithm aStarPathAlgorithm;
    private List<Node> nodeList;
    @Before
    public void setUp() {
        aStarPathAlgorithm = new AStarPathAlgorithm();
    }

    @Test
    public void generatePath() {
        nodeList = getNodes();
        List<Node> path = aStarPathAlgorithm.generatePath(nodeList.get(0), nodeList.get(4));
        List<Node> expected = new ArrayList<>(Arrays.asList(nodeList.get(4), nodeList.get(1), nodeList.get(0)));

        Assert.assertEquals(true,compareList(path,expected));

        resetNodes(nodeList);

        path = aStarPathAlgorithm.generatePath(nodeList.get(6), nodeList.get(2));
        expected = new ArrayList<>(Arrays.asList(nodeList.get(2),nodeList.get(3), nodeList.get(5), nodeList.get(6)));

        Assert.assertEquals(true,compareList(path,expected));

        path = aStarPathAlgorithm.generatePath(nodeList.get(2), nodeList.get(6));
        expected = new ArrayList<>(Arrays.asList(nodeList.get(7),nodeList.get(3), nodeList.get(5)));

        Assert.assertEquals(false,compareList(path,expected));

    }

    @Test
    public void findPath() {

        nodeList = getNodes();

        Assert.assertEquals(true,aStarPathAlgorithm.findPath(nodeList.get(0),nodeList.get(5)));

        resetNodes(nodeList);
        Assert.assertEquals(false,aStarPathAlgorithm.findPath(nodeList.get(0),nodeList.get(7)));

        resetNodes(nodeList);
        Assert.assertEquals(true,aStarPathAlgorithm.findPath(nodeList.get(4),nodeList.get(3)));
    }

    private void resetNodes(List<Node> nodeList) {
        for (Node node : nodeList) {
            node.setOpen(true);
            node.setVisited(false);
            node.setAncestor(null);
        }
    }

    private boolean compareList(List<Node> path, List<Node> expected) {
        if (path.size() != expected.size()) return false;
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i) != expected.get(i)) return false;
        }
        return true;
    }
    private List<Node> getNodes() {
        Node node1 = new Node();
        node1.setRow(0);
        node1.setCol(0);
        Node node2 = new Node();
        node2.setRow(0);
        node2.setCol(9);
        Node node3 = new Node();
        node3.setRow(5);
        node3.setCol(0);
        Node node4 = new Node();
        node4.setRow(5);
        node4.setCol(3);
        Node node5 = new Node();
        node5.setRow(5);
        node5.setCol(9);
        Node node6 = new Node();
        node6.setRow(10);
        node6.setCol(3);
        Node node7 = new Node();
        node7.setRow(10);
        node7.setCol(9);
        Node node8 = new Node();
        node8.setRow(15);
        node8.setCol(9);
        node1.setEdges(new ArrayList<>(Arrays.asList(node3,node2)));
        node2.setEdges(new ArrayList<>(Arrays.asList(node1,node5)));
        node3.setEdges(new ArrayList<>(Arrays.asList(node1,node4)));
        node4.setEdges(new ArrayList<>(Arrays.asList(node3,node5,node6)));
        node5.setEdges(new ArrayList<>(Arrays.asList(node4,node2,node7)));
        node6.setEdges(new ArrayList<>(Arrays.asList(node4,node7)));
        node7.setEdges(new ArrayList<>(Arrays.asList(node6,node5)));
        node8.setEdges(new ArrayList<>(Arrays.asList(node8)));

        return new ArrayList<>(Arrays.asList(node1,node2,node3,node4,node5,node6,node7,node8));
    }
}