use crate::config::*;
use rppal::gpio::{Gpio, InputPin, OutputPin};
use std::error::Error;
use std::time::{Duration, Instant};

pub struct Sensors {
    pub ultra_trig: OutputPin,
    pub ultra_echo: InputPin,
}

impl Sensors {
    pub fn new(gpio: &Gpio) -> Result<Self, Box<dyn Error>> {
        let ultra_trig = gpio.get(ULTRA_TRIG_PIN)?.into_output();
        let ultra_echo = gpio.get(ULTRA_ECHO_PIN)?.into_input();

        Ok(Sensors {
            ultra_trig,
            ultra_echo,
        })
    }

    pub fn read_distance(
        &mut self,
        timeout_ms: u64,
        samples: u32,
        offset_ns: u64,
    ) -> Result<f64, Box<dyn Error>> {
        let mut total_pulse_duration = 0u64;
        let mut count = 0;

        let timeout = Duration::from_millis(timeout_ms);

        let start_time = Instant::now();

        while count < samples && start_time.elapsed() < timeout {
            //Trigger pulse
            self.ultra_trig.set_high();
            std::thread::sleep(Duration::from_micros(10));
            self.ultra_trig.set_low();

            //Wait for echo to go high
            let pulse_start = Instant::now();
            while self.ultra_echo.is_low() {
                if pulse_start.elapsed() > timeout {
                    break;
                }
            }

            //Wait for echo to go low
            let pulse_end = Instant::now();
            while self.ultra_echo.is_high() {
                if pulse_end.elapsed() > timeout {
                    break;
                }
            }

            let pulse_duration = pulse_end.duration_since(pulse_start).as_nanos() as u64;

            if pulse_duration < timeout.as_nanos() as u64 {
                total_pulse_duration += pulse_duration - offset_ns;
                count += 1;
            }
        }

        if count > 0 {
            let avg_pulse_duration = total_pulse_duration as f64 / count as f64;
            let distance = avg_pulse_duration * SPEED_OF_SOUND_CM_NS / 2.0;
            Ok(distance)
        } else {
            Ok(0.0)
        }
    }
}