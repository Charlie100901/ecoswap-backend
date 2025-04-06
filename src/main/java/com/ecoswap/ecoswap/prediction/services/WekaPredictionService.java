package com.ecoswap.ecoswap.prediction.services;

import com.ecoswap.ecoswap.exchange.models.dto.ExchangeDTO;
import com.ecoswap.ecoswap.exchange.repositories.ExchangeRepository;
import com.ecoswap.ecoswap.prediction.models.dto.PredictionResult;
import com.ecoswap.ecoswap.product.models.entities.Product;
import com.ecoswap.ecoswap.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.core.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WekaPredictionService {

    private Classifier classifier;
    private Instances datasetStructure;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    public WekaPredictionService() {
        try {
            classifier = (Classifier) SerializationHelper.read(
                    getClass().getClassLoader().getResourceAsStream("dataset_ecoswap.model")
            );

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("dataset_ecoswap.arff")
            ));
            datasetStructure = new Instances(reader, 1);
            datasetStructure.setClassIndex(datasetStructure.numAttributes() - 1);
            reader.close();
        } catch (Exception e) {
            throw new IllegalStateException("Error al inicializar WekaPredictionService: " + e.getMessage(), e);
        }
    }

    public PredictionResult predictExchangeSuccess(Long productId, int daysPublished, Long interactions, Long userSuccessHistory, String userRating, String location) {
        try {
            Instance newInstance = new DenseInstance(datasetStructure.numAttributes());
            newInstance.setDataset(datasetStructure);

            newInstance.setValue(datasetStructure.attribute("product_id"), productId);
            newInstance.setValue(datasetStructure.attribute("days_published"), daysPublished);
            newInstance.setValue(datasetStructure.attribute("interactions"), interactions);
            newInstance.setValue(datasetStructure.attribute("user_success_history"), userSuccessHistory);
            newInstance.setValue(datasetStructure.attribute("user_rating"), userRating);
            newInstance.setValue(datasetStructure.attribute("location"), location);

            double prediction = classifier.classifyInstance(newInstance);
            double[] distribution = classifier.distributionForInstance(newInstance);

            String predictedClass = datasetStructure.classAttribute().value((int) prediction);
            boolean success = predictedClass.equalsIgnoreCase("True");

            return new PredictionResult(success, distribution[datasetStructure.classAttribute().indexOfValue("True")]);

        } catch (Exception e) {
            return new PredictionResult(false, 0.0);
        }
    }

    public Map<String, Object> generatePredictionResponse(ExchangeDTO requestExchange) {
        Product productFrom = productRepository.findById(requestExchange.getProductFrom().getId())
                .orElseThrow(() -> new IllegalStateException("El producto origen no existe."));
        Product productTo = productRepository.findById(requestExchange.getProductTo().getId())
                .orElseThrow(() -> new IllegalStateException("El producto destino no existe."));

        int daysPublished = productFrom.getReleaseDate() != null ?
                (int) java.time.temporal.ChronoUnit.DAYS.between(productFrom.getReleaseDate(), LocalDateTime.now()) : 0;

        Long interactions = exchangeRepository.countByProductTo(productTo);
        Long userSuccessHistory = exchangeRepository.countByProductTo_User(productTo.getUser().getId());

        double userRating = 4.5;
        String userRatingCategory = userRating >= 4.5 ? "bueno" : userRating >= 3.0 ? "regular" : "malo";

        String location = "medellin"; // o puedes usar otro campo del producto

        PredictionResult result = predictExchangeSuccess(
                productTo.getId(), daysPublished, interactions, userSuccessHistory, userRatingCategory, location
        );

        String confidenceLevel = result.getProbability() > 0.8 ? "alto" :
                result.getProbability() > 0.5 ? "medio" : "bajo";
        String message = result.isPrediction()
                ? "La predicción indica que el intercambio será exitoso."
                : "La predicción indica que este intercambio no será exitoso.";

        Map<String, Object> response = new HashMap<>();
        response.put("prediction", result.isPrediction());
        response.put("probability", result.getProbability());
        response.put("confidenceLevel", confidenceLevel);
        response.put("message", message);

        return response;
    }
}
