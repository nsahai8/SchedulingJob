package in.airtel.service.Impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import in.airtel.dao.SchedulerTaskDao;
import in.airtel.entity.Tasks;
import in.airtel.service.SchedulerService;

@Service
public class SchedulerServiceImpl implements SchedulerService {

	@Autowired
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	SchedulerTaskDao schedulerTaskDao;

	@Override
	public Tasks createNewTask(JsonNode node) {
		// check for reference id

		Tasks task = new Tasks(node.get("url").asText(), node.get("callback_at").asText());
		if (node.has("reference_id")) {
			task.setReferenceId(node.get("reference_id").asText());
		}
		schedulerTaskDao.createNewTask(task);
		return task;
	}

	@Override
	public ArrayNode getTaskById(String id) {
		Tasks task = schedulerTaskDao.getTaskById(id);
		ArrayNode arrayNode = objectMapper.createArrayNode();
		if (task != null) {
			JsonNode node = objectMapper.createObjectNode();
			((ObjectNode)node).put("callback_at", task.getCallBackAt().toString());
			((ObjectNode)node).put("called_status", task.isCalledStatus());
			((ObjectNode)node).put("created_at", task.getCreated().toString());
			JsonNode idNode = objectMapper.createObjectNode();
			((ObjectNode)idNode).put("id", id);
			((ObjectNode)node).put("request_body", idNode+"");
			((ObjectNode)node).put("errors", null+"");
			((ObjectNode)node).put("reference_id", task.getReferenceId());
			((ObjectNode)node).put("request_method", "post");
			((ObjectNode)node).put("id", task.getTaskId());
			((ObjectNode)node).put("updated_at", task.getUpdated().toString());
			((ObjectNode)node).put("url", task.getUrl());
			arrayNode.add(node);
		}
		return arrayNode;
	}

	@Override
	public boolean deleteTaskById(String id) {
		if (schedulerTaskDao.deleteTaskById(id)) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayNode updateTaskById(String id, JsonNode node) {
		String url = node.get("url").asText();
		Date callbackAt = new Date(node.get("callback_at").asText());
		Tasks task = schedulerTaskDao.updateTaskId(id, url, callbackAt);
		ArrayNode arrayNode = objectMapper.createArrayNode();
		JsonNode Rnode = objectMapper.createObjectNode();
		((ObjectNode) Rnode).put("callback_at", task.getCallBackAt().toString());
		((ObjectNode) Rnode).put("called_status", task.isCalledStatus());
		((ObjectNode) Rnode).put("created_at", task.getCreated().toString());
		((ObjectNode) Rnode).put("request_method", "");
		((ObjectNode) Rnode).put("id", task.getTaskId());
		((ObjectNode) Rnode).put("updated_at", task.getUpdated().toString());
		((ObjectNode) Rnode).put("url", task.getUrl());
		JsonNode Resultnode = objectMapper.createObjectNode();
		((ObjectNode) Resultnode).put("response", Rnode);
		arrayNode.add(Resultnode);
		return arrayNode;
	}

}
