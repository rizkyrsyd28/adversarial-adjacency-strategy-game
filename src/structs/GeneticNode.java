package structs;

public class GeneticNode {
    private int FitnessValue;
    private int Gene;
    private String ChromosomeID;


    /**
     * Constructor
     * @param FitnessValue
     * @param gene
     * @param chromosomeID
     */
    public GeneticNode(int FitnessValue,int gene, String chromosomeID) {
        this.FitnessValue = FitnessValue;
        this.Gene = gene;
        this.ChromosomeID = chromosomeID;
    }

    // SETTERS
    public void setFitnessValue(int fitnessValue) {
        FitnessValue = fitnessValue;
    }

    public void setGene(int gene) {
        Gene = gene;
    }

    public void setChromosomeID(String chromosomeID) {
        ChromosomeID = chromosomeID;
    }

    // GETTERS
    public int getFitnessValue() {
        return FitnessValue;
    }

    public int getGene() {
        return Gene;
    }

    public String getChromosomeID() {
        return ChromosomeID;
    }
    
    /**
     * debug the node
     * @return
     */
    public String debugNode(){
        return (this.FitnessValue + " - " + this.Gene + " - " + this.ChromosomeID);
    }
}
