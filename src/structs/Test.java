package structs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {
    
    public static void main(String[] args) {
        GeneticTree tree = new GeneticTree(3);
        GeneticTree child1 = new GeneticTree(new GeneticNode(Integer.MAX_VALUE, 'A', null), 3);
        GeneticTree child2 = new GeneticTree(new GeneticNode(Integer.MAX_VALUE, 'B', null), 3);
        GeneticTree child3 = new GeneticTree(new GeneticNode(Integer.MAX_VALUE, 'C', null), 3);
        child1.addChild(new GeneticTree(new GeneticNode(4, 'D', "sushi")));
        child1.addChild(new GeneticTree(new GeneticNode(5, 'E', "salmon")));
        child1.addChild(new GeneticTree(new GeneticNode(6, 'F', "avocado")));
        child2.addChild(new GeneticTree(new GeneticNode(7, 'G', "banana")));
        child2.addChild(new GeneticTree(new GeneticNode(8, 'H', "xylem")));
        child2.addChild(new GeneticTree(new GeneticNode(9, 'I', "phloem")));
        child3.addChild(new GeneticTree(new GeneticNode(10, 'J', "jakarta")));
        child3.addChild(new GeneticTree(new GeneticNode(11, 'K', "baghdad")));
        child3.addChild(new GeneticTree(new GeneticNode(12, 'L', "era")));
        tree.addChild(child1);
        tree.addChild(child2);
        tree.addChild(child3);
        tree.debugTree(3);
        tree.miniMax(true);
        System.out.println("After minimax:");
        tree.debugTree(3);

        Chromosome chrom = new Chromosome(new ArrayList<>(List.of(1, 1, 4, 4, 5, 8, 8, 8, 9, 10)), "chrom");
        Chromosome chrom2 = new Chromosome(new ArrayList<>(List.of(1, 3, 4, 4, 8, 8, 8, 8, 9, 11)), "chrom2");
        Chromosome.restrictCrossover(chrom, chrom2, 4);
        chrom.debugChromosome();
        chrom2.debugChromosome();

    }
}
