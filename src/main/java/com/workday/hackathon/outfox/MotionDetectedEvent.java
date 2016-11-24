package com.workday.hackathon.outfox;

import org.opencv.core.Mat;

public class MotionDetectedEvent {

	private int confidence;

	private Mat sourceFrame;

	private Mat motionDetectionResultFrame;

	public MotionDetectedEvent(int confidence, Mat sourceFrame, Mat motionDetectionResultFrame) {
		super();
		this.confidence = confidence;
		this.sourceFrame = sourceFrame;
		this.motionDetectionResultFrame = motionDetectionResultFrame;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}

	public Mat getSourceFrame() {
		return sourceFrame;
	}

	public void setSourceFrame(Mat sourceFrame) {
		this.sourceFrame = sourceFrame;
	}

	public Mat getMotionDetectionResultFrame() {
		return motionDetectionResultFrame;
	}

	public void setMotionDetectionResultFrame(Mat motionDetectionResultFrame) {
		this.motionDetectionResultFrame = motionDetectionResultFrame;
	}

}
