#include <Arduino.h>
#include <SoftwareSerial.h>
#include "ThreadHandler.h"
#include <NewPing.h>

// 多线程初始化
SET_THREAD_HANDLER_TICK(0)
THREAD_HANDLER(InterruptTimer::getInstance())

int rece = 0; // 接收烧录重置信号

// 烧录重置线程
class TestThread1 : public Thread
{
public:
    TestThread1() : Thread(1, 200000, 0)
    {
    }

    ~TestThread1() override
    = default;

    void run() override
    {
        while (Serial.available() > 0) {
            rece = Serial.read();
            delay(10);
            if (rece == (int) '#') {
                pinMode(A0, OUTPUT);
                digitalWrite(A0, LOW);
                delay(50);
                digitalWrite(A0, HIGH);
            }
        }
    }
};


const int moveTime = 80;
const int turnTime = 320;  //转向角度
float correct_front_threshold = 0.6;
const int correct_turn_time=10;
const int front_miniDis=10;
const int past_now_miniDis=0.5;
const int side_miniDis=4;
const int walk_straight_turn_time=10;
const int walk_straight_stop_dis=10;
const int walk_straight_detect_interval=1;
//const int walk_straight_delay_interval=2;
const int backToStopTime=10;
const int ok_to_turn=25;

// Define L298N Dual Motor Controller Pins
const int in1 = 10; // Motor A in1 左前 改30
const int in2 = 11; // Motor A in2    改31
const int in3 = 6; // Motor B in1 左后 改32
const int in4 = 7; // Motor B in2      改33
const int in5 = 8; // Motor C in1 右前  改34
const int in6 = 9; // Motor C in2      改35
const int in7 = 16; // Motor D in1 右后   改36
const int in8 = 17; // Motor D in2       改37
const int enA = 3;// 左前
const int enB = 4;// 左后   改
const int enC = 2; // 右前   改 5
const int enD = 5; // 右后

// Define Ultrasonic Sensor pins
const int TRIGGER_PIN_FRONT_LEFT = 13;
const int ECHO_PIN_FRONT_LEFT = 12;
const int TRIGGER_PIN_FRONT_RIGHT = 22 ; //原来20
const int ECHO_PIN_FRONT_RIGHT = 23;  //yuan lai21;
const int TRIGGER_PIN_LEFT = 14;
const int ECHO_PIN_LEFT = 15;
const int TRIGGER_PIN_RIGHT = 24;  //yuan16;
const int ECHO_PIN_RIGHT = 25;  //yuan17;
// Maximum distance we want to ping for (in centimeters).
const unsigned int MAX_DISTANCE = 500;
NewPing sonarFrontLeft(TRIGGER_PIN_FRONT_LEFT, ECHO_PIN_FRONT_LEFT, MAX_DISTANCE);
NewPing sonarFrontRight(TRIGGER_PIN_FRONT_RIGHT, ECHO_PIN_FRONT_RIGHT, MAX_DISTANCE);
NewPing sonarLeft(TRIGGER_PIN_LEFT, ECHO_PIN_LEFT, MAX_DISTANCE);
NewPing sonarRight(TRIGGER_PIN_RIGHT, ECHO_PIN_RIGHT, MAX_DISTANCE);

float distance_cm_front_left = 0;
float distance_cm_front_right = 0;
float distance_cm_left = 0;
float distance_cm_right = 0;

// Set initial speed
int speed = 70;

// Motor Controls
void forward(int pwm) {

    // Motor A and B Forward
    digitalWrite(in1, HIGH);
    digitalWrite(in2, LOW);
    digitalWrite(in3, HIGH);
    digitalWrite(in4, LOW);

    // // Motor C and D Forward
    digitalWrite(in5, HIGH);
    digitalWrite(in6, LOW);
    digitalWrite(in7, HIGH);
    digitalWrite(in8, LOW);
    analogWrite(enA,pwm);
    analogWrite(enB,pwm);
    analogWrite(enC,pwm);
    analogWrite(enD,pwm);
}

void back(int pwm) {

    // Motor A and B Backward
    digitalWrite(in1, LOW);
    digitalWrite(in2, HIGH);
    digitalWrite(in3, LOW);
    digitalWrite(in4, HIGH);

    // Motor C and D Backward
    digitalWrite(in5, LOW);
    digitalWrite(in6, HIGH);
    digitalWrite(in7, LOW);
    digitalWrite(in8, HIGH);
    analogWrite(enA,pwm);
    analogWrite(enB,pwm);
    analogWrite(enC,pwm);
    analogWrite(enD,pwm);
}

