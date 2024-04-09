package bai3.utils;

import all.Constant;
import bai3.entities.Comment;
import bai3.entities.News;
import bai3.entities.User;

public class CommentDAO implements InterfaceDAO<Comment> {

	@Override
	public void insert(Comment obj) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.persist(obj);
		Constant.manager.getTransaction().commit();
	}

	@Override
	public void update(Comment obj) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.merge(obj);
		Constant.manager.getTransaction().commit();
	}

	@Override
	public void delete(Comment obj) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.remove(obj);
		Constant.manager.getTransaction().commit();
	}

	
	public Comment findById(User user,News news) {
		// TODO Auto-generated method stub
		return Constant.manager.createNamedQuery("Comment.findById",Comment.class)
                .setParameter("user_id", user.getId())
                .setParameter("news_id", news.getId())
                .getSingleResult();
	}

	@Override
	@Deprecated
	public Comment findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
