package allegro.agh.auto_detailing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.controller.UserController;
import allegro.agh.auto_detailing.database.car.CarDto;
import allegro.agh.auto_detailing.service.CarService;
import allegro.agh.auto_detailing.service.ReservationService;
import allegro.agh.auto_detailing.service.UserService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock private UserService userService;

  @Mock private CarService carService;

  @Mock private ReservationService reservationService;

  @InjectMocks private UserController userController;

  @Test
  void shouldAddCar() {
    CarDto mockCar = new CarDto("1", "Toyota", "Corolla", "2020", "M", "Black");
    String userId = "1";

    ResponseEntity<?> response = userController.addCar(userId, mockCar);

    assertEquals(200, response.getStatusCodeValue());
    verify(carService, times(1)).addCar(1, "Toyota", "Corolla", "2020", "M", "Black");
  }

  @Test
  void shouldReturnUserCars() {
    List<CarDto> cars = List.of(new CarDto("1", "Toyota", "Corolla", "2020", "M", "Black"));
    when(carService.getUserCars("1")).thenReturn(new ContentDto<>(cars));

    ContentDto<CarDto> result = userController.getUserCars("1");

    assertEquals(1, result.content().size());
    assertEquals("Toyota", result.content().getFirst().make());
    verify(carService, times(1)).getUserCars("1");
  }
}
