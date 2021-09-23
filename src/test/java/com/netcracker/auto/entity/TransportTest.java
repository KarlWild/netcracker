package com.netcracker.auto.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransportTest {

    Transport transport;

    @BeforeEach
    void setUp() {
        transport = new Transport(2, "AC", "Cobra", "model", "generation", "years", "body", "type", "power", "fuel", "equipment", "bodyType");
    }

    @Test
    void getTransportId() {
        assertEquals(2, transport.getTransportId());
    }

    @Test
    void setTransportId() {
        transport.setTransportId(2);
        assertEquals(2, transport.getTransportId());
    }

    @Test
    void getBrand() {
        assertEquals("AC", transport.getBrand());
    }

    @Test
    void setBrand() {
        transport.setBrand("AC");
        assertEquals("AC", transport.getBrand());
    }

    @Test
    void getModel() {
        assertEquals("Cobra", transport.getModel());
    }

    @Test
    void setModel() {
        transport.setModel("Cobra");
        assertEquals("Cobra", transport.getModel());
    }
}