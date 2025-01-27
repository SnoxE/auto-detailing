package allegro.agh.auto_detailing;

import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.controller.ServicesController;
import allegro.agh.auto_detailing.database.services.ServiceDto;
import allegro.agh.auto_detailing.database.services.ServiceNamesDto;
import allegro.agh.auto_detailing.service.ServicesService;


import java.sql.Time;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    assertEquals("Cleaning", result.content().getFirst().name());

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

    assertEquals("Cleaning", result.getFirst().name());
    verify(servicesService, times(1)).getServiceByNameAndCarSize("Cleaning", "Small");
  }
}

