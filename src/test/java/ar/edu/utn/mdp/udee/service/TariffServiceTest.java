package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.Tariff;
import ar.edu.utn.mdp.udee.repository.TariffRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

public class TariffServiceTest {

    @Mock
    TariffRepository tariffRepository;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void getTariffs_Test() {
        // Arrange
        List<Tariff> tariffs = new ArrayList<>();

        Tariff tariff = new Tariff();
        tariff.setValue(5f);

        Mockito.when(tariffRepository.findAll()).thenReturn(tariffs);
        TariffService tariffService = new TariffService(tariffRepository);

        // Act
        List<Tariff> result = null;
        try {
            result = tariffService.getTariffs();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        // Assert
        Assert.assertNotNull(tariffs);
        Assert.assertEquals(tariffs, result);
    }
}
