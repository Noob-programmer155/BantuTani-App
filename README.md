# BantuTani-App
[![made-with-python](https://img.shields.io/badge/Made%20with-Python-1f425f.svg)](https://www.python.org/)
[![TensorFlow 2.6](https://img.shields.io/badge/TensorFlow-2.6.0-FF6F00?logo=tensorflow)](https://github.com/tensorflow/tensorflow/releases/tag/v2.6.0)
[![made-with-java](https://img.shields.io/badge/Made%20with-Java-0066ff.svg)](https://www.java.com/en/)
[![made-with-javascript](https://img.shields.io/badge/Made%20with-JavaScript-ff9900.svg)](https://www.javascript.com)
[![made-with-kotlin](https://img.shields.io/badge/Made%20with-Kotlin-0099ff.svg)](https://developer.android.com/kotlin)


*BantuTani* was created to **help overcome problems and use technology in agriculture**, especially agricultural plant diseases and weeds, by **detecting and providing recommendations** for appropriate and efficient treatment based on detected infections. This project also has other special features, namely **Tips & Tricks, News, Recommendations on the prices, then Expert Consultation, Marketplace, IoT system, and also Maps spreads disease and pest of agricultural commodities for future development**. Latest. This project was motivated by the lack of technology in agriculture and the lack of information on how to plant, care for, and provide the latest prices for a commodity.

**YOU CAN SEE OUR PRODUCT PREVIEW IN:**

[![Youtube](https://img.shields.io/badge/YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white)](https://youtu.be/qH1dMe82DmM)
[![Word](https://img.shields.io/badge/Microsoft_Word-2B579A?style=for-the-badge&logo=microsoft-word&logoColor=white)](https://docs.google.com/document/d/1v6GmTeA4jFQkNRG6ekkHuYs68xKbUM8CDukNPodPa8s/edit)
[![PPT](https://img.shields.io/badge/Microsoft_PowerPoint-B7472A?style=for-the-badge&logo=microsoft-powerpoint&logoColor=white)](https://docs.google.com/presentation/d/1CFki6cNanchsNQENP0NU8lJ7liPXQn1J/edit#slide=id.g13397611528_3_4)



## Feature
### Leaf Disease Image Classification
**1. Dataset**

[![kaggle](https://img.shields.io/badge/Kaggle-blue?logo=kaggle)](https://www.kaggle.com/datasets/gutierrezsoares/rice-leafs-500px) [![Github](https://img.shields.io/badge/Github-black?logo=github)](./ML/Data/Rice%20Leaf)

The data used are **900 for rice leaves**  Furthermore, each data will be split with the provisions of **75%** for the training data used to build the model and **25%** for the test data used to validate the model.

**2. Modelling**

We use [InceptionV3](https://keras.io/api/applications/inceptionv3/) to build the image classification model, combined with [Transfer Learning](https://www.tensorflow.org/tutorials/images/transfer_learning_with_hub). The model was built using existing training data. The weight model we use can be see in  [Model InceptionV3 Used](https://drive.google.com/drive/folders/1NpAdyMmTS3W-XOsjFMdIoYQwPm1z11uW)  From the Model, we can see that model accuracy up to **90.99%**

[![arsitektur-Model](https://img.shields.io/badge/View%20on-Netron-white?logo=electron)](https://github.com/Noob-programmer155/BantuTani-App/blob/master/ML/rice_clf_9099.h5.svg) `For the Rice leaf diseases Image Classification Architecture model Using InceptionV3`


**3. Notebook**

`The notebooks that we use can be accessed in` [![Open In Collab](https://colab.research.google.com/assets/colab-badge.svg)](https://colab.research.google.com/drive/1l9_dAxV-LEz0P0XM1NPZkoIcPY9vlATN?usp=sharing#scrollTo=DoxXRDQgRLV5)

### Tips And Trick 

In this section, we will show tips and tricks in the world of agriculture, such as how to plant, seed selection, plant care and how to deal with plant diseases.

### Price Recomendation

will display the latest price information of agricultural commodities, so that farmers or the general public can update and find out the latest commodity prices

### News 
Contains the latest information about the world of agriculture. It is hoped that with this feature the user can find out the development of the agricultural world



## Next Feature Release

### Consultation **[COMING SOON]**
 In this feature, users will be able to communicate or share experiences about agricultural problem with some expert in their field regarding the problems they are facing. 
 
### Marketplace **[COMING SOON]**
This feature is expected to be a bridge for farmers, supplier agricultural needs, distributors, and consumers to make transactions for agricultural products.  the concept about marketplace is we will make 2 role market for supplier agricultural needs and for products from farmers it self and we provide special section to communicate with distributors that have short distance with the farmer, and farmer can choose distributor which will be selected to sell there taking into account the price given by the distributor.

### IoT System **[COMING SOON]**
utilization in this apps as remote control device


## Other Notes

### Cloud Compute Jobs
- Make Environment both in development and production.
- Make Rest API service
- Provide the need to run the detection model

### Android Jobs
- Make UI/UX apps 
- Configure android environment

-------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Credit For:**

*©BantuTani Team's*
*Bangkit Academy 2022*

