package my.sdl.smarthome.remoteapp.core

import my.sdl.smarthome.remoteapp.core.mqtt.AwsConnection
import my.sdl.smarthome.remoteapp.core.mqtt.Publisher

class AwsPublisher(private val awsManager: AwsConnection) : Publisher {
    override fun publish(topic: String, payload: String) {
        awsManager.publishData(topic, payload)
    }
}