void left(int pwm) {
    // Motor A and B Forward
    digitalWrite(in1, HIGH);
    digitalWrite(in2, LOW);
    digitalWrite(in3, HIGH);
    digitalWrite(in4, LOW);

    // Motor C and D back
    digitalWrite(in5, LOW);
    digitalWrite(in6, HIGH);
    digitalWrite(in7, LOW);
    digitalWrite(in8, HIGH);

    analogWrite(enA,255);
    analogWrite(enB,255);
    analogWrite(enC,255);
    analogWrite(enD,255);
}

void right(int pwm) {
    // Motor A and B Stop
    digitalWrite(in1, LOW);
    digitalWrite(in2, HIGH);
    digitalWrite(in3, LOW);
    digitalWrite(in4, HIGH);

    // Motor C and D Forward
    digitalWrite(in5, HIGH);
    digitalWrite(in6, LOW);
    digitalWrite(in7, HIGH);
    digitalWrite(in8, LOW);
    analogWrite(enA,255);
    analogWrite(enB,255);
    analogWrite(enC,255);
    analogWrite(enD,255);
}

void stop() {
    analogWrite(enA,0);
    analogWrite(enB,0);
    analogWrite(enC,0);
    analogWrite(enD,0);
    digitalWrite(in1, LOW);
    digitalWrite(in2, LOW);
    digitalWrite(in3, LOW);
    digitalWrite(in4, LOW);
    digitalWrite(in5, LOW);
    digitalWrite(in6, LOW);
    digitalWrite(in7, LOW);
    digitalWrite(in8, LOW);
}

float distance_front_left(){
    digitalWrite(TRIGGER_PIN_FRONT_LEFT, LOW);
    delayMicroseconds(2);
    digitalWrite(TRIGGER_PIN_FRONT_LEFT, HIGH);
    delayMicroseconds(10);
    digitalWrite(TRIGGER_PIN_FRONT_LEFT, LOW);
    float distance = pulseIn(ECHO_PIN_FRONT_LEFT, HIGH);
    distance= distance/58;
    delay(10);
    return distance;
}

float distance_front_right(){
    digitalWrite(TRIGGER_PIN_FRONT_RIGHT, LOW);
    delayMicroseconds(2);
    digitalWrite(TRIGGER_PIN_FRONT_RIGHT, HIGH);
    delayMicroseconds(10);
    digitalWrite(TRIGGER_PIN_FRONT_RIGHT, LOW);
    float distance = pulseIn(ECHO_PIN_FRONT_RIGHT, HIGH);
    distance= distance/58;
    delay(10);
    return distance;
}

float distance_left(){
    digitalWrite(TRIGGER_PIN_LEFT, LOW);
    delayMicroseconds(2);
    digitalWrite(TRIGGER_PIN_LEFT, HIGH);
    delayMicroseconds(10);
    digitalWrite(TRIGGER_PIN_LEFT, LOW);
    float distance = pulseIn(ECHO_PIN_LEFT, HIGH);
    distance= distance/58;
    delay(10);
    return distance;
}

float distance_right(){
    digitalWrite(TRIGGER_PIN_RIGHT, LOW);
    delayMicroseconds(2);
    digitalWrite(TRIGGER_PIN_RIGHT, HIGH);
    delayMicroseconds(10);
    digitalWrite(TRIGGER_PIN_RIGHT, LOW);
    float distance = pulseIn(ECHO_PIN_RIGHT, HIGH);
    distance= distance/58;
    delay(10);
    return distance;
}

void turnleft(){
    left(speed);
    delay(turnTime);
    stop();
    delay(500);
}

void turnright(){
    right(speed);
    delay(turnTime);
    stop();
    delay(500);
}

int detect()
{
    distance_cm_front_left=distance_front_left();
    distance_cm_left=distance_left();
    distance_cm_right=distance_right();
    if(distance_cm_front_left<front_miniDis && distance_cm_left>ok_to_turn&&distance_cm_right<ok_to_turn)
    {
        return 0;
    }
    else if(distance_cm_front_right<front_miniDis&&distance_cm_right>ok_to_turn&&distance_cm_left<ok_to_turn)
    {
        return 2;
    }
    else if(distance_cm_front_left<front_miniDis&&distance_cm_right<ok_to_turn&&distance_cm_left<ok_to_turn)
    {
        return 3;
    }
    else if(distance_cm_front_left>front_miniDis)
    {
        return 1;
    }
    else
    {
        if(distance_cm_right>distance_cm_left)
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }
}

void correct()
{
    distance_cm_front_left=distance_front_left();
    distance_cm_front_right=distance_front_right();
    if(distance_cm_front_left < front_miniDis && distance_cm_front_right < front_miniDis )
    {

        while(abs(distance_cm_front_left-distance_cm_front_right)>correct_front_threshold)
        {
            if(distance_cm_front_left>distance_cm_front_right)
            {
                right(speed);
                delay(correct_turn_time);
                stop();
            }
            if(distance_cm_front_right>distance_cm_front_left)
            {
                left(speed);
                delay(correct_turn_time);
                stop();
            }
            distance_cm_front_left=distance_front_left();
            distance_cm_front_right=distance_front_right();
        }
    }
}

