package genetic_package;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.Button;


public class Population {
    public static int defaultPopulationSize = 10;

    private List<Chromosome> chromosomes;
    private int populationSize;

    private Set<Integer> geneSet;
    private int crossoverPoint;
    private int chromosomeLength;

    private Button[][] buttons;

    
    /**
     * Main Constructor
     * Generate a random population with spesific size
     * @param populationSize
     * @param chromosomeLength
     * @param geneSet
     */
    public Population(int populationSize, int chromosomeLength, int crossoverPoint, Button[][] buttons) {
        this.chromosomes = new ArrayList<Chromosome>();
        this.chromosomeLength = chromosomeLength;
        this.crossoverPoint = crossoverPoint;
        this.buttons = buttons;
        this.setGeneSet();
        for(int i = 0; i < populationSize; i++){
            this.chromosomes.add(new Chromosome(this.chromosomeLength, "chromosome" + (i < 10 ? "0" + i : i), this.buttons, this.geneSet));
        }
        this.populationSize = populationSize;
    }

    /**
     * Main Constructor 2
     * Used for creating a new population from list of chromosomes that has been crossovered
     * @param chromosomes
     * @param geneSet 
     * 
     */
    public Population(List<Chromosome> chromosomes, Set<Integer> geneSet, int crossoverPoint, Button[][] buttons) {
        this.chromosomes = chromosomes;
        this.populationSize = chromosomes.size();
        this.geneSet = geneSet;
        this.crossoverPoint = crossoverPoint;
        this.buttons = buttons;
    }

    /**
     * Copy Constructor
     * @param other
     */
    public Population(Population other) {
        this.chromosomes = new ArrayList<Chromosome>();
        for(Chromosome chromosome : other.getChromosomes()){
            this.chromosomes.add(new Chromosome(chromosome));
        }
        this.populationSize = other.populationSize;
        this.geneSet = new HashSet<Integer>(other.getGeneSet());
        this.crossoverPoint = other.crossoverPoint;
        this.chromosomeLength = other.chromosomeLength;
        this.buttons = other.buttons;
    }

    /**
     * Constructor
     * Generate a random population with size of 10
     */
    public Population(int chromosomeLength) {
        this.chromosomes = new ArrayList<Chromosome>();
        for(int i = 0; i < defaultPopulationSize; i++){
            this.chromosomes.add(new Chromosome(chromosomeLength, "chromosome" + (i < 10 ? "0" + i : i)));
        }
        this.populationSize = defaultPopulationSize;
    }

    /**
     * Constructor
     * Generate a random population with spesific size
     * use for test2.java
     * @param chromosomeLength
     * @param geneSet
     */
    public Population(int populationSize, int chromosomeLength) {
        this.chromosomes = new ArrayList<Chromosome>();
        for(int i = 0; i < populationSize; i++){
            this.chromosomes.add(new Chromosome(chromosomeLength, "chromosome" + (i < 10 ? "0" + i : i)));
        }
        this.populationSize = populationSize;
        this.geneSet = Chromosome.geneSetDefault;
    }

