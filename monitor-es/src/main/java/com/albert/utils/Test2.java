package com.albert.utils;

import com.alibaba.fastjson.JSON;

import java.util.*;


public class Test2 {


    public static final String XML_TYPE="xml";
    public static final String JSON_TYPE="json";

    public static void main(String[] args) {
        String str="{\"body/user_info[0]/user_id\":\"user_id1\"," +
                "\"body/user_info[0]/user_name\":\"user_name1\"," +
                "\"body/user_info[0]/role_info[0]/role_id\":\"role_id1\"," +
                "\"body/user_info[0]/role_info[0]/role_name\":\"role_name2\"," +
                "\"body/user_info[0]/role_info[0]/auth_info[0]/auth_id\":\"auth_id1\"," +
                "\"body/user_info[0]/role_info[0]/auth_info[0]/auth_name\":\"auth_name1\"," +
                "\"body/user_info[0]/role_info[0]/auth_info[0]/auth_menu\":\"auth_menu1\"," +
                "\"body/user_info[1]/user_id\":\"user_id2\"," +
                "\"body/user_info[1]/user_name\":\"user_name2\"," +
                "\"body/user_info[1]/role_info[0]/role_id\":\"role_id2\"," +
                "\"body/user_info[1]/role_info[0]/role_name\":\"role_name2\"," +
                "\"body/user_info[1]/role_info[0]/auth_info[0]/auth_id\":\"auth_id2\"," +
                "\"body/user_info[1]/role_info[0]/auth_info[0]/auth_name\":\"auth_name2\"," +
                "\"body/user_info[1]/role_info[0]/auth_info[0]/auth_menu\":\"auth_menu2\"}\n";
        Map map = JSON.parseObject(str, LinkedHashMap.class);
        Map map1 = initDataMap(map, XML_TYPE);
        System.out.println(map1);
    }



    public static Map<String, Object> initDataMap(Map<String, Object> mappingMap, String dataType) {
        Map<String, Object> dataMap = new HashMap<>();

        if (null==mappingMap) {
            return dataMap;
        }

        for (Map.Entry<String, Object> entry : mappingMap.entrySet()) {
            String key = entry.getKey();
            String value = null != entry.getValue() ? entry.getValue().toString() : "";
            String preColumn = null;
            Object preObj = dataMap;
            Integer subIndex = null;
            String[] keys = key.split("/");
            for (int i = 0 ; i < keys.length ; i++) {
                String column = keys[i];
                if (null == preColumn) {
                    preColumn = column;
                }
                if (isArray(column)) {
                    subIndex = getArrayIndex(column);
                    if (preObj instanceof Map) {
                        Object obj = ((Map)preObj).get(getArrayName(column) );
                        if (null == obj) {

                            preObj=initArray(column,preObj,dataType);
                        } else {
                            preObj = obj;
                        }
                    } else if (preObj instanceof List) {
                        Object obj = null;
                        if (((List) preObj).size() > subIndex ) {
                            obj = ((List) preObj).get(subIndex);
                        }
                        if (null == obj) {
                            List<Map<String, Object>> list = new ArrayList<>();
                            Map<String, Object> nodeMap = new LinkedHashMap<>();
                            nodeMap.put(column, column + subIndex);
                            list.add(nodeMap);
                            ((List) preObj).add(list);
                            preObj = list;
                        } else {
                            preObj = obj;
                        }
                        preObj=initArray(column,preObj,dataType);

                    }
                } else if (isObject(i, keys.length)){
                    if (preObj instanceof Map) {
                        String subStr = null != subIndex ? ("[" + subIndex + "]") : "";
                        Object obj = ((Map)preObj).get(getArrayName(column) + subStr );
                        if (null == obj) {
                            Map<String, Object> map = new LinkedHashMap<>();
                            ((Map) preObj).put(getArrayName(column) + subStr , map);
                            preObj = map;
                        } else {
                            preObj = obj;
                        }
                    } else if (preObj instanceof List) {
                        Object obj = null;
                        if (((List) preObj).size() > subIndex) {
                            obj = ((List) preObj).get(subIndex);
                        }

                        if (null == obj) {
                            Map<String, Object> map = new LinkedHashMap<>();
                            ((List) preObj).add(map);
                            preObj = map;
                        } else {
                            if (obj instanceof List) {
                                preObj = obj;
                            } else if (obj instanceof Map) {
                                Object o = ((Map) obj).get(getArrayName(column));
                                if (null == o) {
                                    Map<String, Object> map = new LinkedHashMap<>();
                                    ((Map) obj).put(getArrayName(column), map);
                                    preObj = map;
                                } else {
                                    preObj = o;
                                }
                            } else {
                                preObj = obj;
                            }
                        }
                    }
                } else {
                    if (preObj instanceof Map) {
                        ((Map)preObj).put(getArrayName(column), value);
                    } else if (preObj instanceof List) {
                        Object obj = null;
                        if(((List) preObj).size()>subIndex){
                            obj = ((List) preObj).get(subIndex);
                        }
                        if (null == obj) {
                            Map<String, Object> map = new LinkedHashMap<>();
                            map.put(getArrayName(column), value);
                            ((List) preObj).add(map);
                        } else {
                            ((Map)obj).put(getArrayName(column), value);
                        }
                    }
                }
            }
        }
        return dataMap;
    }

    private static Object initArray(String column,Object preObj, String dataType) {
        if (JSON_TYPE.equals(dataType)) {
            List<Map<String, Object>> list = new ArrayList<>();
            ((Map) preObj).put(getArrayName(column) , list);
            preObj = list;
        } else if (XML_TYPE.equals(dataType)) {
            // xml时，需要在节点外层套一个“节点 + s”标签
            Object map = ((Map) preObj).get(getArrayName(column) + "s");
            if (map == null) {
                map = new LinkedHashMap<>();
                ((Map) preObj).put(getArrayName(column) + "s" , map);
            }
            preObj = map;
            Object list = ((Map) preObj).get(getArrayName(column));
            if (null == list) {
                list = new ArrayList<>();
                ((Map) preObj).put(getArrayName(column) , list);
            }
            preObj = list;
        }
        return preObj;
    }


    public static boolean isArray(String key) {
        int sIndex = key.indexOf("[");
        int eIndex = key.indexOf("]");
        if (sIndex > 0 && eIndex > 0) {
            return true;
        }
        return false;
    }

    public static boolean isObject(int index, int length) {
        if (index < length - 1) {
            return true;
        }
        return false;
    }

    public static int getArrayIndex(String key) {
        int sIndex = key.indexOf("[");
        int eIndex = key.indexOf("]");
        return Integer.parseInt(key.substring(sIndex + 1, eIndex));
    }

    public static String getArrayName(String key) {
        int sIndex = key.indexOf("[");
        if (sIndex > 0) {
            return key.substring(0, sIndex);
        }
        return key;
    }
}