package allegro.agh.auto_detailing;

import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.controller.UserController;
import allegro.agh.auto_detailing.database.car.CarDto;
import allegro.agh.auto_detailing.database.user.dto.UserDto;
import allegro.agh.auto_detailing.service.CarService;
import allegro.agh.auto_detailing.service.ReservationService;
import allegro.agh.auto_detailing.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private CarService carService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private UserController userController;

    @Test
    void shouldReturnLoggedUser() {
        UserDto mockUser = new UserDto(1, "Test", "User", "user@example.com", "123456789", "Some Address", "USER");
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("user@example.com");
        when(userService.getUserByEmail("user@example.com")).thenReturn(mockUser);

        UserDto result = userController.getLoggedUser(principal);

        assertEquals("user@example.com", result.email());
        verify(userService, times(1)).getUserByEmail("user@example.com");
    }

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
        assertEquals("Toyota", result.content().get(0).make());
        verify(carService, times(1)).getUserCars("1");
    }
}
