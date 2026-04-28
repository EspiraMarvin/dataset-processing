package com.dataset.processing.generation;

import com.dataset.processing.generation.dto.GenerateExcelRequest;
import com.dataset.processing.generation.dto.GenerateExcelResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/data-generation")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentDataGenerationController {

	private final StudentExcelGenerationService generationService;

	public StudentDataGenerationController(StudentExcelGenerationService generationService) {
		this.generationService = generationService;
	}

	@PostMapping("/excel")
	@ResponseStatus(HttpStatus.CREATED)
	public GenerateExcelResponse generateExcel(@Valid @RequestBody GenerateExcelRequest request) throws IOException {
		return generationService.generateExcel(request.records());
	}
}
