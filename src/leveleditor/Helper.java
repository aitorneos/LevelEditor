/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor;

import javax.swing.JOptionPane;

/**
 *
 * @author Francis
 */
class Helper {

    static void showError(String no_sprite_selected) {
        JOptionPane.showMessageDialog(null, no_sprite_selected, 
                "Error", JOptionPane.ERROR_MESSAGE);
    }
    
}
