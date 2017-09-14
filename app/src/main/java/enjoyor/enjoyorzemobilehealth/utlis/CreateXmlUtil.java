package enjoyor.enjoyorzemobilehealth.utlis;

import android.text.TextUtils;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.entities.ContentBean;
import enjoyor.enjoyorzemobilehealth.entities.JiChuXiangMuBean;

/**
 * Created by Administrator on 2017/3/8.
 */

public class CreateXmlUtil {
    public static String createSMTZXml(List<ContentBean> allContentList, String tableName, int flag) {
        String xmlResult = null;
        StringWriter xmlWriter = new StringWriter();
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            xmlSerializer.setOutput(xmlWriter);
            xmlSerializer.startDocument("UTF-16", null);
            xmlSerializer.startTag("", "DS");
            xmlSerializer.startTag("", "DT");
            xmlSerializer.attribute("", "Name", tableName);
            for (int i = 0; i < allContentList.size(); i++) {
                ContentBean contentBean = allContentList.get(i);
                xmlSerializer.startTag("", "DR");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "ShengMingTZID");
                if(contentBean.getShengMingTZID()==null||contentBean.getShengMingTZID().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getShengMingTZID());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "JiChuXiangMuID");
                xmlSerializer.text(contentBean.getJiChuXiangMuID());
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "KongJianMC");
                xmlSerializer.text(contentBean.getKongJianMC());
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "ShuZhi1");
                if(contentBean.getContentValue()==null||contentBean.getContentValue().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getContentValue());
                }
                //xmlSerializer.text("这是一个测试");
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "ShuZhi2");
                if(contentBean.getShuZhi2()==null||contentBean.getShuZhi2().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getShuZhi2());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "ShuZhiDW");
                //xmlSerializer.text(contentBean.getShuZhiDW());
                if(contentBean.getShuZhiDW()==null||contentBean.getShuZhiDW().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getShuZhiDW());
                }
                //xmlSerializer.text("");
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "JiLuSJ");
                if(contentBean.getJiLuSJ()==null||contentBean.getJiLuSJ().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getJiLuSJ());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "CaiJiRen");
                if(contentBean.getCaiJiRen()==null||contentBean.getCaiJiRen().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getCaiJiRen());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "CaiJiRQ");
                if(contentBean.getCaiJiRQ()==null||contentBean.getCaiJiRQ().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getCaiJiRQ());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "CaiJiSJ");
                if(contentBean.getCaiJiSJ()==null||contentBean.getCaiJiSJ().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getCaiJiSJ());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "XiuGaiRen");
                if(contentBean.getXiuGaiRen()==null||contentBean.getXiuGaiRen().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getXiuGaiRen());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "XiuGaiSJ");
                if(contentBean.getXiuGaiSJ()==null||contentBean.getXiuGaiSJ().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getXiuGaiSJ());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "XiuGaiBZ");
                if(contentBean.getXiuGaiBZ()==null||contentBean.getXiuGaiBZ().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getXiuGaiBZ());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "BingQuID");
                if(contentBean.getBingQuID()==null||contentBean.getBingQuID().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getBingQuID());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "BingRenZYID");
                if(contentBean.getBingRenZYID()==null||contentBean.getBingRenZYID().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getBingRenZYID());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "BingRenXM");
                if(contentBean.getBingRenXM()==null||contentBean.getBingRenXM().length()<=0){
                    xmlSerializer.text("");
                }else{
                    xmlSerializer.text(contentBean.getBingRenXM());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "PanDuanBZ");
                xmlSerializer.text(contentBean.getPanDuanBZ());
                xmlSerializer.endTag("", "DC");
                xmlSerializer.endTag("", "DR");
            }

            xmlSerializer.endTag("", "DT");
            xmlSerializer.endTag("", "DS");
            xmlSerializer.endDocument();
            xmlResult = xmlWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (xmlWriter != null) {
                try {
                    xmlWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return xmlResult;
    }

    public static String createHuLiDanXml(List<JiChuXiangMuBean> allHuLiDanPageList, String tableName){
        String xmlResult = null;
        StringWriter xmlWriter = new StringWriter();
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            xmlSerializer.setOutput(xmlWriter);
            xmlSerializer.startDocument("UTF-16", null);
            xmlSerializer.startTag("", "DS");
            xmlSerializer.startTag("", "DT");
            xmlSerializer.attribute("", "Name", tableName);
            for(int i=0;i<allHuLiDanPageList.size();i++){
                JiChuXiangMuBean jiChuXiangMuBean=allHuLiDanPageList.get(i);
                xmlSerializer.startTag("", "DR");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "HuLiJiLuID");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getHuLiJiLuID())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getHuLiJiLuID());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "HuLiJiLuDanHao");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getHuLiJiLuDanHao())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getHuLiJiLuDanHao());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "JiChuXiangMuID");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getJiChuXiangMuID())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getJiChuXiangMuID());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "JiChuXiangMuMC");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getJiChuXiangMuMC())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getJiChuXiangMuMC());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "ShuZhi");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getShuZhi())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getShuZhi());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "JiLuSJ");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getJiLuSJ())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getJiLuSJ());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "DanWei");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getDanWei())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getDanWei());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "CaiJiRen");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getCaiJiRen())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getCaiJiRen());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "CaiJiRQ");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getCaiJiRQ())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getCaiJiRQ());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "CaiJiSJ");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getCaiJiSJ())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getCaiJiSJ());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "BingQuID");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getBingQuID())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getBingQuID());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "BingRenZYID");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getBingRenZYID())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getBingRenZYID());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "BingRenXM");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getBingRenXM())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getBingRenXM());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "ChuangHao");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getChuangHao())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getChuangHao());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "CaiJiRenID");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getCaiJiRenID())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getCaiJiRenID());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "HuanYeBZ");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getHuanYeBZ())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getHuanYeBZ());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.startTag("", "DC");
                xmlSerializer.attribute("", "Name", "PanDuanBZ");
                if(TextUtils.isEmpty(jiChuXiangMuBean.getPanDuanBZ())){
                    xmlSerializer.text("");
                }else {
                    xmlSerializer.text(jiChuXiangMuBean.getPanDuanBZ());
                }
                xmlSerializer.endTag("", "DC");

                xmlSerializer.endTag("", "DR");
            }
            xmlSerializer.endTag("", "DT");
            xmlSerializer.endTag("", "DS");
            xmlSerializer.endDocument();
            xmlResult = xmlWriter.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (xmlWriter != null) {
                try {
                    xmlWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return xmlResult;
    }
}
