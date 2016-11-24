package com.workday.hackathon.outfox;

import org.opencv.core.Mat;
import org.opencv.core.Size;

public interface MotionDetector {

	void detectMotion(Mat image, Size frameSize);

}