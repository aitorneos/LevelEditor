/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor.data;

import javax.swing.table.AbstractTableModel;
import leveleditor.Application;

/**
 *
 * @author Francis
 */
public class SpritePropertiesTableModel extends AbstractTableModel {
    private String[] cols = { "property", "value" };
    private String[] properties = { "tiled", "tile cols", "tile rows",
        "active tile" };
    private SpriteProperties spriteproperties;
    private final Sprite sprite;

    public SpritePropertiesTableModel(Sprite sprite) {
        this.sprite = sprite;
        spriteproperties = sprite.getProperties();
    }
    
    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public int getRowCount() {
        return properties.length;
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return properties[rowIndex];
        } else {
            switch (rowIndex) {
                case 0: return spriteproperties.tiled;
                case 1: return spriteproperties.tilecols;
                case 2: return spriteproperties.tilerows;
                case 3: return spriteproperties.activetile;
                default: return "Undefined";
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            String alue = aValue.toString();
            switch (rowIndex) {
                case 0: 
                    spriteproperties.tiled = Boolean.parseBoolean(alue);
                    break;
                case 1: spriteproperties.tilecols = Integer.parseInt(alue); break;
                case 2: spriteproperties.tilerows = Integer.parseInt(alue); break;
                case 3:
                    spriteproperties.activetile = Integer.parseInt(alue);
                    break;
            }

            sprite.updateImage();
            Application.get().getGui().updateSpritePreview();
        }
    }
}
