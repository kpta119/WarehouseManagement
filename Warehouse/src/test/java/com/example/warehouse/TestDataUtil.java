package com.example.warehouse;

import com.example.warehouse.domain.Region;

public class TestDataUtil {

    private TestDataUtil(){}

    public static Region createRegion(){
        return new Region(1,"Asia");
    }
}
