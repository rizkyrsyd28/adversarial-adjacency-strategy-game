package genetic_package;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import javafx.scene.control.Button;
import utils.*;

public class Chromosome {
    public static int MaxLength = 56;
    public static Set<Integer> geneSetDefault = new HashSet<>();

    private List<Integer> genes;
    private int fitnessValue;
    private String chromosomeID;

    static {
        for (int i = 0; i <= 63; i++) {
            geneSetDefault.add(i);
        }
    }

    /**
     * Main Constructor
     * Create a chromosome with spesific genes, fitness value, and ID. 
     * Chromosome is restricted to geneSet.
     * @param length
     * @param chromosomeID
     * @param buttons
     * @param geneSet : restricted gene set
     */
    public Chromosome(int length, String chromosomeID, Button[][] buttons,  Set<Integer> geneSet) {
        this.chromosomeID = chromosomeID;
        this.genes = this.createGenes(geneSet, length);
        this.resolveDuplicateGenes(geneSet);
        this.fitnessValue = UtilityFunction.checkScore(buttons, this.decodeGeneOrder());
    }

    /**
     * Constructor
     * Create a chromosome with spesific genes, fitness value, and ID. Chromosome is restricted to geneSet.
     * @param genes
     * @param fitnessValue
     * @param chromosomeID
     * @param geneSet : restricted gene set
     */
    public Chromosome(List<Integer> genes, int fitnessValue, String chromosomeID, Set<Integer> geneSet) {
        this.genes = genes;
        this.fitnessValue = fitnessValue;
        this.chromosomeID = chromosomeID;
        this.resolveDuplicateGenes(geneSet);
    }

    
    /**
     * Constructor
     * @param genes
     * @param chromosomeID
     */
    public Chromosome(List<Integer> genes, String chromosomeID) {
        this.genes = genes;
        this.chromosomeID = chromosomeID;
        this.fitnessValue = 0;
    }

    /**
     * Constructor
     * Generate a random chromosome and fitness value with spesific length and ID. geneSet is default
     * Used for test 2
     * @param length
     * @param chromosomeID
     */
    public Chromosome(int length, String chromosomeID) {
        this.genes = new ArrayList<>();
        for(int i = 0; i < length; i++){
            Random random = new Random();
            this.genes.add(random.nextInt(64));
        }
        this.chromosomeID = chromosomeID;
        Random random = new Random();
        this.fitnessValue = random.nextInt(100);
        this.resolveDuplicateGenes(geneSetDefault);

    }

    // SETTER
    public void setGenes(List<Integer> genes) {
        this.genes = genes;
    }

