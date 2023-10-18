import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QLabel, QVBoxLayout, QPushButton, QFileDialog, QGraphicsScene, \
    QGraphicsView, QGraphicsPixmapItem, QWidget, QHBoxLayout
from PyQt5.QtGui import QPixmap
from PyQt5.QtCore import Qt

class ObjectRecognitionApp(QMainWindow):
    def __init__(self, original_image_path, new_image_path):
        super().__init__()

        self.original_image_path = original_image_path
        self.new_image_path = new_image_path

        self.initUI()

    def initUI(self):
        self.setWindowTitle('YOLOv8 Image Recognition Display')
        self.setGeometry(100, 100, 3000, 3000)

        # Create the main window layout
        central_widget = QWidget()
        self.setCentralWidget(central_widget)

        # Create a horizontal layout
        layout = QHBoxLayout()
        central_widget.setLayout(layout)

        # Create the left area for displaying the original image
        self.original_image_label = QLabel(self)
        layout.addWidget(self.original_image_label)

        # Create the "Identify" button at the bottom
        self.identify_button = QPushButton('Identify', self)
        self.identify_button.clicked.connect(self.on_identify_button_click)
        # Set the button's style sheet to white
        self.identify_button.setStyleSheet('background-color: white;')
        layout.addWidget(self.identify_button)

        # Create the right area for displaying the new image
        self.new_image_scene = QGraphicsScene(self)
        self.new_image_view = QGraphicsView(self)
        self.new_image_view.setScene(self.new_image_scene)
        layout.addWidget(self.new_image_view)

        # Display the original image
        self.display_original_image(self.original_image_path)

    def display_new_image(self):
        # Display the new image on the right side
        pixmap = QPixmap(self.new_image_path)
        item = QGraphicsPixmapItem(pixmap)
        self.new_image_scene.clear()
        self.new_image_scene.addItem(item)

    def display_original_image(self, image_path):
        pixmap = QPixmap(image_path)
        self.original_image_label.setPixmap(pixmap)
        self.original_image_label.setAlignment(Qt.AlignCenter)

    def on_identify_button_click(self):
        # After clicking the 'Identify' button, change the button color to green
        self.identify_button.setStyleSheet('background-color: green;')
        # Perform a new functionality, such as displaying the new image
        self.display_new_image()

if __name__ == '__main__':
    # Paths to the original and new images
    original_image_path = "original_image_path"
    new_image_path = "new_image_path"

    app = QApplication(sys.argv)
    window = ObjectRecognitionApp(original_image_path, new_image_path)
    window.show()

    sys.exit(app.exec_())
