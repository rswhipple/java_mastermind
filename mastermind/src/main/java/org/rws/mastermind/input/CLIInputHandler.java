package org.rws.mastermind.input;

import org.rws.mastermind.interfaces.InputHandler;

import java.util.Scanner;

public class CLIInputHandler implements InputHandler {
    private Scanner scanner;

    public CLIInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }
}