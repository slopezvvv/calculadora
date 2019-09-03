package cl.tuserver.calculadora;

public class Usuario {
    private String username;
    private char[] passwd;

    public Usuario(String username, char[] passwd){
        this.username = username;
        this.passwd = passwd;
    }

    public String getUsername(){
        return username;
    }

    public boolean isPasswd(char[] passwd){
        if(this.passwd.length != passwd.length) return false;
        for(int i = 0; i < passwd.length; i++)
            if(this.passwd[i] != passwd[i]) return false;
        return true;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setPasswd(char[] passwd){
        this.passwd = passwd;
    }
}
