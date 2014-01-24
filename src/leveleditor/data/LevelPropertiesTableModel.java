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
public class LevelPropertiesTableModel extends AbstractTableModel {
    private String[] cols = {"property", "value" };
    private String[] properties = {"level width", "level height"};
    private final LevelProperties levelProperties;
    
    public LevelPropertiesTableModel(LevelProperties p) {
        levelProperties = p;
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
        }
        else {
            switch (rowIndex) {
                case 0: return levelProperties.getWidth();
                case 1: return levelProperties.getHeight();
                default: return "undefined";
            }
        }
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            switch (rowIndex) {
                case 0:
                    levelProperties.setWidth(Integer.parseInt(aValue.toString()));
                    break;
                case 1:
                    levelProperties.setHeight(Integer.parseInt(aValue.toString()));
                    break;
            }
            Application.get().updatePreviewPanel();
        }
    }
    
}