    //SETTER
    public void setChromosomes(List<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setGeneSet(Set<Integer> geneSet) {
        this.geneSet = geneSet;
    }

    public void setCrossoverPoint(int crossoverPoint) {
        this.crossoverPoint = crossoverPoint;
    }

    public void setChromosomeLength(int chromosomeLength) {
        this.chromosomeLength = chromosomeLength;
    }

    public void setButtons(Button[][] buttons) {
        this.buttons = buttons;
    }

    //GETTER
    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public Set<Integer> getGeneSet() {
        return geneSet;
    }

    public int getCrossoverPoint() {
        return crossoverPoint;
    }

    public int getChromosomeLength() {
        return chromosomeLength;
    }

    public Button[][] getButtons() {
        return buttons;
    }



    // OTHERS METHOD

    private void setGeneSet(){
        this.geneSet = new HashSet<Integer>();
        for(int i = 0; i < this.buttons.length; i++){
            for(int j = 0; j < this.buttons[i].length; j++){
                if(this.buttons[i][j].getText().equals("")){
                    this.geneSet.add(i*8 + j);
                }
            }
        }
    }

    public void debugPopulation(){
        for(Chromosome chromosome : this.chromosomes){
            chromosome.debugChromosome();
        }
    }

    /**
     * This function is used for creating a new population from 2 parent population
     * After crossover and mutation, should recalculate the fitness value
     * 
     */
    public void populationCrossoverAndMutation(){
        if(this.populationSize % 2 == 0){
            for(int i = 0; i < this.populationSize; i+=2){
            Chromosome chromosome1 = this.chromosomes.get(i);
            Chromosome chromosome2 = this.chromosomes.get(i+1);
            Chromosome.restrictCrossover(chromosome1, chromosome2, this.crossoverPoint, this.geneSet);
            }
        }
        else{
            for(int i = 0; i < this.populationSize - 1; i+=2){
                Chromosome chromosome1 = this.chromosomes.get(i);
                Chromosome chromosome2 = this.chromosomes.get(i+1);
                Chromosome.restrictCrossover(chromosome1, chromosome2, this.crossoverPoint, this.geneSet);
            }
            Chromosome chromosome1 = this.chromosomes.get(this.populationSize - 1);
            Chromosome chromosome2 = this.chromosomes.get(0);
            Chromosome.restrictCrossover(chromosome1, chromosome2, this.crossoverPoint, this.geneSet);
        }
        this.recalculateFitnessValue();
    }

    /**
     * This Function is used for selecting the best chromosome from 2 population to create a new population
     * @param population1 : parent population
     * @param population2 : parent population that has been crossovered/mutated
     * @return
     */

    public static Population PopulationSelection(Population population1, Population population2){
        //Parameters:
        List<int[]> order = new ArrayList<int[]>();
        List<Chromosome> selectedChromosomes = new ArrayList<>();
        int orderSize = Math.min(population1.getPopulationSize(),population2.getPopulationSize());
        Set<Integer> geneSetIntersection = new HashSet<Integer>(population1.getGeneSet());
        geneSetIntersection.retainAll(population2.getGeneSet());

        //Algorithm:
        // add the fitness value of each chromosome to order
        for(int i = 0; i < orderSize; i++){
            order.add(new int[]{population1.getChromosomes().get(i).getFitnessValue(), i,0});
            order.add(new int[]{population2.getChromosomes().get(i).getFitnessValue(), i,1});
        }

        //do the quicksort for order:
        quickSortFitness(order, 0, order.size() - 1);

        
        for (int i = 0; i < orderSize; i++) {
            int index = order.get(i)[1];
            int populationId = order.get(i)[2];
            selectedChromosomes.add(populationId == 0 ? population1.getChromosomes().get(index) : population2.getChromosomes().get(index));
        }

        return new Population(selectedChromosomes, geneSetIntersection, population1.getCrossoverPoint(), population1.getButtons());

    }

    private static void quickSortFitness(List<int[]> order, int lo, int hi){
        if (lo < hi) {
            int pivot = partition(order, lo, hi);

            quickSortFitness(order, lo, pivot - 1);
            quickSortFitness(order, pivot + 1, hi);
        }
    }

    private static int partition(List<int[]> order, int lo, int hi) {
        int[] pivot = order.get(hi);
        int i = lo;

        for (int j = lo; j < hi; j++) {
            if (order.get(j)[0] < pivot[0]) {
                Collections.swap(order, i, j);
                i++;
            }
        }

        Collections.swap(order, i, hi);
        return i;
    }

    public void recalculateFitnessValue(){
        for(Chromosome chromosome : this.chromosomes){
            chromosome.recalculateFitnessValue(this.buttons);
        }
    }

    public Chromosome getChromosomeByID(String chromosomeID){
        for(Chromosome chromosome : this.chromosomes){
            if(chromosome.getChromosomeID().equals(chromosomeID)){
                return chromosome;
            }
        }
        return null;
    }
}
