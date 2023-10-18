package utils;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import genetic_package.*;


public class GeneticFunction {
    
    public static void foo(){
        System.out.println("foo");
    }

    public static GeneticTree constructMiniMaxTree(Population population, boolean isO){
        Population pop = new Population(population);
        GeneticTree root = new GeneticTree(pop.getPopulationSize());
        List<Chromosome> chromosomes = new ArrayList<Chromosome>(); // copy the chromosomes
        for(Chromosome chromosome : pop.getChromosomes()){
            chromosomes.add(new Chromosome(chromosome));
          
        }
       
        
        for(Chromosome chromosome : chromosomes){
            constructMiniMaxSubTree(chromosome, root, isO);
        }
        return root;
    }

    public static void constructMiniMaxSubTree(Chromosome chromosome, GeneticTree parent, boolean isO){

        //variable instantiation
        int fitnessValue = chromosome.getFitnessValue();
        int gene = chromosome.getGenes().get(0);
        String chromosomeID = chromosome.getChromosomeID();

        //base case
        if(chromosome.getGenes().size() == 1){ 
            GeneticNode child = new GeneticNode(isO ? fitnessValue : -fitnessValue, gene, chromosomeID);
            parent.addChild(new GeneticTree(child));
            return;
        }

        //recursive case
        //1st case: 
        //check every child in parent, if 1st letter of chromosome genes is the same as the child's gene
        for(NAryTree<GeneticNode> child : parent.getChildren()){
            if(child.getData().getGene() == gene){
                chromosome.getGenes().remove(0);
                constructMiniMaxSubTree(chromosome, (GeneticTree) child, isO);
                return;
            }
        }

        //2nd case:
        //if no child has the same gene, create a new child
        GeneticNode child = new GeneticNode(0, gene, null);
        GeneticTree newChild = new GeneticTree(child);
        parent.addChild(newChild);
        chromosome.getGenes().remove(0);
        constructMiniMaxSubTree(chromosome, newChild, isO);
        return;

    }
}
