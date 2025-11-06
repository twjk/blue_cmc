package com.qcmz.cmc.entity;

import com.qcmz.framework.util.log.BeanDesc;



/**
 * CmcFeeProduct entity. @author MyEclipse Persistence Tools
 */

public class CmcFeeProduct  implements java.io.Serializable {


    // Fields
	 @BeanDesc(desc="编号")
     private Long productid;
     @BeanDesc(desc="名称")
     private String productname;
     @BeanDesc(desc="代码")
     private String productcode;
     @BeanDesc(desc="类型")
     private String producttype;
     @BeanDesc(desc="点数")
     private Integer point;
     @BeanDesc(desc="价格")
     private Double price;
     @BeanDesc(desc="适用范围")
     private String productrange;
     @BeanDesc(desc="描述")
     private String description;
     @BeanDesc(desc="排序")
     private Long sortindex;
     @BeanDesc(desc="状态")
     private Integer status;


    // Constructors

    /** default constructor */
    public CmcFeeProduct() {
    }

    // Property accessors

    public Long getProductid() {
        return this.productid;
    }
    
    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return this.productname;
    }
    
    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductcode() {
        return this.productcode;
    }
    
    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public Integer getPoint() {
        return this.point;
    }
    
    public void setPoint(Integer point) {
        this.point = point;
    }

    public Double getPrice() {
        return this.price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }

	public String getProductrange() {
		return productrange;
	}

	public void setProductrange(String productrange) {
		this.productrange = productrange;
	}

	public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

	public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

	public Long getSortindex() {
		return sortindex;
	}

	public void setSortindex(Long sortindex) {
		this.sortindex = sortindex;
	}
}