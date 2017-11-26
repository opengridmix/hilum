package com.hilum.image.dataaccess.mapper;

import com.hilum.image.dataaccess.model.ImageInfo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

public interface ImageInfoMapper extends BaseMapper<ImageInfo> {

    @Select("select * from image_info where id = #{id}")
    ImageInfo selectById(String id);

}
