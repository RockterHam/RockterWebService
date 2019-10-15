package com.rockter.server.basic.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebContext {
    private List<Entity> entityList = null;
    private List<Mapping> mappingList = null;

    private Map<String,String> entitys = new HashMap<>();
    private Map<String,String> mappings = new HashMap<>();

    public WebContext(List<Entity> entityList, List<Mapping> mappingList) {
        this.entityList = entityList;
        this.mappingList = mappingList;

        for (var temp:entityList){
            entitys.put(temp.getName(),temp.getClz());
        }
        for (var tempMapping: mappingList){
            for (var tempPattern:tempMapping.getPatterns()){
                mappings.put(tempPattern,tempMapping.getName());
            }
        }
    }
    public String getClz(String pattern){
        return entitys.get(mappings.get(pattern));
    }
}
