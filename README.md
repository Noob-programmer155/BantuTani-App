# BantuTani-App
[![made-with-python](https://img.shields.io/badge/Made%20with-Python-1f425f.svg)](https://www.python.org/)
[![TensorFlow 2.6](https://img.shields.io/badge/TensorFlow-2.6.0-FF6F00?logo=tensorflow)](https://github.com/tensorflow/tensorflow/releases/tag/v2.6.0)
[![made-with-javascript](https://img.shields.io/badge/Made%20with-JavaScript-1f425f.svg)](https://www.javascript.com)

Application that use to help farmer or people who want to plant, maintain, and sell agricultural plant with an easy step to understand, reliable, and right on target
## Feature
### Leaf Disease Image Detection
**1. Dataset**

[![kaggle](https://img.shields.io/badge/Kaggle-blue?logo=kaggle)](https://www.kaggle.com/datasets/gutierrezsoares/rice-leafs-500px) [![Github](https://img.shields.io/badge/Github-black?logo=github)](./ML/Data/Rice%20Leaf)

The data used are **900 for rice leaves**  Furthermore, each data will be split with the provisions of **75%** for the training data used to build the model and **25%** for the test data used to validate the model.

**2. Modelling**

We use [InceptionV3](https://keras.io/api/applications/inceptionv3/) to build the image classification model, combined with [Transfer Learning](https://www.tensorflow.org/tutorials/images/transfer_learning_with_hub). The model was built using existing training data. The weight model we use can be see in  [Model InceptionV3 Used](https://drive.google.com/drive/folders/1NpAdyMmTS3W-XOsjFMdIoYQwPm1z11uW)  From the Model, we can see that model accuracy up to **90.99%**

[![arsitektur-Model](https://img.shields.io/badge/View%20on-Netron-white?logo=electron)](https://github.com/Noob-programmer155/BantuTani-App/blob/master/ML/rice_clf_9099.h5.svg) `For the Rice leaf diseases Image Classification Architecture model Using InceptionV3`


**3. Notebook**

`The notebooks that we use can be accessed in` [![Open In Collab](https://colab.research.google.com/assets/colab-badge.svg)](https://colab.research.google.com/drive/1l9_dAxV-LEz0P0XM1NPZkoIcPY9vlATN?usp=sharing#scrollTo=DoxXRDQgRLV5)


## Other Notes

**Cloud Compute Jobs**
- Make Environment both in development and production.
- Make Rest API service
- Provide the need to run the detection model

**Android Jobs**
- Make UI/UX apps 
- Configure android environment
