/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import leveleditor.data.EntityPropertiesTableModel;
import leveleditor.data.Holder;
import leveleditor.data.LevelPropertiesTableModel;
import leveleditor.data.Sprite;
import leveleditor.data.SpritePropertiesTableModel;

/**
 *
 * @author franz
 */
public final class MainGUI extends javax.swing.JFrame {
    private final PreviewPanel previewPanel;
    private File currentDir;
    private PropertiesCellEditor propsEditor;

    /**
     * Creates new form MainGUI
     */
    public MainGUI() {
        initComponents();
        
        String cpath = Application.get().getCurrentDir();
        currentDir = new File(cpath==null?"":cpath);
        
        previewPanel = new PreviewPanel();
        previewPanel.setSelectionChangedListener(new SelectionChangedListener() {

            @Override
            public void selectionChanged(Entity entity) {
                entityChanged(entity);
            }
        });
        
        previewContainer.add(previewPanel);
        
        Application.get().setPreviewPanel(previewPanel);
        
        spritesTable.setModel(Application.get().getSpritesTableModel());
        spritesTable.getSelectionModel()
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        templatesTable.setModel(Application.get().getTemplatesTableModel());
        
        templatesTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                templatesTableClicked(e);
            }
        
        });
        
        outlinerList.setModel(Application.get().getOutlinerListModel());
        
        defaultPropTable();
        
        pack();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        spritesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (! e.getValueIsAdjusting()) {
                    onRowSelectionChanged();
                }
            }
        });
        
        outlinerList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (! e.getValueIsAdjusting()) {
                    if (outlinerList.getSelectedIndex() >= 0) {
                        previewPanel.setSelectedEntityIndex(outlinerList.getSelectedIndex());
                        updatePropertiesTableEntity();
                    }
                }
            }
        });
    }
    
    private void templatesTableClicked(MouseEvent e) {
        int r = templatesTable.rowAtPoint(e.getPoint());
        if (r >= 0 && r < templatesTable.getRowCount()) {
            templatesTable.setRowSelectionInterval(r, r);
        } else {
            templatesTable.clearSelection();
        }

        int rowindex = templatesTable.getSelectedRow();
        if (rowindex < 0 || Application.get().getTemplates().size() <= rowindex) {
            return;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            TemplatesPopupMenu popup = new TemplatesPopupMenu(
                    Application.get().getTemplates().get(rowindex));
            if (previewPanel.getSelectedEntity() != null) {
                popup.init(previewPanel.getSelectedEntity());
            } else {
                popup.init();
            }
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    
    private void entityChanged(Entity entity) {
        ListSelectionListener[] listeners = outlinerList.getListSelectionListeners();
        for (int i = 0; i < listeners.length; i ++) {
            outlinerList.removeListSelectionListener(listeners[i]);
        }
        if (entity != null) {
            outlinerList.setSelectedIndex(Application.get().getEntityHolder().indexOf(entity));
        } else {
            outlinerList.clearSelection();
        }
        for (int i = 0; i < listeners.length; i ++) {
            outlinerList.addListSelectionListener(listeners[i]);
        }
        updatePropertiesTableEntity();
    }
    
    private void updatePropertiesTableEntity() {
        if (propsEditor != null) {
            propsEditor.stopCellEditing();
        }
        
        Entity selected = previewPanel.getSelectedEntity();
        if (selected != null) {
            EntityPropertiesTableModel model = new EntityPropertiesTableModel(selected);
            propertiesTable.setModel(model);
            Application.get().setPropertiesModel(model);
        } else {
            defaultPropTable();
        }
    }
    
    private void updatePropertiesTable(Sprite current) {
        if (propsEditor != null) {
            propsEditor.stopCellEditing();
        } else {
            propsEditor = new PropertiesCellEditor();
        }
        
        SpritePropertiesTableModel model = new SpritePropertiesTableModel(current);
        Application.get().setPropertiesModel(model);
        propertiesTable.setModel(model);
        propertiesTable.getColumnModel().getColumn(1).setCellEditor(propsEditor);
        propertiesTable.getColumnModel().getColumn(1).setCellRenderer(new PropertiesCellRenderer());
    }
    
    public void defaultPropTable() {
        //Object[] columnNames = { "Properties are shown here" };
        //DefaultTableModel defTableModel = new DefaultTableModel(columnNames, 0);
        //propertiesTable.setModel(defTableModel);
        LevelPropertiesTableModel lprop = new LevelPropertiesTableModel(previewPanel.getLevelProperties());
        propertiesTable.setModel(lprop);
        Application.get().setPropertiesModel(null);
        
        spritesTable.clearSelection();
    }
    
    public void onRowSelectionChanged() {
        int i = spritesTable.getSelectedRow();
        if (i >= 0) {
            Sprite current = Application.get().getSpriteHolder().get(i);
            previewPane.removeAll();
            previewPane.add(current.getThumbnail());
            previewPane.repaint();

            updatePropertiesTable(current);

            validate();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btAddSprite = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btOpenLevel = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btSaveLevel = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btExportXML = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        ftfGridSize = new javax.swing.JFormattedTextField();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSlider2 = new javax.swing.JSlider();
        ftfZoom = new javax.swing.JFormattedTextField();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btAbout = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        previewContainer = new javax.swing.JPanel();
        outlinerContainer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        outlinerList = new javax.swing.JList();
        tableContainer = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        propertiesTable = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane4 = new javax.swing.JSplitPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        spritesTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        templatesTable = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        previewPane = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btAdd = new javax.swing.JButton();
        btTemplateAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AndEngine Level Editor");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                onWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        btAddSprite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/leveleditor/gfx/fileopen.png"))); // NOI18N
        btAddSprite.setText("Add Sprite");
        btAddSprite.setFocusable(false);
        btAddSprite.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btAddSprite.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btAddSprite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onAddSprite(evt);
            }
        });
        jToolBar1.add(btAddSprite);
        jToolBar1.add(jSeparator3);

        btOpenLevel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/leveleditor/gfx/fileopen.png"))); // NOI18N
        btOpenLevel.setText("Open Level");
        btOpenLevel.setFocusable(false);
        btOpenLevel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btOpenLevel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btOpenLevel);
        jToolBar1.add(jSeparator2);

        btSaveLevel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/leveleditor/gfx/save.png"))); // NOI18N
        btSaveLevel.setText("Save Level");
        btSaveLevel.setFocusable(false);
        btSaveLevel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btSaveLevel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btSaveLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSave(evt);
            }
        });
        jToolBar1.add(btSaveLevel);
        jToolBar1.add(jSeparator6);

        btExportXML.setIcon(new javax.swing.ImageIcon(getClass().getResource("/leveleditor/gfx/xmlexport.png"))); // NOI18N
        btExportXML.setText("Export XML");
        btExportXML.setFocusable(false);
        btExportXML.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btExportXML.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btExportXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onExportXML(evt);
            }
        });
        jToolBar1.add(btExportXML);
        jToolBar1.add(jSeparator1);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Grid Size");
        jPanel3.add(jLabel1, java.awt.BorderLayout.NORTH);

        jSlider1.setToolTipText("");
        jPanel3.add(jSlider1, java.awt.BorderLayout.CENTER);

        ftfGridSize.setEditable(false);
        ftfGridSize.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jPanel3.add(ftfGridSize, java.awt.BorderLayout.PAGE_END);

        jToolBar1.add(jPanel3);
        jToolBar1.add(jSeparator4);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("Zoom");
        jPanel4.add(jLabel2, java.awt.BorderLayout.NORTH);
        jPanel4.add(jSlider2, java.awt.BorderLayout.CENTER);

        ftfZoom.setEditable(false);
        jPanel4.add(ftfZoom, java.awt.BorderLayout.PAGE_END);

        jToolBar1.add(jPanel4);
        jToolBar1.add(jSeparator5);

        btAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/leveleditor/gfx/about.png"))); // NOI18N
        btAbout.setText("About");
        btAbout.setFocusable(false);
        btAbout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btAbout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onAbout(evt);
            }
        });
        jToolBar1.add(btAbout);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setResizeWeight(0.7);

        jSplitPane2.setMinimumSize(new java.awt.Dimension(800, 48));
        jSplitPane2.setPreferredSize(new java.awt.Dimension(900, 203));

        previewContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview"));
        previewContainer.setMinimumSize(new java.awt.Dimension(400, 23));
        previewContainer.setPreferredSize(new java.awt.Dimension(400, 23));
        previewContainer.setLayout(new java.awt.BorderLayout());
        jSplitPane2.setRightComponent(previewContainer);

        outlinerContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Outliner"));
        outlinerContainer.setName(""); // NOI18N
        outlinerContainer.setPreferredSize(new java.awt.Dimension(240, 201));
        outlinerContainer.setLayout(new java.awt.BorderLayout());

        outlinerList.setBackground(new java.awt.Color(240, 240, 240));
        outlinerList.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        outlinerList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Entity 1", "Entity 2", "Entity 3", "Entity 4", "Entity 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        outlinerList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        outlinerList.setPreferredSize(new java.awt.Dimension(200, 110));
        jScrollPane1.setViewportView(outlinerList);

        outlinerContainer.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane2.setLeftComponent(outlinerContainer);

        jSplitPane1.setLeftComponent(jSplitPane2);

        tableContainer.setPreferredSize(new java.awt.Dimension(300, 850));
        tableContainer.setLayout(new java.awt.BorderLayout());

        jSplitPane3.setDividerSize(8);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setResizeWeight(0.8);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Properties"));
        jPanel5.setLayout(new java.awt.BorderLayout());

        propertiesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11"
            }
        ));
        propertiesTable.setFillsViewportHeight(true);
        jScrollPane4.setViewportView(propertiesTable);

        jPanel5.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jSplitPane3.setBottomComponent(jPanel5);

        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane4.setResizeWeight(0.8);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder("Sprites"));
        jScrollPane5.setPreferredSize(new java.awt.Dimension(400, 250));

        spritesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spritesTable.setFillsViewportHeight(true);
        spritesTable.setPreferredSize(new java.awt.Dimension(200, 64));
        jScrollPane5.setViewportView(spritesTable);

        jSplitPane4.setTopComponent(jScrollPane5);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Templates"));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(452, 100));

        templatesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        templatesTable.setFillsViewportHeight(true);
        templatesTable.setMinimumSize(new java.awt.Dimension(60, 60));
        templatesTable.setPreferredSize(new java.awt.Dimension(300, 60));
        jScrollPane2.setViewportView(templatesTable);

        jPanel6.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jSplitPane4.setBottomComponent(jPanel6);

        jPanel2.add(jSplitPane4, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel8.setLayout(new java.awt.BorderLayout());

        previewPane.setMinimumSize(new java.awt.Dimension(100, 100));
        previewPane.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel8.add(previewPane, java.awt.BorderLayout.CENTER);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("actions"));
        jPanel9.setLayout(new java.awt.GridLayout(2, 1, 5, 5));

        btAdd.setText("add sprite");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onAdd(evt);
            }
        });
        jPanel9.add(btAdd);

        btTemplateAdd.setText("add template");
        btTemplateAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onAddTemplate(evt);
            }
        });
        jPanel9.add(btTemplateAdd);

        jPanel8.add(jPanel9, java.awt.BorderLayout.SOUTH);

        jPanel7.add(jPanel8, java.awt.BorderLayout.EAST);

        jSplitPane3.setTopComponent(jPanel7);

        tableContainer.add(jSplitPane3, java.awt.BorderLayout.PAGE_END);

        jSplitPane1.setRightComponent(tableContainer);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onAddSprite(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onAddSprite
        JFileChooser chooser = new JFileChooser(currentDir);
        ImagePreviewPanel preview = new ImagePreviewPanel();
        
        chooser.setAccessory(preview);
        chooser.addPropertyChangeListener(preview);
        
        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".png") || file.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Images (*.png)";
            }
        });
        chooser.setMultiSelectionEnabled(true);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File[] file = chooser.getSelectedFiles();
                
                currentDir = file[0].getParentFile();
                for (int i = 0; i < file.length; i ++) {
                    Application.get().addSprite(file[i]);
                    Application.get().setCurrentDir(currentDir); 
                }
            } catch (IOException ex) {
                
            }
        }
    }//GEN-LAST:event_onAddSprite

    private void onAdd(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onAdd
        int selsprite = spritesTable.getSelectedRow();
        if (selsprite < 0) {
            Helper.showError("no sprite selected");
        }
        else {
            previewPanel.addEntity(Application.get().getSpriteHolder().get(selsprite));
        }
    }//GEN-LAST:event_onAdd

    private void onWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_onWindowClosing
        try {
            Application.get().saveProperties();
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_onWindowClosing

    private void onAbout(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onAbout
        throw new UnsupportedOperationException("Not yet implemented");
    }//GEN-LAST:event_onAbout

    private void onSave(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSave
        
    }//GEN-LAST:event_onSave

    private void onExportXML(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onExportXML
        SwingWorker<Boolean, Integer> saveWorker = new SwingWorker<Boolean, Integer>() {

            @Override
            protected Boolean doInBackground() throws Exception {
                Holder<Entity> entities = Application.get().getEntityHolder();
                StringBuilder xmlText = new StringBuilder();
                xmlText.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
                xmlText.append("<level width=\"").append(previewPanel.getLevelProperties().getWidth())
                        .append("\" height=\"").append(previewPanel.getLevelProperties().getHeight())
                        .append("\">\n");

                for (Entity e : entities.getList()) {
                    xmlText.append("\t");
                    xmlText.append("<").append("entity");
                    EntityPropertiesTableModel temptb = new EntityPropertiesTableModel(e);
                    for (int i = 1; i < temptb.getRowCount(); i++) {
                        xmlText.append(" ")
                                .append(((String)temptb.getValueAt(i, 0)).replace(' ', '_'))
                                .append("=\"")
                                .append(temptb.getValueAt(i, 1))
                                .append("\" ");
                    }
                    xmlText.append("/>\n");
                }
                xmlText.append("</level>");
                
                JFileChooser saveDialog = new JFileChooser(currentDir);
                if (saveDialog.showSaveDialog(MainGUI.this) == JFileChooser.APPROVE_OPTION) {
                    File f = saveDialog.getSelectedFile();
                    String path = f.getPath();

                    if (!path.toLowerCase().endsWith(".xml")) {
                        f = new File(path + ".xml");
                    }

                    try (FileWriter fout = new FileWriter(f)) {
                        fout.write(xmlText.toString());
                    }
                }
                return true;
            }

            @Override
            protected void process(List<Integer> chunks) {
                super.process(chunks);
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        JOptionPane.showMessageDialog(MainGUI.this, "save successful");
                    }
                } catch (Exception e) {
                    Helper.showError(e.toString());
                }
            }
        };
        saveWorker.execute();
    }//GEN-LAST:event_onExportXML

    private void onAddTemplate(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onAddTemplate
        
    }//GEN-LAST:event_onAddTemplate

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Application.get().loadProperties();
                } catch (Exception ex) {
                    Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                MainGUI gui = new MainGUI();
                Application.get().setGui(gui);
                gui.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAbout;
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btAddSprite;
    private javax.swing.JButton btExportXML;
    private javax.swing.JButton btOpenLevel;
    private javax.swing.JButton btSaveLevel;
    private javax.swing.JButton btTemplateAdd;
    private javax.swing.JFormattedTextField ftfGridSize;
    private javax.swing.JFormattedTextField ftfZoom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel outlinerContainer;
    private javax.swing.JList outlinerList;
    private javax.swing.JPanel previewContainer;
    private javax.swing.JPanel previewPane;
    private javax.swing.JTable propertiesTable;
    private javax.swing.JTable spritesTable;
    private javax.swing.JPanel tableContainer;
    private javax.swing.JTable templatesTable;
    // End of variables declaration//GEN-END:variables

    public void updateSpritePreview() {
        previewPane.repaint();
    }
}
