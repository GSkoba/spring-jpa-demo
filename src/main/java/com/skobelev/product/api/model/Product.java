package com.skobelev.product.api.model;

import com.skobelev.product.api.validations.SizeConstraint;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "PRODUCT")
@Data
public class Product {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PRODUCT_SEQ")
    @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    private Integer id;

    @Basic
    @Column(name = "NAME")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Basic
    @Column(name = "ISFEATURED")
    private boolean featured;

    @Basic
    @Column(name = "EXPIRATIONDATE")
    private Date expirationDate;

    @Basic
    @Column(name = "ITEMSINSTOCK")
    private int itemsInStock;

    @Basic
    @Column(name = "RECEIPTDATE")
    private Date receiptDate;

    @Basic
    @Column(name = "RATING")
    private double rating;

    @ManyToOne(targetEntity = Brand.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "BRANDID", foreignKey = @ForeignKey(name = "fk_brand"))
    private Brand brand;

    @SizeConstraint(message = "Product must have from 1 to 5 categories")
    @NotEmpty(message = "Categories in product is mandatory")
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "CATEGORY_PRODUCT",
            joinColumns = {
                    @JoinColumn(name = "PRODUCTID", referencedColumnName = "ID",
                            foreignKey = @ForeignKey(name = "fk_r_product_category"))},
            inverseJoinColumns = {
                    @JoinColumn(name = "CATEGORYID", referencedColumnName = "ID",
                            foreignKey = @ForeignKey(name = "fk_r_category_product"))
            })
    private Set<Category> categories;
}
