package com.encoway.ecasta.features.custom.ui;

import com.encoway.ecasta.commons.utils.ContextHolder;
import com.encoway.ecasta.features.Feature;
import com.encoway.ecasta.systems.controller.TabsViewController;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Factory for feature listView cells.
 * 
 * @author azzouz
 */
public class FeatureListViewCellFactory implements Callback<ListView<ListCell>, ListCell<Feature>> {

    @Override
    public ListCell<Feature> call(ListView<ListCell> param) {
        TabsViewController controller = ContextHolder.getContext().getBean(TabsViewController.class);
        return new FeatureCell(controller.getCurrent());
    }

}
