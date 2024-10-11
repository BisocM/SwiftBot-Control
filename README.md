# SwiftBot-Control

This repository contains a Rust library (`swiftbot_rs_lib`) that provides hardware control for the SwiftBot robot platform running on a Raspberry Pi, as well as the Java wrapper for it, to provide an additional abstraction layer. The library interfaces with the robot's hardware components such as motors, LEDs, buttons, sensors, and camera, and exposes functionality through the Java Native Interface (JNI) for use in Java applications.

This library assumes all the pins are *pre-mapped*, as would be expected. However, if you would like a more customized configuration for your pins, you may want to modify the `config.rs` file.

## Table of Contents

- [Features](#features)
- [Hardware Requirements](#hardware-requirements)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
  - [Building the Rust Library](#building-the-rust-library)
- [Examples](#examples)
- [Notes](#notes)
- [License](#license)

## Features

- **Motor Control**: Move the robot forward, backward, turn left, and turn right with adjustable speeds.
- **Sensor Reading**: Read distance measurements from the ultrasonic sensor.
- **Button Interaction**: Read the state of the buttons on the robot.
- **LED Control**: Control the brightness of button LEDs and set colors for underlighting LEDs.
- **Camera Capture**: Capture images using the Raspberry Pi Camera V2 with the `rscam` crate.

## Hardware Requirements

- **Raspberry Pi 4**
- **Raspberry Pi Camera V2**
- **HC-SR04 Ultrasonic Sensor**
- **SN3218 LED Driver** for underlighting

## Prerequisites

- **Operating System**: Any Linux Distro (SwiftBot runs on a custom barebones Yocto distribution)
- **Rust**:

  ```sh
  curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
  ```

- **Java Development Kit (JDK)**: Version 8 or higher installed on the Raspberry Pi
- **JNI Development Headers**: Included with the JDK
- **System Libraries**:

  ```sh
  sudo apt-get update
  sudo apt-get install libclang-dev libudev-dev
  ```

- **Camera Dependencies**:

  - Ensure that the Raspberry Pi Camera is enabled in `raspi-config`.
  - Verify that `/dev/video0` exists.

## Installation

### Building the Rust Library

Build the library as a C-compatible dynamic library for JNI support:

```sh
cargo build --release
```

The compiled library will be located at `target/release/libswiftbot_rs_lib.so`.

## Usage

### Building the Rust Library

Ensure that the `Cargo.toml` file is configured correctly:

```toml
[package]
name = "swiftbot_rs_lib"
version = "1.0.0"
edition = "2021"

[lib]
name = "swiftbot_rs_lib"
crate-type = ["cdylib"]  # Compile as a C-compatible dynamic library for JNI support

[dependencies]
rppal = "0.19.0"
jni = { version = "0.21.1", features = ["invocation"] }
lazy_static = "1.4.0"
rscam = "0.5.5"
```

## Examples

### Moving the Robot Forward

```java
SwiftBot robot = new SwiftBot();
robot.forward(0.5); //Move forward at half speed
Thread.sleep(2000); //Move for 2 seconds
robot.stop();
```

### Reading Distance from Ultrasonic Sensor

```java
double distance = robot.readDistance();
System.out.println("Distance: " + distance + " cm");
```

### Checking if a Button is Pressed

```java
if (robot.isButtonPressed(SwiftBot.BUTTON_A)) {
    System.out.println("Button A is pressed!");
}
```

### Controlling Button LEDs

```java
robot.setButtonLed(SwiftBot.BUTTON_A, 1.0); //Set Button A LED to full brightness
robot.setButtonLed(SwiftBot.BUTTON_B, 0.5); //Set Button B LED to half brightness
```

### Setting Underlighting Colors

```java
//Set underlight 0 to red
robot.setUnderlight(0, 255, 0, 0);

//Fill all underlights with blue
robot.fillUnderlighting(0, 0, 255);
```

### Capturing an Image

```java
byte[] imageData = robot.captureImage();
if (imageData != null) {
    try (FileOutputStream fos = new FileOutputStream("captured_image.jpg")) {
        fos.write(imageData);
        System.out.println("Image saved as captured_image.jpg");
    } catch (IOException e) {
        e.printStackTrace();
    }
} else {
    System.out.println("Failed to capture image.");
}
```

## Notes

- **Hardware Interaction**: This library interacts directly with hardware components. Ensure that you have proper permissions and that your user is part of the `gpio` and `i2c` groups.

  ```sh
  sudo usermod -aG gpio,i2c $USER
  ```

- **Thread Safety**: The Rust library uses `Mutex` locks to ensure thread safety. Avoid long-running operations while holding a lock to prevent blocking other threads.

- **Error Handling**: JNI functions throw Java exceptions in case of errors. Make sure to handle these exceptions in your Java code.

- **Camera Usage**: The `rscam` crate is not actively maintained. Compatibility may vary depending on your Raspberry Pi OS version and kernel. Ensure that the camera is properly connected and enabled.

- **Performance Considerations**: Minimize the frequency of JNI calls in performance-critical sections to reduce overhead. The code has yet to be thoroughly tested & benchmarked with the camera module. OpenCV may be required for future use as an alternative.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
