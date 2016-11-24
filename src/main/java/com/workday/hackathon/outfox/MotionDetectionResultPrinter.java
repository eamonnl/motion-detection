package com.workday.hackathon.outfox;

import org.opencv.highgui.Highgui;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * When motion is detected this class dumps the result frame as an image to disk
 * 
 * TODO Create another one of these event listeners that sends the source frame to the ANPR tool
 * 
 * @author eamonn.linehan
 */
@Component
public class MotionDetectionResultPrinter {

	private static final Logger log = LoggerFactory.getLogger(MotionDetectionResultPrinter.class);

	@EventListener
	public void handleMotionDetectedEvent(MotionDetectedEvent event) {
		log.info("Motion detected! Confidence: " + event.getConfidence() + "%");
		Highgui.imwrite("result" + (System.nanoTime() / 1e9) + ".jpg", event.getMotionDetectionResultFrame());
	}

}
