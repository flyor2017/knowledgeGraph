package cn.edu.hust.epic.demo;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDFS;


public class Demo1 {
    public static void main(String[] args) {

        // 创建一个命名空间 namespace
        String NS = "urn:x-hp-jena:eg/";

        // 创建一个模型，jena把一个模型当成一个推理项目
        Model rdfsExample = ModelFactory.createDefaultModel();

        // 声明该模型中具有 p 和 q 属性
        Property p = rdfsExample.createProperty(NS, "p");
        p = rdfsExample.createProperty(NS, "p");
        Property q = rdfsExample.createProperty(NS, "q");

        Property b = rdfsExample.getProperty("urn:x-hp-jena:eg/", "p");
        System.out.println(b);

        // 声明 p 是 q 的子属性
        rdfsExample.add(p, RDFS.subPropertyOf, q);

        // 上面的属性 p 和 q 都只是声明该模型具有这种属性，并没有指定
        // 哪一个个体有该属性。下面创建了一个实体资源 a ，并指定它具有
        // 属性 p ，并且属性 p 的值是 "foo"
        rdfsExample.createResource(NS + "a").addProperty(p, "foo");

        // 创建并使用 RDFS 推理模型
        InfModel inf = ModelFactory.createRDFSModel(rdfsExample);

        // 获取实体资源 a ，并输出其 q 属性
        // 由于 q 是 p 的父属性
        // 所以 a 的 p 属性的值就是其 q 属性的值
        Resource a = inf.getResource(NS + "a");
        System.out.println("Statement: " + a.getProperty(q));
    }
}
