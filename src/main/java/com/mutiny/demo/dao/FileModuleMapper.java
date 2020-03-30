package com.mutiny.demo.dao;

import com.mutiny.demo.pojo.FileModule;
import com.mutiny.demo.pojo.FileModuleExample;
import java.util.List;

public interface FileModuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table File_Module
     *
     * @mbg.generated 2020-03-16 13:16:33
     */
    int deleteByPrimaryKey(Integer fileId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table File_Module
     *
     * @mbg.generated 2020-03-16 13:16:33
     */
    int insert(FileModule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table File_Module
     *
     * @mbg.generated 2020-03-16 13:16:33
     */
    int insertSelective(FileModule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table File_Module
     *
     * @mbg.generated 2020-03-16 13:16:33
     */
    List<FileModule> selectByExample(FileModuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table File_Module
     *
     * @mbg.generated 2020-03-16 13:16:33
     */
    FileModule selectByPrimaryKey(Integer fileId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table File_Module
     *
     * @mbg.generated 2020-03-16 13:16:33
     */
    int updateByPrimaryKeySelective(FileModule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table File_Module
     *
     * @mbg.generated 2020-03-16 13:16:33
     */
    int updateByPrimaryKey(FileModule record);
}