package com.encoway.ecasta.commons.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.encoway.ecasta.commons.dialogs.ConfirmationAlert;
import com.encoway.ecasta.commons.events.ConfirmationDoneEvent;
import com.encoway.ecasta.commons.events.ShowConfirmationDialogEvent;
import com.encoway.ecasta.commons.utils.DialogConstants;
import com.encoway.ecasta.commons.utils.DialogFactory;
import com.encoway.ecasta.commons.utils.LanguageHandler;

import java.util.UUID;

import com.google.common.eventbus.EventBus;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

@RunWith(MockitoJUnitRunner.class)
public class DeleteConfirmationDialogViewControllerTest {

    @Mock
    private EventBus eventBus;

    @Mock
    private Stage stage;

    @Mock
    private DialogFactory dialogFactory;

    @Mock
    private LanguageHandler languageHandler;

    @InjectMocks
    private DeleteConfirmationDialogViewController classUnderTest;

    @Test
    public void testInit() throws Exception {
        classUnderTest.init();
        verify(eventBus).register(classUnderTest);
    }

    @Test
    public void testHandleShowConfirmationDialogForFeature() throws Exception {
        ShowConfirmationDialogEvent event = mock(ShowConfirmationDialogEvent.class);
        UUID id = PowerMockito.mock(UUID.class);
        when(event.getItem()).thenReturn(id);
        when(event.getName()).thenReturn("test.feature");
        ConfirmationAlert view = mock(ConfirmationAlert.class);

        when(languageHandler.getMessage(DialogConstants.FEATURE_TITLE_TEXT)).thenReturn("Feature title");
        when(languageHandler.getMessage(DialogConstants.FEATURE_HEADER_TEXT)).thenReturn("Feature Header Text");
        PowerMockito.when(dialogFactory.createConfirmDialog()).thenReturn(view);
        classUnderTest.handleShowConfirmationDialog(event);

        verify(view).initDialog(stage, "Feature title", "Feature Header Text", "");
    }

    @Test
    public void testHandleShowConfirmationDialogForTestsystem() throws Exception {
        ShowConfirmationDialogEvent event = mock(ShowConfirmationDialogEvent.class);
        UUID id = PowerMockito.mock(UUID.class);
        ConfirmationAlert view = mock(ConfirmationAlert.class);

        when(event.getItem()).thenReturn(id);
        when(event.getName()).thenReturn("test.json");
        when(languageHandler.getMessage(DialogConstants.TESTSYSTEM_TITLE_TEXT)).thenReturn("Testsystem title");
        when(languageHandler.getMessage(DialogConstants.TESTSYSTEM_HEADER_TEXT)).thenReturn("Testsystem \"%s\" Text");
        when(languageHandler.getMessage(DialogConstants.TESTSYSTEM_CONTENT_TEXT)).thenReturn("Testsystem content");

        PowerMockito.when(dialogFactory.createConfirmDialog()).thenReturn(view);
        classUnderTest.handleShowConfirmationDialog(event);

        verify(view).initDialog(stage, "Testsystem title", "Testsystem \"test.json\" Text", "Testsystem content");
    }

    @Test
    public void testOkButtonClicked() throws Exception {
        classUnderTest.okButtonClicked();
        verify(eventBus).post(Matchers.any(ConfirmationDoneEvent.class));
    }
}
