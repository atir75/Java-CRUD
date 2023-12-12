package com.mycompany.desafiomaven;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.ImageResult;
import com.google.maps.StaticMapsApi;
import com.google.maps.StaticMapsRequest;
import com.google.maps.StaticMapsRequest.Markers;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.Size;
import java.awt.Desktop;
import java.net.URI;
import java.sql.ResultSet;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author johnc
 */
public class Endereco implements ResultAsObject {

    private static GeoApiContext _geoCtx = new GeoApiContext.Builder().apiKey("AIzaSyD6UJ0eSUjizZy71FanYuNLuXtssUtF4_I").build();

    private LatLng _local;

    private String codigo;
    private String id;
    private String tipo;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String complemento;

    public Endereco() {
    }

    public void setCodigo(String _codigo) {
        codigo = _codigo;
    }

    public void setId(String _id) {
        id = _id;
    }

    public void setTipo(String _tipo) {
        tipo = _tipo;
    }

    public void setCep(String _cep) {
        cep = _cep;
    }

    public void setEstado(String _estado) {
        estado = _estado;
    }

    public void setCidade(String _cidade) {
        cidade = _cidade;
    }

    public void setBairro(String _bairro) {
        bairro = _bairro;
    }

    public void setRua(String _rua) {
        rua = _rua;
    }

    public void setNumero(String _numero) {
        numero = _numero;
    }

    public void setComplemento(String _complemento) {
        complemento = _complemento;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCep() {
        return cep;
    }

    public String getEstado() {
        return estado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public final void setLatLng() {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(_geoCtx, cep + ", numero: " + this.numero).await();
            this._local = results[0].geometry.location;
        } catch (Exception ex) {

        }
    }

    public byte[] getStaticImage() {
        try {
            StaticMapsRequest req = StaticMapsApi.newRequest(_geoCtx, new Size(500, 200));
            req.center(_local);
            req.maptype(StaticMapsRequest.StaticMapType.roadmap);
            Markers mk = new Markers();
            mk.addLocation(_local);
            mk.size(Markers.MarkersSize.small);
            req.markers(mk);
            req.zoom(17);
            req.format(StaticMapsRequest.ImageFormat.png);
            System.out.println(req.toString());
            ImageResult result = req.await();
            return result.imageData;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void Browser() {
        if (_local == null) {
            return;
        }

        Desktop desk = Desktop.getDesktop();

        try {
            desk.browse(new URI("https://maps.google.com/?q=" + _local.lat + "," + _local.lng));
        } catch (Exception ex) {
        }
    }

    @Override
    public <T extends ResultAsObject> ArrayList<T> GetResultAsObject(ResultSet rs, T obj) {
        ArrayList<T> ls = new ArrayList<T>();
        try {
            while (rs.next()) {
                Endereco u = new Endereco();
                u.setCodigo((rs.getString("codigo")));
                u.setId(rs.getString("id"));
                u.setTipo(rs.getString("tipo"));
                u.setCep(rs.getString("cep"));
                u.setEstado(rs.getString("estado"));
                u.setCidade(rs.getString("cidade"));
                u.setBairro(rs.getString("bairro"));
                u.setRua(rs.getString("rua"));
                u.setNumero(rs.getString("numero"));
                u.setComplemento(rs.getString("complemento"));
                ls.add((T) u);
            }
        } catch (Exception ex) {
            ex = ex;
        }
        return ls;
    }
}
