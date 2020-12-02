package scratch;

import java.util.concurrent.TimeUnit;

public class Scratch {

	public static void main(String[] args) {
		System.out.println(TestStat.sum());
		
		TestStat.a=1;
		
		System.out.println(TestStat.sum());
		
	}

}
