package cn.edu.hust.epic;

import com.opencsv.CSVReader;
import java.io.FileReader;
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

    // 创建model
    public void createModelFromCSV(File file) {
        try {
            CSVReader reader = new CSVReader(new FileReader(file));

            String[] nextline;

            // 跳过文件的第一行
            reader.readNext();

            while ((nextline = reader.readNext()) != null) {
                // System.out.println(nextline[0] + '\t' + nextline[1] + '\t' + nextline[2]);

                if (nextline.length != 3)
                    continue;

                Resource s = model.createResource(ns + nextline[0]);
                Property p = model.createProperty(ns, nextline[1]);
                Resource o = model.createResource(ns + nextline[2]);
                model.add(s, p, o);
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(file.getAbsolutePath());
        }
    }

    // 扫描默认目录
    public Controller() {
        model = ModelFactory.createDefaultModel();
        final String dirPath = "target/classes/new_importdata_test/";
        File dir = new File(dirPath);

        File[] files = dir.listFiles();

        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".csv") && !f.getName().endsWith("_attribute.csv")) {
                createModelFromCSV(f);
            }
        }

        try {
            model.write(new FileOutputStream(new File(dirPath + "test.rdf")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 扫描指定目录
    public Controller(String dirPath) {
        model = ModelFactory.createDefaultModel();
        File dir = new File(dirPath);

        File[] files = dir.listFiles();

        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".csv")) {
                createModelFromCSV(f);
            }
        }

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