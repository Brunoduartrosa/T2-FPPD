import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class AdminServer extends UnicastRemoteObject implements ContaInterface {
    private Map<String, Conta> contas;

    public AdminServer() throws RemoteException {
        contas = new HashMap<String, Conta>();
    }

    public synchronized boolean abrirConta(String nome) throws RemoteException {
        if (contas.containsKey(nome)) {
            contas.put(nome, new Conta(nome));
            return true;
        }
        return false;
    }

    public synchronized boolean fecharConta(String nome) throws RemoteException {
        return contas.remove(nome) != null;
    }

    public synchronized double consultarSaldo(String nome) throws RemoteException {
        Conta conta = contas.get(nome);
        if (conta != null) {
            return conta.getSaldo();
        }
        return -1.0;
    }

    public synchronized boolean depositar(String nome, double valor) throws RemoteException {
        Conta conta = contas.get(nome);
        if (conta != null) {
            return conta.depositar(valor);
        }
        return false;
    }

    public synchronized boolean sacar(String nome, double valor) throws RemoteException {
        Conta conta = contas.get(nome);
        if (conta != null) {
            return conta.sacar(valor);
        }
        return false;
    }

    public static void main(String[] args) {
        try{
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            AdminServer server = new AdminServer();
            Naming.rebind("Banco", server);
            System.out.println("Servidor de Administração pronto.");
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
