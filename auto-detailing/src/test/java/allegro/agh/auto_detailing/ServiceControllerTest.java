package allegro.agh.auto_detailing;

import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.controller.ServicesController;
import allegro.agh.auto_detailing.database.services.ServiceDto;
import allegro.agh.auto_detailing.database.services.ServiceNamesDto;
import allegro.agh.auto_detailing.service.ServicesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    assertEquals("Cleaning", result.content().get(0).name());

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
    assertEquals("Cleaning", result.get(0).name());
    verify(servicesService, times(1)).getServiceByNameAndCarSize("Cleaning", "Small");
  }
}
