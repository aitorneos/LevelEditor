/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor.data;

import javax.swing.AbstractListModel;
import leveleditor.Entity;

/**
 *
 * @author Francis
 */
public class OutlinerListModel extends AbstractListModel<String> {
    private Holder<Entity> entityHolder;

    @Override
    public int getSize() {
        return entityHolder.size();
    }

    @Override
    public String getElementAt(int index) {
        return entityHolder.get(index).getName();
    }

    public void setEntityHolder(Holder<Entity> entityHolder) {
        this.entityHolder = entityHolder;
    }

    @Override
    public void fireContentsChanged(Object source, int index0, int index1) {
        super.fireContentsChanged(source, index0, index1);
    }

    @Override
    public void fireIntervalAdded(Object source, int index0, int index1) {
        super.fireIntervalAdded(source, index0, index1);
    }

    @Override
    public void fireIntervalRemoved(Object source, int index0, int index1) {
        super.fireIntervalRemoved(source, index0, index1);
    }
    
}
