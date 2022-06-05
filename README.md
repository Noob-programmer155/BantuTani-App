# BantuTani-App
[![made-with-python](https://img.shields.io/badge/Made%20with-Python-1f425f.svg)](https://www.python.org/)
[![TensorFlow 2.6](https://img.shields.io/badge/TensorFlow-2.6.0-FF6F00?logo=tensorflow)](https://github.com/tensorflow/tensorflow/releases/tag/v2.6.0)
[![made-with-javascript](https://img.shields.io/badge/Made%20with-JavaScript-1f425f.svg)](https://www.javascript.com)

Application that use to help farmer or people who want to plant, maintain, and sell agricultural plant with an easy step to understand, reliable, and right on target
## Feature
### Leaf Disease Image Detection
**1. Dataset**
- For Corn Leaf we use data from [![kaggle](https://img.shields.io/badge/Kaggle-blue?logo=kaggle)](https://www.kaggle.com/datasets/abdulhasibuddin/cornplantdocdataset) [![Github](https://img.shields.io/badge/Github-black?logo=github)](./ML/Data/Corn%20Leaf)
- For Rice Leaf we use data from [![kaggle](https://img.shields.io/badge/Kaggle-blue?logo=kaggle)](https://www.kaggle.com/datasets/gutierrezsoares/rice-leafs-500px) [![Github](https://img.shields.io/badge/Github-black?logo=github)](./ML/Data/Rice%20Leaf)

The data used are **900 for rice leaves** and **192 for corn leaves**. Furthermore, each data will be split with the provisions of **75%** for the training data used to build the model and **25%** for the test data used to validate the model. The results of the data split can be accessed [Corn Leaf](./ML/Data/Corn%20Leaf) and [Rice Leaf](./ML/Data/Rice%20Leaf)

**2. Modelling**

We use [TFLite Image Detection](https://www.tensorflow.org/lite/examples/object_detection/overview) to build the image detection model. To simplify the process of training the TensorFlow Lite model and shorten the training time and maximize the model, we use [TensorFlow Lite Model Maker Library](https://www.tensorflow.org/lite/api_docs/python/tflite_model_maker/object_detector) combined with the data we have. The weight model we use can be see in  [Corn Leaf](./ML/Model/Image%20Detection/Corn%20Leaf) and [Rice Leaf](./ML/Model/Image%20Detection/Rice%20Leaf)
-  [![arsitektur-Corn](https://img.shields.io/badge/View%20on-Netron-white?logo=electron)](./ML/Model/Image%20Detection/Corn%20Leaf/corn_od_ef1.tflite.svg) `For the Corn leaf diseases Image detection Architecture model`
-  [![arsitektur-Rice](https://img.shields.io/badge/View%20on-Netron-white?logo=electron)](./ML/Model/Image%20Detection/Rice%20Leaf/rice_od_ef1.tflite.svg) `For the Rice leaf diseases Image detection Architecture model`

**3. Notebook**

- `The notebooks that we use in Rice Leaf Disease Image Detection can be accessed in` [![Open In Collab](https://colab.research.google.com/assets/colab-badge.svg)](https://colab.research.google.com/github/Naereen/badges)
- `The notebooks that we use in Corn Leaf Disease Image Detection can be accessed in` [![Open In Collab](https://colab.research.google.com/assets/colab-badge.svg)](https://colab.research.google.com/github/Naereen/badges)
