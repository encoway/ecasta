package com.encoway.ecasta.systems.custom.ui;

import com.encoway.ecasta.commons.utils.ContextHolder;
import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.controller.NavigationViewController;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Factory for Testsystem listView.
 */
public class TestsystemListViewCellFactory implements Callback<ListView<ListCell>, ListCell<Testsystem>> {

    @Override
    public ListCell<Testsystem> call(ListView<ListCell> param) {
        return new TestsystemListCell(ContextHolder.getContext().getBean(NavigationViewController.class));
    }

}
