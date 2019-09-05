package cl.tuserver.calculadora;

import java.util.HashMap;

public class Usuario {
    private String username;
    private char[] passwd;
    private HashMap<Integer, String> historial;

    public Usuario(String username, char[] passwd){
        this.username = username;
        this.passwd = passwd;
        historial = new HashMap<>();
    }

    // GET
    public String getUsername(){
        return username;
    }
    public boolean isPasswd(char[] passwd){
        if(this.passwd.length != passwd.length) return false;
        for(int i = 0; i < passwd.length; i++)
            if(this.passwd[i] != passwd[i]) return false;
        return true;
    }
    public String getHistorial(int key){
        return historial.get(key);
    }
    public boolean historialContainsKey(int key){
        return historial.containsKey(key);
    }

    // SET
    public void setUsername(String username){
        this.username = username;
    }
    public void setPasswd(char[] passwd){
        this.passwd = passwd;
    }
    public void addHistorial(Integer key, String value){
        historial.put(key, value);
    }
}
