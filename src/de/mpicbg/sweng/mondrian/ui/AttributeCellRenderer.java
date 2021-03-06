package de.mpicbg.sweng.mondrian.ui;

import de.mpicbg.sweng.mondrian.core.DataSet;
import de.mpicbg.sweng.mondrian.util.Utils;

import javax.swing.*;
import java.awt.*;


/**
 * Document me!
 *
 * @author Holger Brandl
 */
public class AttributeCellRenderer extends JLabel implements ListCellRenderer {

    final DataSet data;

    final ImageIcon alphaIcon = new ImageIcon(Utils.readGif("/alpha.gif"));
    final ImageIcon alphaMissIcon = new ImageIcon(Utils.readGif("/alpha-miss.gif"));
    final ImageIcon catIcon = new ImageIcon(Utils.readGif("/cat.gif"));
    final ImageIcon catMissIcon = new ImageIcon(Utils.readGif("/cat-miss.gif"));
    final ImageIcon numIcon = new ImageIcon(Utils.readGif("/num.gif"));
    final ImageIcon numMissIcon = new ImageIcon(Utils.readGif("/num-miss.gif"));


    public AttributeCellRenderer(DataSet data) {
        this.data = data;
    }


    public Component getListCellRendererComponent(JList list,
                                                  Object value,            // value to display
                                                  int index,               // cell index
                                                  boolean isSelected,      // is the cell selected
                                                  boolean cellHasFocus)    // the list and the cell have the focus
    {
        String s = value.toString();
        setText(s);
        if (data.alpha(index))
            if (data.getN(index) == data.n)
                setIcon(alphaIcon);
            else
                setIcon(alphaMissIcon);
        else if (data.categorical(index))
            if (data.getN(index) == data.n)
                setIcon(catIcon);
            else
                setIcon(catMissIcon);
        else if (data.getN(index) == data.n)
            setIcon(numIcon);
        else
            setIcon(numMissIcon);
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}
