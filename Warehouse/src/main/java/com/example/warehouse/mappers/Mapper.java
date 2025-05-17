package com.example.warehouse.mappers;

public interface Mapper<A, B> {

    public B mapTo(A a);
    public A mapFrom(B b);
}
