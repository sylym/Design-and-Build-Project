import time
import cv2
import numpy as np
import requests
import paramiko
import atexit
import serial
from ultralytics import YOLO
import subprocess

model = YOLO("best.pt")

# 启动ssh连接
ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
ssh.connect('192.168.1.1', username='root', password='1q2w3e4r')
transport = ssh.get_transport()
channel = transport.open_session()
channel.exec_command(
    'mjpg_streamer -i "input_uvc.so -f 3 -r 480*320 -d /dev/video0" -o "output_http.so -p 8080 -w /www/camwww"')

# 启动串口连接
ser = serial.Serial(port="COM4", baudrate=9600)
ser.write(bytes("\n", 'utf-8'))
if not ser.isOpen():
    raise Exception("串口打开失败")


direction = "front"
with open("data.txt", "w") as f:
    f.write("forward")
process = subprocess.Popen(["python", "demo.py"])

# 退出时关闭ssh连接和串口连接
@atexit.register
def exit_handler():
    ssh.exec_command('killall mjpg_streamer')
    ssh.close()

    ser.close()
    process.terminate()
    print("exit success.")


def download_img():
    """
    从路由器上下载图像
    :return: opencv格式的图像
    """
    response = requests.get("http://192.168.1.1:8080/?action=snapshot")
    data = response.content
    img1 = np.frombuffer(data, np.uint8)
    img_cv = cv2.imdecode(img1, cv2.IMREAD_ANYCOLOR)
    return img_cv


def send_command(command: str) -> int:
    """
    发送命令到串口（命令中不能出现换行符）
    :param command: 命令
    :return: 发送的字节数
    """
    send_len = ser.write(bytes(command + "\n", 'utf-8'))
    return send_len


def read_command() -> str:
    """
    读取串口命令（如果没有命令会阻塞）
    :return: 命令
    """
    data = ser.readline().decode('utf-8').strip("\r\n")
    return data


if __name__ == '__main__':
    while True:
        image = download_img()
        # save image
        cv2.imwrite("0.jpg", image)
        results = model("0.jpg", save=False, save_txt=False, classes=[0, 1, 2], conf=0.4)
        if min(results[0].boxes.cls.shape) != 0:
            command = "b"  # 调整为c不执行识别出物体后的命令
            print(int(results[0].boxes.cls.item()), results[0].boxes.conf.item())
            cv2.imwrite("results.jpg", results[0].plot())
        else:
            command = "c"
            print("no object")
        send_command(command)
        data = int(read_command())

        if data == 4:
            data = 3
        if data == 1:
            match direction:
                case "front":
                    move = "forward"
                case "left":
                    move = "left"
                case "right":
                    move = "right"
                case "back":
                    move = "back"

            with open("data.txt", "w") as f:
                f.write(move)

        match direction:
            case "front":
                match data:
                    case 1:
                        direction = "front"
                    case 0:
                        direction = "left"
                    case 2:
                        direction = "right"
                    case 3:
                        direction = "back"
            case "left":
                match data:
                    case 1:
                        direction = "left"
                    case 0:
                        direction = "back"
                    case 2:
                        direction = "front"
                    case 3:
                        direction = "right"
            case "right":
                match data:
                    case 1:
                        direction = "right"
                    case 0:
                        direction = "front"
                    case 2:
                        direction = "back"
                    case 3:
                        direction = "left"
            case "back":
                match data:
                    case 1:
                        direction = "back"
                    case 0:
                        direction = "right"
                    case 2:
                        direction = "left"
                    case 3:
                        direction = "front"

        print(data, direction)
        print("----------------------------------------")
        time.sleep(1)