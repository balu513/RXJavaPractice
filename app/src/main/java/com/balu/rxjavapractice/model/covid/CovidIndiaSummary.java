package com.balu.rxjavapractice.model.covid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CovidIndiaSummary {

@SerializedName("sno")
@Expose
private String sno;
@SerializedName("state_name")
@Expose
private String stateName;
@SerializedName("positive")
@Expose
private String positive;
@SerializedName("cured")
@Expose
private String cured;
@SerializedName("death")
@Expose
private String death;
@SerializedName("state_code")
@Expose
private String stateCode;

public String getSno() {
return sno;
}

public void setSno(String sno) {
this.sno = sno;
}

public String getStateName() {
return stateName;
}

public void setStateName(String stateName) {
this.stateName = stateName;
}

public String getPositive() {
return positive;
}

public void setPositive(String positive) {
this.positive = positive;
}

public String getCured() {
return cured;
}

public void setCured(String cured) {
this.cured = cured;
}

public String getDeath() {
return death;
}

public void setDeath(String death) {
this.death = death;
}

public String getStateCode() {
return stateCode;
}

public void setStateCode(String stateCode) {
this.stateCode = stateCode;
}

}