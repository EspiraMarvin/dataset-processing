package com.dataset.processing.generation.dto;

public record GenerateExcelResponse(
	String fileName,
	String filePath,
	int recordsGenerated,
	long generationTimeMs
) {
}
