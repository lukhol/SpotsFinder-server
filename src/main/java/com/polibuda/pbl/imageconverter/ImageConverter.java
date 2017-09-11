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
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ImageConverter {
		
		private final static int MINIATURE_RESOLUTION = 300;

		public String createMiniature(Image firstPhoto) throws IOException {
			log.info("Creating miniature from first photo.");
			
			String originalBase64 = firstPhoto.getImage();
			byte[] originalInByteArray = Base64.decode(originalBase64);
			InputStream inputStream = new ByteArrayInputStream(originalInByteArray);
			BufferedImage original = ImageIO.read(inputStream);
			
			int w = original.getWidth();
			int h = original.getHeight();
			
			double s;
			
			if(w>h) {
				s = ((double) MINIATURE_RESOLUTION)/w;
			} else {
				s = ((double) MINIATURE_RESOLUTION)/h;
			}
			
			BufferedImage after = new BufferedImage((int)(w*s), (int)(h*s), BufferedImage.TYPE_3BYTE_BGR);
			
			log.debug("Scale( s = {} )", s);
			
			AffineTransform at = new AffineTransform();
			at.scale(s, s);
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(original, after);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(after, "jpg", baos);
			baos.flush();
			byte[] afterInByteArray = baos.toByteArray();
			baos.close();
			String afterBase64 = Base64.encode(afterInByteArray);
			
			return afterBase64;
		}

}
