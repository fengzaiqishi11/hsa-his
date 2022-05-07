package cn.hsa.module.insure.clinica.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CommentDTO implements Serializable {

    private String columnName;
    private String columnComment;
}
