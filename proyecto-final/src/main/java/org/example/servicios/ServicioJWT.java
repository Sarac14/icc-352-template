package org.example.servicios;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.entidades.Agente;

import java.security.Key;
import java.util.Date;


public class ServicioJWT {

    private static ServicioJWT instancia;

    public static ServicioJWT getInstancia(){
        if(instancia==null){
            instancia = new ServicioJWT();
        }
        return instancia;
    }

    private static final String SECRET_KEY = "unaLlaveSuperSecretaQueEsMuyMuyLargaYDificilDeAdivinar1234567890unaLlaveSuperSecretaQueEsMuyMuyLargaYDificilDeAdivinar1234567890";
    private static final long EXPIRATION_TIME = 3600000;

    public String createToken(Agente user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .claim("username", user.getUsuario())
                .claim("rol", user.getRol())
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
            System.out.println("Error al validar token: " + e.getMessage());
            return false;
        }
    }

}
