package in.airtel.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import in.airtel.entity.Tasks;
import in.airtel.service.SchedulerService;

@Controller
public class SchedulerController {
	
	@Autowired
	SchedulerService schedulerService;
	
	@Autowired
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@ResponseBody
	@RequestMapping(value="/Working",method=RequestMethod.POST)
	void isWorking(){
		System.out.println("working");
	}
	
	@ResponseBody
	@RequestMapping(value="/tasks",method=RequestMethod.POST)
	ArrayNode createNewTask(@RequestBody JsonNode node){
		Tasks task = schedulerService.createNewTask(node);
    	ArrayNode arrayNode = objectMapper.createArrayNode();
		JsonNode resultNode = objectMapper.createObjectNode();
		((ObjectNode)resultNode).put("callback_at", task.getCallBackAt()+"");
		((ObjectNode)resultNode).put("called_status", task.isCalledStatus());
		((ObjectNode)resultNode).put("created_at", task.getCreated()+"");
		((ObjectNode)resultNode).put("request_body", node+"");
		((ObjectNode)resultNode).put("errors", null+"");
		((ObjectNode)resultNode).put("reference_id", task.getReferenceId());
		((ObjectNode)resultNode).put("request_method", "post");
		((ObjectNode)resultNode).put("id", task.getTaskId());
		((ObjectNode)resultNode).put("updated_at", task.getUpdated().toString());
		((ObjectNode)resultNode).put("url", task.getUrl());
		arrayNode.add(resultNode);
		return arrayNode;
	}
	
	@ResponseBody
	@RequestMapping(value="/tasks",method=RequestMethod.GET)
	ArrayNode getTaskById(@RequestParam(value="id") String id){
		ArrayNode arrayNode;
		if(id.isEmpty()){
			arrayNode = objectMapper.createArrayNode();
			arrayNode.add(objectMapper.createObjectNode().put("error", "Id is Null"));
			return arrayNode;
		}
		try{
			int idConvert = Integer.parseInt(id);
			arrayNode = schedulerService.getTaskById(id);
		}catch(Exception e){
			arrayNode = objectMapper.createArrayNode();
			arrayNode.add(objectMapper.createObjectNode().put("error", "Id is Null"));
			return arrayNode;
		}
		
		return arrayNode;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/tasks",method=RequestMethod.PUT)
	ArrayNode updateTasks(@RequestParam(value="id") String id, @RequestBody JsonNode node){
		ArrayNode arrayNode;
		if(id.isEmpty() ){
			arrayNode = objectMapper.createArrayNode();
			arrayNode.add(objectMapper.createObjectNode().put("Error", "id is empty"));
			return arrayNode;
		}
		arrayNode = schedulerService.updateTaskById(id,node);
		return arrayNode;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/tasks",method=RequestMethod.DELETE)
	ArrayNode deleteTaskById(@RequestParam(value="id") String Id){
		ArrayNode arrayNode = objectMapper.createArrayNode();
		if(schedulerService.deleteTaskById(Id)){
			 arrayNode.add(objectMapper.createObjectNode().put("response", "ok"));
			 return arrayNode;
		}
		arrayNode.add(objectMapper.createObjectNode().put("response", "Error Occured Task not deleted"));
		return arrayNode;
		
	}

}
