package cn.edu.hust.epic;

import java.io.File;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Test;

public class ControllerTest {

    @Test
    public void testCSVFile() {
        Model model = ModelFactory.createDefaultModel();
        File dir = new File(Controller.dirPath);

        File[] files = dir.listFiles();

        ModelFromCSVFile modelFromCSVFile = new ModelFromCSVFile(Controller.ns);

        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".csv") && !f.getName().endsWith("_attribute.csv")) {
                modelFromCSVFile.addToModelFromFile(model, f);
            }
        }
        Controller controller = new Controller(model);
        controller.readRuleFromFile("target/classes/new_importdata_test/rules.txt");
        controller.printStatements("p0004", null, null, System.out);
    }

    @Test
    public void testExcelFile() {
        Model model = ModelFactory.createDefaultModel();
        File dir = new File(Controller.dirPath + "../");

        File[] files = dir.listFiles();

        ModelFromExcelFile modelFromExcelFile = new ModelFromExcelFile(Controller.ns);

        for (File f : files) {
            if (f.isFile() && f.getName().endsWith("v1.1.xls")) {
                modelFromExcelFile.addToModelFromFile(model, f);
            }
        }
        Controller controller = new Controller(model);
        controller.readRuleFromFile("target/classes/new_importdata_test/rules.txt");
        controller.printStatements("社会心理", null, null, System.out);
    }
}
