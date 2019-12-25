package cn.edu.hust.epic.demo;


import cn.edu.hust.epic.Controller;

public class Demo3{
    public static void main(String[] args){
        // System.out.println(System.getProperty("user.dir"));
        Controller controller = new Controller();
        controller.readRuleFromFile("target/classes/new_importdata_test/rules.txt");
        controller.printStatements("p0004", null, null, System.out);
    }
}