package com.amazon.product.exceptions;
public class UserNotFoundException extends Exception{
    public UserNotFoundException(){
        super("User Not Found in Database");
    }
}