package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PharmacyServiceTest {
    private PharmacyService pharmacyService;
    private List<PharmacyBranch> branches;

    @BeforeEach
    public void setUp() {
        pharmacyService = Mockito.spy(new PharmacyService());
    }

    @Test
    public void testFindNearestPharmacy_Success() {
        PharmacyBranch mockBranch1 = mock(PharmacyBranch.class);
        PharmacyBranch mockBranch2 = mock(PharmacyBranch.class);

        when(mockBranch1.getXCoordinate()).thenReturn(0.0);
        when(mockBranch1.getYCoordinate()).thenReturn(0.0);
        when(mockBranch2.getXCoordinate()).thenReturn(5.0);
        when(mockBranch2.getYCoordinate()).thenReturn(5.0);
        when(mockBranch1.getName()).thenReturn("Pharmacy A");

        branches = List.of(mockBranch1, mockBranch2);
        PharmacyBranch nearest = pharmacyService.findNearestPharmacy(1, 1, branches);

        assertEquals(mockBranch1, nearest);
    }

    @Test
    public void testFindNearestPharmacy_NoPharmacyFound() {
        branches = List.of();
        PharmacyBranch nearest = pharmacyService.findNearestPharmacy(1, 1, branches);
        assertNull(nearest);
    }

    @Test
    public void testFindNearestPharmacyWithMedicines_Success() {
        PharmacyBranch mockBranch = mock(PharmacyBranch.class);

        when(mockBranch.getXCoordinate()).thenReturn(0.0);
        when(mockBranch.getYCoordinate()).thenReturn(0.0);
        when(mockBranch.getName()).thenReturn("Pharmacy A");
        when(mockBranch.hasMedicine(eq("aspirin"), eq(5))).thenReturn(true);

        branches = List.of(mockBranch);
        Map<String, Integer> cartItems = Map.of("aspirin", 5);

        PharmacyBranch nearest = pharmacyService.findNearestPharmacyWithMedicines(1, 1, branches, cartItems);
        assertEquals(mockBranch, nearest);
    }

    @Test
    public void testFindNearestPharmacyWithMedicines_NoMatch() {
        PharmacyBranch mockBranch = mock(PharmacyBranch.class);

        when(mockBranch.getXCoordinate()).thenReturn(0.0);
        when(mockBranch.getYCoordinate()).thenReturn(0.0);
        when(mockBranch.getName()).thenReturn("Pharmacy A");
        when(mockBranch.hasMedicine(eq("aspirin"), eq(5))).thenReturn(false);

        branches = List.of(mockBranch);
        Map<String, Integer> cartItems = Map.of("aspirin", 5);

        PharmacyBranch nearest = pharmacyService.findNearestPharmacyWithMedicines(1, 1, branches, cartItems);
        assertNull(nearest);
    }
}