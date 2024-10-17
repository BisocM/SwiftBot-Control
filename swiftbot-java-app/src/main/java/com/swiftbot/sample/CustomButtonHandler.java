package com.swiftbot.sample;

import com.swiftbot.ButtonListener;

public class CustomButtonHandler implements ButtonListener {
    @Override
    public void onButtonPressed(int buttonId) {
        System.out.println("Button pressed: " + buttonId);
    }

    @Override
    public void onButtonReleased(int buttonId) {
        System.out.println("Button released: " + buttonId);
    }
}
