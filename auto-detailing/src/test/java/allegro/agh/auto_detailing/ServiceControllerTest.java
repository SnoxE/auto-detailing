package allegro.agh.auto_detailing;

<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

=======
>>>>>>> 3504d29 (Tests for auto-detailing added)
import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.controller.ServicesController;
import allegro.agh.auto_detailing.database.services.ServiceDto;
import allegro.agh.auto_detailing.database.services.ServiceNamesDto;
import allegro.agh.auto_detailing.service.ServicesService;
<<<<<<< HEAD
import java.sql.Time;
import java.util.List;
=======
>>>>>>> 3504d29 (Tests for auto-detailing added)
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

<<<<<<< HEAD
@ExtendWith(MockitoExtension.class)
class ServicesControllerTest {

  @Mock private ServicesService servicesService;

  @InjectMocks private ServicesController servicesController;

  @Test
  void shouldReturnServiceNames() {
    ServiceNamesDto cleaningService = new ServiceNamesDto("Cleaning");

    when(servicesService.getServiceNames()).thenReturn(new ContentDto<>(List.of(cleaningService)));

    ContentDto<ServiceNamesDto> result = servicesController.getServiceNames();

    assertNotNull(result);
    assertEquals(1, result.content().size());
<<<<<<< HEAD
<<<<<<< HEAD
    assertEquals("Cleaning", result.content().get(0).name());
=======
    assertEquals("Cleaning", result.content().get(0).getName());
>>>>>>> fffabcd (Fix)
=======
    assertEquals("Cleaning", result.content().get(0).name());
>>>>>>> 021646f (Deleting getters from ServceDto & ServiceNamesDto)

    verify(servicesService, times(1)).getServiceNames();
  }

  @Test
  void shouldReturnServiceByNameAndCarSize() {
    ServiceDto mockService = new ServiceDto(1, "Cleaning", 25, Time.valueOf("01:30:00"), "M");

    List<ServiceDto> mockResponse = List.of(mockService);

    when(servicesService.getServiceByNameAndCarSize("Cleaning", "Small")).thenReturn(mockResponse);

    List<ServiceDto> result = servicesController.getServiceId("Cleaning", "Small");

    assertNotNull(result);
    assertEquals(1, result.size());
<<<<<<< HEAD
<<<<<<< HEAD
    assertEquals("Cleaning", result.get(0).name());
=======
    assertEquals("Cleaning", result.get(0).getServiceName());
>>>>>>> fffabcd (Fix)
=======
    assertEquals("Cleaning", result.get(0).name());
>>>>>>> 021646f (Deleting getters from ServceDto & ServiceNamesDto)
    verify(servicesService, times(1)).getServiceByNameAndCarSize("Cleaning", "Small");
  }
=======
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicesControllerTest {

    @Mock
    private ServicesService servicesService;

    @InjectMocks
    private ServicesController servicesController;

    @Test
    void shouldReturnServiceNames() {
        ServiceNamesDto cleaningService = new ServiceNamesDto("Cleaning");

        when(servicesService.getServiceNames()).thenReturn(new ContentDto<>(List.of(cleaningService)));

        ContentDto<ServiceNamesDto> result = servicesController.getServiceNames();

        assertNotNull(result);
        assertEquals(1, result.content().size());
        assertEquals("Cleaning", result.content().get(0).getName());

        verify(servicesService, times(1)).getServiceNames();
    }


    @Test
    void shouldReturnServiceByNameAndCarSize() {
        ServiceDto mockService = new ServiceDto(1, "Cleaning", 25, Time.valueOf("01:30:00"), "M");

        List<ServiceDto> mockResponse = List.of(mockService);

        when(servicesService.getServiceByNameAndCarSize("Cleaning", "Small")).thenReturn(mockResponse);

        List<ServiceDto> result = servicesController.getServiceId("Cleaning", "Small");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cleaning", result.get(0).getServiceName());
        verify(servicesService, times(1)).getServiceByNameAndCarSize("Cleaning", "Small");
    }
>>>>>>> 3504d29 (Tests for auto-detailing added)
}
