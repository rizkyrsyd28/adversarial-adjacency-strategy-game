import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


public class Population {
    public static int defaultPopulationSize = 10;

    private List<Chromosome> chromosomes;
    private int populationSize;

    /**
     * Constructor
     * @param chromosomes
     */
    public Population(List<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
        this.populationSize = chromosomes.size();
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
     * @param populationSize
     */
    public Population(int populationSize, int chromosomeLength) {
        this.chromosomes = new ArrayList<Chromosome>();
        for(int i = 0; i < populationSize; i++){
            this.chromosomes.add(new Chromosome(chromosomeLength, "chromosome" + (i < 10 ? "0" + i : i)));
        }
        this.populationSize = populationSize;
    }

    //SETTER
    public void setChromosomes(List<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    //GETTER
    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    // OTHERS METHOD

    public void populationCrossoverAndMutation(){
        for(int i = 0; i < this.populationSize; i++){
            Chromosome.restrictCrossover(this.chromosomes.get(i), this.chromosomes.get(i+1), 4);
        }
    }

    /**
     * 
     * @param population1 : parent population
     * @param population2 : parent population that has been crossovered/mutated
     * @return
     */

    public static Population PopulationSelection(Population population1, Population population2){
        //selection by fitness value
        List<int[]> order = new ArrayList<int[]>();
        List<Chromosome> selectedChromosomes = new ArrayList<>();

        // sorting the 1st population
        for(int i = 0; i < population1.getPopulationSize(); i++){
            order.add(new int[]{population1.getChromosomes().get(i).getFitnessValue(), i});
        }

        //do the quicksort for order:
        quickSortFitness(order, 0, order.size() - 1);

        
        for (int i = 0; i < (int)population1.getPopulationSize()/2; i++) {
            int index = order.get(i)[1];
            selectedChromosomes.add(population1.getChromosomes().get(index));
        }

        order.clear();

        // sorting the 2st population
        for(int i = 0; i < population2.getPopulationSize(); i++){
            order.add(new int[]{population2.getChromosomes().get(i).getFitnessValue(), i});
        }
        //do the quicksort for order:
        quickSortFitness(order, 0, order.size() - 1);
        
        for (int i = 0; i < (int)population2.getPopulationSize()/2; i++) {
            int index = order.get(i)[1];
            selectedChromosomes.add(population2.getChromosomes().get(index));
        }

        return new Population(selectedChromosomes);

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
}
