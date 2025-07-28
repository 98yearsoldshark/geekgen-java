package cn.yifan.geekgen.mapper;

import cn.yifan.geekgen.pojo.entity.ClassMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @FileName ClassMemberMapper
 * @Description
 * @Author yifan
 * @date 2025-02-25 22:38
 **/

@Mapper
public interface ClassMemberMapper {

    ClassMember getById(Long id);

    List<ClassMember> getByClassId(Long classId);

    List<ClassMember> getByUserId(Long userId);

    void insert(ClassMember classMember);

}
