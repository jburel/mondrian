package de.mpicbg.sweng.mondrian.ui;

import de.mpicbg.sweng.mondrian.MFrame;
import de.mpicbg.sweng.mondrian.MonFrame;
import de.mpicbg.sweng.mondrian.core.DragBox;
import de.mpicbg.sweng.mondrian.core.PlotFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


/**
 * Document me!
 *
 * @author Holger Brandl
 */
public class PlotAction extends AbstractAction {

    private PlotFactory plotFactory;
    private MonFrame monFrame;


    public PlotAction(PlotFactory plotFactory, MonFrame monFrame) {
        this.plotFactory = plotFactory;
        this.monFrame = monFrame;

        // actions are enabled if the user changes the variable selection
        setEnabled(false);

        // configure the action
        putValue(NAME, plotFactory.getPlotName()); //$NON-NLS-1$
//        putValue(SMALL_ICON, new ScaleableIcon("/resources/icons/category_add.png"));
        putValue(SHORT_DESCRIPTION, plotFactory.getShortDescription());
//        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
//        putValue(MNEMONIC_KEY, 2);
    }


    public void actionPerformed(ActionEvent e) {
        // this was done for each plot in the originial version; why is not clear yet
        monFrame.checkHistoryBuffer();

        final MFrame plotFrame = new MFrame(monFrame);
        Font SF = new Font("SansSerif", Font.PLAIN, 11);
        plotFrame.setFont(SF);

        PlotPanel plotPanel = plotFactory.createPlotPanel(monFrame, plotFrame, monFrame.dataSets.elementAt(monFrame.dataSetCounter), monFrame.varNames);


        if (plotPanel != null) {
            for (PlotPanel plot : plotPanel.getPlots()) {
                plot.addDataListener(monFrame);
                plot.addSelectionListener(monFrame);

                monFrame.plots.addElement((DragBox) plot);
            }
            plotFrame.getContentPane().add(plotPanel);

            plotFrame.setTitle(plotPanel.getName() != null ? plotPanel.getName() : plotFactory.getPlotName());
            plotFrame.setSize(plotPanel.getMinimumSize());
            plotFrame.setVisible(true);
        }

        // what is this
        monFrame.varNames = null;
        monFrame.setVarList();
    }


    /**
     * Invoked to match the menu-options to the current variable selection.
     */
    public void configureForVarSelection(int numVariables, int numCategoricalVariables) {
        setEnabled(plotFactory.isCompliant(numVariables, numCategoricalVariables));
    }
}