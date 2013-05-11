package br.com.decarli.client;

import br.com.decarli.Mensagem;
import com.google.gson.Gson;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApp {
    public static void main(String[] args) {
        try {
            OutputStream saida;
            
            Socket socket = new Socket("localhost", 1234);

            //escrever para o servidor
            saida = socket.getOutputStream();
            PrintStream print = new PrintStream(saida);
            print.println("DATA_HORA");
            
            //ler do servidor
            InputStream in = socket.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));

            String resp = read.readLine();
            //imprime a resposta do servidor
            System.out.println("Resp: "+ resp);
            
            print.println("MSG");
            
            Mensagem m = new Mensagem();
            m.setData(new Date());
            m.setDestino("Juca");
            m.setMsg("Olá estou chegando...");
            
            Gson gson = new Gson();
            print.println(gson.toJson(m));

            
            //fechando as conexões
            print.close();
            saida.close();
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
