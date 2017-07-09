package com.j0rsa.caricyno.vk.actor;

import com.j0rsa.caricyno.vk.VkProperties;
import com.vk.api.sdk.client.actors.ServiceActor;

public class ServiceActorData {
    private ServiceActor serviceActor;

    private ServiceActorData(VkProperties vkProperties) {
        serviceActor = new com.vk.api.sdk.client.actors.ServiceActor(
                vkProperties.getClient(),
                vkProperties.getSecret(),
                vkProperties.getToken()
        );
    }

    public static ServiceActorData service(VkProperties vkProperties) {
        return new ServiceActorData(vkProperties);
    }

    public ServiceActor actor() {
        return serviceActor;
    }
}
