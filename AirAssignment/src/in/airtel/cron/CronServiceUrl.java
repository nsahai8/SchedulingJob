package in.airtel.cron;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import in.airtel.dao.SchedulerTaskDao;

@Component
public class CronServiceUrl {
	@Autowired
	SchedulerTaskDao schedulerTaskDao;
	
	String getResponse(String url){
		String result="";
		Connection connection = Jsoup.connect(url).timeout(10000);
		try {
			Response response = connection.execute();
			result=response.body();
			System.out.println(result+" for "+url);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
		
	}
	
	void invokeUrl(){
		List<String> urlList = schedulerTaskDao.getUrlToInvoke();
		Map<String, String> list = new HashMap<>();
		for(String url:urlList){
			list.put(url, getResponse(url));
		}
		schedulerTaskDao.saveResponse(list);
	}
	@Scheduled(fixedDelay =10000)
	void invokeUrlMultiple(){
		List<String> urlList = schedulerTaskDao.getUrlToInvoke();
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		Queue<String> queue = new LinkedList<>();
		for(String url:urlList){
			queue.add(url);
		}
		
		Map<String, String> list = new HashMap<>();
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				/*if(!queue.isEmpty()){
					String url=queue.poll();
					String result = "response"+Thread.currentThread().getId();
					list.put(url, result);
					schedulerTaskDao.saveResponseDirect(url,result+"result");
					System.out.println("Thread Invoked: "+Thread.currentThread().getId()+" "+new Date().getTime());
				}*/
				int i=0;
				if(i<urlList.size()){
					String url=urlList.get(i);
					String result = "response"+Thread.currentThread().getId();
					list.put(url, result);
					schedulerTaskDao.saveResponseDirect(url,result);
					i++;
					System.out.println("Thread Invoked: "+Thread.currentThread().getId()+" "+new Date().getTime());
				}
			}
		});
		
		executorService.shutdownNow();
		
		
	}
	
}
