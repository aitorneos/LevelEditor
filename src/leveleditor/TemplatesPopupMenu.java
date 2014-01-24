/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import leveleditor.data.Pair;

/**
 *
 * @author Francis
 */
public class TemplatesPopupMenu extends JPopupMenu {
    private final Pair<String, String> template;
    private Entity entity;

    public TemplatesPopupMenu(Pair<String, String> template) {
        super();
        this.template = template;
    }
    
    private void addDel() {
        JMenuItem del = new JMenuItem("delete template");
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTemplate();
            }
        });
        add(del);
    }
    
    private void addUse() {
        JMenuItem use = new JMenuItem("use template");
        use.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                useTemplate();
            }
        });
        add(use);
    }

    public void init() {
        addDel();
    }
    
    public void init(Entity entity) {
        this.entity = entity;
        init();
        addUse();
    }
    
    private void deleteTemplate() {
        Application.get().removeTemplate(template);
    }
    
    private void useTemplate() {
        if (entity != null) {
            entity.getCustomProperties().add(template);
            Application.get().getPropertiesModel().fireTableDataChanged();
        }
    }
}