void walkStraight(){
    distance_cm_front_left=distance_front_left();
    distance_cm_front_right=distance_front_right();
    if(distance_cm_front_left<front_miniDis||distance_cm_front_right<front_miniDis)
    {

        stop();
        return ;
    }
    int time=0;
    int loop_num=0;
    float past_right_distance=distance_front_right();
    float past_left_distance=distance_front_left();
    while(time<moveTime)
    {
        loop_num+=1;
        distance_cm_front_left=distance_front_left();
        distance_cm_front_right=distance_front_right();
        distance_cm_left=distance_left();
        distance_cm_right=distance_right();
        int is_correct=0;
        if(distance_cm_front_left<walk_straight_stop_dis||distance_cm_front_right<walk_straight_stop_dis)
        {
            back(speed);
            delay(backToStopTime);
            break;
        }
        if(abs(past_right_distance-distance_cm_right)>past_now_miniDis&&abs(past_right_distance-distance_cm_right)<10)
        {
            if((past_right_distance-distance_cm_right)>0)
            {
                left(speed);
                delay(walk_straight_turn_time);
                is_correct=1;
            }
            else
            {
                right(speed);
                delay(walk_straight_turn_time);
                is_correct=1;
            }
        }
        if(abs(past_left_distance-distance_cm_left)>past_now_miniDis&&abs(past_left_distance-distance_cm_left)<10)
        {
            if((past_left_distance-distance_cm_left)<0)
            {
                left(speed);
                delay(walk_straight_turn_time);
                is_correct=1;
            }
            else
            {
                right(speed);
                delay(walk_straight_turn_time);
                is_correct=1;
            }
        }
        if(distance_cm_left<side_miniDis)
        {
            right(speed);
            delay(walk_straight_turn_time);
            is_correct=1;
        }
        else if(distance_cm_right<side_miniDis)
        {
            left(speed);
            delay(walk_straight_turn_time);
            is_correct=1;
        }

        if(distance_cm_front_left>front_miniDis&&distance_cm_front_right>front_miniDis)
        {
            if(is_correct==1)
            {
                forward(speed);
                delay(1);
                time+=1;
            }
            else
            {
                forward(speed);
                delay(walk_straight_detect_interval);
                time+=walk_straight_detect_interval;
            }
            // if((time/walk_straight_detect_interval)%walk_straight_delay_interval==0)
            // {
            //   stop();
            //   delay(1);
            // }

        }
        if(loop_num%5==0)
        {
            past_right_distance=distance_cm_right;
            past_left_distance=distance_cm_left;
        }
    }
    stop();
}

void
action(int choice)
{
    if(choice==0)
    {
        turnleft();
        delay(100);
    }
    if(choice==1)
    {
        walkStraight();
    }
    if(choice==2)
    {
        turnright();
        delay(100);
    }
    if(choice==3)
    {
        delay(1000);
        if(distance_cm_left<distance_cm_right)
        {
            turnright();
            correct();
            turnright();
        }
        else
        {
            turnleft();
            correct();
            turnleft();
        }

    }
}




String comdata = ""; // 接收串口数据

// 主线程
class TestThread2 : public Thread
{
public:
    TestThread2() : Thread(1, 200000, 0)
    {
    }

    ~TestThread2() override
    = default;

    void run() override
    {
        if (Serial1.available() > 0)
        {
            delay(100);
            comdata =Serial1.readStringUntil('\n');
        }

        if (comdata.length() > 0)
        {
            if (comdata == "c")
            {
                correct();
                int choice = detect();
                Serial1.println(choice);
                action(choice);
            } else if(comdata == "b")
            {
                correct();
                Serial1.println(3);
                action(3);
            }
            // 清空字符串
            comdata = "";
        }
    }
};


TestThread1* testThread1 = new TestThread1();
TestThread2* testThread2 = new TestThread2();


void setup()
{
    // 串口和多线程初始化
    Serial.begin(115200);
    Serial1.begin(9600);
    while(Serial.read()>= 0){}
    while(Serial1.read()>= 0){}
    ThreadHandler::getInstance()->enableThreadExecution();

    pinMode(in1, OUTPUT);
    pinMode(in2, OUTPUT);
    pinMode(in3, OUTPUT);
    pinMode(in4, OUTPUT);
    pinMode(in5, OUTPUT);
    pinMode(in6, OUTPUT);
    pinMode(in7, OUTPUT);
    pinMode(in8, OUTPUT);
    pinMode(enA, OUTPUT);
    pinMode(enB, OUTPUT);
}


void loop()
{

}




