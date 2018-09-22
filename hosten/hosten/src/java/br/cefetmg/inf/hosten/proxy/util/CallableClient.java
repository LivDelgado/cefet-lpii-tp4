package br.cefetmg.inf.hosten.proxy.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class CallableClient implements Callable {
    private DatagramSocket clientSocket;
    private ArrayList lista;
    private InetAddress ServerIPAddress;

    private byte[][] outData;
    private byte[][] inData;


    public CallableClient() throws SocketException, UnknownHostException {
        clientSocket = new DatagramSocket();
        ServerIPAddress = getServerIP();
        lista = null;
    }

    public CallableClient(ArrayList lista) throws SocketException, UnknownHostException {
        clientSocket = new DatagramSocket();
        ServerIPAddress = getServerIP();
        this.lista = lista;
    }
    
    private InetAddress getServerIP () {
        InetAddress IPAddress = null;
        try {
            // AQUI É SETADO O ENDEREÇO IP DO SERVIDOR
            // CASO SEJA ALTERADO, MUDAR APENAS ESTA LINHA
            IPAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException ex) {
            System.err.println("Servidor não encontrado");
        }
        return IPAddress;
    }

    private void shutdown() {
        clientSocket.close();
    }

    @Override
    public Object call() throws Exception {
//        System.err.println("Client Started, Listening for Input:");

        while (true) {
            try {
                //
                // ENVIO DE PACOTES
                //
//                System.err.println("Preparando os pacotes para enviar...");
                outData = ProxyUtils.toByteArray(lista);
                
//                System.err.println("Enviando os pacotes para o server...");
                
                DatagramPacket pacoteNumPacotes = new DatagramPacket(outData[0], outData[0].length, ServerIPAddress, ProxyUtils.PORTA);
                clientSocket.send(pacoteNumPacotes);
                for (int i = 1; i <= outData[0][0]; i++) {
//                    System.out.println("Cliente enviando pacote " + i);
                    DatagramPacket DpSend = new DatagramPacket(outData[i], outData[i].length, ServerIPAddress, ProxyUtils.PORTA);
                    clientSocket.send(DpSend);
                }

                //
                // RECEBIMENTO DE PACOTES
                //
                byte [] numPacotesRecebidos = new byte[1];
                DatagramPacket receivedPacketNumberPackets = new DatagramPacket(numPacotesRecebidos, numPacotesRecebidos.length);
                clientSocket.receive(receivedPacketNumberPackets);
                
//                System.out.println("o cliente receberá " + numPacotesRecebidos[0] + " pacotes");
                
                inData = new byte[numPacotesRecebidos[0]][ProxyUtils.TAMANHO];
                int i;
                DatagramPacket [] pacotesRecebidos = new DatagramPacket[numPacotesRecebidos[0]];
                for (i = 0; i < numPacotesRecebidos[0]; i++) {
//                    System.out.println("Cliente recebendo pacote " + i);
                    pacotesRecebidos[i] = new DatagramPacket(inData[i],inData[i].length);
                    clientSocket.receive(pacotesRecebidos[i]);
                }
                
//                System.err.println(i + " pacotes recebidos do adapter!");

                ByteArrayOutputStream matrizArray = new ByteArrayOutputStream();
                for (i = 0; i < pacotesRecebidos.length; i++) {
                    matrizArray.write(pacotesRecebidos[i].getData());
                }
                byte[] vetorArray = matrizArray.toByteArray();
                ArrayList listaRecebida = (ArrayList)ProxyUtils.toObject(vetorArray);
                
                return listaRecebida;
            } catch (IOException e) {
                System.err.println("Exception Thrown: " + e.getLocalizedMessage());
            }
        }
    }
}
