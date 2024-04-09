package bai1.service;

import java.rmi.Remote;

public interface CalculateService extends Remote {
	public int add(int a, int b) throws Exception;

	public int sub(int a, int b) throws Exception;

	public int mul(int a, int b) throws Exception;

	public int div(int a, int b) throws Exception;
}
