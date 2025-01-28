package allegro.agh.auto_detailing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.database.car.CarDto;
import allegro.agh.auto_detailing.database.car.sql.CarSqlRow;
import allegro.agh.auto_detailing.database.car.sql.CarSqlService;
import allegro.agh.auto_detailing.service.CarService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

  @Mock private CarSqlService carSqlService;
  @InjectMocks private CarService carService;

  @Test
  void shouldAddCar() {
    carService.addCar(1, "Toyota", "Corolla", "2020", "M", "Black");
    verify(carSqlService, times(1)).createCar(1, "Toyota", "Corolla", "2020", "M", "Black");
  }

  @Test
  void shouldDeleteCar() {
    carService.deleteCar(1, 2);
    verify(carSqlService, times(1)).deleteCar(1, 2);
  }

  @Test
  void shouldReturnUserCars() {
    List<CarSqlRow> mockRows = List.of(new CarSqlRow(1, "Toyota", "Corolla", "2020", "M", "Black"));
    when(carSqlService.getCars("1")).thenReturn(mockRows);
    ContentDto<CarDto> result = carService.getUserCars("1");
    assertEquals(1, result.content().size());
    assertEquals("Toyota", result.content().getFirst().make());
    verify(carSqlService, times(1)).getCars("1");
  }
}
