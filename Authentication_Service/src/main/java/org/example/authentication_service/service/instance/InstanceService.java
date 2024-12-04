package org.example.authentication_service.service.instance;

import org.example.authentication_service.model.entity.Instance;

public interface InstanceService {

    Instance getByName(String name);
}
