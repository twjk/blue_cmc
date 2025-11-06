package com.qcmz.cmc.entity;



/**
 * CmcTransExample entity. @author MyEclipse Persistence Tools
 */

public class CmcTransExample  implements java.io.Serializable {


    // Fields    

     private Long egid;
     private String transsrc;
     private String fromlang;
     private String src;
     private String tolang;
     private String dst;


    // Constructors

    /** default constructor */
    public CmcTransExample() {
    }

	/** minimal constructor */
    public CmcTransExample(String fromlang, String src, String tolang, String dst) {
        this.fromlang = fromlang;
        this.src = src;
        this.tolang = tolang;
        this.dst = dst;
    }
    
    /** full constructor */
    public CmcTransExample(String transsrc, String fromlang, String src, String tolang, String dst) {
        this.transsrc = transsrc;
        this.fromlang = fromlang;
        this.src = src;
        this.tolang = tolang;
        this.dst = dst;
    }

   
    // Property accessors

    public Long getEgid() {
        return this.egid;
    }
    
    public void setEgid(Long egid) {
        this.egid = egid;
    }

    public String getTranssrc() {
        return this.transsrc;
    }
    
    public void setTranssrc(String transsrc) {
        this.transsrc = transsrc;
    }

    public String getFromlang() {
        return this.fromlang;
    }
    
    public void setFromlang(String fromlang) {
        this.fromlang = fromlang;
    }

    public String getSrc() {
        return this.src;
    }
    
    public void setSrc(String src) {
        this.src = src;
    }

    public String getTolang() {
        return this.tolang;
    }
    
    public void setTolang(String tolang) {
        this.tolang = tolang;
    }

    public String getDst() {
        return this.dst;
    }
    
    public void setDst(String dst) {
        this.dst = dst;
    }
   








}