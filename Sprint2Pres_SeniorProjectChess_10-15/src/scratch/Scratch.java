package scratch;

import java.util.concurrent.TimeUnit;

public class Scratch {

	public static void main(String[] args) {
		for(int i=0; i< 4;i++) {
			System.out.println(i);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
