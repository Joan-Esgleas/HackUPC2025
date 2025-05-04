#include <WiFi.h>
#include <WebServer.h>
#include "DHT.h"
#include <ESP32Servo.h>

#define DHTPIN 14   // GPIO14
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);

#define SERVO_PIN 27
Servo myServo;

const char* ssid = "A35Sofi";
const char* pass = "mandarinas";

float temp = 0;
float hum = 0;

WebServer server(80);
bool servoActive = false;

IPAddress ip(192, 168, 174, 5);
IPAddress gw(192, 168, 174, 73);
IPAddress mask(255, 255, 255, 0);

void TaskSensor(void *pvParameters);
void TaskServo(void *pvParameters);

void setup() {
    Serial.begin(115200);

    dht.begin();

    myServo.attach(SERVO_PIN);
    myServo.write(0);

    Serial.println("Connecting...");
    WiFi.disconnect();
    delay(500);
    WiFi.config(ip,gw,mask);
    WiFi.mode(WIFI_STA);
    WiFi.begin(ssid, pass);

    int status = WiFi.waitForConnectResult();
    if (status != WL_CONNECTED) 
    {
        Serial.println("Connection Failed! Rebooting...");
        delay(500);
        Serial.println("Rebooting... ");
        ESP.restart();  
    }
    Serial.println("Ready");
    Serial.print("IP address: ");

    Serial.println(WiFi.localIP());

    xTaskCreatePinnedToCore(TaskSensor, "TaskSensor", configMINIMAL_STACK_SIZE + 2049, NULL, 1, NULL, 0);
    xTaskCreatePinnedToCore(TaskServo, "TaskoServo", configMINIMAL_STACK_SIZE + 2049, NULL, 1, NULL, 0);

        server.on("/dht", HTTP_GET, []() {
            if (isnan(temp) || isnan(hum)) {
                server.send(500, "application/json", "{\"error\":\"Sensor error\"}");
            } else {
                String json = "{\"temp\":" + String(temp) + ",\"hum\":" + String(hum) + "}";
                server.send(200, "application/json", json);
            }
            Serial.println("GET request recived");
        });

    server.on("/servo", HTTP_GET, []() {
        servoActive = !servoActive;
        myServo.write(servoActive ? 180 : 0);
        Serial.println("SERVO request recived");
        server.send(200, "text/plain", "OK");
    });

    server.begin(80);
}

void loop() {}

void TaskSensor(void *pvParameters) {
    while (true) {

        temp = dht.readTemperature();
        hum = dht.readHumidity();
        vTaskDelay(pdMS_TO_TICKS(50));
    }
}
void TaskServo(void *pvParameters) {
    while (true) {
        server.handleClient();
        vTaskDelay(pdMS_TO_TICKS(10));

    }
}