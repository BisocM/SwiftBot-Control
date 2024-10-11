package com.swiftbot;

public class Main {
    public static void main(String[] args) {
        SwiftBot bot = new SwiftBot();

        // Example commands to control the bot
        bot.forward(0.5); // Move forward at 50% speed
        System.out.println("Bot moved forward");

        double distance = bot.readDistance(); // Read distance from sensor
        System.out.println("Distance sensor reading: " + distance);

        bot.stop(); // Stop the bot
        System.out.println("Bot stopped");
    }
}
