package com.talfinder.pdfgen;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.w3c.tidy.Tidy;

import java.io.*;

public class SimpleUsage {
    public static void main(String[] args) throws Exception {
        try (OutputStream os = new FileOutputStream("out.pdf")) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            String html =
                    "<p>The Project loaded in the IDE contains a boilerplate code for a maven based application. Your task is to complete the code within the <span style=\"color: rgb(111, 66, 193);\">trap()</span> method of TrapRainWater.java class to do as follows:</p><p><br></p><p>Given&nbsp;<em>n</em>&nbsp;non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.</p><p><img src=\"https://assets.leetcode.com/uploads/2018/10/22/rainwatertrap.png\"></p><p>The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.</p><p><br></p><p><strong>Example:</strong></p><pre class=\"ql-syntax\" spellcheck=\"false\">Input: [0,1,0,2,1,0,1,3,2,1,2,1]\n" +
                    "Output: 6\n" +
                    "</pre><p><br></p><p>The code will be evaluated for functional correctness and non-functional parameters like how efficient the execution is. Your scoring will reflect accordingly.</p><p><br></p><p>NOTE : The maven project and class files are already created for you. Please don't change the project name, structure, class and method signatures.</p>";
            html = tidyHtml(html);
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
        }
    }

    public static String tidyHtml(String htmlString) {
        Tidy tidy = new Tidy();
        tidy.setDocType("omit");
        tidy.setXmlOut(true);
        tidy.setQuiet(true);
        tidy.setTidyMark(false);
        tidy.setWraplen(100_000);
        StringWriter swError = new StringWriter();
        tidy.setErrout(new PrintWriter(swError));
        StringWriter sw = new StringWriter();
        tidy.parse(new StringReader(htmlString), sw);
        String tidyHtml = sw.getBuffer().toString();
        tidyHtml = tidyHtml.replaceAll("(\r\n|\n|\r)+\\s*<", "<");
        return tidyHtml;
    }
}
