package com.wayne;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class POIRenewal {

    /**
     * 基本计划
     */
    public static final String BASIC_DUTY_TYPE = "1";
    /**
     * 可选计划
     */
    public static final String OPTIONAL_DUTY_TYPE = "2";

    /**
     * 有医保
     */
    public static final String IS_MEDICAL_INSURANCE = "Y";

    /**
     * 没有医保
     */
    public static final String NO_MEDICAL_INSURANCE = "N";

    static String firstAmount = "0";
    static String secondAmount = "0";
    static String thirdAmount = "0";

    static String riskCode = "121710";


    public static void main(String[] args) {
        try {
            // 需要读取的word文档
            File in = new File("C:\\Users\\fangw\\Desktop\\poi\\poi-4.docx");
            // 内容输出文件
            File out = new File("C:\\Users\\fangw\\Desktop\\poi\\d.sql");
            // 一个可追加的文件输出流
            FileOutputStream fos = new FileOutputStream(out, true);
            readAndWriteTest(in,fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void readAndWriteTest(File file, FileOutputStream fos){
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument doc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            String docStr = extractor.getText();
            // 每一行的内容
            String[] rows = docStr.trim().split("\\n");
            // 获取保额
            getAmount(rows[0]);
            // 获取保费
            List<Renewal> renewalList = new ArrayList<>();
            for (int i = 1; i < rows.length; i++) {
                String row = rows[i].trim();
                String[] cells = row.split("\\t");
                renewalList.addAll(getRenewal(cells));
            }
            for (Renewal renewal : renewalList) {
                log.info("renewal{}", renewal);
            }
            // 缓冲输出流，把SQL写入文件
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            String concatStr = "\", \"";
            for (Renewal renewal : renewalList) {
                String sql = "insert into t_risk_rate_detail_renewal ( risk_code, age, " +
                        "is_medical_insurance, amount_insured, rate, duty_type ) values (" +
                        "\"" + riskCode + concatStr +renewal.getAge()+ concatStr
                        + renewal.getIsMedicalInsurance()+ concatStr
                        + renewal.getAmountInsured() + concatStr
                        + renewal.getPremium() + concatStr
                        + renewal.getDutyType()+"\");\n";
                log.info(sql);
                bos.write(sql.getBytes(StandardCharsets.UTF_8));
            }
            bos.flush();
            bos.close();
            fos.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理保费行
     * @param cells 保费
     * @return 指定区间的续保对象集合
     */
    public static List<Renewal> getRenewal(String[] cells){
        List<Renewal> list = new ArrayList<>();
        // 第一个单元格是年龄
        List<String> ageList = handleAge(cells[0]);
        for (String age : ageList) {
            Renewal first = getOptionalAndNoMedical();
            first.setAge(age);
            first.setAmountInsured(firstAmount);
            first.setPremium(cells[1]+"00");
            list.add(first);

            Renewal second = getOptionalAndNoMedical();
            second.setAge(age);
            second.setAmountInsured(secondAmount);
            second.setPremium(cells[2]+"00");
            list.add(second);

            Renewal third = getOptionalAndNoMedical();
            third.setAge(age);
            third.setAmountInsured(thirdAmount);
            third.setPremium(cells[3]+"00");
            list.add(third);
        }
        return list;
    }

    /**
     * 获取一个 duty_type = 1，is_medical_insurance = Y的 Renewal对象
     * @return Renewal对象
     */
    public static Renewal getBasicAndIsMedical(){
        Renewal renewal = new Renewal();
        renewal.setDutyType(BASIC_DUTY_TYPE);
        renewal.setIsMedicalInsurance(IS_MEDICAL_INSURANCE);
        return renewal;
    }

    /**
     * 获取一个 duty_type = 1，is_medical_insurance = N的 Renewal对象
     * @return Renewal对象
     */
    public static Renewal getBasicAndNoMedical(){
        Renewal renewal = new Renewal();
        renewal.setDutyType(BASIC_DUTY_TYPE);
        renewal.setIsMedicalInsurance(NO_MEDICAL_INSURANCE);
        return renewal;
    }

    /**
     * 获取一个 duty_type = 2，is_medical_insurance = Y的 Renewal对象
     * @return Renewal对象
     */
    public static Renewal getOptionalAndIsMedical(){
        Renewal renewal = new Renewal();
        renewal.setDutyType(OPTIONAL_DUTY_TYPE);
        renewal.setIsMedicalInsurance(IS_MEDICAL_INSURANCE);
        return renewal;
    }

    /**
     * 获取一个 duty_type = 2，is_medical_insurance = N的 Renewal对象
     * @return Renewal对象
     */
    public static Renewal getOptionalAndNoMedical(){
        Renewal renewal = new Renewal();
        renewal.setDutyType(OPTIONAL_DUTY_TYPE);
        renewal.setIsMedicalInsurance(NO_MEDICAL_INSURANCE);
        return renewal;
    }

    /**
     * 把 “0-4” 处理成年龄区间
     * @param ageStr 0-4
     * @return 保存年龄区间的集合
     */
    public static List<String> handleAge(String ageStr){
        String[] split = ageStr.split("-");
        int min = Integer.parseInt(split[0]);
        int max = Integer.parseInt(split[1]);
        List<String> ageList = new ArrayList<>();
        for (int i = min; i <= max ; i++) {
            ageList.add(String.valueOf(i));
        }
        return ageList;
    }

    /**
     * 获取保额的数组
     * @param str 表格的第一行是保额
     */
    public static void getAmount(String str){
        String[] amount = str.split("\\t");
        log.info("保额{}",Arrays.toString(amount));
        firstAmount = amount[0]+"00";
        secondAmount = amount[1]+"00";
        thirdAmount = amount[2]+"00";
    }

}
