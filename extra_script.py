Import("env")

# 刷入程序前先发送r，重置ardunio
def before_upload(source, target, env):
    import serial
    print("before_upload")
    ser = serial.Serial(port="COM5", baudrate=115200)
    print(ser)
    if ser.isOpen():
        print('open success.')
        ser.write(b'#')
    else:
        print('open failed.')
    ser.close()


env.AddPreAction("upload", before_upload)