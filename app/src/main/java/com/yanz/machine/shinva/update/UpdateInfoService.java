package com.yanz.machine.shinva.update;


import com.yanz.machine.shinva.entity.UpdateInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class UpdateInfoService {


    public UpdateInfo parseXml(InputStream inStream) throws Exception {
         HashMap<String,String> hashMap = new HashMap<String,String>();
        //实例化一个文档构建器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //通过文档构建器工厂获取一个文档构建器
        DocumentBuilder builder = factory.newDocumentBuilder();
        //通过文档构建器构建一个文档实例
        Document document = builder.parse(inStream);
        //获取xml文件根节点
        Element root = document.getDocumentElement();
        //获取所有子节点
        NodeList childNodes = root.getChildNodes();
        UpdateInfo updateInfo = new UpdateInfo();
        for(int j=0;j<childNodes.getLength();j++){
            //遍历子节点
            Node chilNode = (Node)childNodes.item(j);
            if (chilNode.getNodeType()==Node.ELEMENT_NODE){
                Element childElement = (Element)chilNode;
                //版本号
                if ("version".equals(childElement.getNodeName())){
                    //hashMap.put("version",childElement.getFirstChild().getNodeValue());
                    updateInfo.setVersion(childElement.getFirstChild().getNodeValue());
                }
                //软件名称
                if ("appname".equals(childElement.getNodeName())){
                    //hashMap.put("appname",childElement.getFirstChild().getNodeValue());
                    updateInfo.setName(childElement.getFirstChild().getNodeValue());
                }
                //描述
                if ("description".equals(childElement.getNodeName())){
                    //hashMap.put("description",childElement.getFirstChild().getNodeValue());
                    updateInfo.setDescription(childElement.getFirstChild().getNodeValue());
                }
                //url地址
                if ("url".equals(childElement.getNodeName())){
                    //hashMap.put("url",childElement.getFirstChild().getNodeValue());
                    updateInfo.setUrl(childElement.getFirstChild().getNodeValue());
                }
            }
        }
        return updateInfo;
    }

}
