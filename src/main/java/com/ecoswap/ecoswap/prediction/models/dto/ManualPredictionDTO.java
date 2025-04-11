package com.ecoswap.ecoswap.prediction.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManualPredictionDTO {
    private Long productToId;
    private int daysPublished;
    private Long interactions;
    private Long userSuccessHistory;
    private String userRatingCategory;
    private String location;
}
