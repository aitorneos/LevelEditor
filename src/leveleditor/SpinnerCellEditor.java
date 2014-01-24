package leveleditor;


import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class SpinnerCellEditor extends DefaultCellEditor
{
    private JSpinner spinner;

    public SpinnerCellEditor()
    {
    	super( new JTextField() );
    	spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 5));
    	spinner.setBorder( null );
    }

    @Override
    public Component getTableCellEditorComponent(
    	JTable table, Object value, boolean isSelected, int row, int column)
    {
    	spinner.setValue( value );
    	return spinner;
    }

    @Override
    public Object getCellEditorValue()
    {
    	return spinner.getValue();
    }
}