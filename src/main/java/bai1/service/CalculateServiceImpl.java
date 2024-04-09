package bai1.service;

public class CalculateServiceImpl implements CalculateService{

	@Override
	public int add(int a, int b) throws Exception {
		// TODO Auto-generated method stub
		return a+b;
	}

	@Override
	public int sub(int a, int b) throws Exception {
		// TODO Auto-generated method stub
		return a-b;
	}

	@Override
	public int mul(int a, int b) throws Exception {
		// TODO Auto-generated method stub
		return a*b;
	}

	@Override
	public int div(int a, int b) throws Exception {
		// TODO Auto-generated method stub
		if (b == 0) {
			throw new Exception("Can't divide by zero.");
		}
		
		return a/b;
	}

	
	
}
