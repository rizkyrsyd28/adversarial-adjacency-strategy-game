package structs;
import utils.*;

public class Test2 {
    public static void main(String[] args) {
        Population population = new Population(5, 5);
        population.debugPopulation();
        // population.populationCrossoverAndMutation();
        // System.out.println("\nAfter crossover and mutation: \n");
        // population.debugPopulation();
        GeneticTree tree = GeneticFunction.constructMiniMaxTree(population);
        tree.miniMax(true);
        tree.debugTree(0);
    }
}
