package com.chainup.core.dto;

import com.chainup.entity.Department;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 会议室预定
 *
 * @author lili
 * @date 2020/10/22 14:58
 * @see
 * @since
 */
@Data
public class MeetingRoomReserveDto {

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

    @ApiModelProperty(value = "会议开始时间")
    private String beginTime;

    @ApiModelProperty(value = "会议结束时间时间")
    private String endTime;

    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "部门名字")
    private String departmentName;

    @ApiModelProperty(value = "预订人")
    private String userName;

    @ApiModelProperty(value = "会议室状态")
    private String status;

    @ApiModelProperty(value = "会议室状态描述")
    private String statusMsg;

    /**
     * 部门列表
     */
    @ApiModelProperty(value = "部门列表")
    private List<Department> departmentList;


    @ApiModelProperty(value = "房间已经有的会议")
    private List<MeetingDto> meetingList;

}
