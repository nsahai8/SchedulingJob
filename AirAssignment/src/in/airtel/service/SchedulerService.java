package in.airtel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import in.airtel.entity.Tasks;

public interface SchedulerService {
	Tasks createNewTask(JsonNode node);

	ArrayNode getTaskById(String id);

	boolean deleteTaskById(String id);

	ArrayNode updateTaskById(String id,JsonNode node);
}
