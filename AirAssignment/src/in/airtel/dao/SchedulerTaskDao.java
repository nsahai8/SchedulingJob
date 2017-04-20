package in.airtel.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import in.airtel.entity.Tasks;

public interface SchedulerTaskDao {
	void createNewTask(Tasks task);

	Tasks getTaskById(String id);

	boolean deleteTaskById(String id);

	Tasks updateTaskId(String id,String url,Date callBackAt);

	List<String> getUrlToInvoke();

	void saveResponse(Map<String, String> list);

	void saveResponseDirect(String url, String result);
}
