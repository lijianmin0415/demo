package com.example.demo.bean.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "SYS_UPLOAD")
public class SysUpload {
    @Id
    private String id;
    private String status;
}
