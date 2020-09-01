package io.advance.pi4led.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by jgriesel on 2016/09/22.
 * 2020/08/31 - reused code
 */

public class MqttEventPublisher {

    private static MqttClient client;

    public static void publish(String topic, byte[] payload) throws MqttException {

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

        if(client == null ){
            client = new MqttClient(
                    "tcp://" + "192.168.57.125" + ":1883",
                    MqttClient.generateClientId(),
                    new MemoryPersistence());

            mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);

            client.connect(mqttConnectOptions);
        }

        client.publish (
                topic,
                payload,
                1,
                false);

    }

}
