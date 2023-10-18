package bot; 

import controllers.OutputFrameController;
import genetic_package.*;
import utils.GeneticFunction;

public class GeneticBot extends Bot {
    public static final int MIN_ITERATION = 5;
    public static final int MAX_ITERATION = 100;
    public static final int POPULATION_SIZE = 100;
    public static final int CHROMOSOME_LENGTH = 5;

    public GeneticBot(String playerString) {
        this.playerString = playerString;
        if (this.playerString.equals("O")) {
            this.opponentString = "X";
        } else {
            this.opponentString = "O";
        }
    }


    @Override
    public int[] move(OutputFrameController outputFrameController) {
        //initiate
        Population population = new Population(POPULATION_SIZE, CHROMOSOME_LENGTH, 3, outputFrameController.getButtons());
        Population population_child = new Population(population);

        //perform crossover and mutation in child
        population_child.populationCrossoverAndMutation();
        GeneticTree tree;
        int iteration = 0;
        while (true) {
           
            // do population selection
            population = Population.PopulationSelection(population, population_child, playerString.equals("O"));
            
            
            if(iteration >= MIN_ITERATION){
       
                // construct minimax tree
                tree = GeneticFunction.constructMiniMaxTree(population, playerString.equals("O"));
                
              
                tree.miniMax(true);
               
                if( tree.getData().getFitnessValue() > 0){
                    Chromosome candidate = population.getChromosomeByID(tree.getData().getChromosomeID());
                    int[] move =  Chromosome.decodeGene(candidate.getGenes().get(0));

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
        tree = GeneticFunction.constructMiniMaxTree(population, playerString.equals("O"));
        tree.miniMax(true);
        Chromosome candidate = population.getChromosomeByID(tree.getData().getChromosomeID());
        int[] move =  Chromosome.decodeGene(candidate.getGenes().get(0));
        return move;

    }
    
}
