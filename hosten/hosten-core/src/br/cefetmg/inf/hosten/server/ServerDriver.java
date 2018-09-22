package br.cefetmg.inf.hosten.server;

import java.net.SocketException;
import java.util.Scanner;

public class ServerDriver {
    public static void main(String[] args) throws SocketException {
        Thread t = new Thread(new HostenServidor());
        t.start();
        Scanner entrada = new Scanner(System.in);
        
        if (entrada.hasNext()) {
            t.interrupt();
            System.err.println("Conexão com o servidor encerrada.");
            System.exit(0);
        }
    }
}
