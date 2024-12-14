package org.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PharmacyServiceTest {

    @Test
    void testMockingFileReading() {
        // Створюємо мок для PharmacyService
        PharmacyService serviceMock = Mockito.mock(PharmacyService.class);

        // Створюємо тестові дані
        List<PharmacyBranch> mockedBranches = List.of(
                new PharmacyBranch("Pharmacy 1", 0.0, 0.0, Map.of("aspirin", 10)),
                new PharmacyBranch("Pharmacy 2", 1.0, 1.0, Map.of("paracetamol", 5))
        );

        // Налаштовуємо поведінку мока
        when(serviceMock.loadPharmaciesFromFile("pharmacies.txt")).thenReturn(mockedBranches);

        // Викликаємо метод і перевіряємо результат
        List<PharmacyBranch> result = serviceMock.loadPharmaciesFromFile("pharmacies.txt");
        assertEquals(2, result.size());
        assertEquals("Pharmacy 1", result.get(0).getName());
    }
}