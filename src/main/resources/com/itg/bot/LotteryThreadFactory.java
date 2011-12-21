package com.itg.bot;

import java.util.concurrent.ThreadFactory;

public class LotteryThreadFactory implements ThreadFactory {


	public Thread th;
	public Thread getThread(){
		return th;
	}
	@Override
	public Thread newThread(Runnable r) {
		th = new Thread(r);
		th.setDaemon(true);
		return th;
	}
	
	

}
