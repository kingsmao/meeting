package com.chainup.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lili
 * @date 2020/10/17 16:58
 * @see
 * @since
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ApiModel(description = "会议室")
public class MeetingDto {

    @ApiModelProperty(value = "meetingId")
    private String meetingId;

    @ApiModelProperty(value = "会议名字/会议内容")
    private String meetingName;

    @ApiModelProperty(value = "会议开始时间")
    private String beginTime;

    @ApiModelProperty(value = "会议结束时间时间")
    private String endTime;

    @ApiModelProperty(value = "部门名字")
    private String departmentName;

    @ApiModelProperty(value = "预订人")
    private String userName;

    @ApiModelProperty(value = "会议室状态")
    private String status;

    @ApiModelProperty(value = "会议室状态描述")
    private String statusMsg;

}
