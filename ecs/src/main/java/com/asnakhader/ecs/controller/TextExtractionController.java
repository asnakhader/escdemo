package com.asnakhader.ecs.controller;

import com.asnakhader.ecs.ECSUserInput;
import com.asnakhader.ecs.request.ImageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/ocr")
public class TextExtractionController {

	@PostMapping("/extract")
	public ResponseEntity<String> extractTextFromBase64(@RequestBody ImageRequest request) {
		File tempFile = null;

		try {
			// Decode base64 image
			byte[] imageBytes = DatatypeConverter.parseBase64Binary(request.getBase64VisaImage());
			System.out.println("Decoded base64 preview: " + Arrays.toString(Arrays.copyOf(imageBytes, 10)));

			// Convert bytes to BufferedImage
			InputStream in = new ByteArrayInputStream(imageBytes);
			BufferedImage originalImage = ImageIO.read(in);

			// Convert to grayscale
			BufferedImage grayImage = new BufferedImage(
					originalImage.getWidth(),
					originalImage.getHeight(),
					BufferedImage.TYPE_BYTE_GRAY);
			Graphics g = grayImage.getGraphics();
			g.drawImage(originalImage, 0, 0, null);
			g.dispose();

			// Write bytes to a temp file
			/*tempFile = File.createTempFile("ocr_", ".jpg");
			try (FileOutputStream fos = new FileOutputStream(tempFile)) {
				fos.write(imageBytes);
			}*/
			// Write grayscale image to temp file
			tempFile = File.createTempFile("ocr_", ".jpg");
			ImageIO.write(grayImage, "jpg", tempFile);

			// OCR with Tesseract
			ITesseract tesseract = new Tesseract();

			// Get the absolute path to 'tessdata' inside resources
			//String tessdataPath = getClass().getClassLoader().getResource("tessdata").getPath();

			tesseract.setDatapath("C:/Users/S485829/forsatek/escdemo/ecs/target/classes/tessdata");
			tesseract.setLanguage("eng");

			//tesseract.setDatapath(tessdataPath); // folder containing eng.traineddata
			//tesseract.setLanguage("eng");
			tesseract.setLanguage("eng+osd");

           // Fine-tune OCR behavior
			tesseract.setOcrEngineMode(1);     // LSTM only
			tesseract.setPageSegMode(1);       //
			tesseract.setTessVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 -/");
            // Optional: enable DPI guessing (if image has no resolution metadata)
			tesseract.setTessVariable("user_defined_dpi", "300");
			tesseract.getWords(grayImage,1);

			String text = tesseract.doOCR(tempFile);
			return ResponseEntity.ok(text);

		} catch (IOException | TesseractException e) {
			return ResponseEntity.status(500).body("OCR failed: " + e.getMessage());
		} finally {
			if (tempFile != null) tempFile.delete();
		}
	}

	/*public static Map<String, String> extractVisaFields(File imageFile) throws TesseractException {
		ITesseract tesseract = new Tesseract();
		tesseract.setDatapath("your-tessdata-path");  // e.g., C:/your/path/tessdata
		tesseract.setLanguage("eng+osd");
		tesseract.setPageSegMode(1);  // Automatic layout

		// Get OCR result as TSV
		String tsv = tesseract.getWords(imageFile, 1);  // Level 1 = word level

		Map<String, String> extractedFields = new HashMap<>();
		List<String> lines = Arrays.asList(tsv.split("\n"));

		for (String line : lines) {
			if (line.startsWith("level") || line.trim().isEmpty()) continue;  // Skip header

			String[] parts = line.split("\t");
			if (parts.length < 12) continue;

			String word = parts[11];
			int conf = Integer.parseInt(parts[10]);

			// Filter low-confidence junk
			if (conf < 60 || word.trim().isEmpty()) continue;

			// Example mapping logic (very simple, can be improved)
			if (word.toLowerCase().contains("passport")) {
				// Look for the next valid word
				int currentIndex = lines.indexOf(line);
				for (int j = currentIndex + 1; j < lines.size(); j++) {
					String[] nextParts = lines.get(j).split("\t");
					if (nextParts.length < 12) continue;
					String nextWord = nextParts[11];
					int nextConf = Integer.parseInt(nextParts[10]);
					if (nextConf > 60 && !nextWord.trim().isEmpty()) {
						extractedFields.put("Passport Number", nextWord);
						break;
					}
				}
			}

			if (word.toLowerCase().contains("surname")) {
				extractedFields.put("Surname", word);  // Can improve logic here too
			}
		}

		return extractedFields;
	}*/
}
