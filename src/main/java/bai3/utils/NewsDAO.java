package bai3.utils;

import java.util.List;

import all.Constant;
import bai3.entities.News;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class NewsDAO implements InterfaceDAO<News> {
	
	@Override
	public void insert(News news) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.persist(news);
		Constant.manager.getTransaction().commit();
		
	}

	@Override
	public void update(News news) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.merge(news);
		Constant.manager.getTransaction().commit();
	}

	@Override
	public void delete(News news) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.remove(news);
		Constant.manager.getTransaction().commit();
	}

	@Override
	public News findById(long id) {
		// TODO Auto-generated method stub
		return Constant.manager.find(News.class, id);
	}

	public List<News> getNewsByTagsOrNewsCategories(String tags) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = Constant.manager.getCriteriaBuilder();
		CriteriaQuery<News> cq = cb.createQuery(News.class);
		
		Root<News> root = cq.from(News.class);
		
		Predicate where = cb.conjunction();
		
		where = cb.or(where, root.join("tags").in(tags,"%"));
        where = cb.or(where, root.join("newCategories").in(tags,"%"));
		
		cq =cq.where(where);
		List<News> ls =  Constant.manager.createQuery(cq).getResultList();
		return ls;
	}
	public List<News> findAllNews(){
		CriteriaBuilder cb = Constant.manager.getCriteriaBuilder();
		CriteriaQuery<News> cq = cb.createQuery(News.class);
		Root<News> root = cq.from(News.class);
		cq.select(root);
		return Constant.manager.createQuery(cq).getResultList();
	}
}
