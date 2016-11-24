package com.workday.hackathon.outfox;

import org.opencv.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Connects with OpenCV (requires native libraries) to capture video and detect
 * cars
 * 
 * 
 * 
 * @author eamonn.linehan
 */
@SpringBootApplication
@EnableScheduling
public class OpenCVCarDetectionApplication {

	private static final Logger logger = LoggerFactory.getLogger(OpenCVCarDetectionApplication.class);

	static {

		final String libraryPath = "/usr/local/Cellar/opencv/2.4.13.1/share/OpenCV/java/lib" + Core.NATIVE_LIBRARY_NAME
				+ ".dylib";
		logger.info("Loading OpenCV library: " + libraryPath);
		System.load(libraryPath);

	}

	public static void main(String[] args) {

		SpringApplicationBuilder application = new SpringApplicationBuilder()
				.sources(OpenCVCarDetectionApplication.class).web(false);
		application.run(args);

	}

}
