package com.dalonedrow.module.barbarianprince.systems;

import java.util.Scanner;

import com.dalonedrow.module.barbarianprince.ui.console.WelcomeScreen;
import com.dalonedrow.rpg.base.consoleui.ConsoleView;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public final class ConsoleInterface {
    /** the one and only instance of the <code>ConsoleInterface</code> class. */
    private static ConsoleInterface instance;
    /**
     * Gives access to the singleton instance of {@link ConsoleInterface}.
     * @return {@link ConsoleInterface}
     */
    public static ConsoleInterface getInstance() {
        if (ConsoleInterface.instance == null) {
            ConsoleInterface.instance = new ConsoleInterface();
        }
        return ConsoleInterface.instance;
    }
    /** the current view. */
    private ConsoleView   currentView;
    /** flag indicating debugging is on. */
    private boolean       debug;
    /** the system scanner. */
    private final Scanner scanIn = new Scanner(System.in);
    /**
     * Gets the system scanner.
     * @return {@link Scanner}
     */
    public Scanner getScanIn() {
        return scanIn;
    }
    /**
     * Prepares the {@link View} for the rendering loop.
     * @throws RPGException if an error occurs
     */
    public void prepareForRendering() throws RPGException {
        if (debug) {
            System.out.println("rendering "
                    + GameState.getInstance().getCurrentState());
        }
        switch (GameState.getInstance().getCurrentState()) {
            case GameState.STATE_00_INITIALIZING:
                WelcomeScreen.getInstance().render();
                break;
            case GameState.STATE_01_IN_PLAY:
                //CharacterSelectionScreen.getInstance().render();
                break;
        }
    }
}
