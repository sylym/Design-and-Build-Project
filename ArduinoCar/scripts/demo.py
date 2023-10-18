from flask import Flask, render_template
from flask_socketio import SocketIO, emit

app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret_key'
socketio = SocketIO(app)

# 初始小车位置
car_x = 200
car_y = 200

# 初始路径
path = [(car_x, car_y)]

# 根路由，显示网页
@app.route('/')
def index():
    return render_template('index.html', car_x=car_x, car_y=car_y)

# 小车移动路由
@socketio.on('move')
def move():
    global car_x, car_y, path
    # 从data.txt中读取小车移动方向
    with open('data.txt', 'r') as f:
        direction = f.read()
    # 根据方向更新小车位置
    if direction == "forward":
        car_y -= 3
    elif direction == "back":
        car_y += 3
    elif direction == "left":
        car_x -= 3
    elif direction == "right":
        car_x += 3

    # 将当前位置添加到路径
    path.append((car_x, car_y))

    # 发送路径更新给所有客户端
    emit('update_path', {'path': path}, broadcast=True)

if __name__ == '__main__':
    socketio.run(app)