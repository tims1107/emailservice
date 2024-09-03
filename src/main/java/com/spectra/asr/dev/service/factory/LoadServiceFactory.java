package com.spectra.asr.dev.service.factory;



public final class LoadServiceFactory {

    private static LoadServiceFactory serviceFactory = null;

    private LoadServiceFactory() {

    }

    public static synchronized LoadServiceFactory getInstance() {
        if (serviceFactory == null) {
            serviceFactory = new LoadServiceFactory();
        }
        return serviceFactory;
    }

    public static Object getServiceImpl(final String serviceName) {
        return LoadServiceEnum.getService(serviceName);
    }
}

