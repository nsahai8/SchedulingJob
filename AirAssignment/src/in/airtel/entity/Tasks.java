package in.airtel.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TASKS",schema="public")
public class Tasks {
	
	
	private Long taskId;
	private String url;
	private Date callBackAt;
	private String referenceId;
	private boolean calledStatus;
	private Date created;
	private Date updated;
	private String response;
	public Tasks(String url, String callBackAt) {
		this.created= new Date();
		this.updated = new Date();
		this.calledStatus=false;
		this.url=url;
		this.callBackAt=new Date(callBackAt);
		
	}
	public Tasks() {
		// TODO Auto-generated constructor stub
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCallBackAt() {
		return callBackAt;
	}
	public void setCallBackAt(Date callBackAt) {
		this.callBackAt = callBackAt;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public boolean isCalledStatus() {
		return calledStatus;
	}
	public void setCalledStatus(boolean calledStatus) {
		this.calledStatus = calledStatus;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
}
