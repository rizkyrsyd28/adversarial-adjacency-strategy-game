package bot; 

import controllers.OutputFrameController;
import structs.Chromosome;
import structs.GeneticTree;
import structs.Population;
import utils.GeneticFunction;

public class GeneticBot extends Bot {
    public static final int MIN_ITERATION = 5;
    public static final int MAX_ITERATION = 100;
    public static final int POPULATION_SIZE = 100;

    @Override
    public int[] move(OutputFrameController outputFrameController) {
        //initiate
        Population population = new Population(POPULATION_SIZE, 5, 3, outputFrameController.getButtons());
        Population population_child = new Population(population);

        //perform crossover and mutation in child
        population_child.populationCrossoverAndMutation();
        GeneticTree tree;
        int iteration = 0;
        while (true) {
            
            // do population selection
            population = Population.PopulationSelection(population, population_child);
            
            
            if(iteration >= MIN_ITERATION){
                // construct minimax tree
                tree = GeneticFunction.constructMiniMaxTree(population);
                tree.miniMax(true);
                if( tree.getData().getFitnessValue() > 0){
                    int[] move =  Chromosome.decodeGene(tree.getData().getGene());
                    return move;
                }
                
            }
            
            // produce child population
            population_child = new Population(population);
            //perform crossover and mutation
            population_child.populationCrossoverAndMutation();
            iteration++;
            if(iteration == MAX_ITERATION) break;
            
        }

        //if failed
        System.out.println("No positive fitness value found");
        tree = GeneticFunction.constructMiniMaxTree(population);
        tree.miniMax(true);
        return Chromosome.decodeGene(tree.getData().getGene());

    }
    
}
