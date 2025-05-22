package com.antonio.core.generator.dto.output;

import com.antonio.core.generator.dto.OutputDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelLargeBox implements OutputDto<LabelLargeBox> {
    private String keyName;
    private Integer itemNo;
    private String imageName;
    private String nameRus;
    private String size;
    private String marking;
    private String quantityInBox;
    private String order;
    private String nameAndSize;

    @Override
    public String toString() {
        return keyName + " | " +
                itemNo + " | " +
                imageName + " | " +
                nameRus + " | " +
                size + " | " +
                marking + " | " +
                quantityInBox + " | " +
                order + " | ";
    }

    @Override
    public String getTypeString() {
        return this.getClass().toString();
    }
}
