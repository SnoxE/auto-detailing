package allegro.agh.auto_detailing.service;

import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.database.services.ServiceDto;
import allegro.agh.auto_detailing.database.services.ServiceNamesDto;
import allegro.agh.auto_detailing.database.services.sql.ServiceSqlRow;
import static allegro.agh.auto_detailing.database.services.sql.ServiceSqlRow.ServiceNamesSqlRow;
import allegro.agh.auto_detailing.database.services.sql.ServicesSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesService {

    @Autowired
    ServicesSqlService servicesSqlService;

    public ServicesService(ServicesSqlService servicesSqlService) {
        this.servicesSqlService = servicesSqlService;
    }

    public ContentDto<ServiceDto> getServiceInfoByName(String serviceName) {
        List<ServiceDto> serviceDtoList = servicesSqlService.getServiceByName(serviceName)
                .stream().map(ServicesService::serviceDtoMapper).toList();

        return new ContentDto<>(serviceDtoList);
    }

    public ContentDto<ServiceNamesDto> getServiceNames() {
        List<ServiceNamesDto> serviceNamesDtoList = servicesSqlService.getServiceNames()
                .stream().map(ServicesService::serviceNamesDtoMapper).toList();

        return new ContentDto<>(serviceNamesDtoList);
    }

    public List<ServiceDto> getServiceByNameAndCarSize(String serviceName, String carSize) {
        return servicesSqlService.getServiceByNameAndCarSize(serviceName, carSize).stream()
                .map(ServicesService::serviceDtoMapper).toList();
    }

    private static ServiceDto serviceDtoMapper(ServiceSqlRow serviceSqlRow) {
        return new ServiceDto(
                serviceSqlRow.id(),
                serviceSqlRow.name(),
                serviceSqlRow.price(),
                serviceSqlRow.length(),
                serviceSqlRow.car_size());
    }

    private static ServiceNamesDto serviceNamesDtoMapper(ServiceNamesSqlRow servicesSqlRow) {
        return new ServiceNamesDto(
                servicesSqlRow.name());
    }
}

