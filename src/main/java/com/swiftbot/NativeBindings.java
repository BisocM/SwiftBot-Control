package com.swiftbot;

public class NativeBindings {
    static {
        System.loadLibrary("swiftbot_rs_lib.so"); //Load the lib
        //Would be better to set an environment variable here that points to the compiled .so lib.
    }

    //Motor control
    public static native void stop();
    public static native void forward(double speed);
    public static native void backward(double speed);
    public static native void turnLeft(double speed);
    public static native void turnRight(double speed);
    public static native void setMotorSpeeds(double leftSpeed, double rightSpeed);

    //Sensor readings
    public static native double readDistance();
    public static native boolean readButton(int buttonId);

    //LED control
    public static native void setButtonLed(int buttonLedId, double value);
    public static native void setUnderlight(int lightId, int red, int green, int blue);
    public static native void fillUnderlighting(int red, int green, int blue);
    public static native void clearUnderlighting();

    //Camera control
    public static native byte[] captureImage();
}