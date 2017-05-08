package com.j0rsa.caricyno.vk.actor;

import com.vk.api.sdk.client.actors.ServiceActor;

import static com.j0rsa.caricyno.vk.AppConfig.*;

public class ServiceActorData {
    private ServiceActor serviceActor;

    private ServiceActorData() {
        serviceActor = new com.vk.api.sdk.client.actors.ServiceActor(CLIENT_ID, CLIENT_SECRET, SERVICE_KEY);
    }

    public static ServiceActorData service() {
        return new ServiceActorData();
    }

    public ServiceActor actor() {
        return serviceActor;
    }
}
