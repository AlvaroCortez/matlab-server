package com.matlab.server.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestWrapper {

    private String filename;
    private List<Block> blocks;
    private List<Param> params;
}
