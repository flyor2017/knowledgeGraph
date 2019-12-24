package cn.edu.hust.epic.translator;

// 转换器的作用是将外部的各种各样的数据转化为三元组 ttl
public interface Translator{
    public void translate(String inputPath, String outputPath);
}