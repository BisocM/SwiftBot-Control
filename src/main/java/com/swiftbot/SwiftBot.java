package com.swiftbot;

import com.swiftbot.NativeBindings;

public class SwiftBot {
    //Constants for buttons
    public static final int BUTTON_A = 0;
    public static final int BUTTON_B = 1;
    public static final int BUTTON_X = 2;
    public static final int BUTTON_Y = 3;

    //Motor control methods
    public void forward(double speed) {
        NativeBindings.forward(speed);
    }

    public void backward(double speed) {
        NativeBindings.backward(speed);
    }

    public void turnLeft(double speed) {
        NativeBindings.turnLeft(speed);
    }

    public void turnRight(double speed) {
        NativeBindings.turnRight(speed);
    }

    public void stop() {
        NativeBindings.stop();
    }

    public void setMotorSpeeds(double leftSpeed, double rightSpeed) { NativeBindings.setMotorSpeeds(leftSpeed, rightSpeed); }

    //Sensor reading
    public double readDistance() {
        return NativeBindings.readDistance();
    }

    //Button interaction
    public boolean isButtonPressed(int buttonId) {
        return NativeBindings.readButton(buttonId);
    }

    //LED control
    public void setButtonLed(int buttonLedId, double value) {
        NativeBindings.setButtonLed(buttonLedId, value);
    }

    //SN3218 control
    public void setUnderlight(int lightId, int red, int green, int blue) { NativeBindings.setUnderlight(lightId, red, green, blue); }
    public void fillUnderlighting(int red, int green, int blue) { NativeBindings.fillUnderlighting(red, green, blue); }
    public void clearUnderlighting() { NativeBindings.clearUnderlighting(); }

    //Camera capture
    public byte[] captureImage() { return NativeBindings.captureImage(); }
}