from ultralytics import YOLO

# Load the YOLO model
model = YOLO("./runs/detect/train15/weights/best.pt")

# Path to the image
img_path = "img_path"

# Perform object detection on the image
results = model.predict(img_path, save=True, save_txt=True, classes=[0, 1, 2], conf=0.4)
class_value = int(results[0].boxes.cls[0].item())
confidence_value = round(results[0].boxes.conf[0].item(), 2)

print(class_value, confidence_value)
