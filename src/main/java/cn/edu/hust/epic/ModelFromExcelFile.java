package cn.edu.hust.epic;

import java.io.File;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ModelFromExcelFile extends ModelFromFile {

    ModelFromExcelFile() {
    }

    ModelFromExcelFile(String ns) {
        super(ns);
    }

    @Override
    public Model addToModelFromFile(Model model, File file) {

        try {
            Workbook wb = Workbook.getWorkbook(file);

            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sheet = wb.getSheet(i);
                // 循环从1开始，跳过文件的第一行。
                for (int r = 1; r < sheet.getRows(); r++) {
                    Cell[] row = sheet.getRow(r);
                    if (row.length < 2 || row.length > 3)
                        continue;

                    Resource s = model.createResource(ns + row[0].getContents());
                    Property p = model.createProperty(ns, row[1].getContents());
                    Resource o = model.createResource(ns + row[2].getContents());
                    model.add(s, p, o);
                }
            }
            wb.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(file.getAbsolutePath());
        }
        return model;
    }
}