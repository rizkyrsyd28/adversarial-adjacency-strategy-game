package utils;

import javafx.scene.control.Button;
import structs.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GeneticFunction {
    
    public static void foo(){
        System.out.println("foo");
    }

    public static GeneticTree constructMiniMaxTree(Population population){
        Population pop = new Population(population);
        GeneticTree root = new GeneticTree(pop.getPopulationSize());
        List<Chromosome> chromosomes = new ArrayList<>(pop.getChromosomes()); // copy the chromosomes
        for(Chromosome chromosome : chromosomes){
            constructMiniMaxSubTree(chromosome, root);
        }
        return root;
    }

    public static void constructMiniMaxSubTree(Chromosome chromosome, GeneticTree parent){

        //variable instantiation
        int fitnessValue = chromosome.getFitnessValue();
        int gene = chromosome.getGenes().get(0);
        String chromosomeID = chromosome.getChromosomeID();

        //base case
        if(chromosome.getGenes().size() == 1){ 
            GeneticNode child = new GeneticNode(fitnessValue, gene, chromosomeID);
            parent.addChild(new GeneticTree(child));
            return;
        }

        //recursive case
        //1st case: 
        //check every child in parent, if 1st letter of chromosome genes is the same as the child's gene
        for(NAryTree<GeneticNode> child : parent.getChildren()){
            if(child.getData().getGene() == gene){
                chromosome.getGenes().remove(0);
                constructMiniMaxSubTree(chromosome, (GeneticTree) child);
                return;
            }
        }

        //2nd case:
        //if no child has the same gene, create a new child
        GeneticNode child = new GeneticNode(0, gene, null);
        GeneticTree newChild = new GeneticTree(child);
        parent.addChild(newChild);
        chromosome.getGenes().remove(0);
        constructMiniMaxSubTree(chromosome, newChild);
        return;

    }
}
