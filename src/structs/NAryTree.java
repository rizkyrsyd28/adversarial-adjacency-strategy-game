package structs;

import java.util.ArrayList;
import java.util.List;

public class NAryTree<T>{

    /**
     * Parameter
     * @param data: data of the node
     * @param children: list of children of the node
     */
    protected T data;
    protected List<NAryTree<T>> children;

    /**
     * Constructor
     * @param data: data of the node
     * @param n_child: number of children of the node
     * @return: a tree with the given node and number of children
     */
    public NAryTree(T data, int n_child) {
        this.data = data;
        this.children = (new ArrayList<>(n_child));
    }

    /**
     * Constructor
     * @param data: data of the node
     * @return: a tree with the given node and number of children
     */
    public NAryTree(T data) {
        this.data = data;
        this.children = new ArrayList<>(0);
    }

    // SETTERS
    public void setData(T data) {
        this.data = data;
    }

    public void setChildren(List<NAryTree<T>> children) {
        this.children = children;
    }

    // GETTERS
    public T getData() {
        return data;
    }

    public List<NAryTree<T>> getChildren() {
        return children;
    }

    /**
     * add a child to the tree
     * @param child: child to add
     */
    public void addChild(NAryTree<T> child) {
        this.children.add(child);
    }

}
