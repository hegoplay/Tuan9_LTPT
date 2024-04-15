package bai1.utils;

public enum ServicesList {
	CALCULATE("CalculateService");

	private String serviceName;

	private ServicesList(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceName() {
		return serviceName;
	}
	
}
