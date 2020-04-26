package com.homework.superblog.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Address {
  
  private String streetAddress;
  private String city;
  private String country;

  public Address(final String streetAddress,
                  final String city,
                  final String country) {
    
    this.streetAddress = streetAddress;
    this.city = city;
    this.country = country;
  }
}
