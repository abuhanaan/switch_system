package com.example.demo.models.constants;

public enum BillerEnum {
  MTN("MTN"),
  AIRTEL("AIRTEL"),
  GLO("GLO"),
  NINEMOBILE("9mobile"),
  SMILE("SMILE"),
  SPECTRANET("SPECTRANET"),
  EKO_ELECTRIC("Eko Electric"),
  IKEJA_ELECTRIC("Ikeja Electric"),
  IBEDC("IBEDC"),
  DSTV("DSTV"),
  GOTV("GOTV"),
  STARTIMES("STARTIMES");

  private final String displayName;

  BillerEnum(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
