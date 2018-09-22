package br.cefetmg.inf.hosten.server;

import br.cefetmg.inf.hosten.adapter.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class HostenServidor implements Runnable {

    private DatagramSocket serverSocket;

    private byte[] numPacotesRecebidos;
    private byte[][] in;
    private byte[][] out;

    public HostenServidor() throws SocketException {
        serverSocket = new DatagramSocket(ServerUtils.PORTA);
    }

    public void run() {
        System.err.println("SERVER ON");

        while (true) {
            try {
                numPacotesRecebidos = new byte[1];
                
                DatagramPacket receivedPacketNumberPackets = new DatagramPacket(numPacotesRecebidos, numPacotesRecebidos.length);
                serverSocket.receive(receivedPacketNumberPackets);
//                System.out.println(numPacotesRecebidos[0] + " pacotes a serem recebidos pelo servidor");
                
                in = new byte[numPacotesRecebidos[0]][ServerUtils.TAMANHO];
                int i;
                DatagramPacket [] pacotesRecebidos = new DatagramPacket[numPacotesRecebidos[0]];
                for (i = 0; i < numPacotesRecebidos[0]; i++) {
                    pacotesRecebidos[i] = new DatagramPacket(in[i],in[i].length);
                    serverSocket.receive(pacotesRecebidos[i]);
//                    System.out.println("Servidor recebeu pacote " + (i+1));
                }
                
//                System.err.println(i + " pacotes recebidos do cliente!");

                ByteArrayOutputStream matrizArray = new ByteArrayOutputStream();
                for (DatagramPacket pacoteRecebido1 : pacotesRecebidos) {
                    matrizArray.write(pacoteRecebido1.getData());
                }
                byte[] vetorArray = matrizArray.toByteArray();
                ArrayList listaRecebida = (ArrayList) ServerUtils.toObject(vetorArray);

                String tipoObjeto = (String) listaRecebida.get(0);

                Thread t = null;

                switch (tipoObjeto) {
                    case "Despesa": 
                        ControlarDespesasAdapter controlarDespesasAdapter 
                                = new ControlarDespesasAdapter(
                                        serverSocket, pacotesRecebidos);
                        t = new Thread(controlarDespesasAdapter);
                        t.start();
                        break;
                    case "Hospedagem":
                        ControlarHospedagemAdapter controlarHospedagemAdapter 
                                = new ControlarHospedagemAdapter(
                                        serverSocket, pacotesRecebidos);
                        t = new Thread(controlarHospedagemAdapter);
                        t.start();
                        break;
                    case "ItemConforto":
                        ManterItemConfortoAdapter itemConfortoAdapter = new ManterItemConfortoAdapter(serverSocket, pacotesRecebidos);
                        t = new Thread(itemConfortoAdapter);
                        t.start();
                        break;
                    case "Cargo":
                        ManterCargoAdapter cargoAdapter = new ManterCargoAdapter(serverSocket, pacotesRecebidos);
                        t = new Thread(cargoAdapter);
                        t.start();
                        break;
                    case "CategoriaQuarto":
                        ManterCategoriaQuartoAdapter categoriaAdapter = new ManterCategoriaQuartoAdapter(serverSocket, pacotesRecebidos);
                        t = new Thread(categoriaAdapter);
                        t.start();
                        break;
                    case "Hospede":
                        ManterHospedeAdapter hospedeAdapter = new ManterHospedeAdapter(serverSocket, pacotesRecebidos);
                        t = new Thread(hospedeAdapter);
                        t.start();
                        break;
                    case "Quarto":
                        ManterQuartoAdapter quartoAdapter = new ManterQuartoAdapter(serverSocket, pacotesRecebidos);
                        t = new Thread(quartoAdapter);
                        t.start();
                        break;
                    case "ServicoArea":
                        ManterServicoAreaAdapter servicoAreaAdapter = new ManterServicoAreaAdapter(serverSocket, pacotesRecebidos);
                        t = new Thread(servicoAreaAdapter);
                        t.start();
                        break;
                    case "Servico":
                        ManterServicoAdapter servicoAdapter = new ManterServicoAdapter(serverSocket, pacotesRecebidos);
                        t = new Thread(servicoAdapter);
                        t.start();
                        break;
                    case "Usuario":
                        ManterUsuarioAdapter usuarioAdapter = new ManterUsuarioAdapter(serverSocket, pacotesRecebidos);
                        t = new Thread(usuarioAdapter);
                        t.start();
                        break;
                    default:
                        break;
                }

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Exceção: " + e.getLocalizedMessage());
            }
        }
    }
}
