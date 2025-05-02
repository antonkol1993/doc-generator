package com.antonio.generator.dto.input;

import com.antonio.generator.dto.InputDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultItem implements InputDto {
    private Integer itemNo;
    private String alterImagePath;

    private String originalName;
    private String alterNameRus;

    private String size;
    private String marking;
    private String quantityInBox;
    private String order;


    @Override
    public String toString() {
        return '{' +
                "\"itemNo\"=" + "\"" + itemNo + "\"" + " | " +
                "\"alterImagePath\"=" + "\"" + alterImagePath + "\"" + " | " +
                "\"originalName\"=" + "\"" + originalName + "\"" + " | " +
                "\"alterNameRus\"=" + "\"" + alterNameRus + "\"" + " | " +
                "\"size\"=" + "\"" + size + "\"" + " | " +
                "\"marking\"=" + "\"" + marking + "\"" + " | " +
                "\"quantityInBox\"=" + "\"" + quantityInBox + "\"" + " | " +
                "\"order\"=" + "\"" + order + "\"" +
                '}';
    }

    @Override
    public String getTypeString() {
        return this.getClass().toString();
    }
}
