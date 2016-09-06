package com.encoway.ecasta;

import com.encoway.ecasta.systems.controller.TabContentViewController;

import com.google.common.eventbus.EventBus;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class.
 */
@Configuration
@ComponentScan(basePackages = { "com.encoway.ecasta" })
public class TestsuiteConfiguration {

    @Bean
    public EventBus eventBus() {
        return new EventBus();
    }

    @Bean
    public Stage stage() {
        return new Stage();
    }

    @Bean
    public TabContentViewController tabContentViewController() {
        return new TabContentViewController();
    }

}
