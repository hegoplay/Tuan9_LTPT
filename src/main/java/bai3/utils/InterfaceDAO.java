package bai3.utils;

public interface InterfaceDAO<T> {
	public void insert(T obj);

	public void update(T obj);

	public void delete(T obj);

	public T findById(long id);
}
