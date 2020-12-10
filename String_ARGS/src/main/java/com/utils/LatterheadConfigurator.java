package com.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class LatterheadConfigurator {

	@Autowired
	private Environment environment;

	public void configureLatterHead(Map<String, Object> map) {
		String fileSeparator = File.separator;
		try {

			String img = "prescription.jpg";

			final BufferedImage image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(img));
			String doctorName = (String) map.get("username");
			String degree = (String) map.get("qualification");
			String speciality = (String) map.get("speciality");
			String regNo = (String) map.get("registration_no");
			String hospitalName = (String) map.get("entityName");
			String address = (String) map.get("address");
			String city = (String) map.get("city");
			String state = (String) map.get("state");
			String pin = (String) map.get("pincode");
			String cityStatePin = city + ", " + state + " - " + pin;

			// Creating the directory

			String rootDir = environment.getProperty("Entity.doctor.filepath");
			int projectId = (Integer) map.get("projectId");
			int entityId = (Integer) map.get("entityId");
			int doctorId = (Integer) map.get("userId");
			String path = rootDir + fileSeparator + entityId;
			File file = new File(path);
			if (!file.exists()) {
				file.mkdir();
			}
			path = rootDir + fileSeparator + entityId + fileSeparator + doctorId;
			file = new File(path);
			if (!file.exists()) {
				file.mkdir();
			}
			path = rootDir + fileSeparator + entityId + fileSeparator + doctorId + "\\images";
			file = new File(path);
			if (!file.exists()) {
				file.mkdir();
			}
			path = rootDir + fileSeparator + entityId + fileSeparator + doctorId + "\\videos";
			file = new File(path);
			if (!file.exists()) {
				file.mkdir();
			}
			path = rootDir + fileSeparator + entityId + fileSeparator + doctorId + "\\stamps";
			file = new File(path);
			if (!file.exists()) {
				file.mkdir();
			}

			// Add dynamic code from database

			Graphics g = image.getGraphics();

			// Clinic Info Code
			// Clinic Info Code
			g.setColor(Color.BLACK);
			g.setFont(new Font("Times New Roman", Font.BOLD, 18));
			g.drawString(doctorName, 40, 50);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			g.drawString(degree, 40, 70);
			g.drawString(speciality, 40, 90);
			g.drawString(regNo, 40, 110);

			// Doctor Info Code
			/*
			 * g.setFont(new Font("Times New Roman", Font.BOLD, 18));
			 * g.drawString(hospitalName, 450, 50); g.setFont(new
			 * Font("Times New Roman", Font.PLAIN, 12)); g.drawString(address,
			 * 450, 70); g.drawString(cityStatePin, 450, 90);
			 */

			// Add powered by string
			g.setFont(new Font("Times New Roman", Font.BOLD, 12));
			g.drawString("Powered By PurpleDocs", 600, 900);

			ImageIO.write(image, "jpg", new File(rootDir + fileSeparator + entityId + fileSeparator + doctorId
					+ fileSeparator + "prescription.jpg"));

			String destination = rootDir + fileSeparator + entityId + fileSeparator + doctorId + fileSeparator
					+ "medicine.txt";
			String filename = "medicine.txt";
			CopyFile(filename, destination);

			destination = rootDir + fileSeparator + entityId + fileSeparator + doctorId + fileSeparator
					+ "config.properties";
			filename = "config.properties";
			CopyFile(filename, destination);

		} catch (IOException ioException) {
			System.out.println("====" + ioException);
			ioException.printStackTrace();
		}
	}

	public void CopyFile(String filename, String destination) {

		InputStream in = null;
		OutputStream out = null;
		try {
			in = this.getClass().getClassLoader().getResourceAsStream(filename);
			out = new FileOutputStream(destination);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (IOException ioException) {
			System.out.println(ioException);
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException exception) {
				System.out.println(exception);
			}
		}
	}

}
