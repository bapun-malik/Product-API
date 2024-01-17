package com.amazon.product.exceptions;

public class FileNameContainsSpace extends RuntimeException{
    public FileNameContainsSpace(){
        super("File name shouldn't have space or File not given");
    }
}
