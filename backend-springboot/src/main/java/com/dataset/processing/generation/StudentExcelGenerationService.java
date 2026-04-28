package com.dataset.processing.generation;

import com.dataset.processing.generation.dto.GenerateExcelResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class StudentExcelGenerationService {

	private static final String[] CLASSES = {"Class1", "Class2", "Class3", "Class4", "Class5"};
	private static final LocalDate MIN_DOB = LocalDate.of(2000, 1, 1);
	private static final LocalDate MAX_DOB = LocalDate.of(2010, 12, 31);
	private static final int RANDOM_NAME_MIN = 3;
	private static final int RANDOM_NAME_MAX = 8;

	public GenerateExcelResponse generateExcel(int records) throws IOException {
		long startedAt = System.currentTimeMillis();

		Path outputDir = resolveOutputDirectory();
		Files.createDirectories(outputDir);

		String fileName = "students_" + records + "_" + startedAt + ".xlsx";
		Path filePath = outputDir.resolve(fileName);

		try (SXSSFWorkbook workbook = new SXSSFWorkbook(200);
		     OutputStream outputStream = Files.newOutputStream(filePath)) {

			workbook.setCompressTempFiles(true);
			SXSSFSheet sheet = workbook.createSheet("students");

			CellStyle dateStyle = workbook.createCellStyle();
			CreationHelper creationHelper = workbook.getCreationHelper();
			dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));

			writeHeader(sheet);
			writeRows(sheet, dateStyle, records);

			workbook.write(outputStream);
			sheet.flushRows();
		}

		long elapsed = System.currentTimeMillis() - startedAt;
		return new GenerateExcelResponse(fileName, filePath.toString(), records, elapsed);
	}

	private Path resolveOutputDirectory() {
		String os = System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT);
		if (os.contains("win")) {
			return Path.of("C:\\var\\log\\applications\\API\\dataprocessing");
		}

		return Path.of(System.getProperty("user.home"), "var", "log", "applications", "API", "dataprocessing");
	}

	private void writeHeader(SXSSFSheet sheet) {
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("studentId");
		header.createCell(1).setCellValue("firstName");
		header.createCell(2).setCellValue("lastName");
		header.createCell(3).setCellValue("DOB");
		header.createCell(4).setCellValue("class");
		header.createCell(5).setCellValue("score");
	}

	private void writeRows(SXSSFSheet sheet, CellStyle dateStyle, int records) {
		for (int i = 1; i <= records; i++) {
			Row row = sheet.createRow(i);
			row.createCell(0).setCellValue(i);
			row.createCell(1).setCellValue(randomAlphabetic(RANDOM_NAME_MIN, RANDOM_NAME_MAX));
			row.createCell(2).setCellValue(randomAlphabetic(RANDOM_NAME_MIN, RANDOM_NAME_MAX));

			Date dob = randomDate(MIN_DOB, MAX_DOB);
			row.createCell(3).setCellValue(dob);
			row.getCell(3).setCellStyle(dateStyle);

			row.createCell(4).setCellValue(randomClassName());
			row.createCell(5).setCellValue(ThreadLocalRandom.current().nextInt(55, 76));
		}
	}

	private String randomClassName() {
		return CLASSES[ThreadLocalRandom.current().nextInt(CLASSES.length)];
	}

	private String randomAlphabetic(int minLength, int maxLength) {
		int length = ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);
		StringBuilder value = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char c = (char) ThreadLocalRandom.current().nextInt('a', 'z' + 1);
			value.append(c);
		}
		return value.toString();
	}

	private Date randomDate(LocalDate start, LocalDate end) {
		long startEpoch = start.toEpochDay();
		long endEpoch = end.toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(startEpoch, endEpoch + 1);
		LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
		return Date.from(randomDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
