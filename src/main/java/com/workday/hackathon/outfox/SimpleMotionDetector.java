package com.workday.hackathon.outfox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Just looks at the difference between this frame and the previous one and uses
 * a threshold to decide whether there is significant motion (excluding camera
 * artifacts)
 * 
 * @author eamonn.linehan
 */
@Component
@Qualifier("simple")
public class SimpleMotionDetector implements MotionDetector {

	// Autowired by constructor - used to send events when motion is detected
	private final ApplicationEventPublisher publisher;

	// Smaller makes algo run faster
	private Size size = new Size(640, 480);

	private Mat lastFrame, currentFrame, diffFrame;

	boolean firstFrame = true;

	@Autowired
	public SimpleMotionDetector(ApplicationEventPublisher publisher) {
		super();
		this.publisher = publisher;
	}

	@Override
	public void detectMotion(Mat frame, Size frameSize) {

		Mat unmodifiedVideoFrame = frame.clone();

		if (!frameSize.equals(size))
			Imgproc.resize(frame, frame, size);

		currentFrame = Mat.zeros(size, CvType.CV_8UC1);

		Imgproc.cvtColor(frame, currentFrame, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(currentFrame, currentFrame, new Size(3, 3), 0);

		diffFrame = Mat.zeros(size, CvType.CV_8UC1);

		if (firstFrame) {
			lastFrame = currentFrame.clone();
			firstFrame = false;
		} else {

			Core.subtract(lastFrame, currentFrame, diffFrame);

			Imgproc.adaptiveThreshold(diffFrame, diffFrame, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C,
					Imgproc.THRESH_BINARY_INV, 5, 2);

			ArrayList<Rect> array = detection_contours(diffFrame, frame);
			if (array.size() > 0) {

				Iterator<Rect> it2 = array.iterator();
				while (it2.hasNext()) {
					Rect obj = it2.next();
					Core.rectangle(frame, obj.br(), obj.tl(), new Scalar(0, 255, 0), 1);
				}

				this.publisher.publishEvent(new MotionDetectedEvent(100, unmodifiedVideoFrame, frame));

			}

		}

		// Current frame becomes last for next time method is called
		lastFrame = currentFrame;

	}

	public static ArrayList<Rect> detection_contours(Mat outmat, Mat resultFrame) {
		Mat v = new Mat();
		Mat vv = outmat.clone();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(vv, contours, v, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

		double maxArea = 100;
		int maxAreaIdx = -1;
		Rect r = null;
		ArrayList<Rect> rect_array = new ArrayList<Rect>();

		for (int idx = 0; idx < contours.size(); idx++) {
			Mat contour = contours.get(idx);
			double contourarea = Imgproc.contourArea(contour);
			if (contourarea > maxArea) {
				// maxArea = contourarea;
				maxAreaIdx = idx;
				r = Imgproc.boundingRect(contours.get(maxAreaIdx));
				rect_array.add(r);
				Imgproc.drawContours(resultFrame, contours, maxAreaIdx, new Scalar(0, 0, 255));
			}

		}

		v.release();

		return rect_array;

	}

}
