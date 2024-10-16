package com.swiftbot;

public class Main {
    public static void main(String[] args) {
        SwiftBot bot = new SwiftBot();

        try {
            System.out.println("Testing motor control...");

            // Move forward
            bot.forward(0.5);
            System.out.println("Moving forward...");
            Thread.sleep(1000);

            // Move backward
            bot.backward(0.5);
            System.out.println("Moving backward...");
            Thread.sleep(1000);

            // Turn left
            bot.turnLeft(0.5);
            System.out.println("Turning left...");
            Thread.sleep(1000);

            // Turn right
            bot.turnRight(0.5);
            System.out.println("Turning right...");
            Thread.sleep(1000);

            // Stop
            bot.stop();
            System.out.println("Stopping...");
            Thread.sleep(1000);

            // Set individual motor speeds
            bot.setMotorSpeeds(0.7, 0.3);
            System.out.println("Setting individual motor speeds...");
            Thread.sleep(1000);
            bot.stop();

            System.out.println("\nTesting sensor readings...");

            // Read distance
            double distance = bot.readDistance();
            System.out.println("Distance sensor reading: " + distance + " cm");

            System.out.println("\nTesting button presses...");

            // Check each button state
            System.out.println("Button A pressed: " + bot.isButtonPressed(SwiftBot.BUTTON_A));
            System.out.println("Button B pressed: " + bot.isButtonPressed(SwiftBot.BUTTON_B));
            System.out.println("Button X pressed: " + bot.isButtonPressed(SwiftBot.BUTTON_X));
            System.out.println("Button Y pressed: " + bot.isButtonPressed(SwiftBot.BUTTON_Y));

            System.out.println("\nTesting LED control...");

            // Set LED brightness
            bot.setButtonLed(SwiftBot.BUTTON_A, 0.5);
            System.out.println("Set Button A LED to half brightness.");

            bot.setButtonLed(SwiftBot.BUTTON_B, 0.5);
            System.out.println("Set Button B LED to half brightness.");

            bot.setButtonLed(SwiftBot.BUTTON_Y, 0.5);
            System.out.println("Set Button Y LED to half brightness.");

            bot.setButtonLed(SwiftBot.BUTTON_X, 0.5);
            System.out.println("Set Button X LED to half brightness.");

            bot.setButtonLed(SwiftBot.BUTTON_Y, 0.0);
            bot.setButtonLed(SwiftBot.BUTTON_X, 0.0);
            bot.setButtonLed(SwiftBot.BUTTON_A, 0.0);
            bot.setButtonLed(SwiftBot.BUTTON_B, 0.0);

        } catch (Exception e) {
            System.err.println("An error occurred during testing: " + e.getMessage());
        }
    }
}
