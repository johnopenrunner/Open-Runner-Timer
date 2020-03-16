package org.open;

import org.open.runner.timer.MainApplication;

import javafx.application.Application;

/**
 * Main entry point for the OpenRunnerTimer application.
 */
public class OpenRunner {

    public static void main(String[] args) {
        new Thread(() -> Application.launch(MainApplication.class)).start();
    }
}