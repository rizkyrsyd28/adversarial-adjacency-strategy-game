package structs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class Chromosome {
    public static int MaxLength = 56;
    public static Set<Integer> geneSet = new HashSet<>();

    private List<Integer> genes;
    private int fitnessValue;
    private String chromosomeID;

    static {
        for (int i = 0; i <= 63; i++) {
            geneSet.add(i);
        }
    }

    /**
     * Constructor
     * @param genes
     * @param fitnessValue
     * @param chromosomeID
     */
    public Chromosome(List<Integer> genes, int fitnessValue, String chromosomeID) {
        this.genes = genes;
        this.fitnessValue = fitnessValue;
        this.chromosomeID = chromosomeID;
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
     * Generate a random chromosome with spesific length
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
        this.fitnessValue = 0;
        this.resolveDuplicateGenes();


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


    /**
     * 
     * @param gene
     * @return the gene at the given index
     */
    public static String decodeGene(int gene){
        int row = gene / 8;
        int col = gene % 8;
        return "[" + row + "," + col + "]";
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

    public void debugChromosome(){
        System.out.println("Chromosome ID: " + this.chromosomeID);
        System.out.println("Fitness Value: " + this.fitnessValue);
        System.out.println("Genes: ");
        for(int i = 0; i < this.genes.size(); i++){
            String after = (i == this.genes.size()-1 ? "\n" : "-");
            System.out.print(decodeGene(this.genes.get(i)) + after);
        }
    }

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
     * crossover the chromosome at the given index
     */
    public static void restrictCrossover(Chromosome chromosome1, Chromosome chromosome2, int crossoverPoint){
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
        chromosome1.resolveDuplicateGenes();
        chromosome2.resolveDuplicateGenes();
    }


    /**
     * 
     * @param mutationPoint
     * mutate the gene at the given index with restricted mutation
     */
    public void restrictMutation(int mutationPoint){

        //Get chromosome hashset
        Set<Integer> set = new HashSet<>();
        for(int i = 0; i < this.genes.size(); i++){
            set.add(this.genes.get(i));
        }

        //duplicating geneSet
        Set<Integer> permissionGene = new HashSet<>(geneSet);

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
     */
    public void resolveDuplicateGenes(){

        if(isChromosomeValid(this.genes)) return;

        //search gene location in chromosome that has duplicate
        for(int i = 0; i < this.genes.size(); i++){
            if(Collections.frequency(this.genes, this.genes.get(i)) > 1){
                int duplicateGene = this.genes.get(i);
                restrictMutation(i);
                while(this.genes.get(i) == duplicateGene){
                    restrictMutation(i);
                }
            }
        }
    }
    
}
