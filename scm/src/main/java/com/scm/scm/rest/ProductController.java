package com.scm.scm.rest;

import com.scm.scm.util.ProductDataUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductDataUtil productDataUtil;

    public ProductController(ProductDataUtil productDataUtil) {
        this.productDataUtil = productDataUtil;
    }
}
