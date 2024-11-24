package org.example.authentication_service.service.instance;

import org.example.authentication_service.model.entity.Instance;
import org.example.authentication_service.repository.InstanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstanceServiceImplTest {

    @Mock
    private InstanceRepository instanceRepository;

    @InjectMocks
    private InstanceServiceImpl instanceService;

    @Test
    void getByName_whenInstanceExists_returnsInstance() {
        Instance instance = new Instance();
        instance.setName("testInstance");

        when(instanceRepository.findByName("testInstance")).thenReturn(Optional.of(instance));

        Instance result = instanceService.getByName("testInstance");

        assertNotNull(result);
        assertEquals("testInstance", result.getName());
    }

    @Test
    void getByName_whenInstanceDoesNotExist_throwsUsernameNotFoundException() {
        when(instanceRepository.findByName("nonExistentInstance")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> instanceService.getByName("nonExistentInstance"));
    }
}