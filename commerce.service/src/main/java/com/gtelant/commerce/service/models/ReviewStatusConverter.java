package com.gtelant.commerce.service.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReviewStatusConverter implements AttributeConverter<ReviewStatus, String> {
    @Override
    public String convertToDatabaseColumn(ReviewStatus attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }
    @Override
    public ReviewStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ReviewStatus.valueOf(dbData.toUpperCase());
    }
}
