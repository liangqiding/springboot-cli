package com.example.mybatis_generation.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.mybatis_generation.domain.ParentList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author qiDing
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ParentListVo extends Model<ParentList> {

    private static final long serialVersionUID=1L;


    @TableId(value = "p_id", type = IdType.AUTO)
    private Integer pId;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 名称
     */
    private String name;

    private List<ParentList> children;


    @Override
    protected Serializable pkVal() {
        return this.pId;
    }

}
