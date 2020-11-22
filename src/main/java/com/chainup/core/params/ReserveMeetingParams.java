package com.chainup.core.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lili
 * @date 2020/10/17 17:26
 * @see
 * @since
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "预定会议室的保存参数")
public class ReserveMeetingParams {

    @ApiModelProperty(value = "roomId")
    private int roomId;
    @ApiModelProperty(value = "房间名字")
    private String roomName;
    @ApiModelProperty(value = "会议开始时间")
    private String beginTime;

    @ApiModelProperty(value = "会议结束时间时间")
    private String endTime;

    @ApiModelProperty(value = "departmentId")
    private int departmentId;
    @ApiModelProperty(value = "部门名字")
    private String departmentName;

    @ApiModelProperty(value = "会议名字/会议内容")
    private String meetingName;

    @ApiModelProperty(value = "用户真实名字")
    private String userName;

    @ApiModelProperty(value = "用户open id")
    private String openId;

    @ApiModelProperty(value = "顺延四周开关")
    private Boolean delaySwitch = false;

}
