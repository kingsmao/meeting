package com.chainup.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 搜索页面的数据结构
 *
 * @author lili
 * @date 2020/10/17 16:54
 * @see
 * @since
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ApiModel(description = "房间")
public class MeetingRoomDto {
    @ApiModelProperty(value = "roomId")
    private String roomId;
    @ApiModelProperty(value = "房间名字")
    private String roomName;
    @ApiModelProperty(value = "房间人数")
    private String roomPersonCount;
    @ApiModelProperty(value = "房间描述")
    private String description;
    @ApiModelProperty(value = "房间所在职场")
    private String workSpace;
    @ApiModelProperty(value = "房间已经有的会议")
    private List<MeetingDto> meetingList;
}
