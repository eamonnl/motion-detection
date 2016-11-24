package com.workday.hackathon.outfox;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WebCamCapture {

	private static final Logger log = LoggerFactory.getLogger(WebCamCapture.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	private final int webcamDeviceId = 0; // Default Camera

	private VideoCapture videoCapture;

	@PostConstruct
	public void initIt() throws Exception {
		this.videoCapture = new VideoCapture(webcamDeviceId);
		this.videoCapture.open(webcamDeviceId);
	}

	@PreDestroy
	public void cleanUp() throws Exception {
		this.videoCapture.release();
	}

	public Mat captureImage() {

		if (!videoCapture.isOpened()) {
			log.error("Video capture device not available");
			return null;
		} else {
			log.info("Capturing " + videoCapture.get(CaptureProperty.FRAME_WIDTH) + "x"
					+ videoCapture.get(CaptureProperty.FRAME_HEIGHT) + " frame at " + dateFormat.format(new Date()));
			Mat image = new Mat();
			videoCapture.read(image);
			return image;
		}

	}

	public Size getVideoSize() {
		int videoWidth = (int) videoCapture.get(CaptureProperty.FRAME_WIDTH);
		int videoHeight = (int) videoCapture.get(CaptureProperty.FRAME_HEIGHT);
		return new Size(videoWidth, videoHeight);
	}

	// Some constants copied from c code
	public static class CaptureProperty {

		public static final int POS_MSEC = 0;
		public static final int POS_FRAMES = 1;
		public static final int POS_AVI_RATIO = 2;
		public static final int FRAME_WIDTH = 3;
		public static final int FRAME_HEIGHT = 4;
		public static final int FPS = 5;
		public static final int FRAME_COUNT = 7;
		public static final int BRIGHTNESS = 10;
		public static final int CONTRAST = 11;
		public static final int SATURATION = 12;
		public static final int HUE = 13;
		public static final int GAIN = 14;
		public static final int EXPOSURE = 15;
		public static final int CONVERT_RGB = 16;
		public static final int WHITE_BALANCE_BLUE_U = 17;
		public static final int RECTIFICATION = 18;
		public static final int MONOCROME = 19;
		public static final int SHARPNESS = 20;
		public static final int GAMMA = 22;
		public static final int TEMPERATURE = 23;
		public static final int ZOOM = 27;
		public static final int FOCUS = 28;
		public static final int ISO_SPEED = 30;
		public static final int BACKLIGHT = 32;
		
	}
	
}
