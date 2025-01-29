package allegro.agh.auto_detailing.service;

import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.common.exceptions.DgAuthException;
import allegro.agh.auto_detailing.database.car.CarDto;
import allegro.agh.auto_detailing.database.car.sql.CarSqlRow;
import allegro.agh.auto_detailing.database.car.sql.CarSqlService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarService {

  private final CarSqlService carSqlService;

  public CarService(CarSqlService carSqlService) {
    this.carSqlService = carSqlService;
  }

  private static CarDto carDtoMapper(CarSqlRow carSqlRow) {
    return new CarDto(
        String.valueOf(carSqlRow.id()),
        carSqlRow.make(),
        carSqlRow.model(),
        carSqlRow.productionYear(),
        carSqlRow.size(),
        carSqlRow.colour());
  }

  public void addCar(
      int userId, String make, String model, String productionYear, String size, String colour)
      throws DgAuthException {

    carSqlService.createCar(userId, make, model, productionYear, size, colour);
  }

  public void deleteCar(int userId, int carId) {
    carSqlService.deleteCar(userId, carId);
  }

  public ContentDto<CarDto> getUserCars(String userId) {
    List<CarDto> carDtoList =
        carSqlService.getCars(userId).stream().map(CarService::carDtoMapper).toList();

    return new ContentDto<>(carDtoList);
  }
}
