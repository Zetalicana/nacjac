package util;

import lombok.Getter;
import model.Node;

import java.util.PriorityQueue;

/**
 * {@code BinaryHeap} is an utility class for {@link model.AStarPathAlgorithm}.
 * It's represent a {@code PriorityQueue} where each element
 * has a priority associated with it.
 * Always offer the highest priority element from the queue.
 */
@Getter
public class BinaryHeap  {
    private PriorityQueue<Node> binaryHeap;

    public BinaryHeap() {
        binaryHeap = new PriorityQueue<>(4, new NodeComparator());
    }


}
