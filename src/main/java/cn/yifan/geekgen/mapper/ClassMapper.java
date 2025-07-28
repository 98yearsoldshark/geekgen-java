package cn.yifan.geekgen.mapper;

import cn.yifan.geekgen.pojo.entity.Class;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @FileName ClassMapper
 * @Description
 * @Author yifan
 * @date 2025-02-25 22:31
 **/

@Mapper
public interface ClassMapper {

    Class getById(Long id);

    Class getByCode(String code);

    Class getByName(String name);

    List<Class> getByTeacherId(Long teacherId);

    void insert(Class clazz);

    void update(Class clazz);

}
