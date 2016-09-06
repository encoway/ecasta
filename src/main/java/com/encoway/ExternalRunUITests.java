package com.encoway;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Runner class.
 * 
 * @author azzouz
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:lib/testsysteme/features/test-result" }, features = { "lib/testsysteme/features" })
public class ExternalRunUITests {
}
