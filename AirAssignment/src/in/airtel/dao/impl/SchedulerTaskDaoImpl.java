package in.airtel.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.airtel.dao.SchedulerTaskDao;
import in.airtel.entity.Tasks;

@Repository
public class SchedulerTaskDaoImpl implements SchedulerTaskDao{
	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public void createNewTask(Tasks task) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.persist(task);
		session.getTransaction().commit();
		session.close();
	}

	@Transactional
	public Tasks getTaskById(String id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("FROM Tasks WHERE id = :id");
		query.setParameter("id", Long.parseLong(id));
		try{
			Tasks task = (Tasks)query.list().get(0);
			Date taskStart = task.getCallBackAt();
			if(taskStart.compareTo(new Date())<=0){
				task.setCalledStatus(true);
			}
			session.getTransaction().commit();
			session.close();
			return task;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	@Transactional
	public boolean deleteTaskById(String id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Tasks task = new Tasks();
		task.setTaskId(Long.parseLong(id));
		session.delete(task);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	@Override
	@Transactional
	public Tasks updateTaskId(String id,String url,Date callBackAt) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Tasks task = getTaskById(id);
		if(task.getCallBackAt().compareTo(new Date())<=0){
			
		}else{
			task.setTaskId(Long.parseLong(id));
			task.setUrl(url);
			task.setCallBackAt(callBackAt);
			session.saveOrUpdate(task);
		}
		session.getTransaction().commit();
		session.close();
		return task;
	}

	@Override
	@Transactional
	public List<String> getUrlToInvoke() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("FROM Tasks WHERE created < :created AND calledStatus =:calledStatus");
		query.setParameter("created", new Date());
		query.setParameter("calledStatus", false);
		List list = query.list();
		List<String> urlList = new ArrayList<>();
		for(Object t:list){
			Tasks task= (Tasks)t;
			task.setCalledStatus(true);
			session.saveOrUpdate(task);
			urlList.add(task.getUrl());
		}
		session.getTransaction().commit();
		session.close();
		//update the called Status as well as time
		return urlList;
	}

	@Override
	@Transactional
	public void saveResponse(Map<String, String> list) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for(String url:list.keySet()){
			Query query = session.createQuery("FROM Tasks WHERE url =:url");
			query.setParameter("url", url);
			Tasks task = (Tasks) query.list().get(0);
			task.setResponse(list.get(url).substring(0, 255));
			session.saveOrUpdate(task);
		}
		
		session.getTransaction().commit();
		session.close();
		
	}

	@Override
	public void saveResponseDirect(String url, String result) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("FROM Tasks WHERE url =:url");
		query.setParameter("url", url);
		Tasks task = (Tasks) query.list().get(0);
		task.setResponse(result);
		session.saveOrUpdate(task);
		session.getTransaction().commit();
		session.close();
	}

}
