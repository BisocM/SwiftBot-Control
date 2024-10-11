use crate::config::*;
use crate::utils::clamp;
use rppal::gpio::{Gpio, OutputPin};
use std::error::Error;

/// Struct to manage LED control and underlighting using the SN3218 LED driver
pub struct Buttons {
    pub led_pins: [OutputPin; NUM_BUTTONS], //GPIO pins for button LEDs
    pub led_frequency: f64, //PWM frequency for the button LEDs
}

impl Buttons {
    /// Initialize the Leds struct, set up GPIO pins, and configure SN3218
    pub fn new(gpio: &Gpio) -> Result<Self, Box<dyn Error>> {
        //Initialize button LEDs as GPIO output pins
        let mut led_a = gpio.get(LED_A_PIN)?.into_output();
        let mut led_b = gpio.get(LED_B_PIN)?.into_output();
        let mut led_x = gpio.get(LED_X_PIN)?.into_output();
        let mut led_y = gpio.get(LED_Y_PIN)?.into_output();

        let mut led_pins = [led_a, led_b, led_x, led_y];

        //Set initial PWM frequency and duty cycle to 0% for all LEDs
        let led_frequency = 2000.0; //Frequency in Hz
        for led in led_pins.iter_mut() {
            led.set_pwm_frequency(led_frequency, 0.0)?; //Start with LEDs off
        }

        Ok(Buttons {
            led_pins,
            led_frequency,
        })
    }

    /// Set the brightness of a specific button LED
    pub fn set_button_led(&mut self, button_led: u8, value: f64) -> Result<(), Box<dyn Error>> {
        if button_led >= NUM_BUTTONS as u8 {
            return Err("Invalid button LED index".into());
        }

        //Clamp brightness value between 0.0 and 1.0
        let brightness = clamp(value, 0.0, 1.0);
        let duty_cycle = brightness * 100.0; //Convert to percentage

        //Set PWM frequency with calculated duty cycle for selected LED
        self.led_pins[button_led as usize].set_pwm_frequency(self.led_frequency, duty_cycle)?;

        Ok(())
    }
}