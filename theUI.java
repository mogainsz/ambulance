import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

class arrowUI extends BasicComboBoxUI {

	    public static ComboBoxUI createUI(JComponent c) {
	        return new arrowUI();
	    }

	    @Override protected JButton createArrowButton() {
	        return new BasicArrowButton(
	            BasicArrowButton.SOUTH,
	            Color.darkGray, Color.black,
	            Color.black, Color.black);
	    }
	}

