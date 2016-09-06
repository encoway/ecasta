package com.encoway.ecasta.commons.dialogs;

import com.encoway.ecasta.commons.utils.PathConstants;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Implements a WebView to show the test results.
 * 
 * @author azzouz
 */
public class WebViewImpl extends Region {

    final WebView webView;
    final WebEngine webEngine;

    public WebViewImpl() throws MalformedURLException {
        webView = new WebView();
        webEngine = webView.getEngine();
        URL url = (new File(PathConstants.TEST_RESULT_PATH)).toURI().toURL();
        webEngine.load(url.toExternalForm());
        getChildren().add(webView);
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(webView, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
    }
}
