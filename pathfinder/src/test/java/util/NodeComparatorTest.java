package util;


import model.Node;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class NodeComparatorTest {
    private NodeComparator nodeComparator;

    @Before
    public void setUp() {
        nodeComparator = new NodeComparator();
    }

    @Test
    public void compare() {
        Node o1 = new Node();
        Node o2 = new Node();
        o1.setCost(100);
        o1.setHeuristic(40);
        o2.setCost(30);
        o2.setHeuristic(60);
        Assert.assertEquals(50, nodeComparator.compare(o1, o2));

        o1.setCost(76);
        o1.setHeuristic(95);
        o2.setCost(121);
        o2.setHeuristic(401);
        Assert.assertEquals(-351, nodeComparator.compare(o1, o2));

        o1.setCost(1000);
        o1.setHeuristic(456);
        o2.setCost(30);
        o2.setHeuristic(202);
        Assert.assertEquals(1224, nodeComparator.compare(o1, o2));
    }
}