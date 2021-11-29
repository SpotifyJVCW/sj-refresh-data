package br.com.spotifyjvcw.domain.enums;

import lombok.Getter;

@Getter
public enum TermType {

    LONG("long_term"),
    MEDIUM("medium_term"),
    SHORT("short_term");

    private final String description;

    TermType(String description) {
        this.description = description;
    }
}
