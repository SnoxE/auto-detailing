package allegro.agh.auto_detailing.controller;

import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.database.services.ServiceDto;
import allegro.agh.auto_detailing.database.services.ServiceNamesDto;
import allegro.agh.auto_detailing.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServicesController {

    ServicesService servicesService;

    @GetMapping()
    public ContentDto<ServiceNamesDto> getServiceNames() {
        return servicesService.getServiceNames();
    }

    @GetMapping("/{serviceName}")
    public ContentDto<ServiceDto> getServiceByName(@PathVariable("serviceName") String serviceName) {
        return servicesService.getServiceInfoByName(serviceName);
    }

    @GetMapping("/{serviceName}/{carSize}")
    public List<ServiceDto> getServiceId(
            @PathVariable("serviceName") String serviceName,
            @PathVariable("carSize") String carSize) {
        return servicesService.getServiceByNameAndCarSize(serviceName, carSize);
    }
}
