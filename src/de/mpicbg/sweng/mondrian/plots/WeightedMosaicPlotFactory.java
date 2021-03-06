package de.mpicbg.sweng.mondrian.plots;

import de.mpicbg.sweng.mondrian.MFrame;
import de.mpicbg.sweng.mondrian.Mondrian;
import de.mpicbg.sweng.mondrian.core.AbstractPlotFactory;
import de.mpicbg.sweng.mondrian.core.DataSet;
import de.mpicbg.sweng.mondrian.core.Table;
import de.mpicbg.sweng.mondrian.ui.PlotPanel;
import de.mpicbg.sweng.mondrian.util.Utils;
import de.mpicbg.sweng.mondrian.util.WeightCaclulator;

import javax.swing.*;


/**
 * Document me!
 *
 * @author Holger Brandl
 */
public class WeightedMosaicPlotFactory extends AbstractPlotFactory {

    public String getPlotName() {
        return "Weighted Mosaic Plot";
    }


    public String getDescription() {
        return "A weighted mosaic plot";
    }


    public PlotPanel createPlotPanel(Mondrian mondrian, MFrame plotFrame, DataSet dataSet, JList varNames) {
        int k = (varNames.getSelectedIndices()).length;
        int[] passBuffer = new int[k];
        for (int i = 0; i < k; i++)
            passBuffer[i] = mondrian.getSelector().selectBuffer[k - i - 1];

        int[] vars = WeightCaclulator.getWeightVariable(passBuffer, dataSet, mondrian.calcNumCategoricalVars(), mondrian.determineWeightIndex(), null, varNames);
        int[] passed = new int[vars.length - 1];
        System.arraycopy(vars, 0, passed, 0, vars.length - 1);
        int weight = vars[vars.length - 1];

        Table breakdown = dataSet.breakDown(dataSet.setName, passed, weight);
        for (int i = 0; i < passed.length - 1; i++)
            breakdown.addInteraction(new int[]{i}, false);

        breakdown.addInteraction(new int[]{passed.length - 1}, true);

        plotFrame.setSize(400, 400);
        plotFrame.setLocation(Utils.genRandomLoacation(plotFrame));

        return new MosaicPlot(plotFrame, 400, 400, breakdown);

        // todo reenable this
//                if (modelNavigator == null)
//            modelNavigator = new ModelNavigator();
//        plotw.addModelListener(modelNavigator);
//        modelNavigatorButton.setEnabled(true);
    }


    public boolean isCompliant(DataSet dataSet, int numVariables, int numCategoricalVariables) {
        return numVariables > 2 && numVariables == numCategoricalVariables;
    }
}