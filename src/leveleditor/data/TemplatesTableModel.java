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
public class TemplatesTableModel extends AbstractTableModel {
    private String[] cols = { "property", "value" };
    private final Holder<Pair<String, String>> templates;

    public TemplatesTableModel(Holder<Pair<String, String>> templates) {
        this.templates = templates;
    }
    
    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public int getRowCount() {
        return templates.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= templates.size()) {
            return "";
        }
        switch (columnIndex) {
            case 0:
                return templates.get(rowIndex).getKey();
            case 1:
                return templates.get(rowIndex).getVal();
            default:
                return "undefined";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex >= templates.size()) {
            if (! aValue.toString().isEmpty()) {
                switch (columnIndex) {
                    case 0:
                        templates.add(new Pair<>(aValue.toString(), ""));
                        break;
                    case 1:
                        templates.add(new Pair<>("", aValue.toString()));
                        break;
                }
            }
        } else {
            switch (columnIndex) {
                case 0:
                    templates.get(rowIndex).setKey(aValue.toString());
                    break;
                case 1:
                    templates.get(rowIndex).setVal(aValue.toString());
                    break;
            }
        }
    }
}