    public void setFitnessValue(int fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public void setChromosomeID(String chromosomeID) {
        this.chromosomeID = chromosomeID;
    }

    // GETTER

    public List<Integer> getGenes() {
        return genes;
    }

    public int getFitnessValue() {
        return fitnessValue;
    }

    public String getChromosomeID() {
        return chromosomeID;
    }

    // OTHER METHOD
    /**
     * Method for default constructor
     * @param geneSet
     * @param length
     * @return a list of random genes with spesific length
     */
    private List<Integer> createGenes(Set<Integer> geneSet, int length){
        List<Integer> result = new ArrayList<>();
        List<Integer> geneSetList = new ArrayList<>(geneSet);
        int feasible_length = Math.min(length, geneSet.size());
        Collections.shuffle(geneSetList);
        for(int i = 0; i < feasible_length; i++){
            result.add(geneSetList.get(i));
        }
        return result;
    }
    /**
     * 
     * @param gene
     * @return the gene at the given index
     */
    public static String decodeGeneStr(int gene){
        int row = gene / 8;
        int col = gene % 8;
        return "[" + row + "," + col + "]";
    }

    public static int[] decodeGene(int gene){
        int row = gene / 8;
        int col = gene % 8;
        return new int[]{row, col};
    }
    /**
     * 
     * @param row
     * @param col
     * @return encoded gene
     */
    public static int encodeGene(int row, int col){
        return row * 8 + col;
    }

    /**
     * 
     * @return
     */
    public List<int[]> decodeGeneOrder(){
        List<int[]> result = new ArrayList<>();
        for(int i = 0; i < this.genes.size(); i++){
            int row = this.genes.get(i) / 8;
            int col = this.genes.get(i) % 8;
            result.add(new int[]{row, col});
        }
        return result;
    }

    /**
     * 
     * @param geneOrder
     */
    public void encodeGeneOrder(List<int[]> geneOrder){
        List<Integer> newGenes = new ArrayList<>();
        for(int i = 0; i < geneOrder.size(); i++){
            int row = geneOrder.get(i)[0];
            int col = geneOrder.get(i)[1];
            newGenes.add(encodeGene(row, col));
        }
        this.genes = newGenes;

    }

    /**
     * 
     * @param chromosome
     * @return the decoded chromosome
     */

    public void debugChromosome(){
        System.out.println("Chromosome ID: " + this.chromosomeID);
        System.out.println("Fitness Value: " + this.fitnessValue);
        System.out.println("Genes: ");
        for(int i = 0; i < this.genes.size(); i++){
            String after = (i == this.genes.size()-1 ? "\n" : "-");
            System.out.print(decodeGeneStr(this.genes.get(i)) + after);
        }
    }

    /**
     * 
     * @param genes
     * @return is Chromosome have duplicate genes
     */

    public static boolean isChromosomeValid(List<Integer> genes){
        Set<Integer> set = new HashSet<>();
        for(int i = 0; i < genes.size(); i++){
            set.add(genes.get(i));
        }

       return genes.size() == set.size();
    }

    /**
     * 
     * @param chromosome1
     * @param chromosome2
     * @param crossoverPoint
     * @param geneSet : restricted gene set
     * crossover the chromosome at the given index
     */
    public static void restrictCrossover(Chromosome chromosome1, Chromosome chromosome2, int crossoverPoint, Set<Integer> geneSet){
        List<Integer> newGenes = new ArrayList<>();
        List <Integer> newGenes2 = new ArrayList<>();
        for(int i = 0; i < chromosome1.getGenes().size(); i++){
            if(i < crossoverPoint){
                newGenes.add(chromosome1.getGenes().get(i));
                newGenes2.add(chromosome2.getGenes().get(i));
            }else{
                newGenes.add(chromosome2.getGenes().get(i));
                newGenes2.add(chromosome1.getGenes().get(i));
            }
        }
        chromosome1.setGenes(newGenes);
        chromosome2.setGenes(newGenes2);
        //mutate
        chromosome1.resolveDuplicateGenes(geneSet);
        chromosome2.resolveDuplicateGenes(geneSet);
    }


    /**
     * 
     * @param mutationPoint
     * mutate the gene at the given index with restricted mutation
     */
    public void restrictMutation(int mutationPoint, Set<Integer> geneSet){

        //Get chromosome hashset
        Set<Integer> set = new HashSet<>();
        for(int i = 0; i < this.genes.size(); i++){
            set.add(this.genes.get(i));
        }

        //duplicating geneSet
        Set<Integer> permissionGene = new HashSet<>(geneSet == null? geneSetDefault : geneSet);

        //remove gen that already exist in chromosome
        permissionGene.removeAll(set);

        //randomize permissionGene
        List<Integer> permissionGeneList = new ArrayList<>(permissionGene);
        Collections.shuffle(permissionGeneList);

        //mutate
        this.genes.set(mutationPoint, permissionGeneList.get(0));

    }
    /**
     * resolve duplicate genes in chromosome
     * @param geneSet : set of genes that can be used in chromosome
     * 
     */
    public void resolveDuplicateGenes(Set<Integer> geneSet){

        if(isChromosomeValid(this.genes)) return;

        //search gene location in chromosome that has duplicate
        for(int i = 0; i < this.genes.size(); i++){
            if(Collections.frequency(this.genes, this.genes.get(i)) > 1){
                int duplicateGene = this.genes.get(i);
                restrictMutation(i, geneSet);
                while(this.genes.get(i) == duplicateGene){
                    restrictMutation(i, geneSet);
                }
            }
        }
    }

    public void recalculateFitnessValue(Button[][] buttons){
        this.fitnessValue = UtilityFunction.checkScore(buttons, this.decodeGeneOrder());
    }
    
}
