package main;

import Thread.KQJMain;

public class MainKQJ {
	public static void main(String[] args) {
		KQJMain main = new KQJMain();
		try {
			main.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
