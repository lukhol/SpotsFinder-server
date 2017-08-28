package com.polibuda.pbl.imageconverter;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.polibuda.pbl.model.Image;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ImageConverter {
	
	private final static int MINIATURE_WIDTH = 30;
	private final static int MINIATURE_HEIGHT = 30;

	public Image createMiniature(Image firstPhoto) throws IOException {
		log.info("Creating miniature from first photo.");
		
		byte[] originalInByteArray = firstPhoto.getImage();
		InputStream inputStream = new ByteArrayInputStream(originalInByteArray);
		BufferedImage original = ImageIO.read(inputStream);
		
		int w = original.getWidth();
		int h = original.getHeight();
		BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				
		double sx = ((double) MINIATURE_WIDTH)/w;
		double sy = ((double) MINIATURE_HEIGHT)/h;
		
		log.debug("Scale( sx = {}, sy = {}", sx, sy);
		
		AffineTransform at = new AffineTransform();
		at.scale(sx, sy);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter(original, after);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(after, "jpg", baos);
		baos.flush();
		byte[] afterInByteArray = baos.toByteArray();
		baos.close();
		
		Image miniature = new Image();
		miniature.setImage(afterInByteArray);
		
		return miniature;
	}

}
