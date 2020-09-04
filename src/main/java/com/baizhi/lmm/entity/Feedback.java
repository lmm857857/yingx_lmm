package com.baizhi.lmm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    private String id;

    private String title;

    private String content;
    @Column(name = "feedback_time")
    private Date feedbackTime;
    @Column(name = "user_d")
    private Integer userId;


}