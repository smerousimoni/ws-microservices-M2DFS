package com.ecommerce.microcommerce.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProduitPrixEgaleZeroException extends RuntimeException {

    public ProduitPrixEgaleZeroException(String s) {
        super(s);
    }
}
