# android Sensitive Methods Detection
A machine learning approach for detecting and categorizing sensitive methods in Android malware


In this research, we proposed a machine learning technique for detecting and categorizing sensitive methods in Android malware. We worked with a dataset we have extracted from a previous work. then we extended our dataset with a new set of Android Java methods. 

The next stage of our work is to extract a set of attributes from our dataset in order to train our system.  Finally, we tested the trained system using a real android malwares.

the code is structured as followng:
- android27: contain a sample of the packages provided by Android Api of version 27
- samples: contains a sample of the real malwares used to test our system
- dataset.csv : represent the dataset we have extracted from our previous work
- ExtractAndroidAppMethods.py: is a script used to extract the method attributes from the malware samples
- ExtractJavaApiMethods.py: is a script used to extract the method attributes from the  Android Api of version 27
- Preprocessing.py : is a script used to extend the current attributes to train our classifiers
- ProposedSystem.ipynb: contain the code of the proposed system
