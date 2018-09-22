package br.cefetmg.inf.hosten.adapter;

import br.cefetmg.inf.hosten.model.domain.Usuario;
import br.cefetmg.inf.hosten.model.service.IManterUsuario;
import br.cefetmg.inf.hosten.model.service.impl.ManterUsuario;
import java.util.ArrayList;
import br.cefetmg.inf.hosten.server.ServerUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ManterUsuarioAdapter implements Runnable {

    private DatagramSocket socket;
    private DatagramPacket [] pacotesRecebidos;

    private final ArrayList listaRecebida;
    private Object objEnviado;
    private String tipoRetorno;

    public ManterUsuarioAdapter(DatagramSocket socket, DatagramPacket [] pacotesRecebidos) throws IOException, ClassNotFoundException {
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

            IManterUsuario manterUsuario = new ManterUsuario();

            switch (operacao) {
                case "Inserir": {
                    tipoRetorno = "Boolean";
                    Usuario itemInserir = (Usuario) listaRecebida.get(2);
                    objEnviado = manterUsuario.inserir(itemInserir);
                    
                    break;
                }
                case "Listar": {
                    tipoRetorno = "List<Usuario>";
                    String dadoBusca = (String) listaRecebida.get(2);
                    String coluna = (String) listaRecebida.get(3);
                    objEnviado = manterUsuario.listar(dadoBusca, coluna);
                    
                    break;
                }
                case "ListarTodos": {
                    tipoRetorno = "List<Usuario>";
                    objEnviado = manterUsuario.listarTodos();
                    
                    break;
                }
                case "Alterar": {
                    tipoRetorno = "Boolean";
                    String codRegistro = (String) listaRecebida.get(2);
                    Usuario itemAlterar = (Usuario) listaRecebida.get(3);
                    objEnviado = manterUsuario.alterar(codRegistro, itemAlterar);
                    
                    break;
                }
                case "Excluir": {
                    tipoRetorno = "Boolean";
                    String codRegistro = (String) listaRecebida.get(2);
                    objEnviado = manterUsuario.excluir(codRegistro);
                    
                    break;
                }   
                case "Login": {
                    tipoRetorno = "Usuario";
                    String email = (String) listaRecebida.get(2);
                    String senha = (String) listaRecebida.get(3);
                    objEnviado = manterUsuario.usuarioLogin(email, senha);
                    
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
