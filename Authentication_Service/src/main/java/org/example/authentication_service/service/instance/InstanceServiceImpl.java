package org.example.authentication_service.service.instance;

import lombok.RequiredArgsConstructor;
import org.example.authentication_service.model.entity.Instance;
import org.example.authentication_service.repository.InstanceRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InstanceServiceImpl implements InstanceService {

    private final InstanceRepository instanceRepository;

    @Override
    public Instance getByName(String name) {
        return instanceRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Instance not found"));
    }
}
