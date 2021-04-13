package com.mu.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class Mail implements Serializable {

    private final static Long SerializableVersion = 1L;

    /**
     * 消息的id
     */
    private String msgId;
    /**
     * 目标邮箱
     */
    private String to;

    /**
     * 发件人
     */
    private String from;
    /**
     * 主题或者标题
     */
    private String title;

    /**
     * 邮箱内容
     */
    private String text;

    /**
     * 发送时间（默认为年月日时分秒）
     */
    private String sentDate;

    /**
     * 抄送
     */
    private String cc;

    /**
     * 密送
     */
    private String bcc;

    /**
     * 附件
     */
    @JsonIgnore
    private MultipartFile[] multipartFiles;
}
