package bai3.utils;

import java.util.HashMap;
import java.util.Map;

import all.Constant;
import bai3.entities.Comment;
import bai3.entities.User;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public class UserDAO implements InterfaceDAO<User> {

	@Override
	public void insert(User user) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.persist(user);
		Constant.manager.getTransaction().commit();
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.merge(user);
		Constant.manager.getTransaction().commit();
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		Constant.manager.getTransaction().begin();
		Constant.manager.remove(user);
		Constant.manager.getTransaction().commit();
	}

	@Override
	public User findById(long id) {
		// TODO Auto-generated method stub
		return Constant.manager.find(User.class, id);

	}

	public boolean addUser(User user) {
		User u = findById(user.getId());
		if (u == null) {
			insert(user);
			return true;
		}
		return false;
	}
//	Thống kê tổng số lượng các tin tức có các lời bình luận từ từng người dùng (chỉ thống 
//	kê những người dùng có ít nhất 3 lượt bình luận trở lên). Thông tin kết quả gồm thông tin 
//	của người dùng và số lượng bình luận cho tin tức.
	public Map<User, Integer> getStatistic() {
		// TODO Auto-generated method stub
		Map<User, Integer> map = new HashMap<>();
		CriteriaBuilder cb = Constant.manager.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<User> root = cq.from(User.class);
		Join<User, Comment> comments = root.join("comments", JoinType.INNER);
		
		cq = cq.multiselect(root, cb.count(comments)).groupBy(root).
				having(cb.gt(cb.count(comments),2));
		Constant.manager.createQuery(cq).getResultList().forEach(t -> {
			map.put(t.get(0, User.class), t.get(1, Long.class).intValue());
        
		});
		return map;
	}
	
}
