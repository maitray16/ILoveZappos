package com.example.maitrayshah.ilovezappos.utils;

import com.example.maitrayshah.ilovezappos.model.ProductDetails;


import java.util.List;

public interface ProductDataCallback {
    void productCallback(List<ProductDetails> searchResponse);
}
