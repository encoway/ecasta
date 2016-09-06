package com.encoway.ecasta.systems.utils;

import com.encoway.ecasta.commons.utils.ContextHolder;
import com.encoway.ecasta.systems.Testsystem;
import com.encoway.ecasta.systems.controller.NavigationViewController;
import com.encoway.ecasta.systems.custom.ui.TestsystemListCell;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * The factory for all {@link Testsystem} listView cells.
 */
public class TestsystemListViewCellFactory implements Callback<ListView<ListCell>, ListCell<Testsystem>> {

    @Override
    public ListCell<Testsystem> call(ListView<ListCell> param) {
        return new TestsystemListCell(ContextHolder.getContext().getBean(NavigationViewController.class));
    }

}
