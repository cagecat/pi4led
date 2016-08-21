package io.advance.pi4led.io.advance.pi4led.controller;

import com.pi4j.io.gpio.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jgriesel on 2016/08/21.
 */

@RestController
public class LedController {

    private static GpioPinDigitalOutput pinRed;
    private static GpioPinDigitalOutput pinGreen;
    private static GpioPinDigitalOutput pinBlue;

    @RequestMapping("/")
    public String greeting() {
        return "Hello World!";
    }

    @RequestMapping("/red")
    public String Red() {
        if (pinRed == null) {
            GpioController gpio = GpioFactory.getInstance();
            pinRed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyRedLED", PinState.LOW);
        }

        pinRed.toggle();

        return "OK";
    }

    @RequestMapping("/green")
    public String Green() {
        if (pinGreen == null) {
            GpioController gpio = GpioFactory.getInstance();
            pinGreen = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyGreenLED", PinState.LOW);
        }

        pinRed.toggle();

        return "OK";
    }

    @RequestMapping("/blue")
    public String Blue() {
        if (pinBlue == null) {
            GpioController gpio = GpioFactory.getInstance();
            pinBlue = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "MyBlueLED", PinState.LOW);
        }

        pinRed.toggle();

        return "OK";
    }

}

