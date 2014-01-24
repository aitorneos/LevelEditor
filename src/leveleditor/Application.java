/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.table.AbstractTableModel;
import leveleditor.data.EntityPropertiesTableModel;
import leveleditor.data.Holder;
import leveleditor.data.OutlinerListModel;
import leveleditor.data.Pair;
import leveleditor.data.Sprite;
import leveleditor.data.SpritesTableModel;
import leveleditor.data.TemplatesTableModel;

/**
 *
 * @author franz
 */
public class Application {
    private final Holder<Sprite> spriteHolder = new Holder<>();
    private final Holder<Entity> entityHolder = new Holder<>();
    private final Holder<Pair<String, String>> templates = new Holder<>();
    
    private final SpritesTableModel spritesTableModel = new SpritesTableModel();
    private final OutlinerListModel outlinerListModel = new OutlinerListModel();
    private final TemplatesTableModel templatesTableModel = new TemplatesTableModel(templates);
    
    private AbstractTableModel propertiesModel;
    
    private PreviewPanel previewPanel;
    private MainGUI gui;
    
    private static Application app;
    private String currentDir;
    
    private Application() {
        spritesTableModel.setSpriteHolder(spriteHolder);
        outlinerListModel.setEntityHolder(entityHolder);
    }
    
    static {
        app = new Application();
    }
    
    public static Application get() {
        return app;
    }

    public Holder<Sprite> getSpriteHolder() {
        return spriteHolder;
    }

    public SpritesTableModel getSpritesTableModel() {
        return spritesTableModel;
    }

    public Holder<Entity> getEntityHolder() {
        return entityHolder;
    }

    public OutlinerListModel getOutlinerListModel() {
        return outlinerListModel;
    }

    public AbstractTableModel getPropertiesModel() {
        return propertiesModel;
    }

    public void setPropertiesModel(AbstractTableModel propertiesModel) {
        this.propertiesModel = propertiesModel;
    }

    public void addSprite(File file) throws IOException {
        spriteHolder.add(new Sprite(file));
        spritesTableModel.fireTableRowsInserted(spriteHolder.size() - 1,
                spriteHolder.size() - 1);
    }

    public void addEntity(Entity e) {
        entityHolder.add(e);
        outlinerListModel.fireIntervalAdded(outlinerListModel, 
                entityHolder.size() - 1, entityHolder.size() - 1);
    }
    
    public void removeEntity(Entity e) {
        int i = entityHolder.indexOf(e);
        entityHolder.remove(e);
        outlinerListModel.fireIntervalRemoved(outlinerListModel, i, i);
        gui.defaultPropTable();
        previewPanel.repaint();
    }
    
    public void updateEntityProperties() {
        if (propertiesModel instanceof EntityPropertiesTableModel) {
            ((EntityPropertiesTableModel) propertiesModel).fireTableDataChanged();
        }
    }

    public void updateOutliner(Entity entity) {
        outlinerListModel.fireContentsChanged(outlinerListModel, 
                entityHolder.indexOf(entity), entityHolder.indexOf(entity));
    }

    public void updatePreviewPanel() {
        previewPanel.repaint();
    }

    public void setPreviewPanel(PreviewPanel previewPanel) {
        this.previewPanel = previewPanel;
    }

    public MainGUI getGui() {
        return gui;
    }

    public void setGui(MainGUI gui) {
        this.gui = gui;
    }

    public Holder<Pair<String, String>> getTemplates() {
        return templates;
    }

    public TemplatesTableModel getTemplatesTableModel() {
        return templatesTableModel;
    }

    public void loadProperties() throws FileNotFoundException, IOException {
        Properties props = new Properties();
        props.loadFromXML(new BufferedInputStream(new FileInputStream("preferences.xml")));
        currentDir = props.getProperty("CURRENT_DIR");
    }
    
    public void saveProperties() throws FileNotFoundException, IOException {
        Properties props = new Properties();
        
        props.setProperty("CURRENT_DIR", currentDir);
        props.storeToXML(new BufferedOutputStream(new FileOutputStream("preferences.xml")), null);
    }

    public void setCurrentDir(File currentDir) {
        this.currentDir = currentDir.getPath();
    }
    
    public String getCurrentDir() {
        return currentDir;
    }

    void removeTemplate(Pair<String, String> template) {
        int index = templates.indexOf(template);
        templates.remove(template);
        templatesTableModel.fireTableRowsDeleted(index, index);
    }
}
