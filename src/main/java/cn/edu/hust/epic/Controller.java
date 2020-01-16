package cn.edu.hust.epic;

import java.io.PrintStream;

import org.apache.jena.rdf.model.*;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.PrintUtil;

public class Controller {

    private Model model;
    private InfModel infModel;
    final static String ns = "http://epic.hust.edu.cn/#";
    final static String dirPath = "target/classes/new_importdata_test/";

    // 扫描默认目录
    public Controller() {
        model = ModelFactory.createDefaultModel();
        final String dirPath = "target/classes/new_importdata_test/";
        File dir = new File(dirPath);

        File[] files = dir.listFiles();

        ModelFromCSVFile modelFromCSVFile = new ModelFromCSVFile(ns);

        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".csv") && !f.getName().endsWith("_attribute.csv")) {
                model = modelFromCSVFile.addToModelFromFile(model, f);
            }
        }

        try {
            model.write(new FileOutputStream(new File(dirPath + "test.rdf")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 扫描指定目录
    public Controller(Model model) {
        try {
            model.write(new FileOutputStream(new File(dirPath + "test.rdf")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 读取规则
    public void readRuleFromFile(String filePath) {
        // 读取规则 rule，并创建推理机
        Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(filePath));

        // 创建推理模型
        infModel = ModelFactory.createInfModel(reasoner, model);

        System.out.println(model.listStatements().toList().size());
        System.out.println(infModel.listStatements().toList().size());
    }

    public InfModel getInfModel() {
        return infModel;
    }

    public void printStatements(String s, String p, String o, PrintStream out) {
        Resource subject = s == null ? null : model.getResource(ns + s);
        Property property = p == null ? null : model.getProperty(ns, p);
        RDFNode object = o == null ? null : model.getResource(ns + o);

        for (StmtIterator it = infModel.listStatements(subject, property, object); it.hasNext();) {
            Statement stmt = it.nextStatement();
            out.println(PrintUtil.print(stmt));
        }
    }
}