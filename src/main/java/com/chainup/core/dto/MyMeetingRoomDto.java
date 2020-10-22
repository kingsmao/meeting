package com.chainup.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 包含了会议部门选项的会议室
 *
 * @author lili
 * @date 2020/10/17 17:17
 * @see
 * @since
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(description = "我的会议室")
public class MyMeetingRoomDto {
    @ApiModelProperty(value = "roomId")
    private int roomId;
    @ApiModelProperty(value = "房间名字")
    private String roomName;
    @ApiModelProperty(value = "房间描述")
    private String description;

    @ApiModelProperty(value = "meetingId")
    private int meetingId;

    @ApiModelProperty(value = "会议名字/会议内容")
    private String meetingName;

    @ApiModelProperty(value = "会议日期时间范围")
    private String dateTimeRange;

    @ApiModelProperty(value = "会议开始时间")
    private String beginTime;

    @ApiModelProperty(value = "会议结束时间时间")
    private String endTime;

    @ApiModelProperty(value = "部门名字")
    private String departmentName;

    @ApiModelProperty(value = "预订真实姓名")
    private String userName;

    @ApiModelProperty(value = "预订用户的昵称")
    private String nickName;

    @ApiModelProperty(value = "会议室状态")
    private String status;

    @ApiModelProperty(value = "会议室状态描述")
    private String statusMsg;

}
