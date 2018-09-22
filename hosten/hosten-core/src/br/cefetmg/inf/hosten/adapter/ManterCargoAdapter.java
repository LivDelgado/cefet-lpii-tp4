package br.cefetmg.inf.hosten.adapter;

import br.cefetmg.inf.hosten.model.domain.Cargo;
import br.cefetmg.inf.hosten.model.service.IManterCargo;
import br.cefetmg.inf.hosten.model.service.impl.ManterCargo;
import java.util.ArrayList;
import java.util.List;
import br.cefetmg.inf.hosten.server.ServerUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ManterCargoAdapter implements Runnable {

    private final DatagramSocket socket;
    private final DatagramPacket [] pacotesRecebidos;


    private final ArrayList listaRecebida;
    private Object objEnviado;
    private String tipoRetorno;

    public ManterCargoAdapter(DatagramSocket socket, DatagramPacket [] pacotesRecebidos) throws IOException, ClassNotFoundException {
        this.socket = socket;
        this.pacotesRecebidos = pacotesRecebidos;

        ByteArrayOutputStream matrizArray = new ByteArrayOutputStream();
        for (DatagramPacket pacotesRecebidos1 : pacotesRecebidos) {
            matrizArray.write(pacotesRecebidos1.getData());
        }
        byte[] vetorArray = matrizArray.toByteArray();

        listaRecebida = (ArrayList) ServerUtils.toObject(vetorArray);
    }

    private void operacao() {
//        System.err.println("Iniciando operação no adapter...");

        String operacao = (String) listaRecebida.get(1);

        try {

            IManterCargo manterCargo = new ManterCargo();

            switch (operacao) {
                case "Inserir": {
                    tipoRetorno = "Boolean";
                    Cargo itemInserir = (Cargo) listaRecebida.get(2);
                    List<String> listaProgramas 
                            = (List<String>)listaRecebida.get(3);
                    
                    objEnviado = manterCargo.inserir(
                            itemInserir, 
                            listaProgramas);
                    
                    break;
                }
                case "Listar": {
                    tipoRetorno = "List<Cargo>";
                    String dadoBusca = (String) listaRecebida.get(2);
                    String coluna = (String) listaRecebida.get(3);
                    objEnviado = manterCargo.listar(dadoBusca, coluna);
                    
                    break;
                }
                case "ListarTodos": {
                    tipoRetorno = "List<Cargo>";
                    objEnviado = manterCargo.listarTodos();
                    
                    break;
                }
                case "ListarProgramasRelacionados": {
                    tipoRetorno = "List<Programa>";
                    String codCargo = (String) listaRecebida.get(2);
                    objEnviado = manterCargo.listarProgramasRelacionados(codCargo);
                    
                    break;
                }
                case "ListarTodosProgramas": {
                    tipoRetorno = "List<Programa>";
                    objEnviado = manterCargo.listarTodosProgramas();
                    
                    break;
                }
                case "Alterar": {
                    tipoRetorno = "Boolean";
                    String codRegistro = (String) listaRecebida.get(2);
                    Cargo itemAlterar = (Cargo) listaRecebida.get(3);
                    List<String> listaProgramas 
                            = (List<String>) listaRecebida.get(4);
                    
                    objEnviado = manterCargo.alterar(
                            codRegistro, 
                            itemAlterar, 
                            listaProgramas);
                    
                    break;
                }
                case "Excluir": {
                    tipoRetorno = "Boolean";
                    String codRegistro = (String) listaRecebida.get(2);
                    objEnviado = manterCargo.excluir(codRegistro);
                    
                    break;
                }
            }
        } catch (Exception ex) {
            tipoRetorno = "Exception";
            objEnviado = ex;
        }
    }

    @Override
    public void run() {
        operacao();

        try {
            ArrayList listaEnviada = new ArrayList();
            listaEnviada.add(tipoRetorno);
            listaEnviada.add(objEnviado);

//            byte[] out = new byte[ServerUtils.TAMANHO];
//            out = ServerUtils.toByteArray(listaEnviada);
//
//            System.out.println("Pacote de retorno montado!");
//
//            InetAddress IPAddress = pacotesRecebidos.getAddress();
//            int port = pacotesRecebidos.getPort();
//            DatagramPacket sendPacket = new DatagramPacket(out, out.length, IPAddress, port);
//
//            System.out.println("pacote de retorno enviado!!");
//            socket.send(sendPacket);
//            System.out.println("Pacotes de retorno sendo montados!");
            
            byte [][] out = ServerUtils.toByteArray(listaEnviada);

            InetAddress IPAddress = pacotesRecebidos[0].getAddress();
            int port = pacotesRecebidos[0].getPort();

//            System.out.println("Serão enviados " + out[0][0] + " pacotes de retorno");
            DatagramPacket pacoteNumPacotes = new DatagramPacket(out[0], out[0].length, IPAddress, port);
            socket.send(pacoteNumPacotes);
            for (int i = 1; i <= out[0][0]; i++) {
//                System.out.println("enviando pacote " + i);
                DatagramPacket DpSend = new DatagramPacket(out[i], out[i].length, IPAddress, port);
                socket.send(DpSend);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
