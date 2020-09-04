package com.baizhi.lmm.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthAndSex implements Serializable {
    private String month;
    private String count;
}
