package model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculateCostTest {

    private CalculateCost calculateCost;

    @Before
    public void setUp() {
        calculateCost = new CalculateCost();
    }

    @Test
    public void calculateHeuristic() {
        Node actualNode = new Node();
        Node endNode = new Node();
        actualNode.setCol(1);
        actualNode.setRow(1);
        endNode.setCol(10);
        endNode.setRow(5);

        Assert.assertEquals(13,calculateCost.calculateHeuristic(actualNode, endNode));

        actualNode.setCol(10);
        actualNode.setRow(18);
        endNode.setCol(25);
        endNode.setRow(20);

        Assert.assertEquals(17,calculateCost.calculateHeuristic(actualNode, endNode));

        actualNode.setCol(0);
        actualNode.setRow(0);
        endNode.setCol(0);
        endNode.setRow(0);

        Assert.assertEquals(0,calculateCost.calculateHeuristic(actualNode, endNode));
    }

    @Test
    public void calculateCost() {
        Node ancestorNode = new Node();
        Node currentNode = new Node();
        ancestorNode.setCol(6);
        ancestorNode.setRow(10);
        currentNode.setCol(30);
        currentNode.setRow(15);

        Assert.assertEquals(24,calculateCost.calculateCost(ancestorNode, currentNode));


        ancestorNode.setCol(5);
        ancestorNode.setRow(10);
        currentNode.setCol(5);
        currentNode.setRow(15);
        Assert.assertEquals(5,calculateCost.calculateCost(ancestorNode, currentNode));


        ancestorNode.setCol(0);
        ancestorNode.setRow(0);
        currentNode.setCol(0);
        currentNode.setRow(0);
        Assert.assertEquals(0,calculateCost.calculateCost(ancestorNode, currentNode));
    }
}