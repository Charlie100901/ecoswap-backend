package com.ecoswap.ecoswap.prediction.models.dto;

public class PredictionResult {
    private boolean prediction;
    private double probability;

    public PredictionResult(boolean prediction, double probability) {
        this.prediction = prediction;
        this.probability = probability;
    }

    public boolean isPrediction() {
        return prediction;
    }

    public double getProbability() {
        return probability;
    }
}
