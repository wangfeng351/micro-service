package com.soft1851.spring.cloud.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/4
 * @Version 1.0
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notice")
//未定义中的二级标题
@ApiModel("通知")
public class Notice {
    //用于表示内容的名称与描述
    @ApiModelProperty(name = "id", value = "通知id")
    private Integer id;
    @ApiModelProperty(name = "content", value = "通知内容")
    private String content;
    @ApiModelProperty(name = "showFlag", value = "是否显示")
    private Boolean showFlag;
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private Timestamp createTime;
}
