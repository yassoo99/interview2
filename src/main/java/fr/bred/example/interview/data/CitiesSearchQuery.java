package fr.bred.example.interview.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class CitiesSearchQuery {

    String namePattern;
    String zipCodePattern;

    Integer limit;
    Integer start;
    String sort;
    String order;
}
