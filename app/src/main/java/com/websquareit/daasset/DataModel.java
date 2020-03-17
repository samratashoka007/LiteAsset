package com.websquareit.daasset;

public class DataModel {
    public String Date;
    public String assetNo;
    public String bladetype;
    public String boxtype;
    public String cmnt;
    public String eroneeight;
    public String eronefour;
    public String erthreesix;
    public String ertwoeight;

    /* renamed from: id */
    public String id;
    public String jumbo;
    public String location;
    public String property;
    public String quickfit;
    public String siteName;
    private int status;
    public String swingtype;
    public String twin;
    public String weatherlevel;
    public String batten;
    public String Emergancy;
    public String exitSign;



    public DataModel(String tempNumber, String tempSiteName, String tempassetno, String tempBatten,
                     String tempEmergancy, String tempExitSign, String tempProperty, String tempCmment, String tempLocation) {
        this.id=tempNumber;
        this.siteName=tempSiteName;
        this.assetNo=tempassetno;
        this.batten=tempBatten;
        this.Emergancy=tempEmergancy;
        this.exitSign=tempExitSign;
        this.property=tempProperty;
        this.cmnt=tempCmment;
        this.location=tempLocation;

    }

    public String getBatten() {
        return batten;
    }

    public void setBatten(String batten) {
        this.batten = batten;
    }

    public String getEmergancy() {
        return Emergancy;
    }

    public void setEmergancy(String emergancy) {
        Emergancy = emergancy;
    }

    public String getExitSign() {
        return exitSign;
    }

    public void setExitSign(String exitSign) {
        this.exitSign = exitSign;
    }



    public DataModel(String siteNameHolder2, String assetNumberHolder2, String batten, String emergancy, String exitsign, String locationHolder2, String propertyLevelHolder2, String cmntHolder, int status) {
        this.siteName = siteNameHolder2;
        this.assetNo = assetNumberHolder2;
        this.location = locationHolder2;
        this.status = status;
        this.cmnt = cmntHolder;
        this.batten=batten;
        this.Emergancy=emergancy;
        this.exitSign=exitsign;
        this.property=propertyLevelHolder2;
    }

    public String getDate() {
        return this.Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataModel() {
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status2) {
        this.status = status2;
    }


    public String getSiteName() {
        return this.siteName;
    }

    public void setSiteName(String siteName2) {
        this.siteName = siteName2;
    }

    public String getAssetNo() {
        return this.assetNo;
    }

    public void setAssetNo(String assetNo2) {
        this.assetNo = assetNo2;
    }

    public String getTwin() {
        return this.twin;
    }

    public void setTwin(String twin2) {
        this.twin = twin2;
    }

    public String getEronefour() {
        return this.eronefour;
    }

    public void setEronefour(String eronefour2) {
        this.eronefour = eronefour2;
    }

    public String getEroneeight() {
        return this.eroneeight;
    }

    public void setEroneeight(String eroneeight2) {
        this.eroneeight = eroneeight2;
    }

    public String getErtwoeight() {
        return this.ertwoeight;
    }

    public void setErtwoeight(String ertwoeight2) {
        this.ertwoeight = ertwoeight2;
    }

    public String getErthreesix() {
        return this.erthreesix;
    }

    public void setErthreesix(String erthreesix2) {
        this.erthreesix = erthreesix2;
    }

    public String getQuickfit() {
        return this.quickfit;
    }

    public void setQuickfit(String quickfit2) {
        this.quickfit = quickfit2;
    }

    public String getBoxtype() {
        return this.boxtype;
    }

    public void setBoxtype(String boxtype2) {
        this.boxtype = boxtype2;
    }

    public String getBladetype() {
        return this.bladetype;
    }

    public void setBladetype(String bladetype2) {
        this.bladetype = bladetype2;
    }

    public String getSwingtype() {
        return this.swingtype;
    }

    public void setSwingtype(String swingtype2) {
        this.swingtype = swingtype2;
    }

    public String getWeatherlevel() {
        return this.weatherlevel;
    }

    public void setWeatherlevel(String weatherlevel2) {
        this.weatherlevel = weatherlevel2;
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property2) {
        this.property = property2;
    }

    public String getCmnt() {
        return this.cmnt;
    }

    public void setCmnt(String cmnt2) {
        this.cmnt = cmnt2;
    }

    public String getJumbo() {
        return this.jumbo;
    }

    public void setJumbo(String jumbo2) {
        this.jumbo = jumbo2;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location2) {
        this.location = location2;
    }
}
