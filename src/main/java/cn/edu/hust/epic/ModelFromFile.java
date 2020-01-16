package cn.edu.hust.epic;

import java.io.File;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public abstract class ModelFromFile {

    protected String ns;

    ModelFromFile() {
        ns = "";
    }

    ModelFromFile(String ns) {
        this.ns = ns;
    }

    // 从一个文件中读取数据，并将数据添加到已存在的Model中。
    public abstract Model addToModelFromFile(Model model, File file);

    // 从一个文件中读取数据，创建Model并返回。
    public Model getModelFromFile(File file) {
        Model model = ModelFactory.createDefaultModel();
        return addToModelFromFile(model, file);
    }
}