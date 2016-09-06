package com.encoway.ecasta.commons.controller;

import javafx.scene.Node;

/**
 * Abstract class for controller.
 */
public abstract class AbstractController {

    private Node view;

    public Node getView() {
        return view;
    }

    public void setView(Node view) {
        this.view = view;
    }

}
