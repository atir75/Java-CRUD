package com.mycompany.desafiomaven;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Gabriel
 */
public interface ResultAsObject {
    public abstract <T extends ResultAsObject> ArrayList<T> GetResultAsObject(ResultSet rs, T obj);
}
