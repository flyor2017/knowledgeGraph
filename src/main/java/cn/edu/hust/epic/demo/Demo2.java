package cn.edu.hust.epic.demo;


import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.PrintUtil;

public class Demo2 {

    public static void printStatement(Model m){
        for (StmtIterator i=m.listStatements(); i.hasNext(); ) {
            Statement stmt = i.nextStatement();
            System.out.println(PrintUtil.print(stmt));
        }
    }

    public static void main(String[] args) throws Exception {

        // 读取schema和instance到模型之中
        Model model = FileManager.get().loadModel("./target/classes/data.ttl", "N-TRIPLES");

        // 读取规则 rule，并创建推理机
        Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL("./target/classes/data-rule.txt"));

        // 创建推理模型
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);


        // 输出推断之前的全部关系
        System.out.println("--------推理前----------");
        printStatement(model);


        // 输出推断之后的全部关系
        System.out.println("--------推理后----------");
        printStatement(infModel);
    }
}
