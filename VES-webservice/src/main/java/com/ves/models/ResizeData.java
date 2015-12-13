package com.ves.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ResizeData {

    @JsonProperty("widthPercentage")
    public double widthPercentage;

    @JsonProperty("heightPercentage")
    public double heightPercentage;

    @JsonCreator
    public ResizeData() {}

};