<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Dashboard</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to bottom, #4c6de5, #3f51b5);
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
            color: white;
        }

        .user-profile {
            position: fixed;
            top: 20px;
            right: 20px;
            display: flex;
            align-items: center;
            z-index: 999;
        }

        .user-avatar {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            overflow: hidden;
            margin-right: 10px;
        }

        .user-avatar img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .me {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            flex-direction: column;
        }

        .right {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-around;
            background-color: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
            padding: 20px;
            margin-top: 20px;
        }

        .pic {
            width: 150px;
            height: 150px;
            object-fit: cover;
            border-radius: 10px;
            margin: 10px;
        }

        .bottom {
            width: 200px;
            height: 60px;
            background-color: #ffffff;
            color: #333333;
            text-align: center;
            padding-top: 10px;
            border: 1px solid #ccc;
            border-radius: 10px;
            margin-top: 20px;
            font-size: 1.2em;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
        }
    </style>
</head>

<body>

    <div class="user-profile">
        <div class="user-avatar">
            <!-- Replace with user avatar image -->
            <img src="user_avatar.png" alt="User Avatar">
        </div>
    </div>

    <div class="me">
        <div class="right">
            <!-- Images will be dynamically added here by JavaScript -->
        </div>
        <div class="bottom">
            Treasure Count: <span id="treasure-count">0</span> (Images Loaded)
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            var imageContainer = document.querySelector(".right");
            var treasureCountElement = document.getElementById("treasure-count");

            var imageNames = ["1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg", "7.jpg", "8.jpg"
            	, "9.jpg", "10.jpg", "11.jpg", "12.jpg", "13.jpg", "14.jpg", "15.jpg"
            	, "16.jpg", "17.jpg", "18.jpg", "19.jpg", "20.jpg"];
            var successfullyLoadedImages = 0;

            imageNames.forEach(function (imageName) {
                var imgPath = "Pics/" + imageName;
                var img = new Image();

                img.onload = function () {
                    var imgElement = document.createElement("img");
                    imgElement.src = imgPath;
                    imgElement.className = "pic";
                    imgElement.alt = imageName;

                    // Check if the image is loaded successfully
                    imgElement.onerror = function () {
                        imgElement.style.display = "none"; // Hide the image if it fails to load
                    };

                    imageContainer.appendChild(imgElement);
                    successfullyLoadedImages++;
                    treasureCountElement.textContent = successfullyLoadedImages;
                };

                img.src = imgPath;
            });
        });
    </script>

</body>

</html>

