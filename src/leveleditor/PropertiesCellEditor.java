/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Francis
 */
public class PropertiesCellEditor extends AbstractCellEditor implements TableCellEditor {
    private TableCellEditor editor;

    @Override
    public Object getCellEditorValue() {
        if (editor != null) {
            return editor.getCellEditorValue();
        }

        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof String) {
            editor = new DefaultCellEditor(new JTextField());
        } else if (value instanceof Boolean) {
            editor = new DefaultCellEditor(new JCheckBox());
        } else if (value instanceof Integer) {
            editor = new PositiveIntegerCellEditor(new JTextField());
        }

        return editor.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

}
