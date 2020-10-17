package com.chainup.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 包含了会议部门选项的会议室
 *
 * @author lili
 * @date 2020/10/17 17:17
 * @see
 * @since
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ApiModel(description = "包含部门的会议室")
public class MeetingRoomExtDto extends MeetingRoomDto {
    @ApiModelProperty(value = "departmentId")
    private int departmentId;
    @ApiModelProperty(value = "部门名字")
    private String departmentName;
}
