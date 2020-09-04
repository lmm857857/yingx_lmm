package com.baizhi.lmm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    private String id;
    @Column(name = "admin_name")
    private String adminName;
    private Date data;
    private String operation;
    private String status;
}
