/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Francis
 */
public class EntityPopupMenu extends JPopupMenu {
    private final Entity entity;

    public EntityPopupMenu(Entity entity) {
        super();
        this.entity = entity;
    }
    
    public void init() {
        JMenuItem del = new JMenuItem("delete " + entity.getName());
        del.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEntity();
            }
        });
        add(del);
    }
    
    private void deleteEntity() {
        Application.get().removeEntity(entity);
    }
}
