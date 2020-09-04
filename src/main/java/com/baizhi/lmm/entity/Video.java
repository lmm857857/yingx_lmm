package com.baizhi.lmm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Table(name = "yx_video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    private String id;

    private String title;

    private String brief;

    private String path;

    private String cover;
    @Column(name = "publish_date")
    private Date publishDate;
    @Column(name = "category_id")
    private String categoryId;
    @Column(name = "group_id")
    private String groupId;
    @Column(name = "user_id")
    private String userId;

}