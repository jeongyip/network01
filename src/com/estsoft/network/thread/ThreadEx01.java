package com.estsoft.network.thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadEx01 {

	public static void main(String[] args) {

		Thread thread01 = new DigitThread();
		Thread thread02 = new DigitThread();
		Thread thread03 = new DigitThread();
		Thread thread04 = new Thread(new UpperCaseAlpahbetRunnableImpl());
		
		thread01.start();
		thread02.start();
		thread03.start();	
		thread04.start();
	}

}
