package com.workday.hackathon.outfox;

import javax.annotation.PostConstruct;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledImageCaptureTask {

	@Autowired
	private WebCamCapture webcamCapture;
	
	@Autowired
	@Qualifier("simple")
	private MotionDetector motionDetection;

	private Size videoSize;
	
	@PostConstruct
	private void init() {
		videoSize = webcamCapture.getVideoSize();
	}
	
	
	@Scheduled(fixedRate = 2000)
	public void reportCurrentTime() {
		
		Mat image = webcamCapture.captureImage();
		
		if (image!= null && !image.empty())
			motionDetection.detectMotion(image, videoSize);
	}

}
