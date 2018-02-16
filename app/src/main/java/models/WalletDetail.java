package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletDetail {

@SerializedName("updatedAt")
@Expose
private String updatedAt;
@SerializedName("createdAt")
@Expose
private String createdAt;
@SerializedName("transactionType")
@Expose
private String transactionType;
@SerializedName("date")
@Expose
private Long date;
@SerializedName("amount")
@Expose
private Integer amount;
@SerializedName("userId")
@Expose
private String userId;
@SerializedName("__v")
@Expose
private Integer v;
@SerializedName("id")
@Expose
private String id;

public String getUpdatedAt() {
return updatedAt;
}

public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public String getTransactionType() {
return transactionType;
}

public void setTransactionType(String transactionType) {
this.transactionType = transactionType;
}

public Long getDate() {
return date;
}

public void setDate(Long date) {
this.date = date;
}

public Integer getAmount() {
return amount;
}

public void setAmount(Integer amount) {
this.amount = amount;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public Integer getV() {
return v;
}

public void setV(Integer v) {
this.v = v;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

}