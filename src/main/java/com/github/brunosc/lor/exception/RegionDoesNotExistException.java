package com.github.brunosc.lor.exception;

public class RegionDoesNotExistException extends RuntimeException {

    public RegionDoesNotExistException(String regionCode) {
        super(String.format("Region code '%s' does not exist!", regionCode));
    }

    public RegionDoesNotExistException(int regionId) {
        super(String.format("Region id '%s' does not exist!", regionId));
    }

}
