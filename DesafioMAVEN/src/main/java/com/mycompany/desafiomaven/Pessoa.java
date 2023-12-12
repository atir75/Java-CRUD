package com.mycompany.desafiomaven;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Gabriel
 */
public class Pessoa {
    private String id;
    private String nome;
    private String telefone;
    private String email;
    
    public void setId(String _id){id = _id;}
    public void setNome(String _nome){nome = _nome;}
    public void setTelefone(String _telefone){telefone = _telefone;}
    public void setEmail(String _email){email = _email;}
    
    public String getId() {return id;}
    public String getNome() {return nome;}
    public String getTelefone() {return telefone;}
    public String getEmail() {return email;}
    
}
