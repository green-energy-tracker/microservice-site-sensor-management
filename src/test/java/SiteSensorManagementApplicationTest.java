import com.green.energy.tracker.site_sensor_management.SiteSensorManagementApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

@ExtendWith(MockitoExtension.class)
class SiteSensorManagementApplicationTest {
    @Test
    void testMain() {
        String[] args = new String[] { "test"};
        try (MockedStatic<SpringApplication> springApp = Mockito.mockStatic(SpringApplication.class)) {
            springApp.when(() -> SpringApplication.run(SiteSensorManagementApplication.class, args)).thenReturn(null);
            SiteSensorManagementApplication.main(args);
            springApp.verify(() -> SpringApplication.run(SiteSensorManagementApplication.class, args), Mockito.times(1));
        }
    }
}
