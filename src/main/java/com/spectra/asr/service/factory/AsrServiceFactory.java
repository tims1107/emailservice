package com.spectra.asr.service.factory;

public final class AsrServiceFactory {

	private static AsrServiceFactory serviceFactory = null;

	private AsrServiceFactory() {

	}

	public static synchronized AsrServiceFactory getInstance() {
		if (serviceFactory == null) {
			serviceFactory = new AsrServiceFactory();
		}
		return serviceFactory;
	}

	public static Object getServiceImpl(final String serviceName) {
		return AsrServiceEnum.getService(serviceName);
	}
}
