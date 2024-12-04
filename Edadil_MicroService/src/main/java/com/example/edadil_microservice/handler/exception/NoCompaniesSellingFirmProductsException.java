package com.example.edadil_microservice.handler.exception;

public class NoCompaniesSellingFirmProductsException extends RuntimeException {
  public NoCompaniesSellingFirmProductsException(String message) {
    super(message);
  }
}

