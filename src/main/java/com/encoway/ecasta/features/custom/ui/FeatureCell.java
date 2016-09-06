package com.encoway.ecasta.features.custom.ui;

import com.encoway.ecasta.features.Feature;
import com.encoway.ecasta.systems.controller.TabContentViewController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Custom-cell of the ListView at the NavigationView.
 * 
 * @author azzouz
 */
public class FeatureCell extends ListCell<Feature> {

    private HBox hbox = new HBox();
    private Label label = new Label("empty");
    private Pane pane = new Pane();
    private Button deleteButton;
    private Feature lastItem;
    private TabContentViewController listener;
    private CheckBox checkBox;

    public FeatureCell(TabContentViewController listener) {
        super();
        this.listener = listener;
        initDeleteButton();
        initCheckBox();
        hbox.getChildren().addAll(checkBox, label, pane, deleteButton);
        HBox.setHgrow(pane, Priority.ALWAYS);
    }

    private void initCheckBox() {
        checkBox = new CheckBox();
        checkBox.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                listener.checkBoxClicked(lastItem);
            }
        });
    }

    private void initDeleteButton() {
        deleteButton = new ImageButton("icons/trash.png");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                listener.deleteButtonClicked(lastItem);
            }
        });

    }

    @Override
    protected void updateItem(Feature item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            if (item.isSelected()) {
                checkBox.setSelected(true);
            }
            lastItem = item;
            label.setText(item.getName() != null ? item.getName() : "<null>");
            setGraphic(hbox);
        }
    }
}
