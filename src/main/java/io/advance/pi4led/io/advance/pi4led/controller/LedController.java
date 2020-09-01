package io.advance.pi4led.io.advance.pi4led.controller;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.advance.pi4led.service.MqttEventPublisher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jgriesel on 2016/08/2
 */

@RestController
public class LedController {

    final String topic = "bwclogic/power/set/";

    private static GpioPinDigitalInput event;
    private static GpioPinDigitalOutput pinRed;
    private static GpioPinDigitalOutput pinGreen;
    private static GpioPinDigitalOutput pinBlue;

    //Thread gpioThread;

    @RequestMapping("/")
    public String greeting() {
        return "Hello World!";
    }

    @RequestMapping("/event")
    public String EventListener() {

        if (event == null){
            GpioController gpio = GpioFactory.getInstance();
            event = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
            event.setShutdownOptions(true);
            event.addListener(new GpioPinListenerDigital() {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpioPinDigitalStateChangeEvent) {
                    Date date = new Date();
                    //System.out.println(dateFormat.format(date) +  " GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                    String payloadString = dateFormat.format(date) + event.getPin() + event.getState();
                    byte[] payload = payloadString.getBytes();

                    try {
                        MqttEventPublisher.publish(topic, payload);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            });

            while(true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        return "OK";
    }


    @RequestMapping("/red")
    public String Red() {
        if (pinRed == null) {
            GpioController gpio = GpioFactory.getInstance();
            pinRed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyRedLED", PinState.LOW);
        }

        //pinRed.toggle();
        pinRed.blink(500, 15000);

        return "OK";
    }

    @RequestMapping("/green")
    public String Green() {
        if (pinGreen == null) {
            GpioController gpio = GpioFactory.getInstance();
            pinGreen = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyGreenLED", PinState.LOW);
        }

        pinGreen.toggle();

        return "OK";
    }

    @RequestMapping("/blue")
    public String Blue() {
        if (pinBlue == null) {
            GpioController gpio = GpioFactory.getInstance();
            pinBlue = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "MyBlueLED", PinState.LOW);
        }

        pinBlue.toggle();

        return "OK";
    }

}

