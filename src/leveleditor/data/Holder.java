/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Francis
 */
public class Holder <T> {
    private final ArrayList<T> sprites = new ArrayList<>();

    public Holder() {
    }

    public int size() {
        return sprites.size();
    }

    public boolean contains(Sprite o) {
        return sprites.contains(o);
    }

    public int indexOf(Object o) {
        return sprites.indexOf(o);
    }

    public T get(int index) {
        return sprites.get(index);
    }

    public boolean add(T e) {
        return sprites.add(e);
    }

    public void add(int index, T element) {
        sprites.add(index, element);
    }

    public T remove(int index) {
        return sprites.remove(index);
    }

    public boolean remove(T o) {
        return sprites.remove(o);
    }

    public boolean addAll(Collection<? extends T> c) {
        return sprites.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return sprites.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return sprites.removeAll(c);
    }

    public ArrayList<T> getList() {
        return sprites;
    }
}
