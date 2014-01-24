package leveleditor;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Francis
 */
public class PropertiesCellRenderer extends DefaultTableCellRenderer {
    private final CheckBoxRenderer checkBoxRender;
    
    public PropertiesCellRenderer() {
        checkBoxRender = new CheckBoxRenderer();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof String) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        } else if (value instanceof Boolean) {
            return checkBoxRender.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        } else if (value instanceof Integer) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
    
}
