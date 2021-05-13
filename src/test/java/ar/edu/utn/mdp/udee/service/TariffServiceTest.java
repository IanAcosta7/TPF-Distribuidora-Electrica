package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.Tariff;
import ar.edu.utn.mdp.udee.repository.TariffRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TariffServiceTest {

    @Test
    public void getTariffsTest() {
        // Arrange
        TariffRepository tariffRepository = new TariffRepository() {
            @Override
            public List<Tariff> findAll() {
                List<Tariff> tariffs = new ArrayList<>();

                Tariff tariff1 = new Tariff();
                Tariff tariff2 = new Tariff();

                tariff1.setValue(5f);
                tariff2.setValue(10f);

                tariffs.add(tariff1);
                tariffs.add(tariff2);

                return tariffs;
            }

            @Override
            public List<Tariff> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Tariff> findAllById(Iterable<Integer> integers) {
                return null;
            }

            @Override
            public <S extends Tariff> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Tariff> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<Tariff> entities) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Tariff getOne(Integer integer) {
                return null;
            }

            @Override
            public <S extends Tariff> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Tariff> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Tariff> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Tariff> S save(S entity) {
                return null;
            }

            @Override
            public Optional<Tariff> findById(Integer integer) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer integer) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Integer integer) {

            }

            @Override
            public void delete(Tariff entity) {

            }

            @Override
            public void deleteAll(Iterable<? extends Tariff> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Tariff> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Tariff> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Tariff> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Tariff> boolean exists(Example<S> example) {
                return false;
            }
        };
        TariffService tariffService = new TariffService(tariffRepository);
        List<Tariff> tariffs = null;

        // Act
        try {
            tariffs = tariffService.getTariffs();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        // Assert
        Assert.assertNotNull(tariffs);
        Assert.assertEquals(tariffs.get(0).getValue(), 5f, 0);
        Assert.assertEquals(tariffs.get(1).getValue(), 10f, 0);
    }
}
