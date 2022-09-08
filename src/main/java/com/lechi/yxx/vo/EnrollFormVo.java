package com.lechi.yxx.vo;


import com.lechi.yxx.model.EnrollForm;
import com.lechi.yxx.model.StoreProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollFormVo implements Serializable {

    private EnrollForm enrollForm;

    private StoreProduct storeProduct;

}
