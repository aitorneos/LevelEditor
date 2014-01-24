/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor.data;

/**
 *
 * @author Francis
 */
public class LevelProperties {
    private int width, height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public LevelProperties(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public LevelProperties() {
    }
}
