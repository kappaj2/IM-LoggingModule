package za.co.ajk.logging.cucumber.stepdefs;

import za.co.ajk.logging.LoggingModuleApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = LoggingModuleApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
