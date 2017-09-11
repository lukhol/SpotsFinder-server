package com.polibuda.pbl.imageconverter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.polibuda.pbl.model.Image;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@RunWith(JUnit4.class)
public class ImageConverterTest {

	private ImageConverter imageConverter = new ImageConverter();

	@Test
	public void testCreateMiniatureSquare() throws IOException {
		String imageName = "300x300.jpg";
		File outputFile = new File("C:\\Users\\Public\\output1.jpg");
		testCreateMiniature(imageName, outputFile);
	}
	
	@Test
	public void testCreateMiniatureRectangle() throws IOException {
		String imageName = "1024x819.jpg";
		File outputFile = new File("C:\\Users\\Public\\output2.jpg");
		testCreateMiniature(imageName, outputFile);
	}
	
	
	private void testCreateMiniature(String imageName, File outputFile) throws IOException {

		BufferedImage before = null;
		before = ImageIO.read(ClassLoader.getSystemResource(imageName));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(before, "jpg", baos);
		baos.flush();
		byte[] beforeInByteArray = baos.toByteArray();
		baos.close();
		String beforeBase64 = Base64.encode(beforeInByteArray);
		Image bigImage = new Image();
		bigImage.setImage(beforeBase64);

		String afterBase64 = imageConverter.createMiniature(bigImage);
		byte[] afterInByteArray = Base64.decode(afterBase64);
		InputStream inputStream = new ByteArrayInputStream(afterInByteArray);
		BufferedImage after = ImageIO.read(inputStream);

		
		ImageIO.write(after, "jpg", outputFile);

		assert true;
	}

}
