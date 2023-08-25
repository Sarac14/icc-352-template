package org.example.servicios;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;


public class ServicioJWT {

    private static ServicioJWT instancia;

    public static ServicioJWT getInstancia(){
        if(instancia==null){
            instancia = new ServicioJWT();
        }
        return instancia;
    }

    public String generarToken(String username) {
        String token = JWT.create()
                .withSubject(username)
                .sign(Algorithm.HMAC256("tu_clave_secreta"));
        return token;
    }

    public boolean validarToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256("tu_clave_secreta"))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static final String SECRET_KEY = "12fgerm567457dfvae3";
    private static final long EXPIRATION_TIME = 86400000;

    public String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
