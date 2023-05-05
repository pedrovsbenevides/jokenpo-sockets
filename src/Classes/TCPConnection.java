import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

/**
 * TCPConnection
 */
public class TCPConnection extends Thread {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;

    // Conexao da Thread-Servidora com o socket do Cliente e
    // criacao dos fluxos de I/O. (inicio)
    public TCPConnection(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.in = new DataInputStream(clientSocket.getInputStream());
            this.out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }
    // Conexao da Thread-Servidora com o socket do Cliente e
    // criacao dos fluxos de I/O. (final)

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    // Conversa entre a Thread-Servidor com o Cliente. (inicio)
    public void run() {
        try {
            while (true) {
                String data = this.getIn().readUTF();
                System.out.println("Recebido o " + data);
                this.getOut().writeUTF("Resposta ao " + data);
            }
            // Conversa entre a Thread-Servidor com o Cliente. (final)

            // Tratamento de Excecoes. (inicio)
        } catch (EOFException e) {
            System.out.println("EOF: Conexao Encerrada");
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("IO:" + e.getMessage());
            }
        }
        // Tratamento de Excecoes. (final)
    }

}