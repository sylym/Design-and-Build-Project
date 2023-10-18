# Object Detection Program - IoT

## Environment Setup

Before using this program, ensure that you have the required dependencies and configurations in place.

### Prerequisites

- [PyTorch](https://pytorch.org/) (CPU or GPU version based on your preference)
- [Ultralytics](https://github.com/ultralytics/yolov5)
- [Conda](https://docs.conda.io/en/latest/) environment

### Installation

1. Install Conda:

   ```bash
   # Example command for Conda installation
   conda install -c conda-forge conda
   ```

2. Create a Conda environment:

   ```bash
   # Example command for creating a Conda environment
   conda create --name your_env_name python=3.8
   conda activate your_env_name
   ```

3. Install Ultralytics:

   ```bash
   # Example command for Ultralytics installation
   pip install git+https://github.com/ultralytics/yolov5
   ```

4. Install PyTorch:

   ```bash
   # Example command for PyTorch installation (CPU version)
   conda install pytorch torchvision torchaudio cpuonly -c pytorch
   ```

   or for GPU version:

   ```bash
   # Example command for PyTorch installation (GPU version)
   conda install pytorch torchvision torchaudio cudatoolkit=11.1 -c pytorch
   ```

## Training

1. Modify `data.yaml`:

   Edit the `data.yaml` file to include the classes you want to train on. For example, if your dataset includes "book," "cube," and "key," add them to the `data.yaml` file:

   ```yaml
   names:
     - book
     - cube
     - key
   ```

2. Train the model:

   Run the `train.py` file to initiate training:

   ```bash
   python train.py --imgsz 640 --epochs 50 --data your_data.yaml --cfg yolov8n.yaml
   ```

   Adjust `--imgsz` and `--epochs` according to your requirements.

## Inference

### Demo

To run a demonstration using the trained model:

```python
from ultralytics import YOLO
import cv2

model = YOLO("./runs/detect/train15/weights/best.pt")

# Example: predict on an image
results = model("bus.jpg")
print(results)

# Example: predict on a video
video_path = "./video/1.mp4"
cap = cv2.VideoCapture(video_path)
# ... (code for video prediction, as in demo.py)
```

### GUI Version

To use the GUI program for displaying recognition results:

1. Modify `gui_version_1.py`:

   Update the paths for `original_image_path` and `new_image_path` variables with your actual image paths.

2. Run the GUI program:

   ```bash
   python gui_version_1.py
   ```

   This will display a GUI window with the original image on the left and the new image on the right after clicking the "Identify" button.

Note: Make sure to adjust paths and parameters according to your specific file locations and requirements.

