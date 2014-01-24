/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor.data;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Francis
 */
public class SpritesTableModel extends AbstractTableModel {
    private Holder<Sprite> spriteHolder;
    
    private String[] cols = {"name"};

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }
    
    @Override
    public int getRowCount() {
        return spriteHolder.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return spriteHolder.get(rowIndex).getName();
            default:
                return "NO DATA";
        }
    }

    public void setSpriteHolder(Holder spriteHolder) {
        this.spriteHolder = spriteHolder;
    }

    public Holder getSpriteHolder() {
        return spriteHolder;
    }
}
