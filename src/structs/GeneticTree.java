package structs;

public class GeneticTree extends NAryTree<GeneticNode> {
    public static char ROOT_VAL = '@';
    public static int MaxDepth = 5;

    /**
     * Constructor
     * @param node: node of the tree
     * @param n_child: number of children of the node
     * @return: a tree with the given node and number of children
     */
    public GeneticTree(GeneticNode node, int n_child) {
        super(node, n_child);
    }

    /**
     * Constructor
     * @param node: node of the tree
     * @return: a tree with the given node and number of children
     */

    public GeneticTree(GeneticNode node) {
        super(node);
    }

    /**
     * Default Root Constructor
     * @param n: number of children of the root
     */
    public GeneticTree(int n) {
        super(new GeneticNode(Integer.MIN_VALUE, ROOT_VAL, null), n);
    }

    /**
     * Add a child to the tree
     * @param child The child to add to the current tree
     */
    public void addChild(GeneticTree child) {
        super.addChild(child);
    }


    /**
     * Print the tree
     * @param th: the depth of the tree
     */
    public void debugTree(int th) { // print tree pre-order

        System.out.print("  ".repeat(MaxDepth - th));
        System.out.println("|- " + this.data.debugNode());

        if(this.children.isEmpty()) return; //base case

        for (NAryTree<GeneticNode> child : this.children) {
            ((GeneticTree) child).debugTree(th - 1);
        }
    }

    public void miniMax(boolean isMaximizingPlayer) {
        System.out.println("MiniMax called on " + this.data.debugNode());
        int bestScore;
        String bestChromosomeID = null;
        if (isMaximizingPlayer){
        //Maximizing case
            bestScore = Integer.MIN_VALUE;
            for (NAryTree<GeneticNode> child : this.children) {
           // recursive case
           if(!child.children.isEmpty()){
               ((GeneticTree) child).miniMax(false);
           }
           // both applied in base case and recursive case, maximizing player
            if(child.data.getFitnessValue() > bestScore){
                 bestScore = child.data.getFitnessValue();
                 bestChromosomeID = child.data.getChromosomeID();
            }
           
        }
        }
    else {
        //Minimizing case
            bestScore = Integer.MAX_VALUE;
            for (NAryTree<GeneticNode> child : this.children) {
           // recursive case
           if(!child.children.isEmpty()){
               ((GeneticTree) child).miniMax(true);
           }
           // both applied in base case and recursive case, minimizing player
            if(child.data.getFitnessValue() < bestScore){
                 bestScore = child.data.getFitnessValue();
                 bestChromosomeID = child.data.getChromosomeID();
            }
           
        }
        }
        this.data.setFitnessValue(bestScore);
        this.data.setChromosomeID(bestChromosomeID);
    }
    
}
