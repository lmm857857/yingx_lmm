package com.baizhi.lmm.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Models implements Serializable {
    private String titles;
    private List<City> cities;
}
