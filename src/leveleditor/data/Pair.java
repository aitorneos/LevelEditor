/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor.data;

/**
 *
 * @author Francis
 */
public class Pair<T, U> {
    private T key;
    private U val;

    public Pair(T key, U val) {
        this.key = key;
        this.val = val;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public U getVal() {
        return val;
    }

    public void setVal(U val) {
        this.val = val;
    }
    
    
}
