package com.mycompany.desafiomaven;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johnc
 */

import java.sql.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;

public class AFDAL {
    private static Connection con;
    
     public static void conecta(String _bd)
    {
        Erro.setErro(false);
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection("jdbc:ucanaccess://" + _bd);
            System.out.println("conectou");
        }
        catch (Exception e)
        {
            Erro.setErro(true,e.getMessage());
        }
    }
     
     public static void desconecta()
    {
        Erro.setErro(false);
        try 
        {
            con.close();
        }
        catch (Exception e)
        {
            Erro.setErro(true,e.getMessage());
        }
    }
     
     public static void executeSQL(String _sql)
    {
        Erro.setErro(false);
        try 
        {
            Statement st = con.createStatement();
            st.executeUpdate(_sql);
            st.close();
        }
        catch(Exception e)
        {
            Erro.setErro(true,e.getMessage());
        }
    }
    
     public static void geraClasse(String _tabela, String _package)
    {
        File arquivo = new File(_tabela + ".java");
        ResultSet rs;
        ResultSetMetaData rsmd;
        
        conecta("Enderecos.mdb");
        Erro.setErro(false);
        try 
        {
            Statement st = con.createStatement();
            rs = st.executeQuery("Select * from " + _tabela);
            rsmd = rs.getMetaData();
            String aux;
            
            FileWriter fw = new FileWriter(arquivo);
            fw.write("package " + _package + ";\n\n");
            fw.write("public class " + _tabela + " {\n\n");
            fw.write("public " + _tabela + "() {}\n\n");
            for (int i = 1; i <= rsmd.getColumnCount(); ++i)
            {
                aux = rsmd.getColumnName(i);
                fw.write("private String " + aux + ";\n");
                fw.write("public void set" + aux.substring(0,1).toUpperCase() + aux.substring(1) + "(String _" + aux + ") { " + aux + " = _" + aux + "; }\n");
                fw.write("public String get" + aux.substring(0,1).toUpperCase() + aux.substring(1) + "() { return " + aux + "; }\n\n");
            }
            fw.write("}\n");
            fw.flush();
            fw.close();
            st.close();
        }
        catch(Exception e)
        {
            Erro.setErro(true,e.getMessage());
        }
        desconecta();
    }
    
    public static void executeSelect(String _sql, Object obj)
    {
        ResultSet rs;
        Erro.setErro(false);
        try
        {
            PreparedStatement st = con.prepareStatement(_sql);
            rs = st.executeQuery();
            if (rs.next())
            {
                Field[] f  = obj.getClass().getDeclaredFields();
                Method mtd;
                String aux;
                for(int i=0; i<f.length; ++i)
                {
                    aux="set"+f[i].getName().substring( 0, 1 ).toUpperCase() + f[i].getName().substring( 1 );
                    try 
                    {
                        mtd = obj.getClass().getMethod(aux, new Class[] {f[i].getType()});
                        mtd.invoke(obj, new Object[] {rs.getString(f[i].getName())});
                    }
                    catch(Exception e){}
                }
            }
            else
            {
                Erro.setErro(true,obj.getClass().getSimpleName() + " não localizado."); return; 
            }
            st.close();
        }
        catch (Exception e)
        {
            Erro.setErro(true,e.getMessage());
        }
    }
    
    public static <T extends ResultAsObject> ArrayList<T> executeSelect(String _sql, T obj) {
        ResultSet rs;
        Erro.setErro(false);
        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(_sql);
            ArrayList<T> result = obj.GetResultAsObject(rs, obj);
            st.close();
            return result;
        } catch (Exception e) {
            System.out.print("executeSelect" + e.getMessage());
            return null;
        }
    }

}
