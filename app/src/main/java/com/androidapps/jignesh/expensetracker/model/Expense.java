package com.androidapps.jignesh.expensetracker.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by developer on 4/4/2015.
 */
public class Expense extends RealmObject {
    @PrimaryKey
    private String id;
    public String  getId(){return id;}
    public void setId(String _id){this.id = _id;}

    private double amount;
    public double getAmount(){return amount;}
    public void setAmount(double amt){this.amount = amt;}

    private String category;
    public String getCategory(){return category;}
    public void setCategory(String cat){this.category = cat;}

    private String comment;
    public String getComment(){return comment;}
    public void setComment(String com){this.comment = com;}

    private Date created;
    public Date getCreated(){return created;}
    public void setCreated(Date crd){this.created = crd;}


}
