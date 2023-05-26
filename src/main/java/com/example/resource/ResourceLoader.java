package com.example.resource;

/**
 * ResourceLoader
 *
 * @author keemo 2023/5/26
 */
public interface ResourceLoader {

    /**
     * 根据文件路径获取资源信息
     *
     * @param location 文件路径
     * @return 返回资源信息
     */
    Resource getResource(String location);
}
