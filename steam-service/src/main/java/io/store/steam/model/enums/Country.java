package io.store.steam.model.enums;

import lombok.Getter;

@Getter
public enum Country {
    UNITED_STATES("United States", "US"),
    UNITED_KINGDOM("United Kingdom", "GB"),
    JAPAN("Japan", "JP"),
    CANADA("Canada", "CA"),
    AUSTRALIA("Australia", "AU"),
    GERMANY("Germany", "DE"),
    FRANCE("France", "FR"),
    SOUTH_KOREA("South Korea", "KR"),
    CHINA("China", "CN"),
    SWEDEN("Sweden", "SE"),
    RUSSIA("Russia", "RU"),
    BRAZIL("Brazil", "BR"),
    POLAND("Poland", "PL"),
    SPAIN("Spain", "ES"),
    ITALY("Italy", "IT");

    private final String name;
    private final String code;

    Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static Country fromCode(String code) {
        for (Country country : Country.values()) {
            if (country.code.equals(code)) {
                return country;
            }
        }
        throw new IllegalArgumentException("Invalid country code: " + code);
    }

    public static Country fromName(String name) {
        for (Country country : Country.values()) {
            if (country.name.equalsIgnoreCase(name)) {
                return country;
            }
        }
        throw new IllegalArgumentException("Invalid country name: " + name);
    }
}
