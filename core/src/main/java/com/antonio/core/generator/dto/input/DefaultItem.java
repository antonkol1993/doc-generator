package com.antonio.core.generator.dto.input;

import com.antonio.core.generator.dto.InputDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultItem implements InputDto {
    private Integer itemNo;
    private String alterImageName;

    private String originalName;
    private String alterNameRus;

    private String size;
    private String marking;
    private String quantityInBox;
    private String order;

    @Override
    public String toString() {
        return '{' +
                "\"itemNo\"=" + (itemNo != null ? "✅\"" + itemNo + "\"" : "❌null") + " | " +
                "\"alterImageName\"=" + (alterImageName != null && !alterImageName.isBlank() ?
                "✅\"" + alterImageName + "\"" : "⚠️\"\"") + " | " +
                "\"originalName\"=" + (originalName != null && !originalName.isBlank() ?
                "✅\"" + originalName + "\"" : "⚠️\"\"") + " | " +
                "\"alterNameRus\"=" + (alterNameRus != null && !alterNameRus.isBlank() ?
                "✅\"" + alterNameRus + "\"" : "⚠️\"\"") + " | " +
                "\"size\"=" + (size != null && !size.isBlank() ?
                "✅\"" + size + "\"" : "⚠️\"\"") + " | " +
                "\"marking\"=" + (marking != null && !marking.isBlank() ?
                "✅\"" + marking + "\"" : "⚠️\"\"") + " | " +
                "\"quantityInBox\"=" + (quantityInBox != null && !quantityInBox.isBlank() ?
                "✅\"" + quantityInBox + "\"" : "⚠️\"\"") + " | " +
                "\"order\"=" + (order != null && !order.isBlank() ?
                "✅\"" + order + "\"" : "⚠️\"\"") +
                '}';
    }


    @Override
    public String getTypeString() {
        return this.getClass().toString();
    }
}
