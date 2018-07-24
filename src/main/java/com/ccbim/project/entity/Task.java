package com.ccbim.project.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Task implements Serializable {
    private Integer taskId;

    private String taskTitle;

    private String projectId;

    private String memberId;

    private Byte taskImgType;

    private Byte isSms;

    private Date beginDate;

    private Date endDate;

    private Byte visibleType;

    private String taskContent;

    private Byte status;

    private Integer replyCnt;

    private Integer fileCnt;

    private Integer parentId;

    private Byte taskType;

    private Byte starLevel;

    private Byte isChildTask;

    private Byte isvoice;

    private Byte inDelList;

    private Date deleteTime;

    private Byte sourceType;

    private Integer portId;

    private Integer portPhoto;

    private String nodeId;

    private String versionId;

    private Date gmtCreate;

    private Date gmtModify;

    private String createId;

    private String modifyId;

    private Byte isDelete;

    private static final long serialVersionUID = 1L;

}