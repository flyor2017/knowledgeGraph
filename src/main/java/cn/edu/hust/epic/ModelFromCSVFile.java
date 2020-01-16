package cn.edu.hust.epic;

import java.io.File;
import java.io.FileReader;

import com.opencsv.CSVReader;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class ModelFromCSVFile extends ModelFromFile {

    ModelFromCSVFile() {
    }

    ModelFromCSVFile(String ns) {
        super(ns);
    }

    @Override
    public Model addToModelFromFile(Model model, File file) {
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
        return model;
    }

}