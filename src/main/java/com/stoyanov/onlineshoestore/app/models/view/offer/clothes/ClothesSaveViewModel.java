package com.stoyanov.onlineshoestore.app.models.view.offer.clothes;

import com.stoyanov.onlineshoestore.app.enums.Condition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClothesSaveViewModel {

    private String id;
    private String categoryId;
    private String title;
    private String description;
    private BigDecimal price;
    private Condition condition;
    private List<MultipartFile> photos;
    private String matter;
}
