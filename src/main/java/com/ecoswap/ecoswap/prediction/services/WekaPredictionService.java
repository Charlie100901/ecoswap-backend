package com.ecoswap.ecoswap.prediction.services;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.Attribute;
import weka.core.DenseInstance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class WekaPredictionService {

    private Classifier classifier;
    private Instances datasetStructure;

    public WekaPredictionService() {
        try {
            // Cargar el modelo entrenado desde el classpath
            classifier = (Classifier) SerializationHelper.read(
                getClass().getClassLoader().getResourceAsStream("dataset_ecoswap.model")
            );

            // Leer el dataset ARFF desde el classpath
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("dataset_ecoswap.arff")
            ));
            datasetStructure = new Instances(reader);
            datasetStructure.setClassIndex(datasetStructure.numAttributes() - 1);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Error al inicializar WekaPredictionService: " + e.getMessage(), e);
        }
    }

    public boolean predictExchangeSuccess(Long productId, int daysPublished, Long interactions, Long userSuccessHistory, String userRating, String location) {
        try {
            // Crear atributos
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("product_id"));
            attributes.add(new Attribute("days_published"));
            attributes.add(new Attribute("interactions"));
            attributes.add(new Attribute("user_success_history"));
    
            // Atributo categórico: user_rating
            ArrayList<String> userRatingValues = new ArrayList<>();
            userRatingValues.add("regular");
            userRatingValues.add("malo");
            userRatingValues.add("bueno");
            attributes.add(new Attribute("user_rating", userRatingValues));
    
            // Atributo categórico: location
            ArrayList<String> locationValues = new ArrayList<>();
            locationValues.add("barranquilla");
            locationValues.add("medellin");
            locationValues.add("bogota");
            locationValues.add("cartagena");
            locationValues.add("cali");
            locationValues.add("santa marta");
            locationValues.add("riohacha");
            attributes.add(new Attribute("location", locationValues));
    
            // Atributo de clase: exchange_successful
            ArrayList<String> classValues = new ArrayList<>();
            classValues.add("False");
            classValues.add("True");
            attributes.add(new Attribute("exchange_successful", classValues));
    
            // Crear dataset
            Instances instances = new Instances("Predictions", attributes, 0);
            instances.setClassIndex(instances.numAttributes() - 1);
    
            // Crear una nueva instancia
            Instance newInstance = new DenseInstance(attributes.size());
            newInstance.setValue(attributes.get(0), productId); // product_id
            newInstance.setValue(attributes.get(1), daysPublished); // days_published
            newInstance.setValue(attributes.get(2), interactions); // interactions
            newInstance.setValue(attributes.get(3), userSuccessHistory); // user_success_history
            newInstance.setValue(attributes.get(4), userRating); // user_rating (categórico)
            newInstance.setValue(attributes.get(5), location); // location (categórico)
            newInstance.setDataset(instances);
    
            // Realizar la predicción
            double prediction = classifier.classifyInstance(newInstance);
            return prediction == 1.0; // True si la predicción es "True"
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
