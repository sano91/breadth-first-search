package com.codecool.bfsexample.service;

class DistanceBelowZeroException extends RuntimeException {

    DistanceBelowZeroException() {
        super("Distance cannot be under 0");
    }
}
