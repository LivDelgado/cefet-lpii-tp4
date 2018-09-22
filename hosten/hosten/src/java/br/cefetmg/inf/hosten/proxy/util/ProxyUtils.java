package br.cefetmg.inf.hosten.proxy.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class ProxyUtils {
    public final static int PORTA = 1234;
    public final static int TAMANHO = 65535;

//    public static int getTamanhoVetorByte () {
//        return TAMANHO;
//    }
//
//    public static int getPorta () {
//        return PORTA;
//    }
    
    public static byte[][] toByteArray(Object obj) throws IOException {
        byte[] byteArray = null;
        byte[][] bytes = null;
        
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            byteArray = bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }

        int i = 1;
        int x = byteArray.length-1;
        int numeroVetores = (byteArray.length / TAMANHO)+1;

        if (byteArray.length > TAMANHO) {
            bytes = new byte[numeroVetores+1][];
            bytes[0] = new byte[1];
            while (i <= numeroVetores) {
                int j = 0;
                if (x >= TAMANHO) { bytes[i] = new byte[TAMANHO];} 
                 else {bytes[i] = new byte[x+1];}
                
                while(j < TAMANHO && x >= 0) {
                    bytes[i][j] = byteArray[(byteArray.length -1)- x];
                    j++; x--;
                }
                i++;
            }
        } else {
            bytes = new byte[2][];
            bytes[0] = new byte[1];
            bytes[1] = new byte[byteArray.length]; bytes[1] = byteArray;
        }
        bytes[0][0] = (byte) numeroVetores;
        return bytes;
    }
    
    public static Object toObject (byte [] bytes) throws IOException,
            ClassNotFoundException {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return obj;
    }
}
