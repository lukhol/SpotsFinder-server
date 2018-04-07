package com.lukhol.spotsfinder.imageconverter;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.lukhol.spotsfinder.model.Image;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ImageConverter {

	private final static int MINIATURE_WIDTH = 350;
	private final static int MINIATURE_HEIGHT = 350;
	private final static int MAX_BYTE_LENGTH = 1048576;

	public String createMiniature(Image firstPhoto) throws IOException {
		log.info("Creating miniature from first photo.");

		byte[] fullSizeImage = base64StringToByteArray(firstPhoto.getImage());
		InputStream inputStream = new ByteArrayInputStream(fullSizeImage);
		BufferedImage original = ImageIO.read(inputStream);
		
		int w = original.getWidth();
		int h = original.getHeight();

		double sx = ((double) MINIATURE_WIDTH) / w;
		double sy = ((double) MINIATURE_HEIGHT) / h;

		BufferedImage after = new BufferedImage(MINIATURE_WIDTH, MINIATURE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

		AffineTransform at = new AffineTransform();
		at.scale(sx, sy);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter(original, after);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(after, "jpg", baos);
		baos.flush();
		
		byte[] afterByteArray = baos.toByteArray();
		baos.close();
		
		return byteArrayToBase64String(afterByteArray);
	}

	public String scaleToMaxOneMbFileSize(String base64Image) throws IOException {
		byte[] imageBytes = base64StringToByteArray(base64Image);
		
		if(imageBytes.length <= MAX_BYTE_LENGTH)
			return base64Image;
		
		int attemptsCount = 0;
		
		while(true) {
			attemptsCount++;
			imageBytes = scaleImageByteTenPercent(imageBytes);
			
			log.debug("Scale attempt, size: " + imageBytes.length);
			
			if(imageBytes.length <= MAX_BYTE_LENGTH || attemptsCount >= 10)
				break;
		}
		
		return byteArrayToBase64String(imageBytes);
	}
	
	private byte[] scaleImageByteTenPercent(byte[] image) throws IOException {
		InputStream inputStream = new ByteArrayInputStream(image);
		BufferedImage original = ImageIO.read(inputStream);
		
		int newWidth = (int)(original.getWidth() * 0.9);
		int newHeight = (int)(original.getHeight() * 0.9);
		
		double sx = ((double) newWidth) / original.getWidth();
		double sy = ((double) newHeight) / original.getHeight();
		
		BufferedImage after = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_3BYTE_BGR);

		AffineTransform at = new AffineTransform();
		at.scale(sx, sy);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter(original, after);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(after, "jpg", baos);
		baos.flush();
		
		byte[] afterByteArray = baos.toByteArray();
		baos.close();
		
		return afterByteArray;
	}
	
	public boolean isValidImage(String base64String) throws IOException {
		byte[] inputAsByte = base64StringToByteArray(base64String);
		
		try(ByteArrayInputStream bais = new ByteArrayInputStream(inputAsByte)) {
			try {
				ImageIO.read(bais).toString();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}
	
	private byte[] base64StringToByteArray(String base64) {
		Base64.Decoder decoder = Base64.getDecoder();
		return decoder.decode(base64);
	}
	
	private String byteArrayToBase64String(byte[] bytes) {
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(bytes);
	}
}
