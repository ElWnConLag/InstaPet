package insta.pet.instapet;

import android.util.Log;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.w3c.dom.Text;

public class MqttHandler {
    private static final String BROKER_URL = "tcp://prueba-mqtt-ust.cloud.shiftr.io:1883";
    private static final String CLIENT_ID = "tu_client_id"; // Puedes usar un ID único para tu cliente MQTT
    private static final String TOPIC = "tu_topic"; // El tema al que te suscribirás

    TextView textViewMensaje;

    private MqttClient client;

    public void connect(String brokerUrl, String clientId) {
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient(BROKER_URL, CLIENT_ID, persistence);
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            connectOptions.setUserName("prueba-mqtt-ust");
            connectOptions.setPassword("TN285V8kWXcveB0a".toCharArray());
            client.connect(connectOptions);
            subscribe(TOPIC);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String message, String s) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            client.publish(TOPIC, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic) {
        try {
            client.subscribe(topic);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    // Reconnection logic could be added here
                }

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    // Message handling logic here
                    String mensajeRecibido = new String(mqttMessage.getPayload());
                    // Procesa el mensaje recibido según tus necesidades

                    mostrarMensajeEnTextView(mensajeRecibido);
                }

                private void mostrarMensajeEnTextView(String mensaje) {
                    // Encuentra el TextView por su ID

                    // Verifica si el TextView se encontró correctamente antes de asignarle un valor
                    if (textViewMensaje != null) {
                        // Establece el mensaje en el TextView
                        textViewMensaje.setText(mensaje);
                    } else {
                        // Manejar la situación donde el TextView no se encuentra
                        // Por ejemplo, imprimir un mensaje de error o tomar alguna otra acción
                        Log.e("TAG", "TextView no encontrado");
                    }
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    // Delivery complete logic
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}