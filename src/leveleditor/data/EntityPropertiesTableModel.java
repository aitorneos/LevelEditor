/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor.data;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import leveleditor.Application;
import leveleditor.Entity;

/**
 *
 * @author Francis
 */
public class EntityPropertiesTableModel extends AbstractTableModel {
    private String[] cols = { "property", "value" };
    private String[] properties = {"entity" ,"x" , "y" , "width" , "height" , "type"};
    private Entity entity;
    private ArrayList<Pair<String, String>> cprops;

    public EntityPropertiesTableModel(Entity e) {
        entity = e;
        cprops = e.getCustomProperties();
    }
    
    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public int getRowCount() {
        return properties.length + cprops.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        if (rowIndex > properties.length - 1 && cprops.size() >= rowIndex - (properties.length - 1))
        {
            Pair<String, String> tpair = cprops.get(rowIndex - properties.length);
            return columnIndex == 0 ? tpair.getKey() : tpair.getVal();
        } 
        else if (columnIndex == 0) 
        {
            return properties[rowIndex];
        } 
        else if (entity != null) 
        {
            switch (rowIndex) 
            {
                case 0:
                    return "entity";
                    
                case 1:
                    return entity.getX();
                    
                case 2:
                    return entity.getY();
                    
                case 3:
                    return 60;
                    
                case 4:
                    return 60;
                    
                case 5:
                    return entity.getType();
                    
                default:
                    return "undefined";
            }
        } else {
            return "no selected entity";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1 || rowIndex >= properties.length;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex > properties.length - 1 && cprops.size() >= rowIndex - (properties.length - 1)) {
            Pair<String, String> tpair = cprops.get(rowIndex - properties.length);
            if (columnIndex == 0) {
                tpair.setKey(aValue.toString());
            } else {
                tpair.setVal(aValue.toString());
            }
        } else if (columnIndex == 1) {
            String alue = aValue.toString();
            switch (rowIndex) {
                case 0:
                    entity.setType(alue);
                    break;
                case 1: 
                    entity.setName(alue); 
                    Application.get().updateOutliner(entity); 
                    break;
                case 2:
                    entity.setActiveTile(Integer.parseInt(alue));
                    Application.get().updatePreviewPanel();
                    break;
                case 3: 
                    entity.setX(Integer.parseInt(alue)); 
                    Application.get().updatePreviewPanel();
                    break;
                case 4: 
                    entity.setY(Integer.parseInt(alue)); 
                    Application.get().updatePreviewPanel();
                    break;
                case 5:
                    entity.setSprScale(Float.parseFloat(alue));
                    Application.get().updatePreviewPanel();
                    break;
                case 6:
                    entity.setMoving(Boolean.parseBoolean(alue));
                    Application.get().updatePreviewPanel();
                    break;
                case 7:
                    entity.setMovingToX(Integer.parseInt(alue));
                    Application.get().updatePreviewPanel();
                    break;
                case 8:
                    entity.setMovingToY(Integer.parseInt(alue));
                    Application.get().updatePreviewPanel();
                    break;
            } 
        }
    }
}
