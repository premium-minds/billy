/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy GIN.
 *
 * billy GIN is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy GIN is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy GIN. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.gin.services.export;

public class AddressData {

  private final String isoCountry;
  private final String details;
  private final String city;
  private final String region;
  private final String postalCode;

  public AddressData(String isoCountry, String details, String city, String region,
      String postalCode) {
    this.isoCountry = isoCountry;
    this.details = details;
    this.city = city;
    this.region = region;
    this.postalCode = postalCode;
  }

  public String getISOCountry() {
    return isoCountry;
  }

  public String getDetails() {
    return details;
  }

  public String getCity() {
    return city;
  }

  public String getRegion() {
    return region;
  }

  public String getPostalCode() {
    return postalCode;
  }

}