package com.dataset.processing.generation.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record GenerateExcelRequest(
	@NotNull(message = "records is required")
	@Min(value = 1, message = "records must be at least 1")
	@Max(value = 1_000_000, message = "records must be at most 1000000")
	Integer records
) {
}
